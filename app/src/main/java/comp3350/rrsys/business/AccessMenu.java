package comp3350.rrsys.business;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.persistence.DataAccess;

public class AccessMenu
{
    private DataAccess dataAccess;

    public AccessMenu() { dataAccess = Services.getDataAccess(Main.dbName); }

    public AccessMenu(DataAccess altDataAccessService) { dataAccess = Services.createDataAccess(altDataAccessService); }

    public ArrayList<Item> getMenuByType(String type) { return dataAccess.getMenuByType(type); }

    public ArrayList<String> getMenuTypes() { return dataAccess.getMenuTypes(); }
}
