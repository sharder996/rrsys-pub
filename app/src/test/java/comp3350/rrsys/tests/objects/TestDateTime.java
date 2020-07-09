package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import comp3350.rrsys.objects.DateTime;

public class TestDateTime extends TestCase
{
    public TestDateTime(String arg0) { super(arg0); }

    public void testDateTime()
    {
        System.out.println("\nStarting TestDateTime");
        DateTime time1 = null;
        DateTime time2 = null;
        try
        {
            time1 = new DateTime(new GregorianCalendar(2020,8,23,14,00));
            time2 = new DateTime(new GregorianCalendar(2020,8,23,15,00));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        assertNotNull(time1);
        assertEquals(time1.getYear(), 2020);
        assertEquals(time1.getMonth(), 8);
        assertEquals(time1.getDate(), 23);
        assertEquals(time1.getHour(), 14);
        assertEquals(time1.getMinutes(), 00);
        assertNotNull(time2);
        assertFalse(time1.equals(time2));

        time2.setHour(14);
        assertTrue(time1.equals(time2));

        time2.setDate(24);
        assertFalse(time1.equals(time2));

        time1.setDate(24);
        assertTrue(time1.equals(time2));

        //test before unix epoch
        DateTime time3 = null;
        try
        {
            time3 = new DateTime(new GregorianCalendar(1969,1,1,00,00));
            fail();
        }
        catch (ParseException e)
        {
            //Success
        }

        assertNull(time3);

        //test valid leap year
        DateTime time4 = null;
        try
        {
            time4 = new DateTime(new GregorianCalendar(2020,2,30,12,00));
        }
        catch (ParseException e)
        {
            fail();
        }

        assertNotNull(time4);
        assertEquals(2, time4.getMonth());
        assertEquals(30, time4.getDate());

        //test invalid leap year
        DateTime time5 = null;
        try
        {
            time5 = new DateTime(new GregorianCalendar(2021,2,30,12,00));
        }
        catch (ParseException e)
        {
            fail();
        }

        assertNotNull(time4);
        assertEquals(2, time5.getMonth());
        assertEquals(30, time5.getDate());

        //test invalid date
        DateTime time6 = null;
        try
        {
            time6 = new DateTime(new GregorianCalendar(2020,2,31,12,00));
        }
        catch (ParseException e)
        {
            fail();
        }

        assertNotNull(time6);
        assertEquals(2, time6.getMonth());
        assertEquals(31, time6.getDate());

        //test invalid time
        DateTime time7 = null;
        try
        {
            time7 = new DateTime(new GregorianCalendar(2020,2,28,-1,00));
        }
        catch (ParseException e)
        {
            fail();
        }

        assertNotNull(time7);
        assertFalse(-1 == time7.getHour());
        assertEquals(27, time7.getDate());

        System.out.println("\nEnd TestDateTime");
    }
}
