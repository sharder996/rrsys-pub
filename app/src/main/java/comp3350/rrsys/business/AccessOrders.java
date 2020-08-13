package comp3350.rrsys.business;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;
import comp3350.rrsys.persistence.DataAccess;

public class AccessOrders
{
    private DataAccess dataAccess;

    public AccessOrders() { dataAccess = Services.getDataAccess(Main.dbName); }

    public AccessOrders(DataAccess altDataAccessService) { dataAccess = Services.createDataAccess(altDataAccessService); }

    public Order getOrder(int resID) { return dataAccess.getOrder(resID); }

    public String insertOrder(Order order)
    {
        String result = null;

        ArrayList<Item> items = order.getOrder();
        for(int i = 0; i < items.size(); i++)
            result = dataAccess.insertItemIntoOrder(order.getReservationID(), items.get(i));

        return result;
    }

    public String removeOrder(int resID) { return dataAccess.removeOrder(resID); }

    public double getPrice(int resID) throws IllegalArgumentException
    {
        if(resID < 0)
            throw new IllegalArgumentException();

        return dataAccess.getPrice(resID);
    }

    public int getSize(int resID) { return dataAccess.getOrder(resID).getSize(); }
}

