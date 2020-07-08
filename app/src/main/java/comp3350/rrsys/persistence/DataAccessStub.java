package comp3350.rrsys.persistence;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.Table;

public class DataAccessStub
{

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
        generateFakeData();
    }

    public void close()
    {
        System.out.println("Closed " + dbType + " database " + dbName);
    }

    public static void updateTables()
    {
        // clear last day/month's reservations of all tables ?
    }

    // return an array of suggested reservations in order
    // which has the same "length" as (endTime-startTime)
    public ArrayList<Reservation> searchReservations(int numPeople, DateTime startTime, DateTime endTime)
    {
        ArrayList<Reservation> results = new ArrayList<Reservation>();
        int month = startTime.getMonth();
        int day = startTime.getDate();
        int index = getIndex(startTime);
        int totalIncrement = (startTime.getPeriod(endTime)+7)/15; // total num of increments
        int maxIndex = Table.getNumIncrement(); // max index

        Table table;
        for(int t = 0; t < tables.size(); t++)
        {
            table = tables.get(t);
            if(table.getCapacity() >= numPeople)
            {
                // within +- half hour of the start time
                int i = Math.max(index-2, 0);
                while(i <= index+2 && i < maxIndex)
                {
                    while(i <= index+2 && i < maxIndex && !table.getAvailable(month, day, i))
                        i++;
                    if (i <= index + 2 && i < maxIndex)
                    {
                        int numIncrement = 1;
                        for (int time = i + 1; time < i + totalIncrement; time++)
                        {
                            if (time < maxIndex && table.getAvailable(month, day, time))
                                numIncrement++;
                            else
                                break;
                        }
                        if (numIncrement == totalIncrement)
                        {
                            DateTime start = getDateTime(startTime, i);
                            DateTime end = getDateTime(endTime, i+numIncrement);
                            orderedInsert(results, new Reservation(customerID, table.getTID(), numPeople, start, end), startTime);
                        }
                    }
                    i++;
                }
            }
        }
        return results;
    }

    // return the index of a date time
    private int getIndex(DateTime time)
    {
        return (time.getHour()-Table.getStartTime())*4 + (time.getMinutes()+7)/15;
    }

    // return the date time corresponding to an index
    private DateTime getDateTime(DateTime time, int index)
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
        catch (java.text.ParseException pe) { System.out.println(pe); }
        return result;
    }

    // ordered insert a suggested reservation into a temp array
    // ordered by how close to the startTime
    private void orderedInsert(ArrayList<Reservation> results, Reservation r, DateTime t)
    {
        int pos = 0;
        int max = results.size();
        while(pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) < Math.abs(r.getStartTime().getPeriod(t)))
            pos++;
        while(pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) == Math.abs(r.getStartTime().getPeriod(t)) && getTableRandom(results.get(pos).getTID()).getCapacity() < getTableRandom(r.getTID()).getCapacity())
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
        DateTime startTime = r.getStartTime();
        DateTime endTime = r.getEndTime();
        r.setRID();
        reservations.add(r);
        setTable(r.getTID(), startTime.getMonth(), startTime.getDate(), getIndex(startTime), getIndex(endTime), false);
        return null;
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

    // get an array of reservations by customerID
    public ArrayList<Reservation> getReservations(int customerID)
    {
        ArrayList<Reservation> results = new ArrayList<Reservation>();
        for(int i = 0; i < reservations.size(); i++)
        {
            if(reservations.get(i).equals(customerID))
                results.add(reservations.get(i));
        }
        return results;
    }

    // delete a reservation
    public String deleteReservation(Reservation r)
    {
        for(int i = 0; i < reservations.size(); i++)
        {
            if (reservations.get(i).equals(r.getRID()))
            {
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
    public String deleteReservation(int rID)
    {
        for(int i = 0; i < reservations.size(); i++)
        {
            if (reservations.get(i).equals(rID))
            {
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
    public String updateReservation(int rID, Reservation curr)
    {
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
        return null;
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

    public Customer getCustomerByID(int customerID)
    {
        Customer result = null;
        for(int i = 0; i < customers.size(); i++)
        {
            if(customers.get(i).equals(customerID))
            {
                result = customers.get(i);
                break;
            }
        }
        return result;
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

    //updates an existing customer with matching customer id
    public String updateCustomer(int customerID, Customer updatedCustomer)
    {
        for (int i = 0; i < customers.size(); i++)
        {
            if (customerID == customers.get(i).getCID())
            {
                try
                {
                    Customer prev = customers.get(i);
                    prev.setLastName(updatedCustomer.getLastName());
                    prev.setFirstName(updatedCustomer.getFirstName());
                    prev.setPhoneNumber(String.valueOf(updatedCustomer.getPhoneNumber()));
                    customers.set(i, prev);
                    break;
                }
                catch (IllegalArgumentException e)
                {
                    return e.getMessage();
                }
            }
            if(i + 1 >= customers.size())
            {
                return "Error: Customer with ID: " + customerID + " already exists.";
            }
        }
        return null;
    }

    //Removes first customer with matching ID
    public String deleteCustomer(int customerID)
    {
        for (int i = 0; i < customers.size(); i++)
        {
            if (customerID == customers.get(i).getCID())
            {
                customers.remove(i);
                break;
            }
        }
        return null;
    }

    //Adds table by Table object
    public String addTable(Table thisTable)
    {
        for ( int i = 0; i < tables.size(); i++ )
        {
            if (thisTable.equals(tables.get(i).getTID()))
            {
                return "Error: Table with ID: " + thisTable.getTID() + " already exists.";
            }
        }
        tables.add(thisTable);
        return null;
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

    //removes a table by matching table ID
    public String deleteTable(int tableID)
    {
        for (int i = 0; i < tables.size(); i++)
        {
            if (tables.get(i).equals(tableID))
            {
                tables.remove(i);
                break;
            }
            if(i + 1 >= tables.size())
            {
                return "Table ID: " + tableID + " not found";
            }
        }
        return null;
    }

    //updates existing table with matching ID
    public String updateTable(int tableID, Table thisTable)
    {
        for (int i = 0; i < tables.size(); i++)
        {
            if (tables.get(i).equals(tableID))
            {
                tables.set(i, thisTable);
                return null;
            }
            if(i + 1 >= tables.size())
            {
                return "Table ID: " + tableID + " not found";
            }
        }
        return null;
    }

    public void generateFakeData()
    {
        //generate tables in the restaurant.
        //assume there are 30 tables
        // Table ID will be 1 to 30.
        // 15 tables for maximum 4 people , 10 tables for maximum 6 people, 5 tables for maximum 10

        // 5 tables each for 2, 4, 6, 8, 10, 12 people, totally 30 tables
        int size = 2;
        for(int i = 1; i <= 30; i++)
        {
            addTable(i, size);
            if(i % 5 == 0)
                size += 2;
        }

        /*
        for(int i = 1; i < 16; i++){ // 4 people maximum table. Table ID will be 1 to 15
            addTable(i, 4);
        }

        for(int i = 0; i < 10; i++){ // 6 people maximum table. Table ID will be 16 to 25
            addTable(i + 16, 6);
        }

        for(int i= 0; i < 5; i++){ // 10 people maximum table. Table ID will be 26 to 30
            addTable(i+16+10, 10);
        }*/

        //generate customer informations
        //assume there are 100 customers.
        Random rand = new Random();
        String name = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String randomfirstName = "";
        String randomlastName = "";

        int length = 4;

        for(int k = 1000; k <1100; k++) { // k is last four digits in phone number. (100 customers)

            ///////////////////////////////////////////////////////////////////////////
            // generate random names
            char[] first = new char[length];
            char[] last = new char[length + 2];

            for (int i = 0; i < length; i++)
            {
                first[i] = name.charAt(rand.nextInt(name.length()));
            }

            for (int i = 0; i < length; i++)
            {
                last[i] = name.charAt(rand.nextInt(name.length()));
            }

            for (int i = 0; i < first.length; i++)
            {
                randomfirstName += first[i];
            }

            for (int i = 0; i < last.length; i++)
            {
                    randomlastName += last[i];
            }
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

        Calendar currTime = Calendar.getInstance(); // get Current Date and Time e.g.. July 8th, 2020.

        /*// Make DateTime for next day e.g.. July 9th, 2020;
        Calendar time = new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE) +1);
        //make 5 reservations on first day e.g. July 9th.
        fakeReservation(currTime, time);

        time = new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE) +2);
        //make 5 reservations on first day e.g. July 10th.
        fakeReservation(currTime, time);

        time = new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE) +3);
        //make 5 reservations on first day e.g. July 11th.
        fakeReservation(currTime, time);

        time = new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE) +4);
        //make 5 reservations on first day e.g. July 12th.
        fakeReservation(currTime, time);

        time = new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE) +5);
        //make 5 reservations on first day e.g. July 13th.
        fakeReservation(currTime, time);*/
    }

    private void fakeReservation(Calendar currTime, Calendar time)
    {
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        int Min = 0;
        int Max = 99;
        int random = 0;
        random = Min + (int)(Math.random() * ((Max - Min) + 1));
        Reservation reservation1;
        DateTime startTime = null;
        DateTime endTime = null;
        try
        {
            startTime = new DateTime(new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE), 11, 00));
            endTime = new DateTime(new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE), 12, 30));
        }
        catch (ParseException e) { }

        reservation1 = new Reservation(customers.get(random).getCID(), tables.get(17).getTID(), 5, startTime, endTime);
        insertReservation(reservation1);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        Reservation reservation2;
        DateTime startTime2 = null;
        DateTime endTime2 = null;
        try
        {
            startTime2 = new DateTime(new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE), 14, 00));
            endTime2 = new DateTime(new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE), 16, 30));
        }
        catch (ParseException e) { }

        reservation2 = new Reservation(tables.get(29).getTID(), 7, startTime2, endTime2);
        insertReservation(reservation2);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        random = Min + (int)(Math.random() * ((Max - Min) + 1));

        Reservation reservation3;
        DateTime startTime3 = null;
        DateTime endTime3 = null;
        try
        {
            startTime3 = new DateTime(new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE), 13, 00));
            endTime3 = new DateTime(new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE), 14, 00));
        }
        catch (ParseException e) { }

        reservation3 = new Reservation(customers.get(random).getCID(), tables.get(18).getTID(), 6, startTime3, endTime3);
        insertReservation(reservation3);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        random = Min + (int)(Math.random() * ((Max - Min) + 1));

        Reservation reservation4;
        DateTime startTime4 = null;
        DateTime endTime4 = null;
        try
        {
            startTime4 = new DateTime(new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE), 18, 00));
            endTime4 = new DateTime(new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE), 21, 00));
        }
        catch (ParseException e) { }

        reservation4 = new Reservation(customers.get(random).getCID(), tables.get(0).getTID(), 4, startTime4, endTime4);
        insertReservation(reservation4);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        random = Min + (int)(Math.random() * ((Max - Min) + 1));

        Reservation reservation5;
        DateTime startTime5 = null;
        DateTime endTime5 = null;
        try
        {
            startTime5 = new DateTime(new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE), 17, 00));
            endTime5 = new DateTime(new GregorianCalendar(currTime.get(currTime.YEAR), currTime.get(currTime.MONTH), currTime.get(currTime.DATE), 20, 15));
        }
        catch (ParseException e) { }

        reservation5 = new Reservation(customers.get(random).getCID(), tables.get(28).getTID(), 10, startTime5, endTime5);
        insertReservation(reservation5);
    }
}
