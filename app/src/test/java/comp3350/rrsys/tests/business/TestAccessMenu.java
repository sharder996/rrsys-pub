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

    public TestAccessMenu(String arg0) { super(arg0); }

    public void setUp()
    {
        System.out.println("\nStarting TestAccessMenu");
        accessMenu = new AccessMenu(new DataAccessStub(Main.dbName));
    }

    public void tearDown()
    {
        System.out.println("\nTestAccessMenu");
    }

    public void testAccessOrdersConnection()
    {
        assertNotNull(accessMenu);
    }

    public void testAccessMenuGetMenuByType()
    {
        System.out.println("\nStarting TestAccessMenuGetMenuByType");

        ArrayList<String> types = accessMenu.getMenuTypes();

        assertNotNull(types);
        assertEquals(6, types.size());
        assertEquals("Salads", types.get(0));
        assertEquals("Sandwiches", types.get(1));
        assertEquals("Burgers", types.get(2));
        assertEquals("Mains", types.get(3));
        assertEquals("Desserts", types.get(4));
        assertEquals("Drinks", types.get(5));

        System.out.println("\nEnding TestAccessMenuGetMenuByType");
    }

    public void testAccessCertainTypeMenu()
    {
        System.out.println("\nStarting TestAccessCertainTypeMenu");

        /*
        There are total 44 items in database(Stub)
        6-salads, 8-Sandwiches, 8-Burgers, 8-Mains, 6-Desserts, 8-Drinks
         */
        ArrayList<String> types = accessMenu.getMenuTypes();//list of all types

        ArrayList<Item> newitems = accessMenu.getMenuByType(types.get(0));
        ArrayList<Item> newitems1 = accessMenu.getMenuByType(types.get(1));
        ArrayList<Item> newitems2 = accessMenu.getMenuByType(types.get(2));
        ArrayList<Item> newitems3 = accessMenu.getMenuByType(types.get(3));
        ArrayList<Item> newitems4 = accessMenu.getMenuByType(types.get(4));
        ArrayList<Item> newitems5 = accessMenu.getMenuByType(types.get(5));

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

        System.out.println("\nEnding TestAccessCertainTypeMenu");
    }

    public void testAccessInvalidEntryTypeMenu()
    {
        System.out.println("\nStarting TestAccessInvalidEntryTypeMenu");

        ArrayList<Item> newitems = accessMenu.getMenuByType("Chicken");

        assertEquals(0, newitems.size());

        System.out.println("\nEnding TestAccessInvalidEntryTypeMenu");
    }
}
