package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import comp3350.rrsys.objects.DateTime;


public class TestDateTime extends TestCase{

    public TestDateTime(String arg0) { super(arg0); }


    public void testDateTime(){
        System.out.println("\nStarting TestReservation");
        DateTime time1 = null;
        DateTime time2 = null;
        try{
            time1 = new DateTime(new GregorianCalendar(2020,8,23,14,00));
            time2 = new DateTime(new GregorianCalendar(2020,8,23,15,00));
        }catch (ParseException e){
            e.printStackTrace();
        }

        assertNotNull(time1);
        assertEquals(time1.getYear(), 2020);
        assertEquals(time1.getMonth(), 8);
        assertEquals(time1.getDate(), 23);
        assertEquals(time1.getHour(), 14);
        assertEquals(time1.getMinutes(), 00);
        assertNotNull(time1);
        assertNotNull(time2);
        assertFalse(time1.equals(time2));

        time2.setHour(14);
        assertTrue(time1.equals(time2));

        time2.setDate(24);
        assertFalse(time1.equals(time2));

        time1.setDate(24);
        assertTrue(time1.equals(time2));

    }
}
