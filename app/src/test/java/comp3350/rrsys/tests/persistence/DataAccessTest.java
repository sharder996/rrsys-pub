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
        dataAccess = new DataAccessStub();
        dataAccess.open("Stub");

        // or switch to the real database:
        // dataAccess = new DataAccessObject(Main.dbName);
        // dataAccess.open(Main.getDBPathName());

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
        //assertEquals(4, customers.size());

        customer = customers.get(0);
        assertEquals(1, customer.getCID());
        assertEquals( "Gary", customer.getFirstName());
        assertEquals("Chalmers", customer.getLastName());
        assertEquals("Gary Chalmers", customer.getFullName());
        assertEquals("2049990123", customer.getPhoneNumber());

        customer = customers.get(3);
        assertEquals(4, customer.getCID());
        assertEquals( "Mary", customer.getFirstName());
        assertEquals("Bailey", customer.getLastName());
        assertEquals("Mary Bailey", customer.getFullName());
        assertEquals("1057770123", customer.getPhoneNumber());
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
        //assertEquals(5, customers.size());
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
        String firstName = "Jane", lastName = "Public", phoneNumber = "2045550123";
        customer = new Customer(firstName, lastName, phoneNumber);
        result = dataAccess.insertCustomer(customer);
        assertNull(result);
        customers.clear();
        result = dataAccess.getCustomerSequential(customers);
        assertNull(result);
        //assertEquals(6, customers.size());
        customer = customers.get(customers.size()-1);
        assertEquals(firstName, customer.getFirstName());
        assertEquals(lastName, customer.getLastName());
        assertEquals(phoneNumber, customer.getPhoneNumber());

    }

    public void testTablesDatabaseTable()
    {
        ArrayList<Table> tables;
        Table table;
        String result;

        tables = new ArrayList<>();
        result = dataAccess.getTableSequential(tables);
        assertNull(result);
        assertNotNull(tables);
        assertEquals(30, tables.size());

        int capacity = 2;
        for(int i = 1; i <= 30; i++)
        {
            table = tables.get(i-1);
            assertEquals(capacity, table.getCapacity());
            if(i % 5 == 0)
                capacity += 2;
        }
    }

    public void testGetTableExists()
    {
        Table table;

        int capacity = 2;
        for(int i = 1; i <= 30; i++)
        {
            table = dataAccess.getTableRandom(i);
            assertNotNull(table);
            assertEquals(capacity, table.getCapacity());
            if(i % 5 == 0)
                capacity += 2;
        }
    }

    public void testGetTableNotExists()
    {
        Table table;

        table = dataAccess.getTableRandom(-1);
        assertNull(table);

        table = dataAccess.getTableRandom(31);
        assertNull(table);
    }

    public void testReservationsDatabaseTable()
    {
        ArrayList<Reservation> reservations;
        Reservation reservation;
        String result;

        reservations = new ArrayList<>();
        result = dataAccess.getReservationSequential(reservations);

        assertNull(result);
        //assertEquals(4, reservations.size());

        reservation = reservations.get(0);
        assertEquals(1, reservation.getRID());
        assertEquals(1, reservation.getCID());
        assertEquals(2, reservation.getTID());
        assertEquals(2, reservation.getNumPeople());

        reservation = reservations.get(3);
        assertEquals(4, reservation.getRID());
        assertEquals(4, reservation.getCID());
        assertEquals(25, reservation.getTID());
        assertEquals(10, reservation.getNumPeople());
    }

    public void testGetNextResID()
    {
        int nextID = dataAccess.getNextReservationID();
        assertEquals(5, nextID);

        ArrayList<Reservation> reservations = new ArrayList<>();
        dataAccess.getReservationSequential(reservations);
        assertEquals(nextID-1, reservations.size());
    }

    public void testMenuGetTypes()
    {
        ArrayList<String> menuTypes = dataAccess.getMenuTypes();
        assertNotNull(menuTypes);
        assertEquals(6, menuTypes.size());
    }

    public void testMenuGetByType()
    {
        ArrayList<Item> menuItems;
        ArrayList<String> menuTypes;

        menuTypes = dataAccess.getMenuTypes();
        assertNotNull(menuTypes);

        for(int i = 0; i < menuTypes.size(); i++)
        {
            menuItems = dataAccess.getMenuByType(menuTypes.get(i));
            assertNotNull(menuItems);
        }

        menuItems = dataAccess.getMenuByType("Salads");
        assertEquals(6, menuItems.size());
        menuItems = dataAccess.getMenuByType("Sandwiches");
        assertEquals(8, menuItems.size());
        menuItems = dataAccess.getMenuByType("Burgers");
        assertEquals(8, menuItems.size());
        menuItems = dataAccess.getMenuByType("Mains");
        assertEquals(8, menuItems.size());
        menuItems = dataAccess.getMenuByType("Desserts");
        assertEquals(6, menuItems.size());
        menuItems = dataAccess.getMenuByType("Drinks");
        assertEquals(8, menuItems.size());
    }

    public void testOrderDatabaseTable()
    {
        ArrayList<Reservation> reservations;
        Order order;
        String result;

        reservations = new ArrayList<>();
        result = dataAccess.getReservationSequential(reservations);

        assertNull(result);
        //assertEquals(4, reservations.size());

        for(int i = 1; i <= 4; i++)
        {
            order = dataAccess.getOrder(i);
            assertNotNull(order);
        }

        order = dataAccess.getOrder(1);
        assertEquals(2, order.getOrder().size());
    }

    public void testInsertOrder()
    {
        Order order;

        order = dataAccess.getOrder(1);
        for(int i = 0; i < order.getSize(); i++)
        {
            System.out.println(order.getOrder().get(i).getName());
        }
        System.out.println();
        order = dataAccess.getOrder(2);
        for(int i = 0; i < order.getSize(); i++)
        {
            System.out.println(order.getOrder().get(i).getName());
        }
        System.out.println();
        order = dataAccess.getOrder(3);
        for(int i = 0; i < order.getSize(); i++)
        {
            System.out.println(order.getOrder().get(i).getName());
        }
        System.out.println();
        order = dataAccess.getOrder(4);
        for(int i = 0; i < order.getSize(); i++)
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
        result = dataAccess.insertReservation(newReservation);
        assertNull(result);

        dish = new Item(1,"SPECIAL SALAD","Salads","A",9.95);
        dish1 = new Item(2,"SPINACH SALAD","Salads","B",10.95);

        result = dataAccess.insertItemIntoOrder(newReservation.getRID(), dish);
        assertNull(result);
        assertEquals(1, dataAccess.getOrder(newReservation.getRID()).getOrder().size());

        result = dataAccess.insertItemIntoOrder(newReservation.getRID(), dish1);
        assertNull(result);

        assertEquals(2, dataAccess.getOrder(newReservation.getRID()).getOrder().size());
    }

    public void testInsertExistingOrder()
    {
        Order order;
        Item item;
        String result;

        order = dataAccess.getOrder(1);
        for(int i = 0; i < order.getSize(); i++)
        {
            System.out.println(order.getOrder().get(i).getName());
        }
        System.out.println();
        item = dataAccess.getMenu().get(0);
        result = dataAccess.insertItemIntoOrder(1, item);
        assertNull(result);
        assertEquals(3, dataAccess.getOrder(1).getOrder().size());
    }

    public void testRemoveOrder()
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

        newOrder.addItem(item, 1, "");
        newOrder.addItem(item1, 1, "");

        assertNull(dataAccess.insertItemIntoOrder(newReservation.getRID(), item));
        assertNull(dataAccess.insertItemIntoOrder(newReservation.getRID(), item1));
        assertEquals(2, newOrder.getSize());

        assertNull(dataAccess.removeOrder(newReservation.getRID()));
        assertEquals(2, newOrder.getSize());
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

        newOrder.addItem(item, 1, "");
        newOrder.addItem(item1, 1, "");

        assertNull(dataAccess.insertItemIntoOrder(newReservation.getRID(), item));
        assertNull(dataAccess.insertItemIntoOrder(newReservation.getRID(), item1));
        assertEquals(orderPrice, newOrder.getTotalPrice());
    }
}
