package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessCustomers;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.persistence.DataAccessStub;

public class TestAccessCustomers extends TestCase
{
    private AccessCustomers accessCustomers;

    public TestAccessCustomers(String arg0) { super(arg0); }

    public void setUp()
    {
        System.out.println("\nStarting TestAccessCustomer");
        accessCustomers = new AccessCustomers(new DataAccessStub(Main.dbName));
    }

    public void tearDown() { System.out.println("\nEnd TestAccessCustomer"); }

    public void testAccessCustomersConnection() { assertNotNull(accessCustomers); }

    public void testAccessCustomer()
    {
        ArrayList<Customer> customerList = new ArrayList<>();
        assertEquals(0, customerList.size());

        accessCustomers.getCustomers(customerList);
        int size = customerList.size();
        assertEquals(5, size);

        accessCustomers.insertCustomer(new Customer("Jim", "Jam", "204-956-1203"));
        accessCustomers.getCustomers(customerList);
        assertEquals(size + 1, customerList.size());

        Customer newCustomer = new Customer("Alice", "Wang", "204-999-4567");
        accessCustomers.insertCustomer(newCustomer);
        accessCustomers.getCustomers(customerList);
        assertEquals(size + 2, customerList.size());

        accessCustomers.deleteCustomer(newCustomer);
        accessCustomers.getCustomers(customerList);
        assertEquals(size + 1, customerList.size());
    }
}
