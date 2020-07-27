package comp3350.rrsys.tests.persistence;

import android.app.ActionBar;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.Menu;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.persistence.DataAccess;
import comp3350.rrsys.persistence.DataAccessObject;
import comp3350.rrsys.persistence.DataAccessStub;

public class DataAccessTest extends TestCase {

    private DataAccess dataAccess;

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
    * - add tests for menu, order
    * - test relations?
     */

    public void testCustomerDatabaseTable() { //will split these
        ArrayList<Customer> customers;
        Customer customer;
        String result;

        System.out.println("Beginning test of table CUSTOMERS");

        customers = new ArrayList<>();
        result = dataAccess.getCustomerSequential(customers);
        assertNull(result);
        assertEquals(4, customers.size());
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

        System.out.println("End test of table CUSTOMERS");
    }

    public void testTablesDatabaseTable() {
        ArrayList<Table> tables;
        Table table;
        String result;

        System.out.println("Beginning test of table TABLES");

        tables = new ArrayList<>();
        result = dataAccess.getTableSequential(tables);
        assertNotNull(tables);
        assertEquals(30, tables.size());
        table = tables.get(0);
        assertEquals(1, table.getTID());
        assertEquals(2, table.getCapacity());

    }

    public void testAddNewTable() {
        ArrayList<Table> tables;
        Table table;
        String result;

        table = new Table(31, 8);

        System.out.println("End test of table TABLES");
    }

    public void testReservations() {
        ArrayList<Reservation> reservations;
        Reservation reservation;
        String result;

        System.out.println("Beginning test of table RESERVATIONS");

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

        System.out.println("End test of table RESERVATIONS");
    }

    public void testMenuGetTypes() {
        System.out.println("Beginning test of table MENU");
        ArrayList<String> menuTypes;
        String result;

        menuTypes = dataAccess.getMenuTypes();

        System.out.println("End test of table MENU");
    }

}
