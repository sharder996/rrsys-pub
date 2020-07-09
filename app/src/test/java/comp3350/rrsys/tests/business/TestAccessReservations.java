package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessReservations;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;

public class TestAccessReservations extends TestCase
{
    private AccessReservations accessReservations;
    public TestAccessReservations(String arg0) { super(arg0); }

    public void setUp(){
        System.out.println("\nStarting TestAccessReservations");
        Main.startUp();
        accessReservations = new AccessReservations();
    }

    public void tearDown(){
        Main.shutDown();
        System.out.println("\nEnd TestAccessReservations");
    }

    public void testAccessReservationConnection(){
        assertNotNull(accessReservations);
    }


    public void testCreateInvalidReservation(){
        DateTime startTime = null;
        DateTime endTime = null;

        try
        {
            startTime = new DateTime(new GregorianCalendar(2020,10,20,12,0));
            endTime = new DateTime(new GregorianCalendar(2020, 10, 20, 13, 0));
        }
        catch (IllegalArgumentException e)
        {
            fail();
        }

        Reservation res0 = new Reservation(-1,1,startTime,endTime);
        String result = accessReservations.insertReservation(res0);
        assertEquals(result, "fail");

        Reservation res1 = new Reservation(1,-1,startTime,endTime);
        String result1 = accessReservations.insertReservation(res1);
        assertEquals(result1, "fail");

        Reservation res2 = new Reservation(1,1,null,endTime);
        String result2 = accessReservations.insertReservation(res2);
        assertEquals(result2, "fail");

        Reservation res3 = new Reservation(1,1,startTime,null);
        String result3 = accessReservations.insertReservation(res3);
        assertEquals(result3, "fail");
    }

    public void testCreateNull(){
        String result = accessReservations.insertReservation(null);
        assertEquals(result, "fail");
    }


    public void testCreateValidReservations() {
        ArrayList<Reservation> reservationsList = new ArrayList<>();
        accessReservations.getReservations(reservationsList);

        assertEquals(0, reservationsList.size());

        DateTime startTime = null;
        DateTime endTime = null;

        try {
            startTime = new DateTime(new GregorianCalendar(2020, 10, 1, 12, 0));
            endTime = new DateTime(new GregorianCalendar(2020, 10, 1, 13, 0));
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(startTime);
        assertNotNull(endTime);

        Reservation res0 = new Reservation(0, 4, startTime, endTime);
        String result = accessReservations.insertReservation(res0);
        accessReservations.getReservations(reservationsList);
        assertEquals(1, reservationsList.size());
        assertEquals(result, "success");

        Reservation res1 = new Reservation(25, 8, startTime, endTime);
        result = accessReservations.insertReservation(res1);
        accessReservations.getReservations(reservationsList);
        assertEquals(2, reservationsList.size());
        assertEquals(result, "success");

        Reservation res2 = new Reservation(21, 6, startTime, endTime);
        result = accessReservations.insertReservation(res2);
        accessReservations.getReservations(reservationsList);
        assertEquals(3, reservationsList.size());
        assertEquals(result, "success");

    }

    public void testUpdateInvalidReservation(){
        ArrayList<Reservation> reservationsList = new ArrayList<>();
        accessReservations.getReservations(reservationsList);

        DateTime startTime = null;
        DateTime endTime = null;

        try {
            startTime = new DateTime(new GregorianCalendar(2020, 10, 1, 12, 0));
            endTime = new DateTime(new GregorianCalendar(2020, 10, 1, 13, 0));
        } catch (IllegalArgumentException e) {
            fail();
        }

        Reservation res0 = new Reservation(0, 4, startTime, endTime);

        res0.setTID(-1);
        String result = accessReservations.updateReservation(res0);
        assertEquals("fail", result);

        res0.setNumPeople(-1);
        result = accessReservations.updateReservation(res0);
        assertEquals("fail", result);
    }

    public void testUpdateValidReservation(){
        ArrayList<Reservation> reservationsList = new ArrayList<>();
        accessReservations.getReservations(reservationsList);

        DateTime startTime = null;
        DateTime endTime = null;

        try {
            startTime = new DateTime(new GregorianCalendar(2020, 10, 1, 12, 0));
            endTime = new DateTime(new GregorianCalendar(2020, 10, 1, 13, 0));
        } catch (IllegalArgumentException e) {
            fail();
        }

        Reservation res0 = new Reservation(0, 4, startTime, endTime);
        accessReservations.insertReservation(res0);

        res0.setTID(1);
        accessReservations.updateReservation(res0);
        assertEquals(1, accessReservations.getRandom(res0.getRID()).getTID());

        res0.setNumPeople(3);
        accessReservations.updateReservation(res0);
        assertEquals(3, accessReservations.getRandom(res0.getRID()).getNumPeople());

        try {
            startTime = new DateTime(new GregorianCalendar(2020, 10, 2, 12, 0));
            endTime = new DateTime(new GregorianCalendar(2020, 10, 2, 13, 0));
        } catch (IllegalArgumentException e) {
            fail();
        }

        res0.setTime(startTime, endTime);
        accessReservations.updateReservation(res0);
        assertEquals(startTime, accessReservations.getRandom(res0.getRID()).getStartTime());
        assertEquals(endTime, accessReservations.getRandom(res0.getRID()).getEndTime());
    }

    public void testDeleteInvalidReservation(){

        String result = accessReservations.deleteReservation(2);
        assertEquals(result, "fail");

        result = accessReservations.deleteReservation(20000);
        assertEquals(result, "fail");

        result = accessReservations.deleteReservation(-1);
        assertEquals(result, "fail");
    }

    public void testDeleteValidReservation(){
        ArrayList<Reservation> reservationsList = new ArrayList<>();
        accessReservations.getReservations(reservationsList);

        DateTime startTime = null;
        DateTime endTime = null;

        try {
            startTime = new DateTime(new GregorianCalendar(2020, 10, 1, 12, 0));
            endTime = new DateTime(new GregorianCalendar(2020, 10, 1, 13, 0));
        } catch (IllegalArgumentException e) {
            fail();
        }

        Reservation res0 = new Reservation(0, 4, startTime, endTime);
        accessReservations.insertReservation(res0);

        Reservation res1 = new Reservation(20, 6, startTime, endTime);
        accessReservations.insertReservation(res1);

        String result = accessReservations.deleteReservation(res0.getRID());
        assertEquals(result, "success");

        String result1 = accessReservations.deleteReservation(res1.getRID());
        assertEquals(result1, "success");
    }
}
