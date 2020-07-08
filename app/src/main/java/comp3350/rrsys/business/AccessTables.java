package comp3350.rrsys.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.persistence.DataAccessStub;

public class AccessTables
{
    private DataAccessStub dataAccess;
    private List<Customer> tables;

    public AccessTables()
    {
        dataAccess = (DataAccessStub)Services.createDataAccess(Main.dbName);
        tables = null;
    }

    public ArrayList<Table> getTables()
    {
        return dataAccess.getTableSequential();
    }

    public int getTableCapacity(int tID)
    {
        return dataAccess.getTableRandom(tID).getCapacity();
    }

    public ArrayList<Table> recommendTables(int numPeople, int month, int date, int startTime, int endTime)
    {

        ArrayList<Table> result = new ArrayList<>();
        ArrayList<Table> allTables;
        allTables = getTables();

        for(int i = 0; i < allTables.size(); i++)
        {
            if (allTables.get(i).getCapacity() >= numPeople)
            {
                for (int j = startTime; j < endTime; j++)
                {
                    if(!allTables.get(i).getAvailable(month, date, j))
                    {
                        //isAvail = false;
                        break;
                    }
                    else
                    {
                        result.add(allTables.get(i));
                    }
                }
            }
        }
        return result;
    }
}
