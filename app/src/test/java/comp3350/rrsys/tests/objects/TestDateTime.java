package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;

import comp3350.rrsys.objects.DateTime;

public class TestDateTime extends TestCase
{
    public TestDateTime(String arg0) { super(arg0); }

    public void testDateCreationGreaterThanValidDateTime()
    {
        System.out.println("\nStarting testDateCreationGreaterThanValidDateTime");
        DateTime dateTime = null;
        GregorianCalendar currDate = new GregorianCalendar();
        currDate.set(GregorianCalendar.DATE, currDate.get(GregorianCalendar.DATE) + DateTime.MAX_DAYS_DIFFERENCE + 1);

        try
        {
            dateTime = new DateTime(currDate);
            fail();
        }
        catch(IllegalArgumentException e)
        {
            assertNull(dateTime);
        }

        System.out.println("\nEnding testDateCreationGreaterThanValidDateTime");
    }
    
    public void testDateCreationLessThanValidDateTime()
    {
        System.out.println("\nStarting testDateCreationLessThanValidDateTime");
        DateTime dateTime = null;
        GregorianCalendar currDate = new GregorianCalendar();
        currDate.set(GregorianCalendar.DATE, currDate.get(GregorianCalendar.DATE) - 1);

        try
        {
            dateTime = new DateTime(currDate);
            fail();
        }
        catch(IllegalArgumentException e)
        {
            assertNull(dateTime);
        }

        System.out.println("\nEnding testDateCreationLessThanValidDateTime");
    }

    public void testDateCreationValidDateTime()
    {
        System.out.println("\nStarting testDateCreationValidDateTime");
        DateTime dateTime = null;
        GregorianCalendar currDate = new GregorianCalendar();
        currDate.set(GregorianCalendar.DATE, currDate.get(GregorianCalendar.DATE) + DateTime.MAX_DAYS_DIFFERENCE);

        try
        {
            dateTime = new DateTime(currDate);
        }
        catch(IllegalArgumentException e)
        {
            fail();
        }

        assertNotNull(dateTime);
        System.out.println("\nEnding testDateCreationValidDateTime");
    }

    public void testDateTimeEqualsPasses()
    {
        System.out.println("\nStarting testDateTimeEqualsPasses");
        DateTime time1 = null;
        DateTime time2 = null;
        GregorianCalendar currDate = new GregorianCalendar();
        currDate.set(GregorianCalendar.DATE, currDate.get(GregorianCalendar.DATE) + 1);

        try
        {
            time1 = new DateTime(currDate);
            time2 = new DateTime(currDate);
        }
        catch(IllegalArgumentException e)
        {
            fail();
        }

        assertNotNull(time1);
        assertNotNull(time2);
        assertTrue(time1.equals(time2));
        System.out.println("\nEnding testDateTimeEqualsPasses");
    }

    public void testDateTimeEqualsFails()
    {
        System.out.println("\nStarting testDateTimeEqualsFails");
        DateTime time1 = null;
        DateTime time2 = null;

        GregorianCalendar currDate = new GregorianCalendar();
        currDate.set(GregorianCalendar.DATE, currDate.get(GregorianCalendar.DATE) + 1);
        GregorianCalendar currDate2 = new GregorianCalendar();
        currDate2.set(GregorianCalendar.DATE, currDate2.get(GregorianCalendar.DATE) + 2);

        try
        {
            time1 = new DateTime(currDate);
            time2 = new DateTime(currDate2);
        }
        catch(IllegalArgumentException e)
        {
            fail();
        }

        assertNotNull(time1);
        assertNotNull(time2);
        assertFalse(time1.equals(time2));
        System.out.println("\nEnding testDateTimeEqualsFails");
    }

    public void testDateTimePreUnixTime()
    {
        System.out.println("\nStarting testDateTimePreUnixTime");

        // test before unix epoch
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
