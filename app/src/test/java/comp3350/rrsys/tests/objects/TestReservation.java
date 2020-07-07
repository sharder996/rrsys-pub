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
    //TODO: insert RID test functions here

    public void testReservation() {

        System.out.println("\nStarting TestReservation");

        //tests fail here???
        Customer customer0 = new Customer("Cody", "Moon", "204-115-4259");
        Reservation res0 = null;
        try {
            res0 = new Reservation(customer0.getCID(), 1, 1, new DateTime(new GregorianCalendar(2020, 7, 28, 13, 00)), new DateTime(new GregorianCalendar(2020, 7, 28, 14, 00)));
            res0.setRID();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        assertNotNull(res0);
        assertEquals(res0.getNumPeople(), 1);
        assertEquals(res0.getTID(), 1);
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
        assertEquals(res0.getRID() , res1.getRID());
        assertTrue(res0.equals(res1.getRID()));
        assertEquals(res0.getCID(), res1.getCID());

    }
}
