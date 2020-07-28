package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;

public class TestOrder extends TestCase {
    public TestOrder(String arg0) { super(arg0); }

    public void TestOrderCreation(){

        Order newOrder = new Order(1);

        assertNotNull(newOrder);

        Item dish = new Item(1,"SPECIAL SALAD","Salads","A",9.95);
        Item dish1 = new Item(2,"SPINACH SALAD","Salads","B",10.95);
        Item dish2 = new Item(3,"KALE SALAD","Salads","C",10.95);
        Item dish3 = new Item(4,"CAESAR SALAD","Salads","D",10.95 );
        Item dish4 = new Item(5,"ARUGULA SALAD","Salads","E",11.95 );
        Item dish5 = new Item(6,"AVOCADO SALAD","Salads","avocado, brussels sprouts, radish, alfalfa sprouts, chickpeas, dried cranberries & pepitas.",12.95 );

        assertNotNull(dish);
        assertNotNull(dish1);
        assertNotNull(dish2);
        assertNotNull(dish3);
        assertNotNull(dish4);
        assertNotNull(dish5);

        newOrder.addItem(dish);
        newOrder.addItem(dish1);
        newOrder.addItem(dish2);
        newOrder.addItem(dish3);
        newOrder.addItem(dish4);
        newOrder.addItem(dish5);

        assertEquals(6, newOrder.size());

        newOrder.setNote("Allergy");
        assertEquals("Allergy", newOrder.getNote());

    }

}
