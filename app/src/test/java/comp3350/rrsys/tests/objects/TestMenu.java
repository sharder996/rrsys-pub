package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;


import java.util.ArrayList;

import comp3350.rrsys.objects.Menu;
import comp3350.rrsys.objects.Item;

public class TestMenu extends TestCase{
    public TestMenu(String arg0) { super(arg0); }

    /*public void testMenuCreation(){
        System.out.println("\nStarting testMenuCreation");

        Menu list = new Menu();

        assertNotNull(list);

        Item dish = new Item("broth", "Mains", "clear soup", 7.75);
        Item dish1 = new Item("broth", "Sandwiches", "clear soup", 7.75);

        list.addItem(dish);

        ArrayList<Item> MainsList = list.getType("Mains");
        assertTrue(MainsList.get(0).equals(dish));

        list.addItem(dish1);
        ArrayList<Item> SandwichList = list.getType("Sandwiches");
        assertTrue(SandwichList.get(0).equals(dish1));

        ArrayList<Item> menu = list.getMenu();
        assertNotNull(menu);

        System.out.println("\nEnding testMenuCreation");

    }

    public void testMenuDuplicate(){
        System.out.println("\nStarting testMenuDuplicate");

        Item dish = new Item("broth", "Mains", "clear soup", 7.75);
        Item dish1 = new Item("broth", "Sandwiches", "clear soup", 7.75);
        Item dish2 = new Item("broth", "Mains", "clear soup", 7.75);

        Menu list = new Menu();

        assertNotNull(list);

        list.addItem(dish);
        list.addItem(dish1);
        list.addItem(dish2);

        ArrayList<Item> menu = list.getMenu();

        assertEquals(2,menu.size());
        assertTrue(menu.get(0).equal(dish));
        assertTrue(menu.get(1).equal(dish1));
        //assert(menu.get(2));
        assertTrue(menu.get(0).equal(dish2));

        System.out.println("\nEnding testMenuDuplicate");

    }*/

}
