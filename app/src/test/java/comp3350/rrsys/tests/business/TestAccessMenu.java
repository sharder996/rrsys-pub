package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessMenu;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.persistence.DataAccessStub;

public class TestAccessMenu extends TestCase
{
    private AccessMenu accessMenu;
    private DataAccessStub accessStub;
    public TestAccessMenu(String arg0) { super(arg0); }

    public void setUp()
    {
        System.out.println("/nStarting TestAccessMenu");
        Main.startUp();
        accessMenu = new AccessMenu();
        accessStub = new DataAccessStub();
        accessStub.open(Main.dbName);
    }

    public void tearDown()
    {
        Main.shutDown();
        System.out.println("\nTestAccessOrders");
    }

    public void testAccessOrdersConnection()
    {
        assertNotNull(accessMenu);
    }

    public void testAccessMenuGetMenuByType()
    {
        System.out.println("\nStarting testAccessMenuGetMenuByType");

        ArrayList<String> types = accessStub.getMenuTypes();

        assertEquals("Salads", types.get(0));
        assertEquals("Sandwiches", types.get(1));
        assertEquals("Burgers", types.get(2));
        assertEquals("Mains", types.get(3));
        assertEquals("Desserts", types.get(4));
        assertEquals("Drinks", types.get(5));

        System.out.println("\nEnding testAccessMenuGetMenuByType");
    }

    public void testAccessCertainTypeMenu()
    {
        System.out.println("\nStarting testAccessCertainTypeMenu");

        /*
        There are total 44 items in database(Stub)
        6-salads, 8-Sandwiches, 8-Burgers, 8-Mains, 6-Desserts, 8-Drinks
         */
        ArrayList<String> types = accessStub.getMenuTypes();//list of all types

        ArrayList<Item> newitems = accessStub.getMenuByType(types.get(0));
        ArrayList<Item> newitems1 = accessStub.getMenuByType(types.get(1));
        ArrayList<Item> newitems2 = accessStub.getMenuByType(types.get(2));
        ArrayList<Item> newitems3 = accessStub.getMenuByType(types.get(3));
        ArrayList<Item> newitems4 = accessStub.getMenuByType(types.get(4));
        ArrayList<Item> newitems5 = accessStub.getMenuByType(types.get(5));

        assertNotNull(newitems);
        assertNotNull(newitems1);
        assertNotNull(newitems2);
        assertNotNull(newitems3);
        assertNotNull(newitems4);
        assertNotNull(newitems5);

        assertEquals(6 , newitems.size());
        assertEquals(8 , newitems1.size());
        assertEquals(8 , newitems2.size());
        assertEquals(8 , newitems3.size());
        assertEquals(6 , newitems4.size());
        assertEquals(8 , newitems5.size());

        System.out.println("\nEnding testAccessCertainTypeMenu");
    }

    public void testAccessInvalidEntryTypeMenu()
    {
        System.out.println("\nStarting testAccessInvalidEntryTypeMenu");

        ArrayList<Item> newitems = accessStub.getMenuByType("Chicken");

        assertEquals(0, newitems.size());

        System.out.println("\nEnding testAccessInvalidEntryTypeMenu");
    }
}
