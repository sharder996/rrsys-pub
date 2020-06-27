package comp3350.rrsys.persistence;

import java.util.List;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.objects.RID;

public class DataAccessStub
{
    private String dbName;
    private String dbType = "stub";

    public DataAccessStub(String dbName)
    {
        this.dbName = dbName;
    }

    public void open(String dbName)
    {
        //TODO: insert hardcoded database here
    }

    public DataAccessStub()
    {
        this(Main.dbName);
    }

    public void close()
    {
        System.out.println("Closed " + dbType + " database " + dbName);
    }

    //TODO: insert/implement database functions here
    //Note: see database functions in database object
    public String getReservationSequential(List<RID> reservationResult)
    {
        return null;
    }

    public String insertReservation(RID currentReservation)
    {
        return null;
    }

    public String updateReservation(RID currentReservation)
    {
        return null;
    }

    public String deleteReservation(RID currentReservation)
    {
        return null;
    }
}
