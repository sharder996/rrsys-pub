package comp3350.rrsys.business;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.persistence.DataAccess;

public class AccessReservations {
    private DataAccess dataAccess;

    public AccessReservations() {
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    public AccessReservations(DataAccess altDataAccessService) { dataAccess = Services.createDataAccess(altDataAccessService); }

    public String getReservations(ArrayList<Reservation> reservations)
    {
        reservations.clear();
        return dataAccess.getReservationSequential(reservations);
    }

    public Reservation getRandom(int reservationID) { return dataAccess.getReservation(reservationID); }

    public int getNextReservationID() {
        return dataAccess.getNextReservationID();
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

    public ArrayList<Reservation> suggestReservations(DateTime startTime, DateTime endTime, int numPeople)
    {
        return SuggestReservations.suggest(dataAccess, startTime, endTime, numPeople);
    }
}