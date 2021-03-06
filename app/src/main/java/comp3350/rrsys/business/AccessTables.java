package comp3350.rrsys.business;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.persistence.DataAccess;

public class AccessTables
{
    private DataAccess dataAccess;

    public AccessTables() { dataAccess = Services.createDataAccess(Main.dbName); }

    public AccessTables(DataAccess altDataAccessService) { dataAccess = Services.createDataAccess(altDataAccessService); }

    public Table getRandom(int tID) { return dataAccess.getTable(tID); }
}
