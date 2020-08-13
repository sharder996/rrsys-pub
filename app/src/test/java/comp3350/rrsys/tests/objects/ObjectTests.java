package comp3350.rrsys.tests.objects;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ObjectTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Object Tests");
        suite.addTestSuite(TestCustomer.class);
        suite.addTestSuite(TestDateTime.class);
        suite.addTestSuite(TestItem.class);
        suite.addTestSuite(TestOrder.class);
        suite.addTestSuite(TestReservation.class);
        suite.addTestSuite(TestTable.class);
        return suite;
    }
}
