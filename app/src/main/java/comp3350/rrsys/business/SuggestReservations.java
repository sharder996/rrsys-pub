package comp3350.rrsys.business;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.persistence.DataAccess;

public class SuggestReservations {

    // return an array of suggested reservations in order
    // which has the same "length" as (endTime-startTime), which is rounded to integer multiple of 15 minutes (an inteval)
    // and start within 30 minutes (2 intervals) of the startTime (rouding to closest interval)
    public static ArrayList<Reservation> suggest(DataAccess dataAccess, DateTime startTime, DateTime endTime, int numPeople)
    {
        ArrayList<Reservation> results = new ArrayList<>();
        int index = getIndex(startTime); // the index of interval in the business hours of a day for start time (after rouding)
        int totalIncrement = (startTime.getPeriod(endTime)+7)/15; // total num of increments (+7 for rounding to the closest number of intervals)
        int maxIndex = Table.INTERVALS_PER_DAY; // max index (the number of 15-minutes intervals in the business hours of a day)

        ArrayList<Table> tables = new ArrayList<>();
        ArrayList<Reservation> reservations = new ArrayList<>();
        dataAccess.getTableSequential(tables);
        dataAccess.getReservationSequential(reservations);

        Table table;
        for(int t = 0; t < tables.size(); t++)
        {
            table = tables.get(t);
            // check if the table can hold numPeople
            if(table.getCapacity() >= numPeople)
            {
                // the available array of table with tableID t+1 at the day defined by (year, month, date) of input startTime
                // available.length == Table.INTERVALS_PER_DAY, each index represents a 15-minutes interval
                // each boolean represents whether the table is available in the corresponding 15-minutes interval
                boolean[] available = getAvailable(t+1, startTime, reservations);

                // within +- 2 intervals (half hour) of the start time
                int i = Math.max(index-2, 0);
                while(i <= index + 2 && i < maxIndex)
                {
                    // obtain the first available index/interval of the table
                    while(i <= index + 2 && i < maxIndex && !available[i])
                        i++;

                    // check if the first available index is within +- half hours of the index of startTime
                    if(i <= index + 2 && i < maxIndex)
                    {
                        int numIncrement = 1; // the number of consecutive available intervals
                        // count the number of consecutive available intervals
                        for(int time = i + 1; time < i + totalIncrement; time++)
                        {
                            if(time < maxIndex && available[time])
                                numIncrement++;
                            else
                                break;
                        }

                        // check if numIncrement (length of consecutive available intervals) == totalIncrement (length of endTime-startTime)
                        // if so, i will be the index of startTime of suggested reservation
                        // i+numIncrement will be the index of endTime of suggested reservation since it has the same length
                        if(numIncrement == totalIncrement)
                        {
                            // obtain the DateTime which has same (year, month, day) as startTime, and (hour, minute) corresponding to index i
                            DateTime start = getDateTime(startTime, i); // startTime of suggested reservation
                            DateTime end = getDateTime(endTime, i+numIncrement); // endTime of suggested reservation

                            // ordered insert the reservation to results
                            //ordered first by how close to the input startTime
                            // if same, ordered secondly by how close the table capacity to number of people
                            // if still same, ordered thirdly by table ID in ascending order

                            orderedInsert(results, new Reservation(table.getTID(), numPeople, start, end), startTime);
                        }
                    }
                    i++; // check for next index i
                }
            }
        }
        return results;
    }

    // return an available array of a table with input TID at the date of input time
    // the array of 15-minutes increment in the business hour of a day
    private static boolean[] getAvailable(int TID, DateTime time, ArrayList<Reservation> reservations)
    {
        boolean[] available = new boolean[Table.INTERVALS_PER_DAY];
        for(int i = 0; i < available.length; i++)
            available[i] = true;
        for(int i = 0; i < reservations.size(); i++)
        {
            if(reservations.get(i).getTID() == TID && reservations.get(i).getStartTime().getYear() == time.getYear() && reservations.get(i).getStartTime().getMonth() == time.getMonth()
                    && reservations.get(i).getStartTime().getDate() == time.getDate()) {
                int startIndex = getIndex(reservations.get(i).getStartTime());
                int endIndex = getIndex(reservations.get(i).getEndTime());
                for(int j = startIndex; j < endIndex; j++)
                    available[j] = false;
            }
        }
        return available;
    }

    // ordered insert a suggested reservation into a temp array
    // ordered first by how close to the startTime
    // if same, ordered secondly by table ID in ascending order
    private static void orderedInsert(ArrayList<Reservation> results, Reservation r, DateTime t)
    {
        int pos = 0;
        int max = results.size();
        while(pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) < Math.abs(r.getStartTime().getPeriod(t)))
            pos++;
        while(pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) == Math.abs(r.getStartTime().getPeriod(t)) && results.get(pos).getTID() < r.getTID())
            pos++;
        results.add(pos, r);
    }

    // return the index corresponding to a date time
    // the closest index of the 15-minutes increment/interval in the business hour of a day
    private static int getIndex(DateTime time)
    {
        return (time.getHour()-Table.START_TIME)*4 + (time.getMinutes()+7)/15;
    }

    // return the date time corresponding to an index
    // set same year, month, date with input date time, set hour and minute transferred from index
    private static DateTime getDateTime(DateTime time, int index)
    {
        DateTime result = null;
        try
        {
            result = new DateTime(new GregorianCalendar(time.getYear(), time.getMonth(), time.getDate(), Table.START_TIME + index / 4, index % 4 * 15));
        }
        catch(IllegalArgumentException pe)
        {
            System.out.println(pe);
        }
        return result;
    }
}
