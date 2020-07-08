package comp3350.rrsys.business;

import java.util.List;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.application.Services;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.persistence.DataAccessStub;

public class AccessCustomers {
    private DataAccessStub dataAccess;
    private List<Customer> customers;
    private Customer customer;
    private int currentCustomer;

    public AccessCustomers() {
        //dataAccess = (DataAccessStub)Services.createDataAccess(Main.dbName);
        dataAccess = (DataAccessStub)Services.getDataAccess(Main.dbName);
        customers = null;
        customer = null;
        currentCustomer = 0;
    }

    public String getCustomers(List<Customer> customers) {
        customers.clear();
        return dataAccess.getCustomerSequential(customers);
    }

    public Customer getSequential()
    {
        if (customers == null) {
            dataAccess.getCustomerSequential(customers);
            currentCustomer = 0;
        }
        if (currentCustomer < customers.size()) {
            customer = (Customer) customers.get(currentCustomer);
            currentCustomer++;
        }
        else {
            customers = null;
            customer = null;
            currentCustomer = 0;
        }
        return customer;
    }

    public Customer getRandom(int customerID)
    {
        customers = null;
        customer = dataAccess.getCustomerByID(customerID);
        currentCustomer = 0;
        if(customer != null) {
            customers.add(customer);
            currentCustomer++;
        }
        return customer;
    }

    public int getCustomerID(String fName, String lName) {
        return dataAccess.getCustomerID(fName, lName);
    }

    public int getCustomerID(int phoneNum) {
        return dataAccess.getCustomerID(phoneNum);
    }

    public String insertCustomer(Customer currentCustomer)
    {
        return dataAccess.insertCustomer(currentCustomer);
    }
}
