package comp3350.rrsys.business;

import java.util.List;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.persistence.DataAccessStub;

public class AccessTables {
    private DataAccessStub dataAccess;
    private List<Customer> tables;

    public AccessTables() {
        dataAccess = (DataAccessStub)Services.createDataAccess(Main.dbName);
        tables = null;
    }

    public String getTables(List<Table> tables) {
        tables.clear();
        return dataAccess.getTables(tables);
    }

    public int getTableCapacity(int tID) {
        return dataAccess.getTableCapacity(tID);
    }

}
