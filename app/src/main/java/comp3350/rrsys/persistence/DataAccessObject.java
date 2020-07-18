package comp3350.rrsys.persistence;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;

import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;

public class DataAccessObject implements DataAccess {

    private Statement st0, st1, st2;
    private Connection c0;
    private ResultSet rs0, rs1, rs2;

    private String dbName;
    private String dbType;

    private ArrayList<Customer> customers;
    private ArrayList<Table> tables;
    private ArrayList<Reservation> reservations;

    private String cmdString;

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
}
