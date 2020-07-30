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

    public AccessReservations()
    {
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    public AccessReservations(DataAccess altDataAccessService) { dataAccess = Services.createDataAccess(altDataAccessService); }

    public String getReservations(ArrayList<Reservation> reservations)
    {
        reservations.clear();
        return dataAccess.getReservationSequential(reservations);
    }

    public Reservation getRandom(int reservationID)
    {
        return dataAccess.getReservation(reservationID);
    }

    public int getNextReservationID(){
        return  dataAccess.getNextReservationID();
    }

    public String insertReservation(Reservation currentReservation)
    {
        return dataAccess.insertReservation(currentReservation);
    }

    public String updateReservation(Reservation newReservation)
    {
        return dataAccess.updateReservation(newReservation.getRID(), newReservation);
    }

    public String deleteReservation(int reservationID)
    {
        return dataAccess.deleteReservation(reservationID);
    }

    // return an array of suggested reservations in order
    // which has the same "length" as (endTime-startTime)
    public ArrayList<Reservation> suggestReservations(DateTime startTime, DateTime endTime, int numPeople)
    {
        ArrayList<Reservation> results = new ArrayList<>();
        int index = getIndex(startTime);
        int totalIncrement = (startTime.getPeriod(endTime)+7)/15; // total num of increments
        int maxIndex = Table.INTERVALS_PER_DAY; // max index
        ArrayList<Table> tables = new ArrayList<>();
        dataAccess.getTableSequential(tables);

        Table table;
        for(int t = 0; t < tables.size(); t++)
        {
            table = tables.get(t);
            if(table.getCapacity() >= numPeople)
            {
                boolean[] available = dataAccess.getAvailable(t+1, startTime);
                // within +- half hour of the start time
                int i = Math.max(index-2, 0);
                while(i <= index + 2 && i < maxIndex)
                {
                    while(i <= index + 2 && i < maxIndex && !available[i])
                        i++;
                    if(i <= index + 2 && i < maxIndex)
                    {
                        int numIncrement = 1;
                        for(int time = i + 1; time < i + totalIncrement; time++)
                        {
                            if(time < maxIndex && available[time])
                                numIncrement++;
                            else
                                break;
                        }
                        if(numIncrement == totalIncrement)
                        {
                            DateTime start = dataAccess.getDateTime(startTime, i);
                            DateTime end = dataAccess.getDateTime(endTime, i+numIncrement);
                            dataAccess.orderedInsert(results, new Reservation(table.getTID(), numPeople, start, end), startTime);
                        }
                    }
                    i++;
                }
            }
        }
        return results;
    }

    // return the index corresponding to a date time
    // the closest index of the 15-minutes increment in the business hour of a day
    public static int getIndex(DateTime time)
    {
        return (time.getHour()-Table.START_TIME)*4 + (time.getMinutes()+7)/15;
    }
}