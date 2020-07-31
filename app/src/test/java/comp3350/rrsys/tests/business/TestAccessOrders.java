package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessOrders;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;
import comp3350.rrsys.persistence.DataAccessStub;

public class TestAccessOrders extends TestCase
{
    private AccessOrders accessOrders;
    private DataAccessStub accessStub;
    public TestAccessOrders(String arg0) { super(arg0); }

    public void setUp()
    {
        System.out.println("/nStarting TestAccessOrders");
        accessOrders = new AccessOrders(new DataAccessStub(Main.dbName));
        accessStub = new DataAccessStub();
        accessStub.open(Main.dbName);
    }

    public void tearDown()
    {
        System.out.println("\nTestAccessOrders");
    }

    public void testAccessOrdersConnection()
    {
        assertNotNull(accessOrders);
    }

    public void testInsertNullOrder()
    {
        Order newOrder = null;
        assertFalse(accessStub.insertOrder(newOrder));
    }

    public void testGetOrderInsert()
    {
        System.out.println("\nStarting testGetOrder");

        Order newOrder = new Order(1);
        Order newOrder1 = new Order(2);
        Order newOrder2 = new Order(3);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91 );
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57 );
        Item dish2 = new Item(3, "Turkey Burger", "Sandwich", "Turkey",12.99 );
        Item dish3 = new Item(4, "Turkey Burger", "Sandwich", "Turkey",12.77 );
        Item dish4 = new Item(5, "Turkey Burger", "Sandwich", "Turkey",12.73 );
        Item dish5 = new Item(6, "Turkey Burger", "Sandwich", "Turkey",12.22 );

        newOrder.addItem(dish);
        newOrder.addItem(dish1);
        newOrder.addItem(dish2);
        newOrder.addItem(dish3);
        newOrder.addItem(dish4);
        newOrder.addItem(dish5);

        newOrder1.addItem(dish);
        newOrder1.addItem(dish5);

        newOrder2.addItem(dish);
        newOrder2.addItem(dish3);
        newOrder2.addItem(dish5);

        assertTrue(accessStub.insertOrder(newOrder));
        assertTrue(accessStub.insertOrder(newOrder1));
        assertTrue(accessStub.insertOrder(newOrder2));

        System.out.println("\nEnding testGetOrder");
    }
}
