package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

import java.text.NumberFormat;

import comp3350.rrsys.objects.Item;

public class TestItem extends TestCase{

    public TestItem(String arg0) { super(arg0); }

    public void testItemCreation(){

        System.out.println("\nStarting testItemCreation");

        Item dish = new Item("broth", "breakfast", "clear soup", 7.75);
        dish.setItemID();

        assertNotNull(dish);
        assertEquals(0, dish.getItemID());
        assertEquals("broth" , dish.getName());
        assertEquals("breakfast", dish.getType());
        assertEquals("clear soup" , dish.getDetail());
        assertEquals(7.75, dish.getPrice());

        System.out.println("\nEnding testItemCreation");
    }

    public void testItemSetter(){

        System.out.println("\nStarting testItemSetter");

        Item dish = new Item("Bacon Sandwich", "lunch", "Sandwich", 13.25);
        dish.setItemID();

        dish.setItemID(5);
        dish.setName("Turkey Sandwich");
        dish.setPrice(12.25);
        dish.setType("breakfast");
        dish.setDetail("Burger");

        assertEquals(5, dish.getItemID());
        assertEquals("Turkey Sandwich" , dish.getName());
        assertEquals("breakfast", dish.getType());
        assertEquals("Burger" , dish.getDetail());
        assertEquals(12.25, dish.getPrice());

        double d = 2.3d;
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String output = formatter.format(d);
        System.out.println(output);

        System.out.println("\nEnding testItemSetter");
    }

    public void testItemNegativePrice(){

    }

}
