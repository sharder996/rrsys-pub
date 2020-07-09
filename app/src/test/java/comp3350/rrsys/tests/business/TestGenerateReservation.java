package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.GenerateReservation;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.persistence.DataAccessStub;

public class TestGenerateReservation extends TestCase
{
    public TestGenerateReservation(String arg0) { super(arg0); }

    public void testGenerateReservations()
    {
        System.out.println("\nStarting testGenerateReservations");

        DataAccessStub accessStub = new DataAccessStub();
        accessStub.open(Main.dbName);
        accessStub.generateFakeData();

        ArrayList<Reservation> reservations = null;
        int month = 12;
        int date = 31;
        int openTime = 8;
        int closeTime = 22;

        for(int i = 1; i < month; i++)
        {
            for(int j = 1; j <= date; j++)
            {
                for(int k = openTime; k < closeTime; k++)
                {
                    DateTime startTime = null;
                    DateTime endTime = null;

                    try{
                        startTime = new DateTime(new GregorianCalendar(2020, i, j, k, 0));
                        endTime = new DateTime(new GregorianCalendar(2020, i, j, k+1, 0));
                    }catch(IllegalArgumentException e){

                    }

                    if(reservations != null)
                    {
                        reservations.clear();
                    }
                    reservations = GenerateReservation.SuggestReservations(startTime, endTime, 4);
                    assertTrue(reservations.size() > 0);
                }
            }
        }

        accessStub.close();
        System.out.println("\nEnd testGenerateReservations");
    }
}
