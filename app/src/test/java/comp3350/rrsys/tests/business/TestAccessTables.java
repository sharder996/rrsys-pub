package comp3350.rrsys.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.business.AccessTables;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.persistence.DataAccessStub;

public class TestAccessTables extends TestCase {
    public TestAccessTables(String arg0) { super(arg0); }

    public void testAccessTables() {
        System.out.println("\nStarting TestAccessTables");

        /*Table t0 = new Table(1, 4);
        Table t1 = new Table(2, 6);
        Table t2 = new Table(3, 10);*/

        Main.startUp();
        AccessTables accessTables = new AccessTables();
        DataAccessStub accessStub = new DataAccessStub();
        accessStub.open(Main.dbName);
        accessStub.generateFakeData();
        ArrayList<Table> tables = null;
        tables = accessStub.getTableSequential();
        for(int i = 0; i < tables.size(); i++) {
            System.out.println(tables.get(i).toString());
        }
        /*ArrayList<Table> tableList = null;
        tableList = accessTables.recommendTables(4, 7, 8, 12, 13);

        for(int i = 0; i < tableList.size(); i++){
            System.out.println(tableList.get(i).toString());
        }*/
        accessStub.close();
        Main.shutDown();
    }
}
