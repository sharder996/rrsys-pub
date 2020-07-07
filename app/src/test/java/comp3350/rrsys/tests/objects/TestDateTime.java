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


    public void TestDateTime(){
        System.out.println("\nStarting TestReservation");
        DateTime time1 = null;
        DateTime time2 = null;
        try{
            time1 = new DateTime(new GregorianCalendar(2020,8,23,14,00));
            time2 = new DateTime(new GregorianCalendar(2020,8,23,15,00));
        }catch (ParseException e){
            e.printStackTrace();
        }

        //assertTrue(time1.equals(time2));
    }
}
