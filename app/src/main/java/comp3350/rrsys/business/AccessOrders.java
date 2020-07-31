package comp3350.rrsys.business;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Order;
import comp3350.rrsys.persistence.DataAccess;

public class AccessOrders
{
    private DataAccess dataAccess;

    public AccessOrders() { dataAccess = Services.getDataAccess(Main.dbName); }

    public AccessOrders(DataAccess altDataAccessService) { dataAccess = Services.createDataAccess(altDataAccessService); }

    public Boolean insertOrder(Order order) { return dataAccess.insertOrder(order); }
}

