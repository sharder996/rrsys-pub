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
    public TestAccessOrders(String arg0) { super(arg0); }

    public void setUp()
    {
        System.out.println("\nStarting TestAccessOrders");
        accessOrders = new AccessOrders(new DataAccessStub(Main.dbName));
    }

    public void tearDown()
    {
        System.out.println("\nTestAccessOrders");
    }

    public void testAccessOrdersConnection()
    {
        assertNotNull(accessOrders);
    }

    public void testGetOrder()
    {
        System.out.println("\nStarting testGetOrder");

        Order newOrder = new Order(0);
        Order newOrder1 = new Order(1);
        Order newOrder2 = new Order(2);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91);
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57);
        Item dish2 = new Item(3, "Turkey Burger", "Sandwich", "Turkey",12.99);
        Item dish3 = new Item(4, "Turkey Burger", "Sandwich", "Turkey",12.77);
        Item dish4 = new Item(5, "Turkey Burger", "Sandwich", "Turkey",12.73);
        Item dish5 = new Item(6, "Turkey Burger", "Sandwich", "Turkey",12.22);

        newOrder.addItem(dish, "");
        newOrder.addItem(dish1, "");
        newOrder.addItem(dish2, "");
        newOrder.addItem(dish3, "");
        newOrder.addItem(dish4, "");
        newOrder.addItem(dish5, "");

        String result = accessOrders.insertItemNewOrder(newOrder.getOrder(), newOrder.getReservationID());

        assertNull(result);

        Order selectedItems = accessOrders.getOrder(newOrder.getReservationID());

        assertEquals(6, selectedItems.size());
        assertEquals(dish.toString(), selectedItems.getOrder().get(0).toString());
        assertEquals(dish1.toString(), selectedItems.getOrder().get(1).toString());
        assertEquals(dish2.toString(), selectedItems.getOrder().get(2).toString());
        assertEquals(dish3.toString(), selectedItems.getOrder().get(3).toString());
        assertEquals(dish4.toString(), selectedItems.getOrder().get(4).toString());
        assertEquals(dish5.toString(), selectedItems.getOrder().get(5).toString());

        newOrder1.addItem(dish, "");
        newOrder1.addItem(dish5, "");

        result = accessOrders.insertItemNewOrder(newOrder1.getOrder(), newOrder1.getReservationID());
        assertNull(result);

        selectedItems.getOrder().clear();
        selectedItems = accessOrders.getOrder(newOrder1.getReservationID());
        assertEquals(2, selectedItems.size());
        assertEquals(dish, selectedItems.getOrder().get(0));
        assertEquals(dish5, selectedItems.getOrder().get(1));

        newOrder2.addItem(dish, "");
        newOrder2.addItem(dish3, "");
        newOrder2.addItem(dish5, "");

        result = accessOrders.insertItemNewOrder(newOrder2.getOrder(), newOrder2.getReservationID());
        assertNull(result);

        selectedItems.getOrder().clear();
        selectedItems = accessOrders.getOrder(newOrder2.getReservationID());
        assertEquals(3, selectedItems.size());
        assertEquals(dish, selectedItems.getOrder().get(0));
        assertEquals(dish3, selectedItems.getOrder().get(1));
        assertEquals(dish5, selectedItems.getOrder().get(2));

        System.out.println("\nEnding testGetOrder");
    }

    public void testGetOrderNegativeValue()
    {
        System.out.println("\nStarting testGetOrderNegativeValue");
        Order selectedItems = null;

        try
        {
            selectedItems = accessOrders.getOrder(-1);
            fail();
        }
        catch(IllegalArgumentException e)
        {
            assertNull(selectedItems);
        }

        assertEquals(null, selectedItems);

        System.out.println("\nEnding testGetOrderNegativeValue");
    }

    public void testGetPrice()
    {
        System.out.println("\nStarting testGetPrice");

        Order newOrder = new Order(1);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91);
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57);
        Item dish2 = new Item(3, "Turkey Burger", "Sandwich", "Turkey",12.99);
        Item dish3 = new Item(4, "Turkey Burger", "Sandwich", "Turkey",12.77);
        Item dish4 = new Item(5, "Turkey Burger", "Sandwich", "Turkey",12.73);
        Item dish5 = new Item(6, "Turkey Burger", "Sandwich", "Turkey",12.22);

        double totalPrice = dish.getPrice() +dish1.getPrice() +dish2.getPrice() +dish3.getPrice() +dish4.getPrice() +dish5.getPrice();
        newOrder.addItem(dish, "");
        newOrder.addItem(dish1, "");
        newOrder.addItem(dish2, "");
        newOrder.addItem(dish3, "");
        newOrder.addItem(dish4, "");
        newOrder.addItem(dish5, "");

        String result = accessOrders.insertItemNewOrder(newOrder.getOrder(), newOrder.getReservationID());
        assertNull(result);

        double price = accessOrders.getPrice(newOrder.getReservationID());

        assertEquals(totalPrice , price);

        System.out.println("\nEnding testGetPrice");
    }

    public void testInvalidEntriesGetPrice()
    {
        System.out.println("\nStarting testInvalidEntriesGetPrice");

        double price = accessOrders.getPrice(5);

        assertEquals(0.0, price);
        try
        {
            price = accessOrders.getPrice(-2);
            fail();
        }
        catch(IllegalArgumentException e)
        {
            assertEquals(0.0, price);
        }

        System.out.println("\nEnding testInvalidEntriesGetPrice");
    }

    public void testGetSize()
    {
        System.out.println("\nStarting testGetSize");

        Order newOrder = new Order(3);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91);
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57);

        newOrder.addItem(dish, "");
        newOrder.addItem(dish1, "");

        String result = accessOrders.insertItemNewOrder(newOrder.getOrder(), newOrder.getReservationID());
        assertNull(result);

        assertEquals(2,accessOrders.getSize(newOrder.getReservationID()));

        result = accessOrders.insertItemExistingOrder(newOrder.getReservationID(), dish, "");
        assertEquals(3,accessOrders.getSize(newOrder.getReservationID()));

        result = accessOrders.removeItemFromOrder(newOrder.getReservationID(), newOrder.getItem(1).getLineItem());
        assertEquals(2,accessOrders.getSize(newOrder.getReservationID()));

        System.out.println("\nEnding testGetSize");
    }

    public void testInvalidEntriesGetSize()
    {
        System.out.println("\nStarting testInvalidEntriesGetSize");

        int size = 0;

        try
        {
            assertNull(accessOrders.getSize(100));
        }
        catch(Exception e)
        {
            assertEquals(0, size);
        }

        try
        {
            size = accessOrders.getSize(-100);
            fail();
        }
        catch(IllegalArgumentException e)
        {
            assertEquals(0, size);
        }
        System.out.println("\nEnding testInvalidEntriesGetSize");
    }

    public void testUpdateNote()
    {
        System.out.println("\nStarting testUpdateNote");

        Order newOrder = new Order(6);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91);
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57);

        newOrder.addItem(dish, "");
        newOrder.addItem(dish1, "");

        String result = accessOrders.insertItemNewOrder(newOrder.getOrder(), newOrder.getReservationID());
        assertNull(result);

        assertEquals("",accessOrders.getOrder(newOrder.getReservationID()).getNote(0));
        assertEquals("",accessOrders.getOrder(newOrder.getReservationID()).getNote(1));

        System.out.println("\nEnding testUpdateNote");
    }

    public void testGetNextReservationID()
    {
        System.out.println("\nStarting testGetNextReservationID");
        assertEquals(0, accessOrders.getNextReservationID());

        Order newOrder = new Order(7);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91);
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57);

        newOrder.addItem(dish, "");
        newOrder.addItem(dish1, "");

        String result = accessOrders.insertItemNewOrder(newOrder.getOrder(), newOrder.getReservationID());
        assertNull(result);

        assertEquals(8, accessOrders.getNextReservationID());

        System.out.println("\nEnding testGetNextReservationID");
    }

    public void testGetNextLineitem()
    {
        System.out.println("\nStarting testGetNextLineitem");

        Order newOrder = new Order(1);

        assertNotNull(newOrder);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91);
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57);

        newOrder.addItem(dish, "");
        newOrder.addItem(dish1, "");

        accessOrders.insertOrder(newOrder);

        assertEquals(true, accessOrders.getNextLineitem(1));

        assertEquals(false, accessOrders.getNextLineitem(8));
        System.out.println("\nEnding testGetNextLineitem");
    }
}
