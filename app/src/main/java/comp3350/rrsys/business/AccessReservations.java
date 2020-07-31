package comp3350.rrsys.business;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.persistence.DataAccess;

public class AccessReservations
{
    private DataAccess dataAccess;

    public AccessReservations() { dataAccess = Services.getDataAccess(Main.dbName); }

    public AccessReservations(DataAccess altDataAccessService) { dataAccess = Services.createDataAccess(altDataAccessService); }

    public String getReservations(ArrayList<Reservation> reservations)
    {
        reservations.clear();
        return dataAccess.getReservationSequential(reservations);
    }

    public Reservation getRandom(int reservationID) { return dataAccess.getReservation(reservationID); }

    public int getNextReservationID() { return  dataAccess.getNextReservationID(); }

    public String insertReservation(Reservation currentReservation) { return dataAccess.insertReservation(currentReservation); }

    public String updateReservation(Reservation newReservation) { return dataAccess.updateReservation(newReservation.getRID(), newReservation); }

    public String deleteReservation(int reservationID) { return dataAccess.deleteReservation(reservationID); }

    // return an array of suggested reservations in order
    // which has the same "length" as (endTime-startTime), which is rounded to integer multiple of 15 minutes (an inteval)
    // and start within 30 minutes (2 intervals) of the startTime (rouding to closest interval)
    public ArrayList<Reservation> suggestReservations(DateTime startTime, DateTime endTime, int numPeople)
    {
        ArrayList<Reservation> results = new ArrayList<>();
        int index = getIndex(startTime); // the index of interval in the business hours of a day for start time (after rouding)
        int totalIncrement = (startTime.getPeriod(endTime)+7)/15; // total num of increments (+7 for rounding to the closest number of intervals)
        int maxIndex = Table.INTERVALS_PER_DAY; // max index (the number of 15-minutes intervals in the business hours of a day)
        ArrayList<Table> tables = new ArrayList<>();
        dataAccess.getTableSequential(tables);

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
                boolean[] available = dataAccess.getAvailable(t+1, startTime);

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
                            DateTime start = dataAccess.getDateTime(startTime, i); // startTime of suggested reservation
                            DateTime end = dataAccess.getDateTime(endTime, i+numIncrement); // endTime of suggested reservation

                            // ordered insert the reservation to results
                            //ordered first by how close to the input startTime
                            // if same, ordered secondly by how close the table capacity to number of people
                            // if still same, ordered thirdly by table ID in ascending order
                            dataAccess.orderedInsert(results, new Reservation(table.getTID(), numPeople, start, end), startTime);
                        }
                    }
                    i++; // check for next index i
                }
            }
        }
        return results;
    }

    // return the index corresponding to a date time
    // the closest index of the 15-minutes increment/interval in the business hour of a day
    public static int getIndex(DateTime time) { return (time.getHour()-Table.START_TIME)*4 + (time.getMinutes()+7)/15; }
}