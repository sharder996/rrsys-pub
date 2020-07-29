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
    private ArrayList<Item> order;
    Order newOrder;

    public AccessOrders()
    {
        dataAccess = Services.getDataAccess(Main.dbName);
        dataAccessStatic = Services.getDataAccess(Main.dbName);

    }

    public String insertOrder(Order order)
    {
        // TODO: implement this method that puts an order in the database

        return dataAccess.insertOrder(order);
    }

    public ArrayList<Item> getOrder(int reservationID)
    {
        this.order = dataAccess.getOrder(reservationID);
        return dataAccess.getOrder(reservationID);
    }

    public double getPrice(int reservationID) { return dataAccess.getPrice(reservationID); }

    public int getSize(int reservationID) { return dataAccess.getSize(reservationID); }
}

