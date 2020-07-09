package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;
import comp3350.rrsys.objects.Table;

public class TestTable extends TestCase
{
    public TestTable(String arg0) { super(arg0); }

    public void testTableEquality() {
        System.out.println("\nStarting TestTableEquality");

        Table t0 = new Table(0, 4);

        assertNotNull(t0);
        assertEquals(0, t0.getTID());
        assertEquals(4, t0.getCapacity());
        assertTrue(t0.equals(0));

        System.out.println("\nEnd TestTableEquality");
    }

    public void testTableAvailable() {
        System.out.println("\nStarting TestTableAvailable");
        Table t0 = new Table(0, 4);
        int month = 12;
        int date = 31;
        int openTime = 8;
        int closeTime = 22;

        for (int i = 1; i <= month; i++) {
            for (int j = 1; j <= date; j++) {
                for (int k = openTime; k <= closeTime; k++) {
                    assertTrue(t0.getAvailable(i, j, k));
                }
            }
        }

        t0.setAvailable(10, 10, 12, false);

        for (int i = 1; i <= month; i++) {
            for (int j = 1; j <= date; j++) {
                for (int k = openTime; k <= closeTime; k++) {
                    if (i == 10 && j == 10 && k == 12) {
                        assertFalse(t0.getAvailable(i, j, k));
                    } else {
                        assertTrue(t0.getAvailable(i, j, k));
                    }
                }
            }
        }
        System.out.println("\nEnd TestTableAvailable");
    }

    public void testTableInvalidEntries() {
        System.out.println("\nStarting TestTableInvalidEntries");

        Table t1 = null;
        try {
            t1 = new Table(-1, 2);
            fail();
        } catch (IllegalArgumentException e) {
            //success
        }
        assertNull(t1);

        try {
            t1 = new Table(1, -1);
            fail();
        } catch (IllegalArgumentException e) {
            //success
        }
        assertNull(t1);

        System.out.println("\nEnd TestTableInvalidEntries");
    }
}
