package comp3350.rrsys.persistence;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.sql.DatabaseMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Menu;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;

public class DataAccessObject implements DataAccess {

    private Statement st0, st1, st2;
    private Connection c0;
    private ResultSet rs0, rs1, rs2, rs3;

    private String dbName;
    private String dbType;

    private ArrayList<Customer> customers;
    private ArrayList<Table> tables;
    private ArrayList<Reservation> reservations;
    private ArrayList<Item> menu;
    private ArrayList<Order> orders;

    private String cmdString;
    private int updateCount;
    private String result;
    private static String EOF = "  ";

    public DataAccessObject(String dbName) {
        this.dbName = dbName;
    }

    public void open(String dbPath) {
        String url;
        try {
            // Setup for HSQL
            dbType = "HSQL";
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
            c0 = DriverManager.getConnection(url, "SA", "");
            st0 = c0.createStatement();
            st1 = c0.createStatement();
            st2 = c0.createStatement();
        } catch (Exception e) {
            processSQLError(e);
        }
        System.out.println("Opened " + dbType + " database " + dbPath);
    }

    public void close() {
        try {    // commit all changes to the database
            cmdString = "shutdown compact";
            rs1 = st0.executeQuery(cmdString);
            c0.close();
        } catch (Exception e) {
            processSQLError(e);
        }
        System.out.println("Closed " + dbType + " database " + dbName);
    }

    public String processSQLError(Exception e) {
        String result = "*** SQL Error: " + e.getMessage();

        // Remember, this will NOT be seen by the user!
        e.printStackTrace();

        return result;
    }

    public String checkWarning(Statement st, int updateCount) {
        String result;

        result = null;
        try {
            SQLWarning warning = st.getWarnings();
            if (warning != null) {
                result = warning.getMessage();
            }
        } catch (Exception e) {
            result = processSQLError(e);
        }
        if (updateCount != 1) {
            result = "Tuple not inserted correctly.";
        }
        return result;
    }

    /*
        TODO:
        public void orderedInsert(ArrayList<Reservation> results, Reservation r, DateTime t)
        private void setTable(int tID, int month, int day, int startIndex, int endIndex, boolean bool)
        public String insertReservation(Reservation r)
     */


    public Reservation getReservation(int reservationID) {
        Reservation reservation;
        int custID, tableID, numPeople, orderID;
        DateTime startTime, endTime;
        reservations = new ArrayList<>();

        try{
            cmdString = "SELECT * from RESERVATIONS where RID=" + reservationID;
            rs2 = st0.executeQuery(cmdString);

            while (rs2.next()) {
                custID = rs2.getInt("CID");
                tableID = rs2.getInt("TID");
                numPeople = rs2.getInt("NUMPEOPLE");
                orderID = rs2.getInt("OID");
                Calendar cal = new GregorianCalendar();
                cal.setTime(rs2.getTimestamp("STARTTIME"));
                startTime = new DateTime(cal);
                cal.setTime(rs2.getTimestamp("ENDTIME"));
                endTime = new DateTime(cal);
                reservation = new Reservation(custID, tableID, numPeople, startTime, endTime);
                reservation.setRID(reservationID);
                reservation.setOID(orderID);
                reservations.add(reservation);
            }
        } catch (Exception e) {
            processSQLError(e);
        }

        if (reservations.size() == 1) {
            return reservations.get(0);
        }

        return null;
    }

