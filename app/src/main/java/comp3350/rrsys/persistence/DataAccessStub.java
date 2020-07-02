package comp3350.rrsys.persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.rrsys.application.Main;
import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.Table;
import comp3350.rrsys.objects.reservation;

public class DataAccessStub {

    private String dbName;
    private String dbType = "stub";

    private ArrayList<Customer> customers;
    private ArrayList<Table> tables;
    private ArrayList<reservation> reservations;

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
        reservation reservation;

        customers = new ArrayList<Customer>();

        tables = new ArrayList<Table>();

        reservations = new ArrayList<comp3350.rrsys.objects.reservation>();
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

    // return the suggested reservations in priority order
    // currentReservation RID(numPeople, month, date, startTime, period)
    public ArrayList<reservation> searchReservations(reservation currentReservation) {
        ArrayList<reservation> results = new ArrayList<reservation>();
        int numPeople = currentReservation.getNumPeople();
        int month = currentReservation.getMonth();
        int date = currentReservation.getDate();
        String startTime = currentReservation.getTime();
        String[] tokens = startTime.split(":");
        int index = (Integer.parseInt(tokens[0])-Table.getTime())*4 + Integer.parseInt(tokens[1])/15; // index of start time
        int totalIncrement = currentReservation.getPeriod()/15; // total num of increments
        int maxIndex = Table.getNumIncrement(); // max index

        Table table;
        for(int t = 0; t < tables.size(); t++) {
            table = tables.get(t);
            if(table.getCapacity() >= numPeople) {
                // within +- 1 hour of the start time
                int i = Math.max(index-4, 0);
                while(i <= index+4 && i < maxIndex) {
                    while (i <= index+4 && i < maxIndex && !table.getAvailable(month, date, i)) {
                        i++;
                    }
                    if (i <= index + 4 && i < maxIndex) {
                        int numIncrement = 1;
                        for (int time = i + 1; time < i + totalIncrement; time++) {
                            if (time < maxIndex && table.getAvailable(month, date, time))
                                numIncrement++;
                            else
                                break;
                        }
                        if (numIncrement == totalIncrement) {
                            int min = Integer.parseInt(tokens[1]) + currentReservation.getPeriod();
                            int hour = Integer.parseInt(tokens[0]) + min / 60;
                            String endTime = hour + ":" + (min % 60);
                            orderedInsert(results, new reservation(numPeople, month, date, startTime, endTime, table.getTID()), Math.abs(i-index));
                        }
                    }
                    i++;
                }
            }
        }
        return results;
    }

    private void orderedInsert(ArrayList<reservation> results, reservation reservation, int index) {
        int pos = 0;
        int max = results.size();
        while(pos < max && Math.abs(incrementIndex(results.get(pos))-index) < Math.abs(incrementIndex(reservation)-index)){
            pos++;
        }
        results.add(pos, reservation);
    }

    // return the index of the start time of a reservation
    private int incrementIndex(reservation reservation){
        String[] startTime = reservation.getTime().split(":");
        return (Integer.parseInt(startTime[0])-Table.getTime())*4 + Integer.parseInt(startTime[1])/15;
    }

    // create a reservation RID(numPeople, month, date, startTime, endTime, tID)
    public String insertReservation(reservation currentReservation)
    {
        reservations.add(currentReservation);
        int tID = currentReservation.getTID();
        int startIndex = incrementIndex(currentReservation);
        String[] endTime = currentReservation.getEndTime().split(":");
        int endIndex = (Integer.parseInt(endTime[0])-Table.getTime())*4 + Integer.parseInt(endTime[1])/15;

        Table table;
        for(int i = 0; i < tables.size(); i++) {
            table = tables.get(i);
            if(table.getTID() == tID) {
                for(int time = startIndex; time < endIndex; time++) {
                    table.setAvailable(currentReservation.getMonth(), currentReservation.getDate(), time);
                }
            }
        }
        return null;
    }

    public String getReservationSequential(List<reservation> reservationResult)
    {
        return null;
    }

    public String updateReservation(reservation currentReservation)
    {
        return null;
    }

    public String deleteReservation(reservation currentReservation)
    {
        return null;
    }

    /*
    public ArrayList<RID> getReservation(Customer customer) {
        ArrayList<RID> results = new ArrayList<RID>();
        RID current;
        for(int i = 0; i < reservations.size(); i++) {
            current = reservations.get(i);
            if(current.getCID() == (customer.getCID()))
                results.add(current);
        }
    }*/
}
