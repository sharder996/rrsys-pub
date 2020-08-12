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

        Services.closeDataAccess();

        System.out.println("Finished Integration test of AccessMenu to persistence");
    }

    public void testAccessOrders()
    {
        AccessOrders ao;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test of AccessOrders to persistence");

        Services.createDataAccess(Main.dbName);

        ao = new AccessOrders();

        /*
           Todo
           Test code
        */

        Services.closeDataAccess();

        System.out.println("Finished Integration test of AccessOrders to persistence");
    }

    public void testAccessReservations()
    {
        AccessReservations ar;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test of AccessReservations to persistence");

        Services.createDataAccess(Main.dbName);

        ar = new AccessReservations();

        /*
           Todo
           Test code
         */
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
