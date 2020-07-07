package comp3350.rrsys.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.Table;

public class DataAccessStub {

    private String dbName;
    private String dbType = "stub";

    private int customerID; // the customer ID of current customer (logged in)
    private ArrayList<Customer> customers;
    private ArrayList<Table> tables;
    private ArrayList<Reservation> reservations;

    public DataAccessStub(String dbName)
    {
        this.dbName = dbName;
    }

    public DataAccessStub()
    {
        this(Main.dbName);
    }

    public void open(String dbName)
    {
        //TODO: insert hardcoded database here
        Customer customer;
        Table table;
        Reservation reservation;

        customers = new ArrayList<Customer>();

        tables = new ArrayList<Table>();

        reservations = new ArrayList<Reservation>();
    }

    public void close()
    {
        System.out.println("Closed " + dbType + " database " + dbName);
    }

    //TODO: insert/implement database functions here

    public static void updateTables(){
        // clear last day/month's reservations of all tables ?
    }

    // return an array of suggested reservations in order
    // which has the same "length" as (endTime-startTime)
    public ArrayList<Reservation> searchReservations(int numPeople, DateTime startTime, DateTime endTime) {
        ArrayList<Reservation> results = new ArrayList<Reservation>();
        int month = startTime.getMonth();
        int day = startTime.getDate();
        int index = getIndex(startTime);
        int totalIncrement = startTime.getPeriod(endTime)/15; // total num of increments
        int maxIndex = Table.getNumIncrement(); // max index

        Table table;
        for(int t = 0; t < tables.size(); t++) {
            table = tables.get(t);
            if(table.getCapacity() >= numPeople) {
                // within +- 1 hour of the start time
                int i = Math.max(index-4, 0);
                while(i <= index+4 && i < maxIndex) {
                    while(i <= index+4 && i < maxIndex && !table.getAvailable(month, day, i))
                        i++;
                    if (i <= index + 4 && i < maxIndex) {
                        int numIncrement = 1;
                        for (int time = i + 1; time < i + totalIncrement; time++) {
                            if (time < maxIndex && table.getAvailable(month, day, time))
                                numIncrement++;
                            else
                                break;
                        }
                        if (numIncrement == totalIncrement) {
                            DateTime start = getDateTime(startTime, i);
                            DateTime end = getDateTime(endTime, i+numIncrement);
                            orderedInsert(results, new Reservation(customerID, table.getTID(), numPeople, start, end), index);
                        }
                    }
                    i++;
                }
            }
        }
        return results;
    }

    // return the index of a date time
    private int getIndex(DateTime time){
        return (time.getHour()-Table.getTime())*4 + time.getMinutes()/15;
    }

    // return the date time corresponding to an index
    private DateTime getDateTime(DateTime time, int index){
        DateTime result = null;
        try {
            result = new DateTime(Calendar.getInstance());
            result.setYear(time.getYear());
            result.setMonth(time.getMonth());
            result.setDate(time.getDate());
            result.setHour(Table.getTime() + index / 4);
            result.setMinutes(index % 4);
        }
        catch (java.text.ParseException pe) { System.out.println(pe); }
        return result;
    }

    // ordered insert a suggested reservation into a temp array
    // ordered by how close to the startTime
    private void orderedInsert(ArrayList<Reservation> results, Reservation r, int index) {
        int pos = 0;
        int max = results.size();
        while(pos < max && Math.abs(getIndex(results.get(pos).getStartTime())-index) < Math.abs(getIndex(r.getStartTime())-index))
            pos++;
        results.add(pos, r);
    }

    // set the availability of a table
    private void setTable(int tID, int month, int day, int startIndex, int endIndex, boolean bool) {
        Table table;
        for(int i = 0; i < tables.size(); i++) {
            table = tables.get(i);
            if(table.getTID() == tID) {
                for(int time = startIndex; time < endIndex; time++)
                    table.setAvailable(month, day, time, bool);
            }
        }
    }

    // insert a reservation
    public String insertReservation(Reservation r) {
        DateTime startTime = r.getStartTime();
        DateTime endTime = r.getEndTime();
        r.setRID();
        reservations.add(r);
        setTable(r.getTID(), startTime.getMonth(), startTime.getDate(), getIndex(startTime), getIndex(endTime), false);
        return null;
    }

    // get a reservation
    // get a reservation by reservationID
    public Reservation getReservation(int reservationID) {
        Reservation result = null;
        for(int i = 0; i < reservations.size(); i++) {
            if(reservations.get(i).equals(reservationID)) {
                result = reservations.get(i);
                break;
            }
        }
        return result;
    }

    // get an array of reservations by customerID
    public ArrayList<Reservation> getReservations(int customerID) {
        ArrayList<Reservation> results = new ArrayList<Reservation>();
        for(int i = 0; i < reservations.size(); i++) {
            if(reservations.get(i).equals(customerID))
                results.add(reservations.get(i));
        }
        return results;
    }

