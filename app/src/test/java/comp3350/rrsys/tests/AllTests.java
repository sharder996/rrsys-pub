package comp3350.rrsys.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.rrsys.tests.business.TestAccessCustomers;
import comp3350.rrsys.tests.objects.TestCustomer;
import comp3350.rrsys.tests.objects.TestDateTime;
import comp3350.rrsys.tests.objects.TestReservation;

public class AllTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("AllTests");
        testObjects();
        testBusiness();
        return suite;
    }

    private static void testObjects(){

        suite.addTestSuite(TestReservation.class);
        suite.addTestSuite(TestCustomer.class);
        suite.addTestSuite(TestDateTime.class);

    }

    private static void testBusiness(){
        suite.addTestSuite(TestAccessCustomers.class);
    }
}
