package comp3350.rrsys.tests.persistence;

import android.app.ActionBar;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Menu;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.persistence.DataAccess;
import comp3350.rrsys.persistence.DataAccessObject;
import comp3350.rrsys.persistence.DataAccessStub;

public class DataAccessTest extends TestCase {

    private DataAccess dataAccess = null;

    public DataAccessTest(String arg0)
    {
        super(arg0);
    }

    public void setUp() {
        System.out.println("\nStarting Persistence test DataAccess (using db)");

        // Use the following statements to run with the stub database:
        //dataAccess = new DataAccessStub();
        //dataAccess.open("Stub");

        // or switch to the real database:
         dataAccess = new DataAccessObject(Main.dbName);

        dataAccess.open(Main.getDBPathName());

        // Note the increase in test execution time.
    }

    public void tearDown() {
        System.out.println("Finished Persistence test DataAccess (using db)");
    }

    /*TODO:
    * - modify database > RR.script with variable types that work with DBMS for table availability
    * - add more tests for each table
    *       - table needs tests for availability
    * - add tests for menu, order
    * - test relations? (INNER JOIN, for example)
     */

    public void testCustomerDatabaseTable() { //will split these
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

    public void testAddExistingCustomer() {
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

    public void testAddNewCustomer() {
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
    public void testTablesDatabaseTable() {
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

    public void testGetTableExists(){
        Table table = null;

        table = dataAccess.getTableRandom(1);
        System.out.println(table.toString());
        assertNotNull(table);
    }

    public void testGetTableNotExists(){
        Table table = null;

        table = dataAccess.getTableRandom(-1);
        assertNull(table);
    }

    public void testAddNewTable() {
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

    public void testAddDuplicateTable(){
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

    public void testReservations() {
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

    public void testMenuGetTypes() {
        ArrayList<String> menuTypes;
        String result;

        menuTypes = null;
        menuTypes = dataAccess.getMenuTypes();
        assertNotNull(menuTypes);

    }

    public void testMenuGetByType() {
        ArrayList<Item> menuItems;
        ArrayList<String> menuTypes;

        menuTypes = new ArrayList<>();
        menuTypes = dataAccess.getMenuTypes();
        assertNotNull(menuTypes);

        menuItems = null;
        for(int i = 0; i < menuTypes.size(); i++){
            menuItems = dataAccess.getMenuByType(menuTypes.get(i));
            assertNotNull(menuItems);
            menuItems.clear();
        }
    }

    public void testAddExistingMenu(){
        ArrayList<Item> menuItems;
        ArrayList<String> menuTypes;

        menuTypes = new ArrayList<>();
        menuTypes = dataAccess.getMenuTypes();
        assertNotNull(menuTypes);

        menuItems = dataAccess.getMenu();

        assertEquals(44, menuItems.size());

        menuItems.add(menuItems.get(0));

        assertEquals(45, menuItems.size());//this should be 44.
    }

    public void testAddNewMenu(){
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
}
