package comp3350.rrsys.business;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.persistence.DataAccessStub;

public class AccessMenu {
    private DataAccessStub dataAccess;

    public AccessMenu()
    {
        dataAccess = (DataAccessStub)Services.getDataAccess(Main.dbName);
    }

    public ArrayList<Item> getMenu(ArrayList<Item> menu)
    {
        menu.clear();
        dataAccess.getMenuSequential(menu);
        return menu;
    }

    public ArrayList<Item> getMenuByType(String type)
    {
        return dataAccess.getType(type);
    }
}
