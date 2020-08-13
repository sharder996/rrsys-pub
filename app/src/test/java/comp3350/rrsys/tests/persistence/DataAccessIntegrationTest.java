package comp3350.rrsys.tests.persistence;

import android.provider.CalendarContract;
import android.view.Menu;

import junit.framework.TestCase;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.persistence.DataAccess;
import comp3350.rrsys.persistence.DataAccessObject;
import comp3350.rrsys.persistence.DataAccessStub;

public class DataAccessIntegrationTest  extends TestCase
{
    private DataAccess dataAccess;

    public DataAccessIntegrationTest(String arg0)
    {
        super(arg0);
    }

    public void setUp()
    {
        System.out.println("\nStarting Persistence test DataAccess (using stub)");

        // Use the following statements to run with the stub database:
        dataAccess = new DataAccessStub();
        dataAccess.open("Stub");
        // or switch to the real database:
        // dataAccess = new DataAccessObject(Main.dbName);
        // dataAccess.open(Main.getDBPathName());
        // Note the increase in test execution time.
    }

    public void tearDown() { System.out.println("Finished Persistence test DataAccess (using stub)"); }

    public static void dataAccessIntegrationTest(DataAccess dataAccess) {
        DataAccessIntegrationTest dataAccessIntegrationTest = new DataAccessIntegrationTest("");
        dataAccessIntegrationTest.dataAccess = dataAccess;
        dataAccessIntegrationTest.test1();
        dataAccessIntegrationTest.test2();
        dataAccessIntegrationTest.test3();
    }

