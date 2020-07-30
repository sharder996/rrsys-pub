package comp3350.rrsys.tests.persistence;

import junit.framework.TestCase;
import java.util.ArrayList;
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

        // Note the increase in test execution time.
    }

    public void tearDown()
    {
        System.out.println("Finished Persistence test DataAccess (using db)");
    }

    /*TODO:
    * - modify database > RR.script with variable types that work with DBMS for table availability
    * - add more tests for each table
    *       - table needs tests for availability
    * - add tests for menu, order
    * - test relations? (INNER JOIN, for example)
     */

    public void testCustomerDatabaseTable()
    { //will split these
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
        assertEquals(4, customers.size());
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
        System.out.println(table.toString());
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

        table = dataAccess.getTableRandom(1);
        assertNotNull(table);
        try {
            result = dataAccess.addTable(table.getTID(), table.getCapacity());
            fail();
        }catch(IllegalArgumentException e){
            assertNull(result);
        }
        assertNotNull(result);

        tables = new ArrayList<>();
        result = dataAccess.getTableSequential(tables);
        assertNull(result);

        assertEquals(30, tables.size());
    }

    public void testReservations()
    {
        ArrayList<Reservation> reservations;
        Reservation reservation;
        String result;

        reservations = new ArrayList<>();
        result = dataAccess.getReservationSequential(reservations);
        assertNull(result);
        assertEquals(4, reservations.size());
        reservation = reservations.get(0);
        assertEquals(1, reservation.getRID());
        assertEquals(1, reservation.getCID());
        assertEquals(2, reservation.getTID());
        assertEquals(2, reservation.getNumPeople());
        // add tests for timestamp / DateTime.toString
        // ... add more for reservation functions in DataAccessObject
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

        menuTypes = new ArrayList<>();
        menuTypes = dataAccess.getMenuTypes();
        assertNotNull(menuTypes);

        menuItems = dataAccess.getMenu();

        assertEquals(44, menuItems.size());

        menuItems.add(menuItems.get(0));

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
        Order newOrder;
        Item item, item1;
        String result;
        Reservation newReservation = null;
        try
        {
            DateTime startTime = new DateTime(new GregorianCalendar(2020, 10, 2, 12, 0));
            DateTime endTime= new DateTime(new GregorianCalendar(2020, 10, 2, 14, 0));
            newReservation = new Reservation(4,10,4, startTime, endTime);
        }
        catch(IllegalArgumentException e)
        {
        //    assertNotNull(newReservation);
        }

       // assertNotNull(newReservation);

        newReservation.setRID(5);
        result = dataAccess.insertReservation(newReservation);
        assertNull(result);


        newOrder = new Order(newReservation.getRID());

        assertNotNull(newOrder);

        item = dataAccess.getMenu().get(0); //item ID = 1
        item1 = dataAccess.getMenu().get(1); //item ID = 2

        newOrder.addItem(item);
        newOrder.addItem(item1);

        assertTrue(dataAccess.insertOrder(newOrder));

        ArrayList<Item> selectedItem = dataAccess.getOrder(5);

        assertEquals(2, selectedItem.size());
        assertEquals(item, selectedItem.get(0));
        assertEquals(item1, selectedItem.get(1));

    }
    public void testGetOrder()
    {
        ArrayList<Item> order;
        ArrayList<Item> menuItems = dataAccess.getMenu();

        /* RR.script // order(ReservationID, ItemID, Quantity, Note)
        INSERT INTO ORDERS VALUES(1,1,2,'null') -> item1 is "SPECIAL SALAD"
        INSERT INTO ORDERS VALUES(1,2,2,'null') -> item 2 is "SPINACH SALAD"
        INSERT INTO ORDERS VALUES(1,4,1,'') -> item 3 is "CAESAR SALAD"
        INSERT INTO ORDERS VALUES(1,12,1,'') -> item 4 is"BEEF"
         */
        order = dataAccess.getOrder(1);

        assertEquals(1, order.get(0).getItemID());
        assertEquals(1, order.get(1).getItemID());
        assertEquals(2, order.get(2).getItemID());
        assertEquals(2, order.get(3).getItemID());
        assertEquals(4, order.get(4).getItemID());
        assertEquals(12, order.get(5).getItemID());

        assertEquals("SPECIAL SALAD", order.get(0).getName());
        assertEquals("SPECIAL SALAD", order.get(1).getName());
        assertEquals("SPINACH SALAD", order.get(2).getName());
        assertEquals("SPINACH SALAD", order.get(3).getName());
        assertEquals("CAESAR SALAD", order.get(4).getName());
        assertEquals("BEEF", order.get(5).getName());

         /* RR.script // order(ReservationID, ItemID, Quantity, Note)
        INSERT INTO ORDERS VALUES(2,29,1,'Extra hot sauce') -> item 29 is "Fish Tacos"
        INSERT INTO ORDERS VALUES(2,34,1,'') -> item 34 is "CHEESECAKE"
        INSERT INTO ORDERS VALUES(2,41,2,'Spicy') -> item 41 is "CAESAR"
         */
        order = dataAccess.getOrder(2);

        assertEquals(29, order.get(0).getItemID());
        assertEquals(34, order.get(1).getItemID());
        assertEquals(41, order.get(2).getItemID());
        assertEquals(41, order.get(3).getItemID());

        assertEquals("FISH TACOS", order.get(0).getName());
        assertEquals("CHEESECAKE", order.get(1).getName());
        assertEquals("CAESAR", order.get(2).getName());
        assertEquals("CAESAR", order.get(3).getName());

         /* RR.script // order(ReservationID, ItemID, Quantity, Note)
        INSERT INTO ORDERS VALUES(3,5,1,'') -> item 5 is "ARUGULA SALAD"
         */
        order = dataAccess.getOrder(3);

        assertEquals(5, order.get(0).getItemID());

        assertEquals("ARUGULA SALAD", order.get(0).getName());

         /* RR.script // order(ReservationID, ItemID, Quantity, Note)
        INSERT INTO ORDERS VALUES(4,19,2,'No cheese on one burger') -> item19 is "BEEF"
        INSERT INTO ORDERS VALUES(4,43,2,'') -> item43 is "LONG ISLAND ICE TEA"
         */
        order = dataAccess.getOrder(4);

        assertEquals(19, order.get(0).getItemID());
        assertEquals(19, order.get(1).getItemID());
        assertEquals(43, order.get(2).getItemID());
        assertEquals(43, order.get(3).getItemID());

        assertEquals("BEEF", order.get(0).getName());
        assertEquals("BEEF", order.get(1).getName());
        assertEquals("LONG ISLAND ICE TEA", order.get(2).getName());
        assertEquals("LONG ISLAND ICE TEA", order.get(3).getName());
    }

    public void testGetPrice()
    {
        double accessPrice;
        double totalPrice= 0.0;
        ArrayList<Item> order;
        order = dataAccess.getOrder(1);
        assertNotNull(order);

        accessPrice = dataAccess.getPrice(1);
        assertNotNull(accessPrice);

        for(int i =0; i< order.size(); i++)
        {
            totalPrice += order.get(i).getPrice(); //add all price of items.
        }

        assertEquals(totalPrice, accessPrice);
    }

    public void testOrderSize()
    {
        int size = dataAccess.getSize(1);//bring all orders corresponding to reservationID 2.

        assertEquals(6, size); //Count quantities of item. Don't get confused.

        size = dataAccess.getSize(2);
        assertEquals(4, size);

        size = dataAccess.getSize(3);
        assertEquals(1, size);

        size = dataAccess.getSize(4);
        assertEquals(4, size);
    }
}
