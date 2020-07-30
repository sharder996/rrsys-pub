package comp3350.rrsys.persistence;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;

public class DataAccessObject implements DataAccess
{
    private Statement st0, st1, st2;
    private Connection c1;
    private ResultSet rs1, rs2, rs3;
    private String dbName;
    private String dbType;
    private ArrayList<Table> tables;
    private ArrayList<Reservation> reservations;
    private ArrayList<Item> menu;
    private String cmdString;
    private int updateCount;
    private String result;

    public DataAccessObject(String dbName)
    {
        this.dbName = dbName;
    }

    public void open(String dbPath)
    {
        String url;
        try
        {
            // Setup for HSQL
            dbType = "HSQL";
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
            c1 = DriverManager.getConnection(url, "SA", "");
            st0 = c1.createStatement();
            st1 = c1.createStatement();
            st2 = c1.createStatement();
        }
        catch(Exception e)
        {
            processSQLError(e);
        }
        System.out.println("Opened " + dbType + " database " + dbPath);
    }

    public void close()
    {
        try
        {
            // commit all changes to the database
            cmdString = "shutdown compact";
            rs1 = st0.executeQuery(cmdString);
            c1.close();
        }
        catch(Exception e)
        {
            processSQLError(e);
        }
        System.out.println("Closed " + dbType + " database " + dbName);
    }

    public String processSQLError(Exception e)
    {
        String result = "*** SQL Error: " + e.getMessage();

        // Remember, this will NOT be seen by the user!
        e.printStackTrace();

        return result;
    }

