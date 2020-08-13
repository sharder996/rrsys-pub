package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;

public class TestOrder extends TestCase
{
    public TestOrder(String arg0) { super(arg0); }

    public void testEmptyOrderCreation()
    {
        System.out.println("\nStarting TestEmptyOrderCreation");

        Order newOrder = new Order(1);

        assertNotNull(newOrder);
        assertNotNull(newOrder.getOrder());

        assertEquals(1, newOrder.getReservationID());
        assertEquals(0, newOrder.getSize());
        assertEquals(0, newOrder.getTotalPrice());

        System.out.println("\nEnding TestEmptyOrderCreation");
    }

    public void testInvalidReservationIDEntry()
    {
        System.out.println("\nStarting TestInvalidReservationIDEntry");

        Order newOrder = null;

        try
        {
            newOrder = new Order(-2);
            fail();
        }
        catch(IllegalArgumentException e)
        {
            assertNull(newOrder);
        }

        try
        {
            newOrder = new Order(-100);
            fail();
        }
        catch(IllegalArgumentException e)
        {
            assertNull(newOrder);
        }

        System.out.println("\nEnding TestInvalidReservationIDEntry");
    }

    public void testAddItem()
    {
        System.out.println("\nStarting TestAddItem");

        Order newOrder = new Order(1);
        assertNotNull(newOrder);

        Item dish = new Item(1,"SPECIAL SALAD","Salads","A",9.95);
        Item dish1 = new Item(2,"SPINACH SALAD","Salads","B",10.95);
        Item dish2 = new Item(3,"KALE SALAD","Salads","C",10.95);
        Item dish3 = new Item(4,"CAESAR SALAD","Salads","D",10.95 );
        Item dish4 = new Item(5,"ARUGULA SALAD","Salads","E",11.95 );
        Item dish5 = new Item(6,"AVOCADO SALAD","Salads", "avocado, brussels sprouts, radish.",12.95 );

        assertNotNull(dish);
        assertNotNull(dish1);
        assertNotNull(dish2);
        assertNotNull(dish3);
        assertNotNull(dish4);
        assertNotNull(dish5);
        assertEquals(0, newOrder.getSize());

        newOrder.addItem(dish);
        newOrder.addItem(dish1);
        newOrder.addItem(dish2);
        newOrder.addItem(dish3);
        newOrder.addItem(dish4);
        newOrder.addItem(dish5);
        assertEquals(6, newOrder.getSize());

        newOrder.addItem(dish,1,  "");
        newOrder.addItem(dish1, 2, "");
        newOrder.addItem(dish2, 2, "");
        assertEquals(9, newOrder.getSize());

        newOrder.addItem(dish3, 3, "");
        newOrder.addItem(dish4, 5, "");
        newOrder.addItem(dish5, 4, "");
        assertEquals(12, newOrder.getSize());

        System.out.println("\nEnding TestAddItem");
    }

