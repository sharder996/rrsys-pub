package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

import java.text.NumberFormat;

import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Table;

public class TestItem extends TestCase
{
    public TestItem(String arg0) { super(arg0); }

    public void testItemCreation()
    {
        System.out.println("\nStarting TestItemCreation");

        Item dish = new Item(0, "Special Salads", "Salads", "red onion & toasted sunflower seeds",9.95 );

        assertNotNull(dish);
        assertEquals(0, dish.getItemID());
        assertEquals("Special Salads" , dish.getName());
        assertEquals("Salads", dish.getType());
        assertEquals("red onion & toasted sunflower seeds" , dish.getDetail());
        assertEquals(9.95, dish.getPrice());
        assertEquals(Item.MIN_QUANTITY, dish.getQuantity());
        assertEquals("", dish.getNote());

        System.out.println("\nEnding TestItemCreation");
    }

    public void testItemSetter()
    {
        System.out.println("\nStarting TestItemSetter");

        Item dish = new Item(0, "Turkey Burger", "Sandwich", "Turkey",12.95 );

        dish.setItemID(5);
        dish.setName("Turkey Sandwich");
        dish.setPrice(12.30);
        dish.setType("Sandwiches");
        dish.setDetail("Sandwich");
        dish.setQuantity(5);
        dish.setNote("More sause");

        assertEquals(5, dish.getItemID());
        assertEquals("Turkey Sandwich" , dish.getName());
        assertEquals("Sandwiches", dish.getType());
        assertEquals("Sandwich" , dish.getDetail());
        assertEquals(12.30, dish.getPrice());
        assertEquals(5, dish.getQuantity());
        assertEquals("More sause", dish.getNote());

        System.out.println("\nEnding TestItemSetter");
    }

    public void testNegativeItemID()
    {
        System.out.println("\nStarting TestNegativeItemID");

        Item dish = null;
        try
        {
            dish = new Item(-1, "Turkey Burger", "Sandwich", "Turkey",12.91 );
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(dish);
        }

        try
        {
            dish = new Item(-122, "Turkey Burger", "Sandwich", "Turkey",12.91 );
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(dish);
        }

        System.out.println("\nEnding TestNegativeItemID");
    }

    public void testItemPrice()
    {
        System.out.println("\nStarting TestItemPrice");

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91 );
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57 );
        Item dish2 = new Item(3, "Turkey Burger", "Sandwich", "Turkey",12.99 );
        Item dish3 = new Item(4, "Turkey Burger", "Sandwich", "Turkey",12.77 );
        Item dish4 = new Item(5, "Turkey Burger", "Sandwich", "Turkey",12.73 );
        Item dish5 = new Item(6, "Turkey Burger", "Sandwich", "Turkey",12.22 );

        assertNotNull(dish);
        assertNotNull(dish1);
        assertNotNull(dish2);
        assertNotNull(dish3);
        assertNotNull(dish4);
        assertNotNull(dish5);

        assertEquals(12.90, dish.getPrice());
        assertEquals(12.55, dish1.getPrice());
        assertEquals(13.00, dish2.getPrice());
        assertEquals(12.75, dish3.getPrice());
        assertEquals(12.75, dish4.getPrice());
        assertEquals(12.20, dish5.getPrice());

        dish.setPrice(13.76);
        dish1.setPrice(12.21);
        dish2.setPrice(19.99);
        dish3.setPrice(20.00);
        dish4.setPrice(13.01);
        dish5.setPrice(10.51);

        assertEquals(13.75, dish.getPrice());
        assertEquals(12.20, dish1.getPrice());
        assertEquals(20.00, dish2.getPrice());
        assertEquals(20.00, dish3.getPrice());
        assertEquals(13.00, dish4.getPrice());
        assertEquals(10.50, dish5.getPrice());

        System.out.println("\nEnding TestItemPrice");
    }

    public void testItemInvalidPriceEntries()
    {
        System.out.println("\nStarting TestItemInvalidPriceEntries");
        Item dish = null;

        try
        {
            dish = new Item(0, "Turkey Burger", "Sandwich", "Turkey",-12.91 );
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(dish);
        }

        try
        {
            dish = new Item(0, "Turkey Burger", "Sandwich", "Turkey",-16.99 );
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(dish);
        }

        try
        {
            dish = new Item(0, "Turkey Burger", "Sandwich", "Turkey",1000.91 );
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(dish);
        }

        try
        {
            dish = new Item(0, "Turkey Burger", "Sandwich", "Turkey",500.25 );
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(dish);
        }

        dish = new Item(0, "Turkey Burger", "Sandwich", "Turkey",70.25 );

        try
        {
            dish.setPrice(-20000.574856);
            fail();
        }
        catch(IllegalArgumentException e)
        {
            assertNotNull(dish);
        }

        try
        {
            dish.setPrice(5000.574856);
            fail();
        }
        catch(IllegalArgumentException e)
        {
            assertNotNull(dish);
        }

        System.out.println("\nEnding TestItemInvalidPriceEntries");
    }

    public void testNotes()
    {
        System.out.println("\nStarting TestNotes");

        Item dish = new Item(1, "Turkey Burger", "Sandwich", "Turkey",12.91);
        Item dish1 = new Item(2, "Turkey Burger", "Sandwich", "Turkey",12.57);

        assertEquals("", dish.getNote());
        assertEquals("", dish1.getNote());

        dish.setNote("Extra mustard");
        assertEquals("Extra mustard", dish.getNote());
        dish.setNote("Extra'mustard");
        assertEquals("Extra mustard", dish.getNote());
        dish.setNote("Extra-mustard");
        assertEquals("Extra mustard", dish.getNote());
        dish.setNote("Extra*mustard;");
        assertEquals("Extra mustard", dish.getNote());

        dish.setNote("Toasted");
        assertEquals("Toasted", dish.getNote());
        dish.setNote("Toasted***");
        assertEquals("Toasted", dish.getNote());
        dish.setNote("----Toasted**");
        assertEquals("Toasted", dish.getNote());

        System.out.println("\nEnding TestNotes");
    }
}
