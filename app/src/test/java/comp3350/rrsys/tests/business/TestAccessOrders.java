package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessOrders;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;

public class TestAccessOrders extends TestCase
{
    private AccessOrders accessOrders;
    public TestAccessOrders(String arg0) { super(arg0); }

    public void setUp()
    {
        System.out.println("/nStarting TestAccessorders");
        Main.startUp();
        accessOrders = new AccessOrders();
    }

    public void tearDown()
    {
        Main.shutDown();
        System.out.println("\nTestAccessOrders");
    }

    public void testAccessOrdersConnection()
    {
        assertNotNull(accessOrders);
    }

    public void testInsertNullItem()
    {
        Item newItem = null;
        Order newOrder = new Order(1);

        //ArrayList<Item> order = accessOrders.getOrder(1);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91 );
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57 );
        Item dish2 = new Item(3, "Turkey Burger", "Sandwich", "Turkey",12.99 );
        Item dish3 = new Item(4, "Turkey Burger", "Sandwich", "Turkey",12.77 );
        Item dish4 = new Item(5, "Turkey Burger", "Sandwich", "Turkey",12.73 );
        Item dish5 = new Item(6, "Turkey Burger", "Sandwich", "Turkey",12.22 );

        newOrder.addItem(dish);
        newOrder.addItem(dish1);

        ArrayList<Item> items = accessOrders.getOrder(1);

        assertEquals(2,items.size());
    }

}
