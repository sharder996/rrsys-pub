package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;


import java.util.ArrayList;

import comp3350.rrsys.objects.Menu;
import comp3350.rrsys.objects.Item;

public class TestMenu extends TestCase{
    public TestMenu(String arg0) { super(arg0); }

    public void testMenuCreation(){
        System.out.println("\nStarting testMenuCreation");

        Menu list = new Menu();

        assertNotNull(list);

        Item dish = new Item(0, "Turkey Burger", "Sandwiches", "Turkey",12.91 );

        Item dish1 = new Item(1, "Turkey Burger", "Burgers", "Turkey",7.75 );


        try {
            list.addItem(dish);
        } catch (Exception e) {
           assertNull(list.getMenu());
        }

        ArrayList<Item> MainsList = list.getType("Sandwiches");
        assertTrue(MainsList.get(0).equals(dish));

        try {
            list.addItem(dish1);
        } catch (Exception e) {
            assertNotSame(dish1,list.getMenu().get(0));
        }
        ArrayList<Item> SandwichList = list.getType("Sandwiches");
        assertTrue(SandwichList.get(0).equals(dish));

        ArrayList<Item> menu = list.getMenu();
        assertNotNull(menu);

        System.out.println("\nEnding testMenuCreation");

    }

    public void testMenuDuplicate(){
        System.out.println("\nStarting testMenuDuplicate");

        Item dish = new Item(0, "Turkey Burger", "Sandwiches", "Turkey",12.91 );
        Item dish1 = new Item(1, "Turkey Burger", "Burgers", "Turkey",7.75 );
        Item dish2 = new Item(0, "Turkey Burger", "Sandwiches", "Turkey",12.91 );

        Menu list = new Menu();

        assertNotNull(list);
        assertEquals(0, list.getMenu().size());

        try {
            list.addItem(dish);
            list.addItem(dish1);
            list.addItem(dish2);
        } catch (Exception e) {
            assertEquals(2, list.getMenu().size());
        }


        ArrayList<Item> menu = list.getMenu();

        assertEquals(2,menu.size()); //dish2 is not added to the menu list.
        assertTrue(menu.get(0).equal(dish));
        assertTrue(menu.get(1).equal(dish1));
        assertTrue(menu.get(0).equal(dish2));

        System.out.println("\nEnding testMenuDuplicate");

    }

}
