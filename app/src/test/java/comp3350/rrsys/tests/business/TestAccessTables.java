package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Calendar;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessTables;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.persistence.DataAccessStub;

public class TestAccessTables extends TestCase
{
    private AccessTables accessTables;
    private DataAccessStub accessStub;

    public TestAccessTables(String arg0) { super(arg0); }

    public void setUp()
    {
        System.out.println("\nStarting TestAccessTables");
        accessTables = new AccessTables(new DataAccessStub(Main.dbName));
        accessStub = new DataAccessStub();
        accessStub.open(Main.dbName);
        accessStub.generateFakeData();
    }

    public void tearDown()
    {
        accessStub.close();
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