    public void test1() //user looks at menu then goes to create a reservation and reviews it after
    {
        ArrayList<Item> menu;
        ArrayList<String> menuTypes;

        Item item;
        Reservation reservation;

        String result;

        menu = dataAccess.getMenu();
        assertNotNull(menu);
        assertEquals(44, menu.size());

        menuTypes = dataAccess.getMenuTypes();
        assertEquals(6, menuTypes.size());

        item = menu.get(0);
        assertEquals(1, item.getItemID());
        assertEquals("SPECIAL SALAD", item.getName());
        assertEquals("Salads", item.getType());
        assertEquals("romaine lettuce, arugula, red cabbage, carrot, red onion & toasted sunflower seeds.", item.getDetail());
        assertEquals(9.95, item.getPrice());

        Calendar currDate = Calendar.getInstance();
        DateTime start = null;
        DateTime end = null;

        try
        {
            start = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1,9,0));
            end = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 11, 0));
        }
        catch (IllegalArgumentException e)
        {
            fail();
        }

        int rid = dataAccess.getNextReservationID();

        reservation = dataAccess.getReservation(rid);
        assertNull(reservation);

        reservation = new Reservation(2, 2, start, end);
        reservation.setRID(rid);
        reservation.setCustomerID(2);
        assertNotNull(reservation);
        result = dataAccess.insertReservation(reservation);
        assertEquals("success", result);

        reservation = dataAccess.getReservation(rid);
        assertNotNull(reservation);
        assertEquals(rid, reservation.getRID());
        assertEquals(2, reservation.getTID());
        assertEquals(2, reservation.getNumPeople());
        assertEquals(start.toString(), reservation.getStartTime().toString());
        assertEquals(end.toString(), reservation.getEndTime().toString());
    }

    public void test2() //user creates a reservation and pre-orders a meal
    {
        ArrayList<Item> menu;

        Reservation reservation;
        Item item;
        Order order;

        String result;

        Calendar currDate = Calendar.getInstance();
        DateTime start = null;
        DateTime end = null;

        try
        {
            start = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1,12,0));
            end = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 13, 0));
        }
        catch (IllegalArgumentException e)
        {
            fail();
        }

        int rid = dataAccess.getNextReservationID();

        reservation = dataAccess.getReservation(rid);
        assertNull(reservation);

        reservation = new Reservation(1, 2, start, end);
        reservation.setRID(rid);
        reservation.setCustomerID(1);
        assertNotNull(reservation);
        result = dataAccess.insertReservation(reservation);
        assertEquals("success", result);

        reservation = dataAccess.getReservation(rid);
        assertNotNull(reservation);
        assertEquals(rid, reservation.getRID());
        assertEquals(1, reservation.getTID());
        assertEquals(2, reservation.getNumPeople());
        assertEquals(start.toString(), reservation.getStartTime().toString());
        assertEquals(end.toString(), reservation.getEndTime().toString());

        menu = dataAccess.getMenu();
        assertNotNull(menu);
        assertEquals(44, menu.size());

        order = new Order(rid);
        assertNotNull(order);

        item = menu.get(5);
        assertNotNull(item);
        order.addItem(item, 1, "");
        double price = item.getPrice()*item.getQuantity();

        item = menu.get(10);
        assertNotNull(item);
        order.addItem(item, 2, "No nuts");
        price += item.getPrice()*item.getQuantity();

        item = menu.get(15);
        assertNotNull(item);
        order.addItem(item, 1, "Add cheese");
        price += item.getPrice()*item.getQuantity();

        assertEquals(rid, order.getReservationID());
        assertEquals(3, order.getSize());
        assertEquals(price, order.getTotalPrice());
        assertEquals(menu.get(5), order.getOrder().get(0));
        assertEquals(menu.get(10), order.getOrder().get(1));
        assertEquals(menu.get(15), order.getOrder().get(2));

        for(int i = 0; i < order.getSize(); i++)
        {
            result = dataAccess.insertItemIntoOrder(rid, order.getOrder().get(i));
            assertNull(result);
        }

        order = dataAccess.getOrder(rid);
        assertNotNull(order);
    }

    public void test3() //tests creating invalid reservations
    {
        Reservation reservation;
        String result;

        Calendar currDate = Calendar.getInstance();
        DateTime start = null;
        DateTime end = null;

        //try making a reservation that has 2 different days
        try
        {
            start = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1,12,0));
            end = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 2, 13, 0));
        }
        catch (IllegalArgumentException e)
        {
            fail();
        }

        int rid = dataAccess.getNextReservationID();

        reservation = dataAccess.getReservation(rid);
        assertNull(reservation);

        reservation = new Reservation(1, 2, start, end);
        reservation.setRID(rid);
        reservation.setCustomerID(1);
        assertNotNull(reservation);

        result = dataAccess.insertReservation(reservation);
        assertEquals("fail", result); // cant add a reservation in with too big of time difference
        reservation = dataAccess.getReservation(rid);
        assertNull(reservation); // it shouldnt have been added

        // try making a reservation that lasts more than 3 hours
        try
        {
            start = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1,10,0));
            end = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 15, 0));
        }
        catch (IllegalArgumentException e)
        {
            fail();
        }

        reservation = new Reservation(1, 2, start, end);
        reservation.setRID(rid);
        reservation.setCustomerID(1);
        assertNotNull(reservation);

        result = dataAccess.insertReservation(reservation);
        assertEquals("fail", result); // cant add a reservation in with too big of time difference
        reservation = dataAccess.getReservation(rid);
        assertNull(reservation); // it shouldnt have been added

        // try making a reservation with a null value or an invalid number
        try
        {
            start = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1,13,0));
            end = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 15, 0));
        }
        catch (IllegalArgumentException e)
        {
            fail();
        }

        //negative table number
        reservation = new Reservation(-1, 2, start, end);
        reservation.setRID(rid);
        reservation.setCustomerID(1);
        assertNotNull(reservation);

        result = dataAccess.insertReservation(reservation);
        assertEquals("fail", result);
        reservation = dataAccess.getReservation(rid);
        assertNull(reservation); // it shouldnt have been added

        //negative number of people
        reservation = new Reservation(1, -2, start, end);
        reservation.setRID(rid);
        reservation.setCustomerID(1);
        assertNotNull(reservation);

        result = dataAccess.insertReservation(reservation);
        assertEquals("fail", result);
        reservation = dataAccess.getReservation(rid);
        assertNull(reservation); // it shouldnt have been added

        //null end time
        reservation = new Reservation(1, 2, start, null);
        reservation.setRID(rid);
        reservation.setCustomerID(1);
        assertNotNull(reservation);

        result = dataAccess.insertReservation(reservation);
        assertEquals("fail", result);
        reservation = dataAccess.getReservation(rid);
        assertNull(reservation); // it shouldnt have been added

        //null start time
        reservation = new Reservation(1, 2, null, end);
        reservation.setRID(rid);
        reservation.setCustomerID(1);
        assertNotNull(reservation);

        result = dataAccess.insertReservation(reservation);
        assertEquals("fail", result);
        reservation = dataAccess.getReservation(rid);
        assertNull(reservation); // it shouldnt have been added

        //negative customer id
        reservation = new Reservation(1, 2, start, end);
        reservation.setRID(rid);
        reservation.setCustomerID(-1);
        assertNotNull(reservation);

        result = dataAccess.insertReservation(reservation);
        assertEquals("fail", result);
        reservation = dataAccess.getReservation(rid);
        assertNull(reservation); // it shouldnt have been added

    }
}