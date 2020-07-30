package comp3350.rrsys.business;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.persistence.DataAccess;

public class AccessCustomers
{
    private DataAccess dataAccess;

    public AccessCustomers()
    {
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    public AccessCustomers(DataAccess altDataAccessService)
    {
        dataAccess = Services.createDataAccess(altDataAccessService);
    }

    public String getCustomers(ArrayList<Customer> customers)
    {
        customers.clear();
        return dataAccess.getCustomerSequential(customers);
    }

    public String insertCustomer(Customer currentCustomer)
    {
        return dataAccess.insertCustomer(currentCustomer);
    }
}
