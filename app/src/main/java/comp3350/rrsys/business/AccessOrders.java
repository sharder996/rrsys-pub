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

    public String insertOrder(Order order)
    {
        String result = null;

        ArrayList<Item> items = order.getOrder();
        for(int i = 0; i < items.size(); i++)
            result = dataAccess.insertItemIntoOrder(order.getReservationID(), items.get(i), items.get(i).getNote());

        return result;
    }

    public String removeOrder(int resID)
    {
        return dataAccess.removeOrder(resID);
    }

    public Order getOrder(int reservationID) { return dataAccess.getOrder(reservationID); }

    public double getPrice(int reservationID) throws IllegalArgumentException
    {
        if(reservationID < 0)
            throw new IllegalArgumentException();

        return dataAccess.getPrice(reservationID);
    }

    public int getSize(int reservationID)
    {
        return dataAccess.getOrder(reservationID).size();
    }

    public int getNextReservationID() { return dataAccess.getNextReservationID(); }

    public boolean getNextLineItem(int resID)
    {
       boolean exist = false;

       if(dataAccess.getNextLineItem(resID) > 1)
       {
           exist = true;
       }

        return exist;
    }

}

