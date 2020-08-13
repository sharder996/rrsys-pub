package comp3350.rrsys.tests.business;

import junit.framework.Test;
import junit.framework.TestSuite;

public class BusinessTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Business Tests");
        suite.addTestSuite(TestAccessCustomers.class);
        suite.addTestSuite(TestAccessMenu.class);
        suite.addTestSuite(TestAccessOrders.class);
        suite.addTestSuite(TestAccessReservations.class);
        suite.addTestSuite(TestAccessTables.class);
        suite.addTestSuite(TestSuggestReservations.class);
        return suite;
    }
}
