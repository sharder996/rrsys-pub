package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

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
    //TODO: insert RID test functions here
    public void testReservation() {

        System.out.println("\nStarting TestReservation");

        //tests fail here???
        Customer customer0 = new Customer("Cody", "Moon", "555-555-5555");
        Reservation res0 = null;
        try {
            res0 = new Reservation(customer0.getCID(), 1, 1, new DateTime(new GregorianCalendar(2013, 7, 28, 13, 00)), new DateTime(new GregorianCalendar(2013, 7, 28, 14, 00)));
            res0.setRID();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertNotNull(res0);
        assertEquals(res0.getNumPeople(), 1);
        assertEquals(res0.getStartTime(), new GregorianCalendar(2013, 7, 28, 13, 00));
        assertEquals(res0.getEndTime(), new GregorianCalendar(2013, 7, 28, 14, 00));
        assertEquals(res0.getTID(), 1);
        assertFalse(res0.confirmation() == null);

        Reservation res1 = null;
        try {
            res1 = new Reservation(customer0.getCID(), 1, 1, new DateTime(new GregorianCalendar(2013, 7, 28, 13, 00)), new DateTime(new GregorianCalendar(2013, 7, 28, 14, 00)));
            res1.setRID(res0.getRID());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertTrue(res0.equals(res1));
        assertEquals(res0.getCID(), res1.getCID());
    }
}
