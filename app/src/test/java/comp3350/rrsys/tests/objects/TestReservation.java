package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;

public class TestReservation extends TestCase
{
    public TestReservation(String arg0) { super(arg0); }

    public void testReservation() {

        System.out.println("\nStarting TestReservation");

        Customer customer0 = new Customer("Cody", "Moon", "204-115-4259");
        Reservation res0 = null;
        try {
            res0 = new Reservation(customer0.getCID(), 1, 1, new DateTime(new GregorianCalendar(2020, 7, 28, 13, 00)), new DateTime(new GregorianCalendar(2020, 7, 28, 14, 00)));
            res0.setRID();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Test creation of a basic reservation
        assertNotNull(res0);
        assertEquals(res0.getNumPeople(), 1);
        assertEquals(res0.getTID(), 1);
        assertEquals(res0.getCID(), customer0.getCID());
        assertEquals(1, res0.getTID());
        assertEquals(1, res0.getNumPeople());
        assertFalse(res0.confirmation() == null);

        try {
            assertTrue(res0.getStartTime().equals(new DateTime(new GregorianCalendar(2020, 7, 28, 13, 00))));
            assertTrue(res0.getEndTime().equals( new DateTime(new GregorianCalendar(2020, 7, 28, 14, 00))));
        }catch(ParseException y){
            y.printStackTrace();
        }

        Reservation res1 = null;
        try {
            res1 = new Reservation(customer0.getCID(), 1, 1, new DateTime(new GregorianCalendar(2020, 7, 28, 13, 00)), new DateTime(new GregorianCalendar(2020, 7, 28, 14, 00)));
            res1.setRID(res0.getRID());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Test creation of a duplicate reservation
        assertEquals(res0.getRID() , res1.getRID());
        assertTrue(res0.equals(res1.getRID()));
        assertEquals(res0.getCID(), res1.getCID());

        //Beginning tests of invalid/odd data
        Reservation res2 = null;
        try {
            res2 = new Reservation(customer0.getCID(), 2, -1, new DateTime(new GregorianCalendar(2020, 7, 28, 13, 00)), new DateTime(new GregorianCalendar(2020, 7, 28, 14, 00)));
            res2.setRID(res0.getRID() + 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //testing with negative numbers of people
        assertNotNull(res2);
        assertEquals(-1, res2.getNumPeople());
        assertEquals(customer0.getCID(), res2.getCID());
        assertFalse(res2.getRID() == res0.getRID());
        assertFalse(res2.getTID() == res0.getTID());

        Reservation res3 = null;
        try {
            res3 = new Reservation(customer0.getCID(), -2, 6, new DateTime(new GregorianCalendar(2020, 7, 28, 13, 00)), new DateTime(new GregorianCalendar(2020, 7, 28, 14, 00)));
            res3.setRID(res2.getRID() + 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //test negative table number
        assertNotNull(res3);
        assertEquals(6, res3.getNumPeople());
        assertEquals(-2, res3.getTID());

        Reservation res4 = null;

        res4 = new Reservation(customer0.getCID(), 5, 6, null, null);
        res4.setRID(res3.getRID() + 1);

        assertNotNull(res4);
        assertNull(res4.getStartTime());
        assertNull(res4.getEndTime());

        //test negative customer number
        Reservation res5 = null;
        try {
            res5 = new Reservation(-1, 10, 6, new DateTime(new GregorianCalendar(2020, 7, 28, 13, 00)), new DateTime(new GregorianCalendar(2020, 7, 28, 14, 00)));
            res5.setRID(res3.getRID() + 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertNotNull(res5);
        assertEquals(-1, res5.getCID());

    }
}
