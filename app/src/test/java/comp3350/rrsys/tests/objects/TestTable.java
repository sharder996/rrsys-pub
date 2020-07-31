package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;
import comp3350.rrsys.objects.Table;

public class TestTable extends TestCase
{
    public TestTable(String arg0) { super(arg0); }

    public void testTableCreate()
    {
        System.out.println("\nStarting TestTableCreate");

        Table t0 = new Table(0, 4);

        assertNotNull(t0);

        assertEquals(0, t0.getTID());
        assertEquals(4, t0.getCapacity());

        System.out.println("\nEnd TestTableCreate");
    }


    public void testTableEquality()
    {
        System.out.println("\nStarting TestTableEquality");

        Table t0 = new Table(0, 4);
        Table t1 = new Table(2, 4);

        assertNotNull(t0);
        assertNotNull(t1);

        assertTrue(t0.equals(0));
        assertTrue(t1.equals(1));
        assertFalse(t0.equals(1));

        System.out.println("\nEnd TestTableEquality");
    }

    public void testTableInvalidEntries()
    {
        System.out.println("\nStarting TestTableInvalidEntries");

        Table t1 = null;
        try
        {
            t1 = new Table(-1, 2);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(t1);
        }

        try
        {
            t1 = new Table(1, -1);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertNull(t1);
        }

        System.out.println("\nEnd TestTableInvalidEntries");
    }
}
