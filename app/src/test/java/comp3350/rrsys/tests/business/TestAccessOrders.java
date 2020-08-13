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
        System.out.println("\nStarting TestGetOrder");

        Order newOrder = new Order(5);
        Order newOrder1 = new Order(6);
        Order newOrder2 = new Order(7);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91);
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57);
        Item dish2 = new Item(3, "Turkey Burger", "Sandwich", "Turkey",12.99);
        Item dish3 = new Item(4, "Turkey Burger", "Sandwich", "Turkey",12.77);
        Item dish4 = new Item(5, "Turkey Burger", "Sandwich", "Turkey",12.73);
        Item dish5 = new Item(6, "Turkey Burger", "Sandwich", "Turkey",12.22);

        newOrder.addItem(dish, 1, "");
        newOrder.addItem(dish1, 2, "");
        newOrder.addItem(dish2, 1, "");
        newOrder.addItem(dish3, 3, "");
        newOrder.addItem(dish4, 4, "");
        newOrder.addItem(dish5, 6, "");

        String result = accessOrders.insertOrder(newOrder);
        assertNull(result);

        Order selectedItems = accessOrders.getOrder(newOrder.getReservationID());

        assertEquals(6, selectedItems.getSize());
        assertEquals(dish, selectedItems.getOrder().get(0));
        assertEquals(dish1, selectedItems.getOrder().get(1));
        assertEquals(dish2, selectedItems.getOrder().get(2));
        assertEquals(dish3, selectedItems.getOrder().get(3));
        assertEquals(dish4, selectedItems.getOrder().get(4));
        assertEquals(dish5, selectedItems.getOrder().get(5));

        newOrder1.addItem(dish, 3, "");
        newOrder1.addItem(dish5, 5, "");

        result = accessOrders.insertOrder(newOrder1);
        assertNull(result);

        selectedItems = accessOrders.getOrder(newOrder1.getReservationID());
        assertEquals(2, selectedItems.getSize());
        assertEquals(dish, selectedItems.getOrder().get(0));
        assertEquals(dish5, selectedItems.getOrder().get(1));

        newOrder2.addItem(dish, 4, "");
        newOrder2.addItem(dish3, 4, "");
        newOrder2.addItem(dish5, 2, "");

        result = accessOrders.insertOrder(newOrder2);
        assertNull(result);

        selectedItems = accessOrders.getOrder(newOrder2.getReservationID());
        assertEquals(3, selectedItems.getSize());
        assertEquals(dish, selectedItems.getOrder().get(0));
        assertEquals(dish3, selectedItems.getOrder().get(1));
        assertEquals(dish5, selectedItems.getOrder().get(2));

        System.out.println("\nEnding TestGetOrder");
    }

    public void testGetOrderNegativeValue()
    {
        System.out.println("\nStarting TestGetOrderNegativeValue");
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

        System.out.println("\nEnding TestGetOrderNegativeValue");
    }

    public void testRemoveOrder()
    {
        System.out.println("\nStarting TestRemoveOrder");

        Order newOrder = new Order(8);
        Order newOrder1 = new Order(9);
        Order newOrder2 = new Order(10);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91);
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57);
        Item dish2 = new Item(3, "Turkey Burger", "Sandwich", "Turkey",12.99);

        newOrder.addItem(dish, 1, "");
        newOrder1.addItem(dish1, 2, "");
        newOrder2.addItem(dish2, 1, "");

        String result = accessOrders.insertOrder(newOrder);
        assertNull(result);
        result = accessOrders.insertOrder(newOrder1);
        assertNull(result);
        result = accessOrders.insertOrder(newOrder2);
        assertNull(result);

        Order selectedItems = accessOrders.getOrder(newOrder.getReservationID());
        assertNotNull(selectedItems);
        selectedItems = accessOrders.getOrder(newOrder1.getReservationID());
        assertNotNull(selectedItems);
        selectedItems = accessOrders.getOrder(newOrder2.getReservationID());
        assertNotNull(selectedItems);

        accessOrders.removeOrder(newOrder.getReservationID());
        accessOrders.removeOrder(newOrder1.getReservationID());
        accessOrders.removeOrder(newOrder2.getReservationID());

        selectedItems = accessOrders.getOrder(newOrder.getReservationID());
        assertNull(selectedItems);
        selectedItems = accessOrders.getOrder(newOrder1.getReservationID());
        assertNull(selectedItems);
        selectedItems = accessOrders.getOrder(newOrder2.getReservationID());
        assertNull(selectedItems);

        System.out.println("\nEnding TestRemoveOrder");
    }

    public void testGetPrice()
    {
        System.out.println("\nStarting TestGetPrice");

        Order newOrder = new Order(11);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91);
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57);
        Item dish2 = new Item(3, "Turkey Burger", "Sandwich", "Turkey",12.99);
        Item dish3 = new Item(4, "Turkey Burger", "Sandwich", "Turkey",12.77);
        Item dish4 = new Item(5, "Turkey Burger", "Sandwich", "Turkey",12.73);
        Item dish5 = new Item(6, "Turkey Burger", "Sandwich", "Turkey",12.22);

        newOrder.addItem(dish, 2, "");
        newOrder.addItem(dish1, 3, "");
        newOrder.addItem(dish2, 4, "");
        newOrder.addItem(dish3, 1, "");

        double totalPrice = dish.getPrice() * dish.getQuantity() + dish1.getPrice() * dish1.getQuantity()
                + dish2.getPrice() * dish2.getQuantity() + dish3.getPrice() * dish3.getQuantity();
        totalPrice = Math.round(totalPrice * 100.0) / 100.0;

        String result = accessOrders.insertOrder(newOrder);
        assertNull(result);

        double price = accessOrders.getPrice(newOrder.getReservationID());
        assertEquals(totalPrice, price);

        newOrder.getOrder().clear();
        newOrder.addItem(dish4, 2, "");
        newOrder.addItem(dish5, 6, "");
        result = accessOrders.insertOrder(newOrder);
        assertNull(result);

        totalPrice += dish4.getPrice() * dish4.getQuantity() + dish5.getPrice() * dish5.getQuantity();
        totalPrice = Math.round(totalPrice * 100.0) / 100.0;

        price = accessOrders.getPrice(newOrder.getReservationID());
        assertEquals(totalPrice , price);

        System.out.println("\nEnding TestGetPrice");
    }

    public void testInvalidEntriesGetPrice()
    {
        System.out.println("\nStarting testInvalidEntriesGetPrice");

        double price = accessOrders.getPrice(15);

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
        System.out.println("\nStarting TestGetSize");

        Order newOrder = new Order(8);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91);
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57);
        Item dish2 = new Item(4,"CAESAR SALAD","Salads","D",10.95);
        Item dish3 = new Item(5,"ARUGULA SALAD","Salads","E",11.95);
        Item dish4 = new Item(6,"AVOCADO SALAD","Salads", "F",12.95);
        Item dish5 = new Item(3,"KALE SALAD","Salads","C",10.95);

        newOrder.addItem(dish);
        newOrder.addItem(dish1);
        newOrder.addItem(dish2);
        newOrder.addItem(dish3);

        String result = accessOrders.insertOrder(newOrder);
        assertNull(result);
        assertEquals(4, accessOrders.getSize(newOrder.getReservationID()));

        newOrder.getOrder().clear();
        newOrder.addItem(dish4);
        result = accessOrders.insertOrder(newOrder);
        assertNull(result);
        assertEquals(5, accessOrders.getSize(newOrder.getReservationID()));

        newOrder.getOrder().clear();
        newOrder.addItem(dish5);
        result = accessOrders.insertOrder(newOrder);
        assertNull(result);
        assertEquals(6, accessOrders.getSize(newOrder.getReservationID()));

        System.out.println("\nEnding TestGetSize");
    }

    public void testInvalidEntriesGetSize()
    {
        System.out.println("\nStarting TestInvalidEntriesGetSize");

        int size = 0;

        try
        {
            size = accessOrders.getSize(100);
            fail();
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

        System.out.println("\nEnding TestInvalidEntriesGetSize");
    }
}
