package comp3350.rrsys.business;

import android.app.Activity;

import java.util.List;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.reservation;
import comp3350.rrsys.persistence.DataAccessStub;

public class AccessReservations extends Activity
{
    private DataAccessStub dataAccess;
    private List<reservation> reservations;

    public AccessReservations()
    {
        dataAccess = Services.getDataAccess(Main.dbName);
        reservations = null;
    }

    public String getReservations(List<reservation> reservations)
    {
        reservations.clear();
        return dataAccess.getReservationSequential(reservations);
    }

    public String insertReservation(reservation currentReservation)
    {
        return dataAccess.insertReservation(currentReservation);
    }

    public String updateReservation(reservation currentReservation)
    {
        return dataAccess.updateReservation(currentReservation);
    }

    public String deleteReservation(reservation currentReservation)
    {
        return dataAccess.deleteReservation(currentReservation);
    }
}
