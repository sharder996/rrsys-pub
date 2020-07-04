package comp3350.rrsys.persistence;

import java.lang.reflect.Array;
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
    //Note: see database functions in database object

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
                            orderedInsert(results, new Reservation(table.getTID(), numPeople, start, end), index);
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
    private DateTime getDateTime(DateTime time, int index) {
        // TODO: need some changes here...
        // DateTime result = new DateTime(???);
        DateTime result = time;
        result.setYear(time.getYear());
        result.setMonth(time.getMonth());
        result.setDate(time.getDate());
        result.setHour(Table.getTime() + index/4);
        result.setMintues(index%4);
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
    public String insertReservation(int customerID, Reservation r) {
        int tID = r.getTID();
        DateTime startTime = r.getStartTime();
        DateTime endTime = r.getEndTime();
        Reservation reservation = new Reservation(customerID, tID, r.getNumPeople(), startTime, endTime);
        reservations.add(reservation);
        setTable(tID, startTime.getMonth(), startTime.getDate(), getIndex(startTime), getIndex(endTime), false);
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
                reservations.remove(i);
                DateTime start = r.getStartTime();
                DateTime end = r.getEndTime();
                setTable(r.getTID(), start.getMonth(), start.getDate(), getIndex(start), getIndex(end), true);
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
                reservations.remove(i);
                setTable(reservations.get(i).getTID(), start.getMonth(), start.getDate(), getIndex(start), getIndex(end), true);
                break;
            }
        }
        return null;
    }

    // update a reservation
    public String updateReservation(Reservation prev) {
        deleteReservation(prev);
        // TODO: update a reservation
        return null;
    }

    public String getReservationSequential(List<Reservation> reservationResult) {
        return null;
    }

}
