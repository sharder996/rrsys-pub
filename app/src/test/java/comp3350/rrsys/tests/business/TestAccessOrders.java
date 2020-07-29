package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import java.lang.reflect.Array;
import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessOrders;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;
import comp3350.rrsys.persistence.DataAccessStub;


public class TestAccessOrders extends TestCase
{
    private AccessOrders accessOrders;
    DataAccessStub accessStub;
    public TestAccessOrders(String arg0) { super(arg0); }

    public void setUp()
    {
        System.out.println("/nStarting TestAccessorders");
        Main.startUp();
        accessOrders = new AccessOrders();
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
        assertNotNull(accessOrders);
    }

    public void testInsertNullOrder()
    {
        Order newOrder = null;

        String result = accessStub.insertOrder(newOrder);
        assertEquals(result, "fail");
    }

    public void testgetOrder()
    {
        System.out.println("\nStarting testgetOrder");

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

        newOrder.addItem(dish);
        newOrder.addItem(dish5);

        newOrder.addItem(dish);
        newOrder.addItem(dish3);
        newOrder.addItem(dish5);

        String result = accessStub.insertOrder(newOrder);
        assertEquals(result, "success");

        result = accessStub.insertOrder(newOrder1);
        assertEquals(result, "success");

        result = accessStub.insertOrder(newOrder2);
        assertEquals(result, "success");

        ArrayList<Item> selectedItems = accessStub.getOrder(1);
        assertEquals(6, selectedItems.size());
        assertEquals(dish, selectedItems.get(0));
        assertEquals(dish1, selectedItems.get(1));
        assertEquals(dish2, selectedItems.get(2));
        assertEquals(dish3, selectedItems.get(3));
        assertEquals(dish4, selectedItems.get(4));
        assertEquals(dish5, selectedItems.get(5));

        selectedItems = accessStub.getOrder(2);
        assertEquals(2, selectedItems.size());
        assertEquals(dish, selectedItems.get(0));
        assertEquals(dish5, selectedItems.get(1));

        selectedItems = accessStub.getOrder(3);
        assertEquals(3, selectedItems.size());
        assertEquals(dish1, selectedItems.get(0));
        assertEquals(dish3, selectedItems.get(1));
        assertEquals(dish5, selectedItems.get(2));

        System.out.println("\nEnding testgetOrder");
    }

    public void testgetOrderNegativeValue()
    {
        System.out.println("\nStarting testgetOrderNegativeValue");
        ArrayList<Item> selectedItems = null;

        try
        {
            selectedItems = accessStub.getOrder(-1);
            fail();
        }
        catch(IllegalArgumentException e)
        {
            assertNull(selectedItems);
        }

        assertEquals(null, selectedItems);

        System.out.println("\nEnding testgetOrderNegativeValue");
    }

    public void testgetPrice()
    {
        System.out.println("\nStarting testgetPrice");

        Order newOrder = new Order(1);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91 );
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57 );
        Item dish2 = new Item(3, "Turkey Burger", "Sandwich", "Turkey",12.99 );
        Item dish3 = new Item(4, "Turkey Burger", "Sandwich", "Turkey",12.77 );
        Item dish4 = new Item(5, "Turkey Burger", "Sandwich", "Turkey",12.73 );
        Item dish5 = new Item(6, "Turkey Burger", "Sandwich", "Turkey",12.22 );

        double totalPrice = dish.getPrice() +dish1.getPrice() +dish2.getPrice() +dish3.getPrice() +dish4.getPrice() +dish5.getPrice();
        newOrder.addItem(dish);
        newOrder.addItem(dish1);
        newOrder.addItem(dish2);
        newOrder.addItem(dish3);
        newOrder.addItem(dish4);
        newOrder.addItem(dish5);

        String result = accessStub.insertOrder(newOrder);
        assertEquals(result, "success");

        double price = accessStub.getPrice(1);

        assertEquals(totalPrice , price);

        System.out.println("\nEnding testgetPrice");
    }

    public void testInvalidEntriesGetPrice()
    {
        System.out.println("\nStarting testInvalidEntriesGetPrice");

        double price = accessStub.getPrice(5);

        assertEquals(0.0, price);
        try
        {
            price = accessStub.getPrice(-2);
            fail();
        }
        catch(IllegalArgumentException e)
        {
            assertEquals(0.0, price);
        }

        System.out.println("\nEnding testInvalidEntriesGetPrice");
    }

    public void testgetSize()
    {
        System.out.println("\nStarting testgetSize");

        Order newOrder = new Order(1);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91 );
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57 );

        newOrder.addItem(dish);
        newOrder.addItem(dish1);

        String result = accessStub.insertOrder(newOrder);
        assertEquals(result, "success");

        assertEquals(2,accessStub.getSize(1));

        System.out.println("\nEnding testgetSize");
    }

    public void testInvalidEntriesgetSize()
    {
        System.out.println("\nStarting testInvalidEntriesgetSize");

        int size = 0;
        assertEquals(size, accessStub.getSize(10));

        try
        {
            size = accessStub.getSize(-2);
            fail();
        }
        catch(IllegalArgumentException e)
        {
            assertEquals(0, size);
        }
        System.out.println("\nEnding testInvalidEntriesgetSize");
    }
}
