package comp3350.rrsys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;

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

    // Reservation Functions:
    public String getReservationSequential(ArrayList<Reservation> reservationResult)
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
                Calendar calStart = new GregorianCalendar();
                calStart.setTime(rs2.getTimestamp("STARTTIME"));
                startTime = new DateTime(calStart);
                Calendar calEnd = new GregorianCalendar();
                calEnd.setTime(rs2.getTimestamp("ENDTIME"));
                endTime = new DateTime(calEnd);
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

    public String insertReservation(Reservation r)
    {
        String values;

        if(r == null || r.getEndTime() == null || r.getStartTime() == null || r.getNumPeople() < 0 || r.getTID() < 0 || r.getCID() < 0)
            return "fail";

        if(r.getEndTime().getDate() != r.getStartTime().getDate() || r.getEndTime().getHour() - r.getStartTime().getHour() > 3)
            return "fail";

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
            result = "success";
        }
        catch(Exception e)
        {
            result = "success";
        }

        return result;
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
            cmdString = "UPDATE RESERVATIONS" + " SET " + values + " " + where;
            updateCount = st0.executeUpdate(cmdString);
            result = checkWarning(st0, updateCount);
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        return result;
    }

    public String deleteReservation(int reservationID)
    {
        try
        {
            cmdString = "DELETE from RESERVATIONS where RID=" + reservationID;
            updateCount = st0.executeUpdate(cmdString);
            result = "success";
        }
        catch(Exception e)
        {
            result = "fail";
        }

        return result;
    }

    // get next reservationID for creating a new reservation
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

    // Table Functions:
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

    public Table getTable(int tableID)
    {
        Table table;
        int capacity;
        tables = new ArrayList<>();

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

    // Customer Functions:
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
            currID = customers.get(customers.size() - 1).getCID();
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

    // only for testing purpose
    public String deleteCustomer(Customer customer)
    {
        result = null;
        try
        {
            cmdString = "DELETE from CUSTOMERS where CID=" + customer.getCID();
            updateCount = st0.executeUpdate(cmdString);
            result = checkWarning(st0, updateCount);
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        return result;
    }


    // Menu Functions:
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

    // Order Functions:
    public Order getOrder(int rID)
    {
        ArrayList<Integer> itemID;
        ArrayList<Integer> quantities;
        ArrayList<String> notes;
        Order orderResult;
        Item item;
        int iID, quantity;
        String note, name, type, detail;
        double price;

        itemID = new ArrayList<>();
        notes = new ArrayList<>();
        quantities = new ArrayList<>();
        orderResult = null;
        try
        {
            cmdString = "SELECT * from ORDERS where RID=" + rID;
            rs2 = st0.executeQuery(cmdString);

            while(rs2.next())
            {
                iID = rs2.getInt("IID");
                quantity = rs2.getInt("QUANTITY");
                note = rs2.getString("NOTE");
                itemID.add(iID);
                notes.add(note);
                quantities.add(quantity);
            }
            rs2.close();

            if(itemID.size() > 0) // there exists at least one item in the order with rID
            {
                orderResult = new Order(rID);
                for(int i = 0; i < itemID.size(); i++)
                {
                    cmdString = "SELECT * from MENU where IID=" + itemID.get(i);
                    rs1 = st0.executeQuery(cmdString);

                    while (rs1.next()) //should only add 1
                    {
                        name = rs1.getString("NAME");
                        type = rs1.getString("TYPE");
                        detail = rs1.getString("DETAIL");
                        price = rs1.getDouble("PRICE");
                        item = new Item(itemID.get(i), name, type, detail, price);
                        orderResult.addItem(item, quantities.get(i), notes.get(i));
                    }
                    rs1.close();
                }
            }
        }
        catch(Exception e)
        {
            processSQLError(e);
        }

        return orderResult;
    }

    // insert single item into order
    public String insertItemIntoOrder(int resID, Item item)
    {
        String values;

        result = null;
        try
        {
            values = resID
                    + ", " + item.getItemID()
                    + ", " + item.getQuantity()
                    + ", '" + item.getNote()
                    + "'";

            cmdString = "INSERT into ORDERS VALUES(" + values + ")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }
        return result;
    }

    public String removeOrder(int resID)
    {
        result = null;
        try
        {
            cmdString = "DELETE from ORDERS where RID=" + resID;
            updateCount = st0.executeUpdate(cmdString);
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        return result;
    }

    // get the price of an order with reservationID
    public double getPrice(int resID)
    {
        double totalPrice = 0.0;
        try
        {
            cmdString = "SELECT SUM(PRICE*QUANTITY) as TOTAL_PRICE from MENU INNER JOIN ORDERS on MENU.IID = ORDERS.IID where RID =" + resID;
            rs2 = st0.executeQuery(cmdString);
            rs2.next();
            totalPrice = rs2.getDouble("TOTAL_PRICE");
            totalPrice = Math.round(totalPrice * 100.0) / 100.0;
            rs2.close();
        }
        catch(Exception e)
        {
            result = processSQLError(e);
        }

        return totalPrice;
    }
}