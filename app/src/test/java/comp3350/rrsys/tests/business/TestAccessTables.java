package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessTables;
import comp3350.rrsys.persistence.DataAccessStub;

public class TestAccessTables extends TestCase
{
    private AccessTables accessTables;

    public TestAccessTables(String arg0) { super(arg0); }

    public void setUp()
    {
        System.out.println("\nStarting TestAccessTables");
        accessTables = new AccessTables(new DataAccessStub(Main.dbName));
    }

    public void tearDown()
    {
        System.out.println("\nEnd TestAccessTables");
    }

    public void testAccessTables()
    {
        assertEquals(2, accessTables.getTableCapacity(1));
        assertEquals(4, accessTables.getTableCapacity(6));
        assertEquals(6, accessTables.getTableCapacity(11));
        assertEquals(8, accessTables.getTableCapacity(16));
        assertEquals(10, accessTables.getTableCapacity(21));
        assertEquals(12, accessTables.getTableCapacity(26));
    }
}
