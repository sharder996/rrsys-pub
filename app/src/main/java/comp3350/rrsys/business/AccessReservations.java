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
    private DataAccessStub dataAccess;
    private ArrayList<Reservation> reservations;
    private Reservation reservation;
    private int currentReservation;

    public AccessReservations()
    {
        dataAccess = (DataAccessStub) Services.getDataAccess(Main.dbName);
        reservations = null;
    }

    public String getReservations(ArrayList<Reservation> reservations)
    {
        reservations.clear();
        return dataAccess.getReservationSequential(reservations);
    }

    public ArrayList<Reservation> searchReservations(int numPeople, DateTime startTime, DateTime endTime) {
        return dataAccess.searchReservations(numPeople, startTime, endTime);
    }

    public ArrayList<Reservation> getSequential(int customerID)
    {
        reservations = dataAccess.getReservations(customerID);
        return reservations;
    }

    public Reservation getRandom(int reservationID)
    {
        /*reservations = null;
        reservation = dataAccess.getReservation(reservationID);
        currentReservation = 0;
        if(reservation != null) {
            reservations.add(reservation);
            currentReservation++;
        }*/
        return dataAccess.getReservation(reservationID);
    }

    public String insertReservation(Reservation currentReservation)
    {
        return dataAccess.insertReservation(currentReservation);
    }

    public String updateReservation(Reservation newReservation)
    {
        return dataAccess.updateReservation(reservation.getRID(), newReservation);
    }

    public String deleteReservation(Reservation currentReservation)
    {
        return dataAccess.deleteReservation(currentReservation);
    }

    public String deleteReservation(int reservationID)
    {
        return dataAccess.deleteReservation(reservationID);
    }



}