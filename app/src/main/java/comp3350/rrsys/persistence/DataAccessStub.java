package comp3350.rrsys.persistence;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;

public class DataAccessStub
{
    private String dbName;
    private String dbType = "stub";

    //private int customerID; // the customer ID of current customer (logged in)
    private ArrayList<Customer> customers;
    private ArrayList<Table> tables;
    private ArrayList<Reservation> reservations;
    private ArrayList<Item> menu;
    private ArrayList<Order> orders;

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
        customers = new ArrayList<Customer>();
        tables = new ArrayList<Table>();
        reservations = new ArrayList<Reservation>();
        menu = new ArrayList<Item>();
        orders = new ArrayList<Order>();

        generateFakeData();
    }

    public void close()
    {
        System.out.println("Closed " + dbType + " database " + dbName);
    }

    // return the index of a date time
    public int getIndex(DateTime time)
    {
        return (time.getHour()-Table.getStartTime())*4 + (time.getMinutes()+7)/15;
    }

    // return the date time corresponding to an index
    public DateTime getDateTime(DateTime time, int index)
    {
        DateTime result = null;
        try
        {
            result = new DateTime(Calendar.getInstance());
            result.setYear(time.getYear());
            result.setMonth(time.getMonth());
            result.setDate(time.getDate());
            result.setHour(Table.getStartTime() + index / 4);
            result.setMinutes(index % 4 * 15);
        }
        catch (IllegalArgumentException pe)
        {
            System.out.println(pe);
        }
        return result;
    }

    // ordered insert a suggested reservation into a temp array
    // ordered by how close to the startTime
    public void orderedInsert(ArrayList<Reservation> results, Reservation r, DateTime t)
    {
        int pos = 0;
        int max = results.size();
        while(pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) < Math.abs(r.getStartTime().getPeriod(t)))
            pos++;
        while(pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) == Math.abs(r.getStartTime().getPeriod(t)) && getTableRandom(results.get(pos).getTID()).getCapacity() < getTableRandom(r.getTID()).getCapacity())
            pos++;
        while(pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) == Math.abs(r.getStartTime().getPeriod(t)) &&
                getTableRandom(results.get(pos).getTID()).getCapacity() == getTableRandom(r.getTID()).getCapacity() && results.get(pos).getTID() < r.getTID())
            pos++;
        results.add(pos, r);
    }

    // set the availability of a table
    private void setTable(int tID, int month, int day, int startIndex, int endIndex, boolean bool)
    {
        Table table;
        for(int i = 0; i < tables.size(); i++)
        {
            table = tables.get(i);
            if(table.getTID() == tID)
            {
                for(int time = startIndex; time < endIndex; time++)
                    table.setAvailable(month, day, time, bool);
            }
        }
    }

    // insert a reservation
    public String insertReservation(Reservation r)
    {
        if(r == null || r.getEndTime() == null || r.getStartTime() == null || r.getNumPeople() < 0 || r.getTID() < 0){
            return "fail";
        }
        DateTime startTime = r.getStartTime();
        DateTime endTime = r.getEndTime();
        r.setRID();
        reservations.add(r);
        setTable(r.getTID(), startTime.getMonth(), startTime.getDate(), getIndex(startTime), getIndex(endTime), false);
        return "success";
    }

    // get a reservation
    // get a reservation by reservationID
    public Reservation getReservation(int reservationID)
    {
        Reservation result = null;
        for(int i = 0; i < reservations.size(); i++)
        {
            if(reservations.get(i).equals(reservationID))
            {
                result = reservations.get(i);
                break;
            }
        }
        return result;
    }

    // delete a reservation by reservation ID
    public String deleteReservation(int rID)
    {
        boolean found = false;
        for(int i = 0; i < reservations.size(); i++)
        {
            if (reservations.get(i).equals(rID))
            {
                DateTime start = reservations.get(i).getStartTime();
                DateTime end = reservations.get(i).getEndTime();
                setTable(reservations.get(i).getTID(), start.getMonth(), start.getDate(), getIndex(start), getIndex(end), true);
                reservations.remove(i);
                found = true;
                break;
            }
        }
        if(!found) {
            return "fail";
        }
        return "success";
    }

    // update a reservation with rID to curr
    public String updateReservation(int rID, Reservation curr)
    {
        if(curr.getNumPeople() < 0 || curr.getTID() < 0 ){
            return "fail";
        }
        for(int i = 0; i < reservations.size(); i++)
        {
            if (reservations.get(i).equals(rID))
            {
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
        return "success";
    }

    public String getReservationSequential(List<Reservation> reservationResult)
    {
        reservationResult.addAll(reservations);
        return null;
    }

    public ArrayList<Table> getTableSequential()
    {
        ArrayList<Table> result = new ArrayList<Table>();
        result.addAll(tables);
        return result;
    }

    public Table getTableRandom(int tableID)
    {
        Table result = null;
        for(int i = 0; i < tables.size(); i++)
        {
            if(tables.get(i).equals(tableID))
            {
                result = tables.get(i);
                break;
            }
        }
        return result;
    }

    public String getCustomerSequential(List<Customer> customerResult)
    {
        customerResult.addAll(customers);
        return null;
    }

    //Adds a customer to list by object
    public String insertCustomer(Customer customer)
    {
        customers.add(customer);
        return null;
    }

    //Adds a customer by raw data type values
    public String insertCustomer(String firstName, String lastName, String phoneNumber)
    {
        try
        {
            customers.add(new Customer(firstName, lastName, phoneNumber));
            return null;
        }
        catch (IllegalArgumentException e)
        {
            return e.getMessage();
        }
    }

    //adds a new table object by raw data types
    public String addTable(int tableID, int size)
    {
        for (int i = 0; i < tables.size(); i++)
        {
            if(tableID == tables.get(i).getTID())
            {
                return "Error: Table with ID: " + tableID + " already exists.";
            }
        }
        tables.add(new Table(tableID, size));
        return null;
    }

    public void generateFakeData()
    {
        //generate tables in the restaurant.
        //assume there are 30 tables
        // Table ID will be 1 to 30.
        // 5 tables each for 2, 4, 6, 8, 10, 12 people, totally 30 tables
        int size = 2;
        for(int i = 1; i <= 30; i++)
        {
            addTable(i, size);
            if(i % 5 == 0)
                size += 2;
        }

        //generate customer informations
        //assume there are 100 customers.
        Random rand = new Random();
        String name = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String randomfirstName = "";
        String randomlastName = "";

        int length = 4;

        for(int k = 1000; k <1100; k++)
        { // k is last four digits in phone number. (100 customers)
            ///////////////////////////////////////////////////////////////////////////
            // generate random names
            char[] first = new char[length];
            char[] last = new char[length + 2];

            for (int i = 0; i < length; i++)
                first[i] = name.charAt(rand.nextInt(name.length()));

            for (int i = 0; i < length; i++)
                last[i] = name.charAt(rand.nextInt(name.length()));

            for (int i = 0; i < first.length; i++)
                randomfirstName += first[i];

            for (int i = 0; i < last.length; i++)
                    randomlastName += last[i];

            ///////////////////////////////////////////////////////////////////////////
            // generate random phone numbers
            int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
            int num2 = rand.nextInt(743);
            int num3 = rand.nextInt(10000);

            DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
            DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros

            String phoneNumber = df3.format(num1) + "-" + df3.format(num2) + "-" + df4.format(num3);
            insertCustomer(randomfirstName, randomlastName, phoneNumber);
        }
    }

    public String insertItem(Item newItem)
    {
        menu.add(newItem);
        return null;
    }


    public String getMenuSequential(ArrayList<Item> menuResult)
    {
        menuResult.addAll(menu);
        return null;
    }

    public ArrayList<Item> getMenuByType(String type)
    {
        ArrayList<Item> items = new ArrayList<>();
        for(int i = 0; i < menu.size(); i++)
        {
            if(menu.get(i).getType().equals(type))
                items.add(menu.get(i));
        }
        return items;
    }

    public ArrayList<String> getMenuTypes(){
        ArrayList<String> types = new ArrayList<String>();

        types.add("Drinks");
        types.add("Sandwiches");
        types.add("Salads");
        types.add("Desserts");

        return types;
    }
}
