package comp3350.rrsys.tests.integration;

import junit.framework.TestCase;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.persistence.DataAccess;
import comp3350.rrsys.tests.persistence.DataAccessIntegrationTest;

public class DataAccessHSQLDBTest extends TestCase
{
    private static String dbName = Main.dbName;

    public DataAccessHSQLDBTest(String arg0)
    {
        super(arg0);
    }

    public void testDataAccess()
    {
        DataAccess dataAccess;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test DataAccess (using default DB)");

        // Use the following two statements to run with the real database
        Services.createDataAccess(dbName);
        dataAccess = Services.getDataAccess(dbName);

        DataAccessIntegrationTest.dataAccessIntegrationTest(dataAccess);

        Services.closeDataAccess();

        System.out.println("Finished Integration test DataAccess (using default DB)");
    }
}
