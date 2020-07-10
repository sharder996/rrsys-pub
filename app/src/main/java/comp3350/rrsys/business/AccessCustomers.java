package comp3350.rrsys.business;

import java.util.ArrayList;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.persistence.DataAccessStub;

public class AccessCustomers
{
    private DataAccessStub dataAccess;

    public AccessCustomers()
    {
        dataAccess = (DataAccessStub)Services.getDataAccess(Main.dbName);
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
