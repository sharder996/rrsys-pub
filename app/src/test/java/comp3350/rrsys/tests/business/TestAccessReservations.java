package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessReservations;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;

public class TestAccessReservations extends TestCase
{
    public TestAccessReservations(String arg0) { super(arg0); }

    public void testAccessReservations()
    {
        System.out.println("\nStarting TestAccessReservations");

        Main.startUp();
        AccessReservations accessReservations = new AccessReservations();

        assertNotNull(accessReservations);

        ArrayList<Reservation> reservationsList = new ArrayList<>();
        accessReservations.getReservations(reservationsList);
        assertEquals(0, reservationsList.size());

        DateTime startTime = null;
        DateTime endTime = null;
        try
        {
            startTime = new DateTime(new GregorianCalendar(2020,10,01,12,00));
            endTime = new DateTime(new GregorianCalendar(2020, 10, 01, 13, 00));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        assertNotNull(startTime);
        assertNotNull(endTime);
        Reservation res0 = new Reservation(0, 4, startTime, endTime);
        accessReservations.insertReservation(res0);
        accessReservations.getReservations(reservationsList);
        assertEquals(1, reservationsList.size());

        //assertTrue()

        Main.shutDown();
        System.out.println("\nEnd TestAccessReservations");
    }
}
