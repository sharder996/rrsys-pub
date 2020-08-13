package comp3350.rrsys.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.rrsys.tests.integration.IntegrationTests;

public class RunIntegrationTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Integration Tests");
        suite.addTest(IntegrationTests.suite());
        return suite;
    }
}