    public String checkWarning(Statement st, int updateCount)
    {
        String result = null;
        try
        {
            SQLWarning warning = st.getWarnings();
            if(warning != null)
                result = warning.getMessage();
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        if(updateCount != 1)
            result = "Tuple not inserted correctly.";

        return result;
    }

    public Reservation getReservation(int reservationID)
    {
        Reservation reservation;
        int custID, tableID, numPeople;
        DateTime startTime, endTime;
        reservations = new ArrayList<>();

        try
        {
            cmdString = "SELECT * from RESERVATIONS where RID=" + reservationID;
            rs2 = st0.executeQuery(cmdString);

            while(rs2.next())
            {
                custID = rs2.getInt("CID");
                tableID = rs2.getInt("TID");
                numPeople = rs2.getInt("NUMPEOPLE");
                Calendar calStart = new GregorianCalendar();
                calStart.setTime(rs2.getTimestamp("STARTTIME"));
                startTime = new DateTime(calStart);
                Calendar calEnd = new GregorianCalendar();
                calEnd.setTime(rs2.getTimestamp("ENDTIME"));
                endTime = new DateTime(calEnd);
                reservation = new Reservation(custID, tableID, numPeople, startTime, endTime);
                reservation.setRID(reservationID);
                reservations.add(reservation);
            }
            rs2.close();
        }
        catch(Exception e)
        {
            processSQLError(e);
        }

        if(reservations.size() == 1)
            return reservations.get(0);

        return null;
    }

    public String deleteReservation(int reservationID)
    {
        result = null;
        try
        {
            cmdString = "DELETE from RESERVATIONS where RID=" + reservationID;
            updateCount = st0.executeUpdate(cmdString);
            result = checkWarning(st0, updateCount);
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        return result;
    }

    public int getIndex(DateTime time)
    {
        return (time.getHour() - Table.START_TIME) * 4 + (time.getMinutes() + 7) / 15;
    }

    public String updateReservation(int RID, Reservation curr)
    {
        String values;
        String where;

        result = null;
        try
        {
            values = "TID=" + curr.getTID()
                    + ", NUMPEOPLE=" + curr.getNumPeople()
                    + ", STARTTIME='" + curr.getStartTime().toString()
                    + "', ENDTIME='" + curr.getEndTime().toString()
                    + "'";
            where = "where RID=" + RID;
            cmdString = "UPDATE RESERVATIONS " + " SET " + values + " " + where;
            updateCount = st0.executeUpdate(cmdString);
            result = checkWarning(st0, updateCount);

        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        return result;
    }

    // return the date time corresponding to an index
    public DateTime getDateTime(DateTime time, int index)
    {
        DateTime result = null;
        try
        {
            result = new DateTime(new GregorianCalendar(time.getYear(), time.getMonth(), time.getDate(), Table.START_TIME + index / 4, index % 4 * 15));
        }
        catch(IllegalArgumentException pe)
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
        while(pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) == Math.abs(r.getStartTime().getPeriod(t))
                && getTableRandom(results.get(pos).getTID()).getCapacity() < getTableRandom(r.getTID()).getCapacity())
            pos++;
        while(pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) == Math.abs(r.getStartTime().getPeriod(t)) &&
                getTableRandom(results.get(pos).getTID()).getCapacity() == getTableRandom(r.getTID()).getCapacity() && results.get(pos).getTID() < r.getTID())
            pos++;
        results.add(pos, r);
    }

    public int getNextReservationID()
    {
        int next = 0;
        try
        {
            cmdString = "SELECT MAX(RID) as RID from RESERVATIONS";
            rs2 = st0.executeQuery(cmdString);
            rs2.next();
            next = rs2.getInt("RID") + 1;
            rs2.close();
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        return next;
    }

    public String insertReservation(Reservation r)
    {
        String values;

        result = null;
        try
        {
            values = r.getRID()
                    + ", " + r.getCID()
                    + ", " + r.getTID()
                    + ", " + r.getNumPeople()
                    + ", '" + r.getStartTime().toString()
                    + "', '" + r.getEndTime().toString()
                    + "'";
            cmdString = "INSERT into RESERVATIONS " + " Values(" + values + ")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        return result;
    }

    public String getReservationSequential(List<Reservation> reservationResult)
    {
        Reservation reservation;
        int resID, custID, tableID, numPeople;
        DateTime startTime, endTime;

        result = null;
        try
        {
            cmdString = "SELECT * from RESERVATIONS";
            rs2 = st0.executeQuery(cmdString);

            while(rs2.next())
            {
                resID = rs2.getInt("RID");
                custID = rs2.getInt("CID");
                tableID = rs2.getInt("TID");
                numPeople = rs2.getInt("NUMPEOPLE");
                Calendar cal = new GregorianCalendar();
                cal.setTime(rs2.getTimestamp("STARTTIME"));
                startTime = new DateTime(cal);
                cal.setTime(rs2.getTimestamp("ENDTIME"));
                endTime = new DateTime(cal);
                reservation = new Reservation(custID, tableID, numPeople, startTime, endTime);
                reservation.setRID(resID);
                reservationResult.add(reservation);
            }
            rs2.close();
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        return result;
    }

    public String getTableSequential(ArrayList<Table> tableResult)
    {
        Table table;
        int tableID, capacity;

        result = null;
        try
        {
            cmdString = "SELECT * from TABLES";
            rs2 = st0.executeQuery(cmdString);

            while(rs2.next())
            {
                tableID = rs2.getInt("TID");
                capacity = rs2.getInt("CAPACITY");
                table = new Table(tableID, capacity);
                tableResult.add(table);
            }
            rs2.close();
        }
        catch(Exception e)
        {
            processSQLError(e);
        }

        return result;
    }

    public Table getTableRandom(int tableID)
    {
        Table table;
        int capacity;
        tables = new ArrayList<Table>();

        try
        {
            cmdString = "SELECT * from TABLES where TID=" + tableID;
            rs2 = st0.executeQuery(cmdString);

            while(rs2.next())
            {
                capacity = rs2.getInt("CAPACITY");
                table = new Table(tableID, capacity);
                tables.add(table);
            }
            rs2.close();
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        if(tables.size() == 1)
        {
            return tables.get(0);
        }

        return null;
    }

    public String addTable(int tableID, int size)
    {
        String values;

        result = null;
        try
        {
            values = tableID
                    + ", " + size;
            cmdString = "INSERT into TABLES " + " Values(" + values + ")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }
        return result;
    }

    public String getCustomerSequential(List<Customer> customerResult)
    {
        Customer customer;
        int cID;
        String fName, lName, phNum;

        result = null;
        try
        {
            cmdString = "SELECT * from CUSTOMERS";
            rs2 = st0.executeQuery(cmdString);

            while(rs2.next())
            {
                cID = rs2.getInt("CID");
                fName = rs2.getString("FIRSTNAME");
                lName = rs2.getString("LASTNAME");
                phNum = rs2.getString("PHONENUM");
                customer = new Customer(fName, lName, phNum);
                customer.setCID(cID);
                customerResult.add(customer);
            }
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        return result;
    }

    public String insertCustomer(Customer customer)
    {
        ArrayList<Customer> customers = new ArrayList<>();
        String values;
        int currID;

        getCustomerSequential(customers);
        result = null;
        try
        {
            currID = customers.get(customers.size()-1).getCID(); //may need a better way to get new ID
            currID++;
            values = currID
                    + ", '" + customer.getFirstName()
                    + "', '" + customer.getLastName()
                    + "', '" + customer.getPhoneNumber()
                    + "'";
            cmdString = "INSERT into CUSTOMERS" + " Values(" + values + ")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        return result;
    }

    public String insertCustomer(String firstName, String lastName, String phoneNumber)
    {
        ArrayList<Customer> customers = new ArrayList<>();
        String values;
        int currID;

        getCustomerSequential(customers);
        result = null;
        try
        {
            currID = customers.get(customers.size()-1).getCID();
            currID++;
            values = currID
                    + ", '" + firstName
                    + "', '" + lastName
                    + "', '" + phoneNumber
                    + "'";
            cmdString = "INSERT into CUSTOMERS" + " Values(" + values + ")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        return result;
    }

    public String insertItem(Item newItem)
    {
        String values;

        result = null;
        try
        {
            values = newItem.getItemID()
                    + ", '" + newItem.getName()
                    + "', '" + newItem.getType()
                    + "', '" + newItem.getDetail()
                    + "', " + newItem.getPrice();
            cmdString = "INSERT into MENU" + " Values(" + values + ")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        return result;
    }

    public ArrayList<Item> getMenuByType(String type)
    {
        Item item;
        int IID;
        String name, detail;
        double price;

        menu = new ArrayList<>();
        try
        {
            cmdString = "SELECT * from MENU where TYPE='" + type + "'";
            rs2 = st0.executeQuery(cmdString);

            while(rs2.next())
            {
                IID = rs2.getInt("IID");
                name = rs2.getString("NAME");
                detail = rs2.getString("DETAIL");
                price = rs2.getDouble("PRICE");
                item = new Item(IID, name, type, detail, price);
                menu.add(item);
            }
            rs2.close();
        }
        catch(Exception e)
        {
            processSQLError(e);
        }

        return menu;
    }

    public ArrayList<String> getMenuTypes()
    {
        ArrayList<String> types = new ArrayList<>();

        try
        {
            cmdString = "SELECT DISTINCT TYPE from MENU";
            rs3 = st0.executeQuery(cmdString);
            while (rs3.next())
                types.add(rs3.getString("TYPE"));
            rs3.close();
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }
        return types;
    }

    public ArrayList<Item> getMenu()
    {
        Item item;
        int IID;
        String name, detail;
        String type;
        double price;

        ArrayList<Item> returnMenu = new ArrayList<>();
        try
        {
            cmdString = "SELECT * from MENU";
            rs2 = st0.executeQuery(cmdString);
            while(rs2.next())
            {
                IID = rs2.getInt("IID");
                name = rs2.getString("NAME");
                type = rs2.getString("TYPE");
                detail = rs2.getString("DETAIL");
                price = rs2.getDouble("PRICE");
                item = new Item(IID, name, type, detail, price);
                returnMenu.add(item);
            }
            rs2.close();
        }
        catch(Exception e)
        {
            processSQLError(e);
        }

        return returnMenu;
    }

    public boolean[] getAvailable(int TID, DateTime time)
    {
        reservations = new ArrayList<>();
        getReservationSequential(reservations);
        boolean[] available = new boolean[Table.INTERVALS_PER_DAY];
        for(int i = 0; i < available.length; i++)
            available[i] = true;
        for(int i = 0; i < reservations.size(); i++)
        {
            if(reservations.get(i).getTID() == TID && reservations.get(i).getStartTime().getYear() == time.getYear() && reservations.get(i).getStartTime().getMonth() == time.getMonth()
                    && reservations.get(i).getStartTime().getDate() == time.getDate()) {
                int startIndex = getIndex(reservations.get(i).getStartTime());
                int endIndex = getIndex(reservations.get(i).getEndTime());
                for(int j = startIndex; j < endIndex; j++)
                    available[j] = false;
            }
        }
        return available;
    }

    public Boolean insertOrder(Order order)
    {
        ArrayList<Item> items;
        items = order.getOrder();
        ArrayList<Integer> quantities = new ArrayList<>();
        String values;
        result = null;

        int i = 0;
        Item current;
        boolean duplicate;
        while(i < items.size())
        {
            current = items.get(i);
            duplicate = false;
            for(int j = 0; j < i && !duplicate; j++)
            {
                if(current.equals(items.get(j)))
                {
                    quantities.set(j,quantities.get(j)+1);
                    items.remove(i);
                    duplicate = true;
                }
            }
            if(!duplicate)
            {
                quantities.add(1);
                i++;
            }
        }
        try
        {
            for(int k  = 0 ; k < items.size(); k++)
            {
                values = order.getReservationID()
                        + ", " + items.get(k).getItemID()
                        + ", " + quantities.get(k)
                        + ", '" + order.getNote()
                        + "'";

                cmdString = "INSERT into ORDERS VALUES(" + values + ")";
                updateCount = st1.executeUpdate(cmdString);
                result = checkWarning(st1, updateCount);
            }
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }
        return result != null;
    }

    public ArrayList<Item> getOrder(int rID)
    {
        ArrayList<Item> items;
        ArrayList<Integer> itemID;
        Item item;
        int iID, quantity;
        String name, type, detail;
        double price;

        itemID = new ArrayList<>();
        items = new ArrayList<>();
        try
        {
            cmdString = "SELECT * from ORDERS where RID=" + rID;
            rs2 = st0.executeQuery(cmdString);

            while(rs2.next())
            {
                quantity = rs2.getInt("QUANTITY");
                for(int i = 0; i < quantity; i++)
                {
                    iID = rs2.getInt("IID");
                    itemID.add(iID);
                }
            }
            rs2.close();


            for(int i = 0; i < itemID.size(); i++)
            {
                cmdString = "SELECT * from MENU where IID=" + itemID.get(i);
                rs1 = st0.executeQuery(cmdString);

                while(rs1.next())
                {
                    name = rs1.getString("NAME");
                    type = rs1.getString("TYPE");
                    detail = rs1.getString("DETAIL");
                    price = rs1.getDouble("PRICE");
                    item = new Item(itemID.get(i), name, type, detail, price);
                    items.add(item);
                }
                rs1.close();
            }

        }
        catch(Exception e)
        {
            processSQLError(e);
        }

        return items;
    }

    //GetPrice and size method should be used in confirmation page - Cody
    public double getPrice(int reservationID)
    {
        ArrayList<Item> items;
        double totalPrice = 0.0;

        items = getOrder(reservationID);
        for(Item item : items)
        {
            totalPrice += item.getPrice();
        }

        return totalPrice;
    }

    public int getSize(int reservationID)
    {
        ArrayList<Item> items = getOrder(reservationID);
        return items.size();
    }
}