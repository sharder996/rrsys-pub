package comp3350.rrsys.persistence;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Menu;
import comp3350.rrsys.objects.Order;

public class DataAccessObject implements DataAccess {

    private Statement st0, st1, st2;
    private Connection c0;
    private ResultSet rs0, rs1, rs2;

    private String dbName;
    private String dbType;

    private ArrayList<Customer> customers;
    private ArrayList<Table> tables;
    private ArrayList<Reservation> reservations;
    private Menu menu;
    private ArrayList<Order> orders;

    private String cmdString;
    private int updateCount;
    private String result;
    private static String EOF = "  ";

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
            c0 = DriverManager.getConnection(url, "SA", "");
            st0 = c0.createStatement();
            st1 = c0.createStatement();
            st2= c0.createStatement();
        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        System.out.println("Opened " +dbType +" database " +dbPath);
    }

    public void close()
    {
        try
        {	// commit all changes to the database
            cmdString = "shutdown compact";
            rs1 = st0.executeQuery(cmdString);
            c0.close();
        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        System.out.println("Closed " +dbType +" database " +dbName);
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
        String result;

        result = null;
        try
        {
            SQLWarning warning = st.getWarnings();
            if (warning != null)
            {
                result = warning.getMessage();
            }
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }
        if (updateCount != 1)
        {
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



    public Reservation getReservation(int reservationID){
        Reservation reservation;
        int resID, custID, tableID, numPeople, orderID;
        DateTime startTime, endTime;
        reservations = new ArrayList<Reservation>();

        try {

            cmdString = "SELECT * from RESERVATIONS where RID=" + reservationID;
            rs2 = st0.executeQuery(cmdString);

            while(rs2.next()) {
                resID = rs2.getInt("RID");
                custID = rs2.getInt("CID");
                tableID = rs2.getInt("TID");
                numPeople = rs2.getInt("NUMPEOPLE");
                orderID = rs2.getInt("OID");
                Calendar cal = new GregorianCalendar();
                cal.setTime(rs2.getDate("STARTTIME"));
                startTime = new DateTime(cal);
                cal.setTime(rs2.getDate("ENDTIME"));
                endTime = new DateTime(cal);
                reservation = new Reservation(custID, tableID, numPeople, startTime, endTime);
                reservation.setRID(resID);
                reservation.setOrderID(orderID);
                reservations.add(reservation);
            }

        } catch (Exception e) {
            processSQLError(e);
        }

        if(reservations.size() == 1) {
            return reservations.get(0);
        }

        return null;
    }

    public String deleteReservation(int rID) {
        result = null;

        try {
            cmdString = "DELETE from RESERVATIONS where RID=" + rID;
            updateCount = st0.executeUpdate(cmdString);
            result = checkWarning(st0, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String updateReservation(int rID, Reservation curr) {
        String values;
        String where;

        result = null;
        try {
            values = "CID='" + curr.getCID()
                    +"', TID='" + curr.getTID()
                    +"', NUMPEOPLE='" + curr.getNumPeople()
                    +"', STARTTIME='" + curr.getStartTime().toString()
                    +"', ENDTIME='" + curr.getEndTime().toString()
                    +"'";
            where = "where RID=" + rID;
            cmdString = "UPDATE RESERVATION " +"SET " +values +" " +where;
            //System.out.println(cmdString);
            updateCount = st0.executeUpdate(cmdString);
            result = checkWarning(st0, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String getReservationSequential(List<Reservation> reservationResult){
        Reservation reservation;
        int resID, custID, tableID, numPeople;
        DateTime startTime, endTime;

        result = null;
        try {

            cmdString = "SELECT * from RESERVATION";
            rs2 = st0.executeQuery(cmdString);

            while(rs2.next()) {
                resID = rs2.getInt("RID");
                custID = rs2.getInt("CID");
                tableID = rs2.getInt("TID");
                numPeople = rs2.getInt("NUMPEOPLE");
                Calendar cal = new GregorianCalendar();
                cal.setTime(rs2.getDate("STARTTIME"));
                startTime = new DateTime(cal);
                cal.setTime(rs2.getDate("ENDTIME"));
                endTime = new DateTime(cal);
                reservation = new Reservation(custID, tableID, numPeople, startTime, endTime);
                reservation.setRID(resID);
                reservationResult.add(reservation);
            }

        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public ArrayList<Table> getTableSequential(){
        Table table;
        int tableID, capacity;
        ArrayList<Table> tableResult = new ArrayList<Table>();

        try {

            cmdString = "SELECT * from TABLES";
            rs2 = st0.executeQuery(cmdString);

            while(rs2.next()) {
                tableID = rs2.getInt("TID");
                capacity = rs2.getInt("CAPACITY");
                table = new Table(tableID, capacity);
                tableResult.add(table);
            }

        } catch (Exception e) {
            processSQLError(e);
        }

        return tableResult;
    }

    public Table getTableRandom(int tableID) {
        Table table;
        int tID, capacity;
        tables = new ArrayList<Table>();

        try {

            cmdString = "SELECT * from TABLES where RID=" + tableID;
            rs2 = st0.executeQuery(cmdString);

            while(rs2.next()) {
                tID = rs2.getInt("TID");
                capacity = rs2.getInt("CAPACITY");
                table = new Table(tID, capacity);
                tables.add(table);
            }

        } catch (Exception e) {
            processSQLError(e);
        }

        if(tables.size() == 1) {
            return tables.get(0);
        }

        return null;
    }

    public String addTable(int tableID, int size){
        String values;

        result = null;
        try{
            values =  "'" + tableID
                    +"', '" + size
                    +"'";
            cmdString = "INSERT into TABLES " +" Values(" +values +")";
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

            cmdString = "SELECT * from CUSTOMER";
            rs2 = st0.executeQuery(cmdString);

            while(rs2.next()) {
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
        String values;

        result = null;
        try{
            values =  "'" + customer.getFirstName()
                    +"', '" + customer.getLastName()
                    +"', '" + customer.getPhoneNumber()
                    +"'";
            cmdString = "INSERT into CUSTOMER" +" Values(" +values +")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

    public String insertCustomer(String firstName, String lastName, String phoneNumber) {
        String values;

        result = null;
        try{
            values =  "'" + firstName
                    +"', '" + lastName
                    +"', '" + phoneNumber
                    +"'";
            cmdString = "INSERT into CUSTOMER" +" Values(" +values +")";
            updateCount = st1.executeUpdate(cmdString);
            result = checkWarning(st1, updateCount);
        } catch (Exception e) {
            result = processSQLError(e);
        }

        return result;
    }

}
