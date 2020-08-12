package comp3350.rrsys.tests.persistence;

import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.persistence.DataAccess;
import comp3350.rrsys.persistence.DataAccessObject;
import comp3350.rrsys.persistence.DataAccessStub;

public class DataAccessTest extends TestCase
{
    private DataAccess dataAccess;

    public DataAccessTest(String arg0)
    {
        super(arg0);
    }

    public void setUp()
    {
        System.out.println("\nStarting Persistence test DataAccess (using db)");

        // Use the following statements to run with the stub database:
        //dataAccess = new DataAccessStub();
        //dataAccess.open("Stub");

        // or switch to the real database:
        dataAccess = new DataAccessObject(Main.dbName);
        dataAccess.open(Main.getDBPathName());

    }

    public void tearDown()
    {
        System.out.println("Finished Persistence test DataAccess (using db)");
    }

    public void testCustomerDatabaseTable()
    {
        ArrayList<Customer> customers;
        Customer customer;
        String result;

        customers = new ArrayList<>();
        result = dataAccess.getCustomerSequential(customers);

        assertNull(result);

        assertEquals(5, customers.size());
        customer = customers.get(0);
        assertEquals(1, customer.getCID());
        assertEquals( "Gary", customer.getFirstName());
        assertEquals("Chalmers", customer.getLastName());
        assertEquals("Gary Chalmers", customer.getFullName());
        assertEquals("2049990123", customer.getPhoneNumber());
    }

    public void testAddExistingCustomer()
    {
        ArrayList<Customer> customers;
        Customer customer;
        String result;

        customers = new ArrayList<>();
        result = dataAccess.getCustomerSequential(customers);
        assertNull(result);

        customer = customers.get(0);
        //Adding existing customer -- should still allow to be added with new Primary Key
        result = dataAccess.insertCustomer(customer);
        assertNull(result);
        customers.clear();
        result = dataAccess.getCustomerSequential(customers);
        assertNull(result);
        assertEquals(5, customers.size());
    }

    public void testAddNewCustomer()
    {
        ArrayList<Customer> customers;
        Customer customer;
        String result;

        customers = new ArrayList<>();
        result = dataAccess.getCustomerSequential(customers);
        assertNull(result);

        //Adding new customer
        String firstName = "Jane", lastName = "Public", phoneNumber = "3065550123";
        result = dataAccess.insertCustomer(firstName, lastName, phoneNumber);
        assertNull(result);
        customers.clear();
        result = dataAccess.getCustomerSequential(customers);
        assertNull(result);
        assertEquals(6, customers.size());
        customer = customers.get(5);
        assertEquals(firstName, customer.getFirstName());
        assertEquals(lastName, customer.getLastName());
        assertEquals(phoneNumber, customer.getPhoneNumber());

    }

    //should allow duplicate table with new TID
    public void testTablesDatabaseTable()
    {
        ArrayList<Table> tables;
        Table table;
        String result;

        tables = new ArrayList<>();
        result = dataAccess.getTableSequential(tables);
        assertNotNull(tables);
        assertEquals(31, tables.size());
        table = tables.get(0);
        assertEquals(1, table.getTID());
        assertEquals(2, table.getCapacity());
    }

    public void testGetTableExists()
    {
        Table table = null;

        table = dataAccess.getTableRandom(1);
        assertNotNull(table);
    }

    public void testGetTableNotExists()
    {
        Table table = null;

        table = dataAccess.getTableRandom(-1);
        assertNull(table);
    }

    public void testAddNewTable()
    {
        ArrayList<Table> tables;
        Table table = null;
        String result;

        result = null;
        result = dataAccess.addTable(31, 8);
        assertNull(result);

        tables = new ArrayList<>();
        result = dataAccess.getTableSequential(tables);
        assertNull(result);
        assertEquals(31, tables.size());

        table = tables.get(tables.size()-1);
        assertEquals(31, table.getTID());
        assertEquals(8, table.getCapacity());
    }