    public String deleteReservation(int reservationID) {
        result = null;
        try {
            cmdString = "DELETE from RESERVATIONS where RID=" + reservationID;
            updateCount = st0.executeUpdate(cmdString);
            result = checkWarning(st0, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public int getIndex(DateTime time) {
        return (time.getHour() - Table.getStartTime()) * 4 + (time.getMinutes() + 7) / 15;
    }

    public String updateReservation(int RID, Reservation curr) {
        String values;
        String where;

        result = null;
        try {
            values = "CID=" + curr.getCID()
                    + ", TID=" + curr.getTID()
                    + ", NUMPEOPLE=" + curr.getNumPeople()
                    + ", STARTTIME='" + curr.getStartTime().toString()
                    + "', ENDTIME='" + curr.getEndTime().toString()
                    + "'";
            where = "where RID=" + RID;
            cmdString = "UPDATE RESERVATION" + " SET " + values + " " + where;
            updateCount = st0.executeUpdate(cmdString);
            result = checkWarning(st0, updateCount);

        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    // return the date time corresponding to an index
    public DateTime getDateTime(DateTime time, int index) {
        DateTime result = null;
        try {
            result = new DateTime(Calendar.getInstance());
            result.setYear(time.getYear());
            result.setMonth(time.getMonth());
            result.setDate(time.getDate());
            result.setHour(Table.getStartTime() + index / 4);
            result.setMinutes(index % 4 * 15);
        } catch (IllegalArgumentException pe) {
            System.out.println(pe);
        }
        return result;
    }

    // ordered insert a suggested reservation into a temp array
    // ordered by how close to the startTime
    public void orderedInsert(ArrayList<Reservation> results, Reservation r, DateTime t) {
        int pos = 0;
        int max = results.size();
        while (pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) < Math.abs(r.getStartTime().getPeriod(t)))
            pos++;
        while (pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) == Math.abs(r.getStartTime().getPeriod(t)) && getTableRandom(results.get(pos).getTID()).getCapacity() < getTableRandom(r.getTID()).getCapacity())
            pos++;
        while (pos < max && Math.abs(results.get(pos).getStartTime().getPeriod(t)) == Math.abs(r.getStartTime().getPeriod(t)) &&
                getTableRandom(results.get(pos).getTID()).getCapacity() == getTableRandom(r.getTID()).getCapacity() && results.get(pos).getTID() < r.getTID())
            pos++;
        results.add(pos, r);
    }

    public String insertReservation(Reservation r) {
        String values;

        result = null;
        try {
            r.setRID();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            values = r.getRID()
                    + ", " + r.getCID()
                    + ", " + r.getTID()
                    + ", " + r.getNumPeople()
                    + ", " + r.getOID()
                    + ", '" + sdf.format(r.getStartTime())
                    + ", '" + sdf.format(r.getEndTime())
                    + "'";
            cmdString = "INSERT into RESERVATIONS " + " Values(" + values + ")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String getReservationSequential(List<Reservation> reservationResult) {
        Reservation reservation;
        int resID, custID, tableID, numPeople, orderID;
        DateTime startTime, endTime;

        result = null;
        try {
            cmdString = "SELECT * from RESERVATIONS";
            rs2 = st0.executeQuery(cmdString);

            while (rs2.next()) {
                resID = rs2.getInt("RID");
                custID = rs2.getInt("CID");
                tableID = rs2.getInt("TID");
                numPeople = rs2.getInt("NUMPEOPLE");
                orderID = rs2.getInt("OID");
                Calendar cal = new GregorianCalendar();
                cal.setTime(rs2.getTimestamp("STARTTIME"));
                startTime = new DateTime(cal);
                cal.setTime(rs2.getTimestamp("ENDTIME"));
                endTime = new DateTime(cal);
                reservation = new Reservation(custID, tableID, numPeople, startTime, endTime);
                reservation.setRID(resID);
                reservation.setOID(orderID);
                reservationResult.add(reservation);
            }
        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String getTableSequential(ArrayList<Table> tableResult) {
        Table table;
        int tableID, capacity;

        result = null;
        try {
            cmdString = "SELECT * from TABLES";
            rs2 = st0.executeQuery(cmdString);

            while (rs2.next()) {
                tableID = rs2.getInt("TID");
                capacity = rs2.getInt("CAPACITY");
                table = new Table(tableID, capacity);
                tableResult.add(table);
            }
        } catch (Exception e) {
            processSQLError(e);
        }

        return result;
    }

    public Table getTableRandom(int tableID) {
        Table table;
        int tID, capacity;
        boolean[][][] available;
        tables = new ArrayList<Table>();

        try {
            cmdString = "SELECT * from TABLES where TID='" + tableID + "'";
            rs2 = st0.executeQuery(cmdString);

            while (rs2.next()) {
                capacity = rs2.getInt("CAPACITY");
                table = new Table(tableID, capacity);
                tables.add(table);
            }
        } catch (Exception e) {
            result = processSQLError(e);
        }

        if (tables.size() == 1) {
            return tables.get(0);
        }

        return null;
    }

    public String addTable(int tableID, int size) {
        String values;
        String available = "";

        result = null;
        try {
            for (int i = 0; i < 365 * 12 * Table.getNumIncrement(); i++)
                available += "1";
            values = "'" + tableID
                    + "', '" + size
                    + "', '" + available
                    + "'";
            cmdString = "INSERT into TABLES " + " Values(" + values + ")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String getCustomerSequential(List<Customer> customerResult) {
        Customer customer;
        int cID;
        String fName, lName, phNum;

        result = null;
        try {

            cmdString = "SELECT * from CUSTOMERS";
            rs2 = st0.executeQuery(cmdString);

            while (rs2.next()) {
                cID = rs2.getInt("CID");
                fName = rs2.getString("FIRSTNAME");
                lName = rs2.getString("LASTNAME");
                phNum = rs2.getString("PHONENUM");
                customer = new Customer(fName, lName, phNum);
                customer.setCID(cID);
                customerResult.add(customer);
            }

        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String insertCustomer(Customer customer) {
        ArrayList<Customer> customers = new ArrayList<>();
        String values;
        int currID;

        getCustomerSequential(customers);
        result = null;
        try {
            currID = customers.get(customers.size()-1).getCID(); //may need a better way to get new ID
            currID++;
            values = "'" + currID
                    + "', '" + customer.getFirstName()
                    + "', '" + customer.getLastName()
                    + "', '" + customer.getPhoneNumber()
                    + "'";
            //System.out.println(values);
            cmdString = "INSERT into CUSTOMERS" + " Values(" + values + ")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String insertCustomer(String firstName, String lastName, String phoneNumber) {
        ArrayList<Customer> customers = new ArrayList<>();
        String values;
        int currID;

        getCustomerSequential(customers);
        result = null;
        try {
            currID = customers.get(customers.size()-1).getCID();
            currID++;
            values = "'" + currID
                    + "', '" + firstName
                    + "', '" + lastName
                    + "', '" + phoneNumber
                    + "'";
            cmdString = "INSERT into CUSTOMERS" + " Values(" + values + ")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String insertItem(Item newItem) {
        String values;

        result = null;
        try {
            values = "'" + newItem.getItemID()
                    + "', '" + newItem.getName()
                    + "', '" + newItem.getType()
                    + "', '" + newItem.getDetail()
                    + "', '" + newItem.getPrice()
                    + "'";
            cmdString = "INSERT into ITEMS" + " Values(" + values + ")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String insertItem(int IID, String name, String type, String detail, double price) {
        String values;

        result = null;
        try {
            values = "'" + IID
                    + "', '" + name
                    + "', '" + type
                    + "', '" + detail
                    + "', '" + price
                    + "'";
            cmdString = "INSERT into MENU" + " Values(" + values + ")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String getMenuSequential(ArrayList<Item> menuResult) {
        Item item;
        int IID;
        String name, type, detail;
        double price;

        tables = new ArrayList<Table>();
        try {

            cmdString = "SELECT * from MENU";
            rs2 = st0.executeQuery(cmdString);

            while (rs2.next()) {
                IID = rs2.getInt("IID");
                name = rs2.getString("NAME");
                type = rs2.getString("TYPE");
                detail = rs2.getString("DETAIL");
                price = rs2.getDouble("PRICE");
                item = new Item(IID, name, type, detail, price);
                menuResult.add(item);
            }

        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }


    public ArrayList<Item> getMenuByType(String type) {
        Item item;
        int IID;
        String name, detail;
        double price;

        menu = new ArrayList<Item>();
        try {

            cmdString = "SELECT * from MENU where type='" + type + "'";
            rs2 = st0.executeQuery(cmdString);

            while (rs2.next()) {
                IID = rs2.getInt("IID");
                name = rs2.getString("NAME");
                detail = rs2.getString("DETAIL");
                price = rs2.getDouble("PRICE");
                item = new Item(IID, name, type, detail, price);
                menu.add(item);
            }

        } catch (Exception e) {
            result = processSQLError(e);
        }

        return menu;
    }

    public ArrayList<String> getMenuTypes() {
        ArrayList<String> types = new ArrayList<String>();

        try {
            cmdString = "SELECT DISTINCT TYPE from MENU";
            rs3 = st0.executeQuery(cmdString);
            while (rs3.next()) {
                types.add(rs3.getString("TYPE"));

            }
        } catch (Exception e) {
            result = processSQLError(e);
        }
        for(int i = 0; i < types.size(); i++){
            System.out.println(types.get(i));
        }
        return types;
    }

    public boolean[] getAvailable(int TID, DateTime time) {
        getReservationSequential(reservations);
        boolean[] available = new boolean[Table.getNumIncrement()];
        for(int i = 0; i < available.length; i++)
            available[i] = true;
        for(int i = 0; i < reservations.size(); i++) {
            if(reservations.get(i).getTID() == TID && reservations.get(i).getStartTime().getYear() == time.getYear() && reservations.get(i).getStartTime().getMonth() == time.getMonth() && reservations.get(i).getStartTime().getDate() == time.getDate()) {
                int startIndex = getIndex(reservations.get(i).getStartTime());
                int endIndex = getIndex(reservations.get(i).getEndTime());
                for(int j = startIndex; j < endIndex; j++)
                    available[j] = false;
            }
        }
        return available;
    }

}