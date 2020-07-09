package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;

import comp3350.rrsys.objects.DateTime;

public class TestDateTime extends TestCase
{
    public TestDateTime(String arg0) { super(arg0); }

    public void testDateTimeCreationAndEquals()
    {
        System.out.println("\nStarting testDateTimeCreationAndEquals");
        DateTime time1 = null;
        DateTime time2 = null;
        try
        {
            time1 = new DateTime(new GregorianCalendar(2020,8,23,14,0));
            time2 = new DateTime(new GregorianCalendar(2020,8,23,15,0));
        }
        catch (IllegalArgumentException e)
        {
            fail();
        }

        assertNotNull(time1);
        assertEquals(time1.getYear(), 2020);
        assertEquals(time1.getMonth(), 8);
        assertEquals(time1.getDate(), 23);
        assertEquals(time1.getHour(), 14);
        assertEquals(time1.getMinutes(), 0);
        assertNotNull(time2);
        assertFalse(time1.equals(time2));

        time2.setHour(14);
        assertTrue(time1.equals(time2));

        time2.setDate(24);
        assertFalse(time1.equals(time2));

        time1.setDate(24);
        assertTrue(time1.equals(time2));

        System.out.println("\nEnding testDateTimeCreationAndEquals");
    }

    public void testDateTimePreUnixTime()
    {
        System.out.println("\nStarting testDateTimePreUnixTime");

        //test before unix epoch
        DateTime dateTime = null;
        try
        {
            dateTime = new DateTime(new GregorianCalendar(1969,1,1,0,0));
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(dateTime);
        }

        System.out.println("\nEnding testDateTimePreUnixTime");
    }

    public void testDateTimeInvalidYear()
    {
        System.out.println("\nStarting testDateTimeInvalidYear");

        DateTime dateTime = null;
        try
        {
            dateTime = new DateTime(new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR) + 2,1,1,0,0));
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(dateTime);
        }

        System.out.println("\nEnding testDateTimeInvalidYear");
    }
}
