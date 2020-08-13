package comp3350.rrsys.tests.persistence;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PersistenceTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Persistence Tests");
        suite.addTestSuite(DataAccessIntegrationTest.class);
        suite.addTestSuite(DataAccessTest.class);
        return suite;
    }
}
