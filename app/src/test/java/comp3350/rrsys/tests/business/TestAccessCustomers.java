package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessCustomers;
import comp3350.rrsys.objects.Customer;

public class TestAccessCustomers extends TestCase
{
    public TestAccessCustomers(String arg0) { super(arg0); }

    public void testAccessCustomer()
    {
        System.out.println("\nStarting TestAccessCustomer");

        Main.startUp();
        AccessCustomers accessCustomers = new AccessCustomers();
        assertNotNull(accessCustomers);
        ArrayList<Customer> customerList = new ArrayList<>();

        assertEquals(0, customerList.size());

        accessCustomers.insertCustomer(new Customer("Jim", "Jam", "204-956-1203"));
        accessCustomers.getCustomers(customerList);
        assertTrue(customerList.size() > 0);
        Main.shutDown();
        System.out.println("\nEnd TestAccessCustomer");
    }
}
