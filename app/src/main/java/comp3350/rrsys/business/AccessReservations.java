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
    private static DataAccess dataAccessStatic;
    private DataAccess dataAccess;
    private ArrayList<Reservation> reservations;
    private Reservation reservation;

    public AccessReservations()
    {
        dataAccess = Services.getDataAccess(Main.dbName);
        dataAccessStatic = Services.getDataAccess(Main.dbName);
        reservations = null;
    }

    public String getReservations(ArrayList<Reservation> reservations)
    {
        reservations.clear();
        return dataAccess.getReservationSequential(reservations);
    }

    public Reservation getRandom(int reservationID)
    {
        reservation = dataAccess.getReservation(reservationID);
        return reservation;
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
    public static ArrayList<Reservation> suggestReservations(DateTime startTime, DateTime endTime, int numPeople)
    {
        ArrayList<Reservation> results = new ArrayList<>();
        int index = getIndex(startTime);
        int totalIncrement = (startTime.getPeriod(endTime)+7)/15; // total num of increments
        int maxIndex = Table.INTERVALS_PER_DAY; // max index
        ArrayList<Table> tables = new ArrayList<>();
        dataAccessStatic.getTableSequential(tables);

        Table table;
        for(int t = 0; t < tables.size(); t++)
        {
            table = tables.get(t);
            if(table.getCapacity() >= numPeople)
            {
                // within +- half hour of the start time
                boolean[] available = dataAccessStatic.getAvailable(t+1, startTime);
                int i = Math.max(index-2, 0);
                while(i <= index+2 && i < maxIndex)
                {
                    while(i <= index+2 && i < maxIndex && !available[i])
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
                        if (numIncrement == totalIncrement)
                        {
                            DateTime start = dataAccessStatic.getDateTime(startTime, i);
                            DateTime end = dataAccessStatic.getDateTime(endTime, i+numIncrement);
                            dataAccessStatic.orderedInsert(results, new Reservation(table.getTID(), numPeople, start, end), startTime);
                        }
                    }
                    i++;
                }
            }
        }
        return results;
    }

    public static int getIndex(DateTime time)
    {
        return (time.getHour()-Table.START_TIME)*4 + (time.getMinutes()+7)/15;
    }
}