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
    }

    public void test1() //user looks at menu then goes to create a reservation and reviews it after
    {
        ArrayList<Item> menu;
        ArrayList<String> menuTypes;

        Item item;
        Reservation reservation = null;

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

        int rid = dataAccess.getNextReservationID();
        DateTime start = new DateTime(new GregorianCalendar(2020, 9, 28, 13, 0));
        DateTime end = new DateTime(new GregorianCalendar(2020, 9, 28, 15, 0));

        reservation = dataAccess.getReservation(rid);
        assertNull(reservation);

        reservation = new Reservation(rid, 2, start, end);
        assertNotNull(reservation);
        result = dataAccess.insertReservation(reservation);
        assertEquals("success", result);

        reservation = dataAccess.getReservation(rid);
        assertNotNull(reservation);
        assertEquals(1, reservation.getRID());
        assertEquals(2, reservation.getNumPeople());
        assertEquals(start, reservation.getStartTime());
        assertEquals(end, reservation.getEndTime());

    }

    public void test2() //user creates a reservation and pre-orders a meal
    {
        ArrayList<Item> menu;

        Reservation reservation = null;
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
        assertNotNull(reservation);
        result = dataAccess.insertReservation(reservation);
        reservation.setRID(rid);
        assertEquals("success", result);

        reservation = dataAccess.getReservation(rid);
        assertNotNull(reservation);
        assertEquals(rid, reservation.getRID());
        assertEquals(1, reservation.getTID());
        assertEquals(2, reservation.getNumPeople());
        assertEquals(start, reservation.getStartTime());
        assertEquals(end, reservation.getEndTime());

        menu = dataAccess.getMenu();
        assertNotNull(menu);
        assertEquals(44, menu.size());

        order = new Order(rid);
        assertNotNull(order);

        item = menu.get(5);
        assertNotNull(item);
        order.addItem(item, "");
        item = menu.get(10);
        assertNotNull(item);
        order.addItem(item, "No nuts");
        item = menu.get(15);
        assertNotNull(item);
        order.addItem(item, "Add cheese");

        assertEquals(3, order.size());
        order.getTotalPrice();
        assertEquals(41.85, order.getTotalPrice());
        assertEquals("", order.getNote(1));
        assertEquals(rid, order.getReservationID());

        result = dataAccess.insertOrder(order);
        assertEquals("success", result);

        order = dataAccess.getOrder(rid);
        assertNotNull(order);
    }
}
