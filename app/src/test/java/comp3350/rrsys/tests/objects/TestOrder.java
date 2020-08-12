package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;

public class TestOrder extends TestCase
{
    public TestOrder(String arg0) { super(arg0); }

    public void testOrderCreation()
    {

        System.out.println("\nStarting TestOrderCreation");

        Order newOrder = new Order(1);

        assertNotNull(newOrder);

        Item dish = new Item(1,"SPECIAL SALAD","Salads","A",9.95);
        Item dish1 = new Item(2,"SPINACH SALAD","Salads","B",10.95);
        Item dish2 = new Item(3,"KALE SALAD","Salads","C",10.95);
        Item dish3 = new Item(4,"CAESAR SALAD","Salads","D",10.95 );
        Item dish4 = new Item(5,"ARUGULA SALAD","Salads","E",11.95 );
        Item dish5 = new Item(6,"AVOCADO SALAD","Salads",
                "avocado, brussels sprouts, radish, alfalfa sprouts, chickpeas.",12.95 );

        assertNotNull(dish);
        assertNotNull(dish1);
        assertNotNull(dish2);
        assertNotNull(dish3);
        assertNotNull(dish4);
        assertNotNull(dish5);

        newOrder.addItem(dish, "");
        newOrder.addItem(dish1, "");
        newOrder.addItem(dish2, "");
        newOrder.addItem(dish3, "");
        newOrder.addItem(dish4, "");
        newOrder.addItem(dish5, "");

        assertEquals(6, newOrder.size());
        assertEquals(1, newOrder.getReservationID());

        newOrder.setNote("Allergy", 2);
        assertEquals("Allergy", newOrder.getNote(2));

        assertEquals(dish, newOrder.getOrder().get(0));

        newOrder.deleteItem(newOrder.getOrder().get(0).getLineItem());
        newOrder.deleteItem(newOrder.getOrder().get(0).getLineItem());

        assertEquals(4, newOrder.size());
        assertEquals(dish2, newOrder.getOrder().get(0));

        System.out.println("\nEnding TestOrderCreation");
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

    public void testOrderNotes()
    {
        System.out.println("\nStarting testNotes");

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91 );
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57 );
        assertNotNull(dish);
        assertNotNull(dish1);

        Order newOrder = new Order(10);
        assertNotNull(newOrder);

        newOrder.addItem(dish, "");
        newOrder.addItem(dish1, "");

        newOrder.setNote("Extra mustard", 1);
        assertEquals("Extra mustard", newOrder.getNote(1));

        assertEquals("", newOrder.getNote(2));

        newOrder.setNote("Toasted", 1);
        assertEquals("Toasted", newOrder.getNote(1));

        System.out.println("\nEnding testNotes");

    }

    public void testGetPrice()
    {
        System.out.println("\nStarting testGetPrice");

        Order newOrder = new Order(11);

        assertNotNull(newOrder);

        Item dish = new Item(1,"SPECIAL SALAD","Salads","A",9.95);
        Item dish1 = new Item(2,"SPINACH SALAD","Salads","B",10.95);
        Item dish2 = new Item(3,"KALE SALAD","Salads","C",10.95);
        Item dish3 = new Item(4,"CAESAR SALAD","Salads","D",10.95);
        Item dish4 = new Item(5,"ARUGULA SALAD","Salads","E",11.95);
        Item dish5 = new Item(6,"AVOCADO SALAD","Salads",
                "avocado, brussels sprouts, radish, alfalfa sprouts, chickpeas.",12.95);

        assertNotNull(dish);
        assertNotNull(dish1);
        assertNotNull(dish2);
        assertNotNull(dish3);
        assertNotNull(dish4);
        assertNotNull(dish5);

        newOrder.addItem(dish, "");
        newOrder.addItem(dish1, "");

        double totalPrice = dish.getPrice() +dish1.getPrice();
        assertEquals(totalPrice, newOrder.getTotalPrice());

        newOrder.addItem(dish2, "");
        newOrder.addItem(dish3, "");
        newOrder.addItem(dish4, "");
        newOrder.addItem(dish5, "");

        totalPrice = totalPrice +dish2.getPrice() +dish3.getPrice() +dish4.getPrice() +dish5.getPrice();
        assertEquals(totalPrice, newOrder.getTotalPrice());

        System.out.println("\nEnding testGetPrice");
    }
}
