package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.business.AccessReservations;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.persistence.DataAccess;
import comp3350.rrsys.persistence.DataAccessStub;

public class TestAccessReservations extends TestCase
{
    private AccessReservations accessReservations;

    public TestAccessReservations(String arg0) { super(arg0); }

    public void setUp()
    {
        System.out.println("\nStarting TestAccessReservations");
        accessReservations = new AccessReservations(new DataAccessStub(Main.dbName));
    }

    public void tearDown()
    {
        System.out.println("\nEnd TestAccessReservations");
    }

    public void testAccessReservationConnection()
    {
        assertNotNull(accessReservations);
    }


    public void testCreateInvalidReservation()
    {
        Calendar currDate = Calendar.getInstance();
        DateTime startTime = null;
        DateTime endTime = null;

        try
        {
            startTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1,12,0));
            endTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 13, 0));
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

    public void testCreateNull()
    {
        String result = accessReservations.insertReservation(null);
        assertEquals(result, "fail");
    }


    public void testCreateValidReservations()
    {
        ArrayList<Reservation> reservationsList = new ArrayList<>();
        accessReservations.getReservations(reservationsList);

        assertEquals(0, reservationsList.size());

        Calendar currDate = Calendar.getInstance();
        DateTime startTime = null;
        DateTime endTime = null;

        try
        {
            startTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 12, 0));
            endTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 13, 0));
        }
        catch (IllegalArgumentException e)
        {
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

    public void testUpdateInvalidReservation()
    {
        Services.createDataAccess(new DataAccessStub(Main.dbName));
        ArrayList<Reservation> reservationsList = new ArrayList<>();
        accessReservations.getReservations(reservationsList);

        Calendar currDate = Calendar.getInstance();
        DateTime startTime = null;
        DateTime endTime = null;

        try
        {
            startTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 12, 0));
            endTime= new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 14, 0));
        }
        catch (IllegalArgumentException e)
        {
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

    public void testUpdateValidReservation()
    {
        ArrayList<Reservation> reservationsList = new ArrayList<>();
        accessReservations.getReservations(reservationsList);

        Calendar currDate = Calendar.getInstance();
        DateTime startTime = null;
        DateTime endTime = null;

        try
        {
            startTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 12, 0));
            endTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 13, 0));
        }
        catch (IllegalArgumentException e)
        {
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

        try
        {
            startTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 2, 12, 0));
            endTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 2, 13, 0));
        }
        catch (IllegalArgumentException e)
        {
            fail();
        }

        res0.setTime(startTime, endTime);
        accessReservations.updateReservation(res0);
        assertEquals(startTime, accessReservations.getRandom(res0.getRID()).getStartTime());
        assertEquals(endTime, accessReservations.getRandom(res0.getRID()).getEndTime());
    }

    public void testDeleteInvalidReservation()
    {
        String result = accessReservations.deleteReservation(100);
        assertEquals(result, "fail");

        result = accessReservations.deleteReservation(20000000);
        assertEquals(result, "fail");

        result = accessReservations.deleteReservation(-1);
        assertEquals(result, "fail");
    }

    public void testDeleteValidReservation()
    {
        ArrayList<Reservation> reservationsList = new ArrayList<>();
        accessReservations.getReservations(reservationsList);

        Calendar currDate = Calendar.getInstance();
        DateTime startTime = null;
        DateTime endTime = null;


        try
        {
            startTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 12, 0));
            endTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 13, 0));
        }
        catch (IllegalArgumentException e)
        {
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

    public void testGenerateReservations()
    {
        System.out.println("\nStarting testGenerateReservations");

        DataAccessStub accessStub = new DataAccessStub();
        accessStub.open(Main.dbName);
        accessStub.generateFakeData();
        ArrayList<Reservation> reservations = null;
        int openTime = 7;
        int closeTime = 22;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH);
        int date = cal.get(cal.DATE);
        int maxDaysDifference = DateTime.MAX_DAYS_DIFFERENCE;
        int daysDifference = 0;

        for(int i = date + 1; i < cal.getActualMaximum(Calendar.DATE); i++) {
            daysDifference++;
            if(daysDifference < maxDaysDifference) {
                for(int j = openTime; j < closeTime; j++) {
                    DateTime startTime = null;
                    DateTime endTime = null;

                    try {
                        startTime = new DateTime(new GregorianCalendar(year, month, date, j, 0));
                        endTime = new DateTime(new GregorianCalendar(year, month, date, j + 1, 0));
                    } catch (IllegalArgumentException e) {
                        fail();
                    }

                    if (reservations != null) {
                        reservations.clear();
                    }
                    reservations = accessReservations.suggestReservations(startTime, endTime, 4);
                    assertTrue(reservations.size() > 0);
                }
            }
        }
        if(daysDifference < maxDaysDifference) {
            cal.set(Calendar.MONTH, month++);
            date = 1;
            for(int i = date; i < cal.getActualMaximum(Calendar.DATE); i++) {
                daysDifference++;
                if(daysDifference < maxDaysDifference) {
                    for(int j = openTime; j < closeTime; j++) {
                        DateTime startTime = null;
                        DateTime endTime = null;

                        try {
                            startTime = new DateTime(new GregorianCalendar(year, month, date, j, 0));
                            endTime = new DateTime(new GregorianCalendar(year, month, date, j + 1, 0));
                        } catch (IllegalArgumentException e) {
                            fail();
                        }

                        if (reservations != null) {
                            reservations.clear();
                        }
                        reservations = accessReservations.suggestReservations(startTime, endTime, 4);
                        assertTrue(reservations.size() > 0);
                    }
                }
            }
        }

        accessStub.close();
        System.out.println("\nEnd testGenerateReservations");
    }
}
