package comp3350.rrsys.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.rrsys.tests.business.SampleBusinessTest;
import comp3350.rrsys.tests.objects.RIDTest;

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
        suite.addTestSuite(RIDTest.class);
    }

    private static void testBusiness(){
        suite.addTestSuite(SampleBusinessTest.class);
    }
}
