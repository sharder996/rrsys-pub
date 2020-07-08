package comp3350.rrsys.tests.objects;

import junit.framework.TestCase;

import comp3350.rrsys.objects.Table;

public class TestTable extends TestCase {
    public TestTable(String arg0) { super(arg0); }

    public void testTable() {
        System.out.println("\nStarting TestTable");

        Table t0 = new Table(0, 4);

        assertNotNull(t0);
        assertEquals(0, t0.getTID());
        assertEquals(4, t0.getCapacity());
        assertTrue(t0.equals(0));

        int month = 12;
        int date = 31;
        int openTime = 8;
        int closeTime = 22;

        for(int i = 1; i <= month; i++){
            for(int j = 1; j <= date; j++) {
                for(int k = openTime; k <= closeTime; k++){
                    //System.out.println("i:" + i + " j:" + j + " k:" + k);
                    assertTrue(t0.getAvailable(i, j, k));
                }
            }
        }

        t0.setAvailable(10, 10, 12, false);

        for(int i = 1; i <= month; i++){
            for(int j = 1; j <= date; j++) {
                for(int k = openTime; k <= closeTime; k++){
                    if(i == 10 && j == 10 && k == 12) {
                        assertFalse(t0.getAvailable(i, j, k));
                    } else {
                        assertTrue(t0.getAvailable(i, j, k));
                    }
                }
            }
        }

        Table t1 = new Table(-1, -1);
        assertEquals(-1, t1.getTID());
        assertEquals(-1, t1.getCapacity());

        for(int i = 1; i <= month; i++){
            for(int j = 1; j <= date; j++) {
                for(int k = openTime; k <= closeTime; k++){
                    assertTrue(t1.getAvailable(i, j, k));
                }
            }
        }

    }
}
