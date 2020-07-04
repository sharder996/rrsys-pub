package comp3350.rrsys.business;

import android.app.Activity;

import java.util.List;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.persistence.DataAccessStub;

public class AccessReservations extends Activity
{
    private DataAccessStub dataAccess;
    private List<Reservation> reservations;

    public AccessReservations()
    {
        dataAccess = Services.getDataAccess(Main.dbName);
        reservations = null;
    }

    public String getReservations(List<Reservation> reservations)
    {
        reservations.clear();
        return dataAccess.getReservationSequential(reservations);
    }

    /*public String insertReservation(Reservation currentReservation)
    {
        return dataAccess.insertReservation(currentReservation);
    }*/

    public String updateReservation(Reservation currentReservation)
    {
        return dataAccess.updateReservation(currentReservation);
    }

    public String deleteReservation(Reservation currentReservation)
    {
        return dataAccess.deleteReservation(currentReservation);
    }
}
