package comp3350.rrsys.business;

import android.app.Activity;

import java.util.List;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.RID;
import comp3350.rrsys.persistence.DataAccessStub;

public class AccessReservations extends Activity
{
    private DataAccessStub dataAccess;
    private List<RID> reservations;

    public AccessReservations()
    {
        dataAccess = Services.getDataAccess(Main.dbName);
        reservations = null;
    }

    public String getReservations(List<RID> reservations)
    {
        reservations.clear();
        return dataAccess.getReservationSequential(reservations);
    }

    public String insertReservation(RID currentReservation)
    {
        return dataAccess.insertReservation(currentReservation);
    }

    public String updateReservation(RID currentReservation)
    {
        return dataAccess.updateReservation(currentReservation);
    }

    public String deleteReservation(RID currentReservation)
    {
        return dataAccess.deleteReservation(currentReservation);
    }
}
