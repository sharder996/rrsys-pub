package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessTables;
import comp3350.rrsys.objects.Table;
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
        /*
        There are total 30 tables in database(Stub)
        Table ID from be 1 to 30.
        5 tables each for 2, 4, 6, 8, 10, 12 people
        Totally 5 * 6 = 30 tables
         */
        Table table;
        int capacity = 2;

        for(int i = 1; i <= 30; i++)
        {
            table = accessTables.getRandom(i);

            assertNotNull(table);
            assertEquals(capacity, table.getCapacity());

            if(i % 5 == 0)
                capacity += 2;
        }
    }

    public void testAccessInvalidTables()
    {
        Table table;

        table = accessTables.getRandom(40);
        assertNull(table);

        table = accessTables.getRandom(-1);
        assertNull(table);
    }
}
