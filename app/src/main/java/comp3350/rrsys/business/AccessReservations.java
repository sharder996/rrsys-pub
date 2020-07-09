package comp3350.rrsys.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.rrsys.R;
import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.persistence.DataAccessStub;

public class AccessReservations
{
    private static DataAccessStub dataAccessStatic;
    private DataAccessStub dataAccess;
    private ArrayList<Reservation> reservations;
    private Reservation reservation;
    private int currentReservation;

    public AccessReservations()
    {
        dataAccess = (DataAccessStub) Services.getDataAccess(Main.dbName);
        dataAccessStatic = (DataAccessStub) Services.getDataAccess(Main.dbName);
        reservations = null;
    }

    public String getReservations(ArrayList<Reservation> reservations)
    {
        reservations.clear();
        return dataAccess.getReservationSequential(reservations);
    }

    public ArrayList<Reservation> searchReservations(int numPeople, DateTime startTime, DateTime endTime)
    {
        return SuggestReservations(startTime, endTime, numPeople);
    }

    public ArrayList<Reservation> getSequential(int customerID)
    {
        reservations = dataAccess.getReservations(customerID);
        return reservations;
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
        if(reservation != null)
            return dataAccess.updateReservation(reservation.getRID(), newReservation);
        else
            return dataAccess.updateReservation(newReservation.getRID(), newReservation);
    }

    public String deleteReservation(Reservation currentReservation)
    {
        return dataAccess.deleteReservation(currentReservation);
    }

    public String deleteReservation(int reservationID)
    {
        return dataAccess.deleteReservation(reservationID);
    }

    public static ArrayList<Reservation> SuggestReservations(DateTime startTime, DateTime endTime, int numPeople)
    {

        ArrayList<Reservation> results = new ArrayList<Reservation>();
        int month = startTime.getMonth();
        int day = startTime.getDate();
        int index = getIndex(startTime);
        int totalIncrement = (startTime.getPeriod(endTime)+7)/15; // total num of increments
        int maxIndex = Table.getNumIncrement(); // max index
        ArrayList<Table> tables = dataAccessStatic.getTableSequential();

        Table table;
        for(int t = 0; t < tables.size(); t++)
        {
            table = tables.get(t);
            if(table.getCapacity() >= numPeople)
            {
                // within +- half hour of the start time
                int i = Math.max(index-2, 0);
                while(i <= index+2 && i < maxIndex)
                {
                    while(i <= index+2 && i < maxIndex && !table.getAvailable(month, day, i))
                        i++;
                    if (i <= index + 2 && i < maxIndex)
                    {
                        int numIncrement = 1;
                        for (int time = i + 1; time < i + totalIncrement; time++)
                        {
                            if (time < maxIndex && table.getAvailable(month, day, time))
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
        /*
        ArrayList<Reservation> result = new ArrayList<>();
        result.add(new Reservation(0, 0, numberOfPeople, start, end));

        return result;

         */
    }

    public static int getIndex(DateTime time)
    {
        return (time.getHour()-Table.getStartTime())*4 + (time.getMinutes()+7)/15;
    }
}