    public void testDeleteItem()
    {
        System.out.println("\nStarting TestDeleteItem");

        Order newOrder = new Order(1);
        assertNotNull(newOrder);

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91 );
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57 );
        Item dish2 = new Item(31, "CHOCOLATE CAKE", "Desserts", "double-stacked dark chocolate cake", 8.00);
        Item dish3 = new Item(32, "CHOCOLATE TORTE", "Desserts", "Cream", 8.00);
        Item dish4 = new Item(33, "CARROT CAKE", "Desserts", "Two layers", 8.00);
        Item dish5 = new Item(34, "CHEESECAKE", "Desserts", "half chocolate, half vanilla", 8.00);
        assertEquals(0, newOrder.getSize());

        newOrder.addItem(dish);
        newOrder.addItem(dish1, 1, "");
        newOrder.addItem(dish2);
        newOrder.addItem(dish3, 3, "");
        newOrder.addItem(dish4);
        newOrder.addItem(dish5, 2, "");
        assertEquals(6, newOrder.getSize());

        newOrder.deleteItem(dish2);
        newOrder.deleteItem(dish1);
        newOrder.deleteItem(dish);
        assertEquals(3, newOrder.getSize());

        newOrder.deleteItem(dish3);
        assertEquals(2, newOrder.getSize());
        newOrder.deleteItem(dish3);
        assertEquals(2, newOrder.getSize());

        newOrder.deleteItem(dish4);
        assertEquals(1, newOrder.getSize());
        newOrder.deleteItem(dish5);
        assertEquals(0, newOrder.getSize());

        System.out.println("\nEnding TestDeleteItem");
    }

    public void testAddDeleteItem()
    {
        System.out.println("\nStarting TestAddDeleteItem");

        Order newOrder = new Order(1);
        assertNotNull(newOrder);

        Item dish = new Item(1,"SPECIAL SALAD","Salads","A",9.95);
        Item dish1 = new Item(2,"SPINACH SALAD","Salads","B",10.95);
        Item dish2 = new Item(3,"KALE SALAD","Salads","C",10.95);
        Item dish3 = new Item(4,"CAESAR SALAD","Salads","D",10.95);
        Item dish4 = new Item(5,"ARUGULA SALAD","Salads","E",11.95);
        Item dish5 = new Item(6,"AVOCADO SALAD","Salads", "F",12.95);
        assertEquals(0, newOrder.getSize());

        newOrder.addItem(dish,1,  "");
        newOrder.addItem(dish1, 2, "");
        newOrder.addItem(dish2, 2, "");
        newOrder.addItem(dish3, 3, "");
        assertEquals(4, newOrder.getSize());

        newOrder.deleteItem(dish);
        newOrder.deleteItem(dish1);
        assertEquals(2, newOrder.getSize());

        newOrder.deleteItem(dish4);
        assertEquals(2, newOrder.getSize());
        newOrder.deleteItem(dish3);
        assertEquals(1, newOrder.getSize());
        newOrder.deleteItem(dish3);
        assertEquals(1, newOrder.getSize());
        newOrder.deleteItem(dish2);
        assertEquals(0, newOrder.getSize());

        newOrder.addItem(dish4, 5, "");
        newOrder.addItem(dish5, 4, "");
        assertEquals(2, newOrder.getSize());

        newOrder.deleteItem(dish4);
        assertEquals(1, newOrder.getSize());
        newOrder.addItem(dish4, 1, "");
        assertEquals(2, newOrder.getSize());
        newOrder.deleteItem(dish5);
        assertEquals(1, newOrder.getSize());
        newOrder.deleteItem(dish4);
        assertEquals(0, newOrder.getSize());

        System.out.println("\nEnding TestAddDeleteItem");
    }

    public void testTotalPrice()
    {
        System.out.println("\nStarting TestTotalPrice");

        Order newOrder = new Order(11);
        assertNotNull(newOrder);

        Item dish = new Item(1,"SPECIAL SALAD","Salads","A",9.95);
        Item dish1 = new Item(2,"SPINACH SALAD","Salads","B",10.95);
        Item dish2 = new Item(3,"KALE SALAD","Salads","C",10.95);
        Item dish3 = new Item(4,"CAESAR SALAD","Salads","D",10.95);
        Item dish4 = new Item(5,"ARUGULA SALAD","Salads","E",11.95);
        Item dish5 = new Item(6,"AVOCADO SALAD","Salads", "F",12.95);
        assertEquals(0, newOrder.getSize());

        newOrder.addItem(dish, 1, "");
        newOrder.addItem(dish1, 2, "");

        double totalPrice = dish.getPrice()*dish.getQuantity() + dish1.getPrice()*dish1.getQuantity();
        assertEquals(totalPrice, newOrder.getTotalPrice());

        newOrder.addItem(dish2, 2, "");
        newOrder.addItem(dish3, 4, "");
        newOrder.addItem(dish4, 1, "");
        newOrder.addItem(dish5, 3, "");

        totalPrice += dish2.getPrice()*dish2.getQuantity() + dish3.getPrice()*dish3.getQuantity()
                + dish4.getPrice()*dish4.getQuantity() + dish5.getPrice()*dish5.getQuantity();
        assertEquals(totalPrice, newOrder.getTotalPrice());

        newOrder.deleteItem(dish4);
        newOrder.deleteItem(dish5);
        totalPrice -= (dish4.getPrice()*dish4.getQuantity() + dish5.getPrice()*dish5.getQuantity());
        assertEquals(totalPrice, newOrder.getTotalPrice());

        newOrder.deleteItem(dish5);
        assertEquals(totalPrice, newOrder.getTotalPrice());
        newOrder.deleteItem(dish4);
        assertEquals(totalPrice, newOrder.getTotalPrice());
        newOrder.deleteItem(dish3);
        totalPrice -= dish3.getPrice()*dish3.getQuantity();
        assertEquals(totalPrice, newOrder.getTotalPrice());

        System.out.println("\nEnding TestTotalPrice");
    }
}
