package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.SuggestReservations;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.persistence.DataAccessStub;

public class TestSuggestReservations extends TestCase
{

    public TestSuggestReservations(String arg0) { super(arg0); }

    public void setUp()
    {
        System.out.println("\nStarting TestSuggestReservations");
    }

    public void tearDown()
    {
        System.out.println("\nEnd TestSuggestReservations");
    }

    public void testSuggestReservations()
    {
        DataAccessStub dataAccess = new DataAccessStub(Main.dbName);
        dataAccess.open(Main.dbName);
        dataAccess.generateFakeData();

        /* expected number of suggest reservations for each capacity level
           5 tables wth capacity 2, 4, 6, 8, 10, 12
           eg. 5 tables with capacity >= 11 or 12 (number of people)
               10 tables with capacity >= 9 or 10 (number of people)
               30 tables with capacity >= 1 or 2 (number of people)
           5 different time periods, early or late than input time
         */
        int size = 5 * 5 * 2;

        ArrayList<Reservation> reservations = null;
        int openTime = Table.START_TIME;
        int closeTime = Table.END_TIME;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH);
        int date = cal.get(cal.DATE) + 1;
        int maxDaysDifference = DateTime.MAX_DAYS_DIFFERENCE;
        int daysDifference = 0;

        for(int i = date + 1; i < cal.getActualMaximum(Calendar.DATE); i++)
        {
            daysDifference++;
            if(daysDifference < maxDaysDifference)
            {
                for(int j = openTime + 1; j < closeTime - 1; j++)
                {
                    DateTime startTime = null;
                    DateTime endTime = null;

                    try
                    {
                        startTime = new DateTime(new GregorianCalendar(year, month, date, j, 0));
                        endTime = new DateTime(new GregorianCalendar(year, month, date, j + 1, 0));
                    }
                    catch (IllegalArgumentException e)
                    {
                        fail();
                    }

                    for(int k = 1; k <= 12; k++)
                    {
                        if (reservations != null)
                            reservations.clear();

                        reservations = SuggestReservations.suggest(dataAccess, startTime, endTime, k);
                        assertTrue(reservations.size() > 0);
                        assertEquals((14 - k)/2 * size, reservations.size());
                    }
                }
            }
        }

        if(daysDifference < maxDaysDifference)
        {
            cal.set(Calendar.MONTH, month++);
            date = 1;
            for(int i = date; i < cal.getActualMaximum(Calendar.DATE); i++)
            {
                daysDifference++;
                if(daysDifference < maxDaysDifference)
                {
                    for(int j = openTime + 1; j < closeTime - 1; j++)
                    {
                        DateTime startTime = null;
                        DateTime endTime = null;

                        try
                        {
                            startTime = new DateTime(new GregorianCalendar(year, month, date, j, 0));
                            endTime = new DateTime(new GregorianCalendar(year, month, date, j + 1, 0));
                        }
                        catch (IllegalArgumentException e)
                        {
                            fail();
                        }

                        for(int k = 1; k <= 12; k++)
                        {
                            if (reservations != null)
                                reservations.clear();

                            reservations = SuggestReservations.suggest(dataAccess, startTime, endTime, k);
                            assertTrue(reservations.size() > 0);
                            assertEquals((14 - k)/2 * size, reservations.size());
                        }
                    }
                }
            }
        }
        dataAccess.close();
    }
}
