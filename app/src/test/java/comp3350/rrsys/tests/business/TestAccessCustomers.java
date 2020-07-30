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
    private DataAccess dataAccess;

    public TestAccessCustomers(String arg0) { super(arg0); }

    public void setUp()
    {
        System.out.println("\nStarting TestAccessCustomer");

        dataAccess = new DataAccessStub(Main.dbName);
        dataAccess.open(Main.getDBPathName());

    }

    public void tearDown()
    {
        System.out.println("\nEnd TestAccessCustomer");
    }

    public void testAccessCustomersConnection() { assertNotNull(dataAccess); }

    public void testAccessCustomer()
    {
        ArrayList<Customer> customerList = new ArrayList<>();

        assertEquals(0, customerList.size());

        dataAccess.insertCustomer(new Customer("Jim", "Jam", "204-956-1203"));
        dataAccess.getCustomerSequential(customerList);

        assertTrue(customerList.size() > 0);
    }
}
