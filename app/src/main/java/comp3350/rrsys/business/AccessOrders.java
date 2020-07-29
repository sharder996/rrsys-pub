package comp3350.rrsys.business;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Item;
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

    public String insertSelectedItem(Item newItem, int oID, int quantity, String note)
    {
        return dataAccess.insertSelectedItem(newItem, oID, quantity, note);
    }

    public String deleteSelectedItem(Item newItem, int oID)
    {
        return dataAccess.deletedSelectedItem(newItem, oID);
    }

    public ArrayList<Item> getOrder(int oID)
    {
        this.order = dataAccess.getOrder(oID);
        return dataAccess.getOrder(oID);
    }

    public double getPrice(int oID){
        return dataAccess.getPrice(oID);
    }

    public int getSize(int oID) { return dataAccess.getSize(oID); }
}