    public void testAddDuplicateTable()
    {
        ArrayList<Table> tables;
        Table table = null;
        String result = null;
        int numTables;

        tables = new ArrayList<>();
        result = dataAccess.getTableSequential(tables);
        assertNull(result);
        numTables = tables.size();

        table = dataAccess.getTableRandom(1);
        assertNotNull(table);

        result = dataAccess.addTable(table.getTID(), table.getCapacity());
        assertNotNull(result);

        tables.clear();
        tables = new ArrayList<>();
        result = dataAccess.getTableSequential(tables);
        assertNull(result);

        assertEquals(numTables, tables.size());
    }

    public void testReservations()
    {
        ArrayList<Reservation> reservations;
        Reservation reservation;
        String result;

        reservations = new ArrayList<>();
        result = dataAccess.getReservationSequential(reservations);

        assertNull(result);
        assertEquals(7, reservations.size());

        reservation = reservations.get(0);

        assertEquals(1, reservation.getRID());
        assertEquals(1, reservation.getCID());
        assertEquals(2, reservation.getTID());
        assertEquals(2, reservation.getNumPeople());
    }

    public void testGetNextResID()
    {
        int nextID = dataAccess.getNextReservationID();
        assertEquals(5, nextID);
    }

    public void testMenuGetTypes()
    {
        ArrayList<String> menuTypes;
        String result;

        menuTypes = null;
        menuTypes = dataAccess.getMenuTypes();
        assertNotNull(menuTypes);
    }

    public void testMenuGetByType()
    {
        ArrayList<Item> menuItems;
        ArrayList<String> menuTypes;

        menuTypes = new ArrayList<>();
        menuTypes = dataAccess.getMenuTypes();
        assertNotNull(menuTypes);

        menuItems = null;
        for(int i = 0; i < menuTypes.size(); i++)
        {
            menuItems = dataAccess.getMenuByType(menuTypes.get(i));
            assertNotNull(menuItems);
            menuItems.clear();
        }
    }

    public void testAddExistingMenu()
    {
        ArrayList<Item> menuItems;
        ArrayList<String> menuTypes;
        Item item;
        String result = null;

        menuTypes = new ArrayList<>();
        menuTypes = dataAccess.getMenuTypes();
        assertNotNull(menuTypes);

        menuItems = dataAccess.getMenu();
        assertEquals(44, menuItems.size());

        item = menuItems.get(0);
        result = dataAccess.insertItem(item);
        assertNotNull(result);

        menuItems = dataAccess.getMenu();
        assertEquals(44, menuItems.size());//this should be 44.
    }

    public void testAddNewMenu()
    {
        ArrayList<Item> menuItems;
        ArrayList<String> menuTypes;

        menuTypes = new ArrayList<>();
        menuTypes = dataAccess.getMenuTypes();
        assertNotNull(menuTypes);

        menuItems = dataAccess.getMenu();

        assertEquals(44, menuItems.size());
        Item newitem = new Item(45, "Turkey Sandwiches", "Sandwiches", "Turkey",12.22);
        menuItems.add(newitem);

        assertEquals(45, menuItems.size());
        assertTrue(menuItems.get(44).equal(newitem));
    }

    public void testInsertOrder()
    {
        Order order;

        order = dataAccess.getOrder(1);
        for(int i = 0; i < order.size(); i++)
        {
            System.out.println(order.getOrder().get(i).getName());
        }
        System.out.println();
        order = dataAccess.getOrder(2);
        for(int i = 0; i < order.size(); i++)
        {
            System.out.println(order.getOrder().get(i).getName());
        }
        System.out.println();
        order = dataAccess.getOrder(3);
        for(int i = 0; i < order.size(); i++)
        {
            System.out.println(order.getOrder().get(i).getName());
        }
        System.out.println();
        order = dataAccess.getOrder(4);
        for(int i = 0; i < order.size(); i++)
        {
            System.out.println(order.getOrder().get(i).getName());
        }
        System.out.println();
    }

