package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessCustomers;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.persistence.DataAccess;
import comp3350.rrsys.persistence.DataAccessObject;
import comp3350.rrsys.persistence.DataAccessStub;

public class TestAccessCustomers extends TestCase
{
    private AccessCustomers accessCustomers;
    private DataAccessStub accessStub;

    public TestAccessCustomers(String arg0) { super(arg0); }

    public void setUp()
    {
        System.out.println("\nStarting TestAccessCustomer");

        accessCustomers = new AccessCustomers(new DataAccessStub(Main.dbName));

        accessStub = new DataAccessStub(); //to get customer list for test
        accessStub.open(Main.dbName);

    }

    public void tearDown()
    {
        System.out.println("\nEnd TestAccessCustomer");
    }

    public void testAccessCustomersConnection() { assertNotNull(accessCustomers); }

    public void testAccessCustomer()
    {
        ArrayList<Customer> customerList = new ArrayList<>();

        assertEquals(0, customerList.size());

        accessCustomers.insertCustomer(new Customer("Jim", "Jam", "204-956-1203"));
        accessStub.getCustomerSequential(customerList);

        assertTrue(customerList.size() > 0);
    }
}
