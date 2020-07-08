package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessTables;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.persistence.DataAccessStub;

public class TestAccessTables extends TestCase {
    public TestAccessTables(String arg0) { super(arg0); }

    public void testAccessTables() {
        System.out.println("\nStarting TestAccessTables");

        Main.startUp();
        AccessTables accessTables = new AccessTables();
        DataAccessStub accessStub = new DataAccessStub();
        accessStub.open(Main.dbName);
        accessStub.generateFakeData();
        ArrayList<Table> tables = null;
        tables = accessStub.getTableSequential();

        int openTime = 8;
        int closeTime = 22;

        ArrayList<Table> tableList;
        Calendar currTime = Calendar.getInstance();
        for(int i = 1; i <= 31; i++){
            for(int j = openTime; j <= closeTime; j++){
                for(int k = 1; k <= 10; k++){
                    tableList = accessTables.recommendTables(k, currTime.MONTH, i, j, j+1);
                    assertTrue(tableList.size() > 0);
                }

            }
        }

        accessStub.close();
        Main.shutDown();
        System.out.println("\nEnd TestAccessTables");
    }
}
