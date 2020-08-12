package comp3350.rrsys.business;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;
import comp3350.rrsys.persistence.DataAccess;

public class AccessOrders
{
    private static DataAccess dataAccessStatic;
    private DataAccess dataAccess;

    public AccessOrders() { dataAccess = Services.getDataAccess(Main.dbName); }

    public AccessOrders(DataAccess altDataAccessService) { dataAccess = Services.createDataAccess(altDataAccessService); }

    public String insertOrder(Order order) { return insertItemNewOrder(order.getOrder(), order.getReservationID()); }

    private String insertItemNewOrder(ArrayList<Item> items, int resID)
    {
        String result = null;

        for(int i = 0; i < items.size(); i++)
            result = dataAccess.insertItemIntoOrder(resID, items.get(i), items.get(i).getNote());

        return result;
    }

    public String insertItemExistingOrder(int resID, Item item, String note)
    {
        String result = null;

        result = dataAccess.insertItemIntoOrder(resID, item, note);

        return result;
    }

    public String removeOrder(int resID)
    {
        String result = null;

        int maxLineItem = dataAccess.getNextLineItem(resID);
        for(int i = 1; i < maxLineItem; i++)
            removeItemFromOrder(resID, i);

        return result;
    }

    public String removeItemFromOrder(int resID, int lineItem)
    {
        String result = null;

        result = dataAccess.removeItemFromOrder(resID, lineItem);

        return result;
    }

    public Order getOrder(int reservationID) { return dataAccess.getOrder(reservationID); }

    //GetPrice and size method should be used in confirmation page - Cody
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

}

