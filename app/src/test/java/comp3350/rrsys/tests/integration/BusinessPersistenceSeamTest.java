package comp3350.rrsys.tests.integration;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.business.AccessCustomers;
import comp3350.rrsys.business.AccessMenu;
import comp3350.rrsys.business.AccessOrders;
import comp3350.rrsys.business.AccessReservations;
import comp3350.rrsys.business.AccessTables;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;
import comp3350.rrsys.objects.Reservation;

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

        /* is there any way to test AccessCustomer?
        Services.createDataAccess(Main.dbName);

        ac = new AccessCustomers();

        result = ac.insertCustomer(new Customer("Jim", "Jam", "204-956-1203"));

        assertNull(result);
        */

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
        String result;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test of AccessReservations to persistence");

        Services.createDataAccess(Main.dbName);

        ar = new AccessReservations();

        result = ar.getReservations(reservations);
        assertNull(result);

        Services.closeDataAccess();

        System.out.println("Finished Integration test of AccessReservations to persistence");
    }

    public void testAccessTables()
    {
        AccessTables at;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test of AccessTables to persistence");

        Services.createDataAccess(Main.dbName);

        at = new AccessTables();

        /*
           Todo
           Test code
         */


        Services.closeDataAccess();

        System.out.println("Finished Integration test of AccessTables to persistence");
    }
}
