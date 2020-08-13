package comp3350.rrsys.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.rrsys.tests.business.BusinessTests;
import comp3350.rrsys.tests.objects.ObjectTests;
import comp3350.rrsys.tests.persistence.PersistenceTests;

public class RunUnitTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Unit tests");
        suite.addTest(ObjectTests.suite());
        suite.addTest(BusinessTests.suite());
        suite.addTest(PersistenceTests.suite());
        return suite;
    }
}