    public void testInsertIntoNewOrder()
    {
        Item dish, dish1;
        String result;
        Reservation newReservation = null;
        Calendar currDate = Calendar.getInstance();

        try
        {
            DateTime startTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 12, 0));
            DateTime endTime= new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 14, 0));
            newReservation = new Reservation(4,10,4, startTime, endTime);
        }
        catch(IllegalArgumentException e)
        {
            fail();
        }
        System.out.println();

        newReservation.setRID(5);
        dataAccess.insertReservation(newReservation);

        dish = new Item(1,"SPECIAL SALAD","Salads","A",9.95);
        dish1 = new Item(2,"SPINACH SALAD","Salads","B",10.95);

        result = dataAccess.insertItemIntoOrder(newReservation.getRID(), dish, "");
        assertNull(result);
        assertTrue(dataAccess.getOrder(newReservation.getRID()).getOrder().size() == 1);

        result = dataAccess.insertItemIntoOrder(newReservation.getRID(), dish1, "");
        assertNull(result);

        assertTrue(dataAccess.getOrder(newReservation.getRID()).getOrder().size() == 2);
        assertEquals(dataAccess.getOrder(newReservation.getRID()).getItem(1).getName(), dish.getName());
        assertEquals(dataAccess.getOrder(newReservation.getRID()).getItem(2).getName(), dish1.getName());

    }

    public void testInsertExistingOrder()
    {
        Order order;
        Item item;
        String result;

        order = dataAccess.getOrder(1);
        for(int i = 0; i < order.size(); i++)
        {
            System.out.println(order.getOrder().get(i).getName());
        }
        System.out.println();
        item = dataAccess.getMenu().get(0);
        result = dataAccess.insertItemIntoOrder(1, item, "");
        assertNull(result);

        order = dataAccess.getOrder(1);
        for(int i = 0; i < order.size(); i++)
        {
            System.out.println(order.getOrder().get(i).getName());
        }
        System.out.println();
    }

    public void testDeleteFromExistingOrder()
    {

        Order newOrder;
        Item item, item1;
        String result;
        Reservation newReservation = null;
        Calendar currDate = Calendar.getInstance();

        try
        {
            DateTime startTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 12, 0));
            DateTime endTime= new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 14, 0));
            newReservation = new Reservation(4,10,4, startTime, endTime);
        }
        catch(IllegalArgumentException e)
        {
            fail();
        }
        System.out.println();

        assertNotNull(newReservation);

        newReservation.setRID(6);
        result = dataAccess.insertReservation(newReservation);
        assertNull(result);

        newOrder = new Order(newReservation.getRID());

        assertNotNull(newOrder);

        item = dataAccess.getMenu().get(0); //item ID = 1
        item1 = dataAccess.getMenu().get(1); //item ID = 2

        newOrder.addItem(item, "");
        newOrder.addItem(item1, "");

        assertNull(dataAccess.insertItemIntoOrder(newReservation.getRID(), newOrder.getItem(1), newOrder.getItem(1).getNote()));
        assertNull(dataAccess.insertItemIntoOrder(newReservation.getRID(), newOrder.getItem(2), newOrder.getItem(2).getNote()));
        assertEquals(2, dataAccess.getOrder(6).size());

        assertNull(dataAccess.removeItemFromOrder(newReservation.getRID(), newOrder.getItem(1).getLineItem()));
        assertEquals(1, dataAccess.getOrder(6).size());
    }

    public void testGetPrice()
    {
        Order newOrder;
        Item item, item1;
        String result;
        Reservation newReservation = null;
        Calendar currDate = Calendar.getInstance();

        try
        {
            DateTime startTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 12, 0));
            DateTime endTime= new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 14, 0));
            newReservation = new Reservation(4,10,4, startTime, endTime);
        }
        catch(IllegalArgumentException e)
        {
            fail();
        }
        System.out.println();

        assertNotNull(newReservation);

        newReservation.setRID(7);
        result = dataAccess.insertReservation(newReservation);
        assertNull(result);

        newOrder = new Order(newReservation.getRID());

        assertNotNull(newOrder);

        item = dataAccess.getMenu().get(0); //item ID = 1
        item1 = dataAccess.getMenu().get(1); //item ID = 2
        double orderPrice = item.getPrice() + item1.getPrice();

        newOrder.addItem(item, "");
        newOrder.addItem(item1, "");

        assertNull(dataAccess.insertItemIntoOrder(newReservation.getRID(), newOrder.getItem(1), newOrder.getItem(1).getNote()));
        assertNull(dataAccess.insertItemIntoOrder(newReservation.getRID(), newOrder.getItem(2), newOrder.getItem(2).getNote()));
        assertEquals(orderPrice, dataAccess.getOrder(7).getTotalPrice());

        assertNull(dataAccess.removeItemFromOrder(newReservation.getRID(), newOrder.getItem(2).getLineItem()));
        orderPrice -= newOrder.getItem(2).getPrice();
        assertEquals(orderPrice, dataAccess.getOrder(7).getTotalPrice());
    }

    public void testAddNotes()
    {
        Order newOrder;
        Item item, item1;
        String result;
        Reservation newReservation = null;
        Calendar currDate = Calendar.getInstance();

        try
        {
            DateTime startTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 12, 0));
            DateTime endTime= new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 14, 0));
            newReservation = new Reservation(4,10,4, startTime, endTime);
        }
        catch(IllegalArgumentException e)
        {
            fail();
        }
        System.out.println();

        assertNotNull(newReservation);

        newReservation.setRID(8);
        result = dataAccess.insertReservation(newReservation);
        assertNull(result);

        newOrder = new Order(newReservation.getRID());

        assertNotNull(newOrder);

        item = dataAccess.getMenu().get(0); //item ID = 1
        item1 = dataAccess.getMenu().get(1); //item ID = 2

        newOrder.addItem(item, "");
        newOrder.addItem(item1, "");

        assertNull(dataAccess.insertItemIntoOrder(newReservation.getRID(), newOrder.getItem(1), newOrder.getItem(1).getNote()));
        assertNull(dataAccess.insertItemIntoOrder(newReservation.getRID(), newOrder.getItem(2), newOrder.getItem(2).getNote()));
        assertEquals("", dataAccess.getOrder(8).getItem(1).getNote());
        assertEquals("", dataAccess.getOrder(8).getItem(2).getNote());
    }

    public void testGetNextLineItem()
    {
        Order newOrder;
        Item item, item1;
        Reservation newReservation = null;
        Calendar currDate = Calendar.getInstance();

        try
        {
            DateTime startTime = new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 12, 0));
            DateTime endTime= new DateTime(new GregorianCalendar(currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE) + 1, 14, 0));
            newReservation = new Reservation(4,10,4, startTime, endTime);
        }
        catch(IllegalArgumentException e)
        {
            fail();
        }
        System.out.println();

        newReservation.setRID(9);
        dataAccess.insertReservation(newReservation);

        assertEquals(1, dataAccess.getNextLineItem(newReservation.getRID()));

        item = dataAccess.getMenu().get(0); //item ID = 1
        item1 = dataAccess.getMenu().get(1); //item ID = 2

        newOrder = new Order(newReservation.getRID());
        newOrder.addItem(item, "");
        newOrder.addItem(item1, "");

        assertNull(dataAccess.insertItemIntoOrder(newReservation.getRID(), newOrder.getItem(1), newOrder.getItem(1).getNote()));
        assertNull(dataAccess.insertItemIntoOrder(newReservation.getRID(), newOrder.getItem(2), newOrder.getItem(2).getNote()));

        assertEquals(3, dataAccess.getNextLineItem(newReservation.getRID()));
    }
}
