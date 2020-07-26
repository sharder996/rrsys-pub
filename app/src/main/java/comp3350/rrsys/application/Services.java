package comp3350.rrsys.application;

import comp3350.rrsys.persistence.DataAccess;
import comp3350.rrsys.persistence.DataAccessObject;
import comp3350.rrsys.persistence.DataAccessStub;

public class Services
{
    //private static DataAccessStub dataAccessService = null;
    private static DataAccess dataAccessService = null;

    public static DataAccess createDataAccess(String dbName)
    {
        if(dataAccessService == null)
        {
           //dataAccessService = new DataAccessStub(dbName);
            dataAccessService = new DataAccessObject(dbName);
            dataAccessService.open(Main.dbName);
        }

        return dataAccessService;
    }

    public static DataAccess getDataAccess(String dbName)
    {
        if(dataAccessService == null)
        {
            System.out.println("Connection to data access has not been established");
            System.exit(1);
        }

        return dataAccessService;
    }

    public static void closeDataAccess()
    {
        if(dataAccessService != null)
        {
            dataAccessService.close();
        }
        dataAccessService = null;
    }
}
