package comp3350.rrsys.tests.integration;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.business.AccessCustomers;
import comp3350.rrsys.business.AccessMenu;
import comp3350.rrsys.business.AccessOrders;
import comp3350.rrsys.business.AccessReservations;
import comp3350.rrsys.business.AccessTables;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;

public class BusinessPersistenceSeamTest extends TestCase
{
    public BusinessPersistenceSeamTest(String arg0)
    {
        super(arg0);
    }

    public void testAccessCustomers()
    {
        ArrayList<Customer> customerList = new ArrayList<>();
        AccessCustomers ac;
        String result;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test of AccessCustomers to persistence");

        Services.createDataAccess(Main.dbName);

        ac = new AccessCustomers();

        result = ac.getCustomers(customerList);
        assertNull(result);
        assertEquals(customerList.size(), 5);
        assertEquals(customerList.get(0).getFirstName() , "Gary");
        assertEquals(customerList.get(0).getLastName() , "Chalmers");

        Customer newCustomer= new Customer("Jim", "Ddd", "204-956-1203");
        newCustomer.setCID(6);
        result = ac.insertCustomer(newCustomer);
        assertNull(result);

        result = ac.getCustomers(customerList);
        assertNull(result);
        assertEquals(customerList.size(), 6);
        assertEquals(customerList.get(5).getFirstName() , "Jim");
        assertEquals(customerList.get(5).getLastName() , "Ddd");

        result = ac.deleteCustomer(newCustomer);
        assertNull(result);

        result = ac.getCustomers(customerList);
        assertNull(result);
        assertEquals(customerList.size(), 5);

        result = ac.deleteCustomer(newCustomer);//remove customer that does not exist.
        assertEquals(result, "Tuple not inserted correctly.");//checkWarning  message.

        Services.closeDataAccess();

        System.out.println("Finished Integration test of AccessCustomers to persistence");
    }

    public void testAccessMenu()
    {
        ArrayList<Item> itemList;
        ArrayList<String> itemType;
        AccessMenu am;
        String result;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test of AccessMenu to persistence");

        Services.createDataAccess(Main.dbName);

        am = new AccessMenu();

        itemType = am.getMenuTypes();

        assertEquals(itemType.size(), 6);

        assertEquals(itemType.get(0), "Burgers"); //alphabetical order
        assertEquals(itemType.get(5), "Sandwiches");

        itemList = am.getMenuByType(itemType.get(0));
        assertEquals(itemList.size(), 8);

        itemList = am.getMenuByType(itemType.get(5));
        assertEquals(itemList.size(), 8);

        itemList = am.getMenuByType(itemType.get(4));
        assertEquals(itemList.size(), 6);

        itemList = am.getMenuByType("Invalid Entry");
        assertNotNull(itemList);
        assertEquals(itemList.size(), 0);

        Services.closeDataAccess();

        System.out.println("Finished Integration test of AccessMenu to persistence");
    }

    public void testAccessOrders()
    {
        AccessOrders ao;
        String result;
        Order order;
        ArrayList<Item> items;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test of AccessOrders to persistence");

        Services.createDataAccess(Main.dbName);

        ao = new AccessOrders();

        order = ao.getOrder(1);
        assertNotNull(order);

        items = order.getOrder();
        assertNotNull(items);

        //Assert
        assertEquals(items.size(), ao.getSize(1));
        assertNull(ao.getOrder(6));

        Order newOrder = new Order(6);
        newOrder.addItem(new Item(1,"SPECIAL SALAD","Salads","romaine lettuce, arugula, red cabbage, carrot, red onion & toasted sunflower seeds.",9.95));

        result = ao.insertOrder(newOrder);
        assertNull(result);

        order = ao.getOrder(6);

        assertEquals(order.getSize(), 1);
        assertEquals(order.getSize(), order.getOrder().size());

        result = ao.removeOrder(6);
        assertNull(result);
        assertNull(ao.getOrder(6));

        //invalid entry(doesn't exist such reservation ID)
        assertNull(ao.getOrder(200));

        Services.closeDataAccess();

        System.out.println("Finished Integration test of AccessOrders to persistence");
    }

    public void testAccessReservations()
    {
        AccessReservations ar;

        ArrayList<Reservation> reservations = new ArrayList<>();

        Reservation res;
        String result;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test of AccessReservations to persistence");

        Services.createDataAccess(Main.dbName);

        ar = new AccessReservations();

        assertTrue(reservations.size() == 0);

        result = ar.getReservations(reservations);
        assertNull(result);
        assertNotNull(reservations);
        assertTrue(reservations.size() > 0);

        res = ar.getRandom(3);
        assertNotNull(res);
        assertTrue(res.getRID() == 3);
        assertTrue(res.getCID() == 3);

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

        res = new Reservation(4,7,2,start,end);
        assertNotNull(res);

        int rid = ar.getNextReservationID();
        res.setRID(rid);
        assertEquals(res.getRID(), rid);

        result = ar.insertReservation(res);
        assertEquals("success", result);

        result = ar.getReservations(reservations);
        assertNull(result);

        res = ar.getRandom(rid);
        assertEquals(res.getRID() , rid);
        assertTrue(res.getCID() == 4);
        assertTrue(res.getNumPeople() == 2);

        //Tests updateReservation
        try
        {
            start = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1,10,0));
            end = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 12, 0));
        }
        catch (IllegalArgumentException e)
        {
            fail();
        }

        res = new Reservation(4, 7, 3, start, end);
        res.setRID(10);

        result = ar.updateReservation(res);
        assertNull(result);

        result = ar.getReservations(reservations);
        assertNull(result);
        assertEquals(reservations.get(10).getCID() , 4);
        assertEquals(reservations.get(10).getNumPeople() , 2);

        result = ar.deleteReservation(11);
        assertEquals("success", result);
        res = ar.getRandom(11);
        assertNull(res);

        reservations = ar.suggestReservations(start, end, 7);
        assertTrue(reservations.size() > 0);

        assertTrue(ar.getRandom(11) == null);

        Services.closeDataAccess();

        System.out.println("Finished Integration test of AccessReservations to persistence");
    }

    public void testAccessTables()
    {
        AccessTables at;
        Table table;
        Services.closeDataAccess();

        System.out.println("\nStarting Integration test of AccessTables to persistence");

        Services.createDataAccess(Main.dbName);

        at = new AccessTables();

        table = at.getRandom(7);
        assertNotNull(table);
        assertEquals(table.getTID(), 7);
        assertEquals(table.getCapacity(), 4);

        table = at.getRandom(12);
        assertNotNull(table);
        assertEquals(table.getTID(), 12);
        assertEquals(table.getCapacity(), 6);

        Services.closeDataAccess();

        System.out.println("Finished Integration test of AccessTables to persistence");
    }
}
