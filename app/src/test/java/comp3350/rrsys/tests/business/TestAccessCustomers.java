package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessCustomers;
import comp3350.rrsys.objects.Customer;

public class TestAccessCustomers extends TestCase
{
    private AccessCustomers accessCustomers;
    public TestAccessCustomers(String arg0) { super(arg0); }

    public void setUp()
    {
        System.out.println("\nStarting TestAccessCustomer");
        Main.startUp();
    }

    public void tearDown()
    {
        Main.shutDown();
        accessCustomers = new AccessCustomers();
        System.out.println("\nEnd TestAccessCustomer");
    }

    public void testAccessCustomersConnection() { assertNotNull(accessCustomers); }

    public void testAccessCustomer()
    {
        ArrayList<Customer> customerList = new ArrayList<>();

        assertEquals(0, customerList.size());

        accessCustomers.insertCustomer(new Customer("Jim", "Jam", "204-956-1203"));
        accessCustomers.getCustomers(customerList);

        assertTrue(customerList.size() > 0);
    }
}
