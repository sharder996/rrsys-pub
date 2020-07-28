package comp3350.rrsys.business;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;
import comp3350.rrsys.persistence.DataAccess;

public class AccessOrders {

    private static DataAccess dataAccessStatic;
    private DataAccess dataAccess;
    private ArrayList<Item> order;

    public AccessOrders()
    {
        dataAccess = Services.getDataAccess(Main.dbName);
        dataAccessStatic = Services.getDataAccess(Main.dbName);
    }

    public String insertSelectedItem(Item newItem)
    {
        //order = newItem;
        return dataAccess.insertSelectedItem(newItem);
    }

    public String deleteSelectedItem(Item newItem){

        return dataAccess.deletedSelectedItem(newItem);
    }

    public ArrayList<Item> getOrder(){
        this.order = dataAccess.getOrder();
        return dataAccess.getOrder();
    }

    public double getPrice(){
        return dataAccess.getprice();
    }

    public int getSize(){
        return dataAccess.getSize();
    }
}

