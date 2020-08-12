package comp3350.rrsys.tests.persistence;

import junit.framework.TestCase;

import comp3350.rrsys.persistence.DataAccess;

public class DataAccessIntegrationTest  extends TestCase
{
    private DataAccess dataAccess;

    public DataAccessIntegrationTest(String arg0)
    {
        super(arg0);
    }

    public void setUp() { }

    public void tearDown() { }

    public static void dataAccessIntegrationTest(DataAccess dataAccess) {
        DataAccessIntegrationTest dataAccessIntegrationTest = new DataAccessIntegrationTest("");
        dataAccessIntegrationTest.dataAccess = dataAccess;
        dataAccessIntegrationTest.test1();
    }

    public void test1() { }
}