    // delete a reservation
    public String deleteReservation(Reservation r){
        for(int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).equals(r.getRID())) {
                DateTime start = r.getStartTime();
                DateTime end = r.getEndTime();
                setTable(r.getTID(), start.getMonth(), start.getDate(), getIndex(start), getIndex(end), true);
                reservations.remove(i);
                break;
            }
        }
        return null;
    }

    // delete a reservation by reservation ID
    public String deleteReservation(int rID){
        for(int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).equals(rID)) {
                DateTime start = reservations.get(i).getStartTime();
                DateTime end = reservations.get(i).getEndTime();
                setTable(reservations.get(i).getTID(), start.getMonth(), start.getDate(), getIndex(start), getIndex(end), true);
                reservations.remove(i);
                break;
            }
        }
        return null;
    }

    // update a reservation with rID to curr
    public String updateReservation(int rID, Reservation curr) {
        for(int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).equals(rID)) {
                Reservation prev = reservations.get(i);
                DateTime prevStart = prev.getStartTime();
                DateTime prevEnd = prev.getEndTime();
                DateTime currStart = curr.getStartTime();
                DateTime currEnd = curr.getEndTime();
                curr.setRID(prev.getRID());
                setTable(prev.getTID(), prevStart.getMonth(), prevStart.getDate(), getIndex(prevStart), getIndex(prevEnd), true);
                setTable(curr.getTID(), currStart.getMonth(), currStart.getDate(), getIndex(currStart), getIndex(currEnd), false);
                curr.setRID(prev.getRID());
                reservations.set(i, curr);
            }
        }
        return null;
    }

    public String getReservationSequential(List<Reservation> reservationResult) {
        reservationResult.addAll(reservations);
        return null;
    }

    public String getTableSequential(List<Table> tableResult) {
        tableResult.addAll(tables);
        return null;
    }

    public Table getTableRandom(int tableID) {
        Table result = null;
        for(int i = 0; i < tables.size(); i++) {
            if(tables.get(i).equals(tableID)) {
                result = tables.get(i);
                break;
            }
        }
        return result;
    }

    public String getCustomerSequential(List<Customer> customerResult) {
        customerResult.addAll(customers);
        return null;
    }

    public Customer getCustomerRandom(int customerID) {
        Customer result = null;
        for(int i = 0; i < customers.size(); i++) {
            if(tables.get(i).equals(customerID)) {
                result = customers.get(i);
                break;
            }
        }
        return result;
    }

    //Adds a customer to list by object
    public String insertCustomer(Customer customer) {
        customers.add(customer);
        return null;
    }

    //Adds a customer by raw data type values
    public String insertCustomer(String firstName, String lastName, String phoneNumber) {
        try {
            customers.add(new Customer(firstName, lastName, phoneNumber));
            return null;
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    //updates an existing customer with matching customer id
    public String updateCustomer(int customerID, Customer updatedCustomer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customerID == customers.get(i).getCID()) {
                try {
                    Customer prev = customers.get(i);
                    prev.setLastName(updatedCustomer.getLastName());
                    prev.setFirstName(updatedCustomer.getFirstName());
                    prev.setPhoneNumber(String.valueOf(updatedCustomer.getPhoneNumber()));
                    customers.set(i, prev);
                    break;
                } catch (IllegalArgumentException e) {
                    return e.getMessage();
                }
            }
            if(i + 1 >= customers.size()) {
                return "Error: Customer with ID: " + customerID + " already exists.";
            }
        }
        return null;
    }

    //Removes first customer with matching ID
    public String deleteCustomer(int customerID) {
        for (int i = 0; i < customers.size(); i++) {
            if (customerID == customers.get(i).getCID()) {
                customers.remove(i);
                break;
            }
        }
        return null;
    }

    //Adds table by Table object
    public String addTable(Table thisTable) {
        for ( int i = 0; i < tables.size(); i++ ) {
            if (thisTable.equals(tables.get(i).getTID())) {
                return "Error: Table with ID: " + thisTable.getTID() + " already exists.";
            }
        }
        tables.add(thisTable);
        return null;
    }

    //adds a new table object by raw data types
    public String addTable(int tableID, int size) {
        for (int i = 0; i < tables.size(); i++) {
            if(tableID == tables.get(i).getTID()) {
                return "Error: Table with ID: " + tableID + " already exists.";
            }
        }
        tables.add(new Table(tableID, size));
        return null;
    }

    //removes a table by matching table ID
    public String deleteTable(int tableID) {
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).equals(tableID)){
                tables.remove(i);
                break;
            }
            if(i + 1 >= tables.size()) {
                return "Table ID: " + tableID + " not found";
            }
        }
        return null;
    }

    //updates existing table with matching ID
    public String updateTable(int tableID, Table thisTable) {
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).equals(tableID)) {
                tables.set(i, thisTable);
                return null;
            }
            if(i + 1 >= tables.size()) {
                return "Table ID: " + tableID + " not found";
            }
        }
        return null;
    }
}
