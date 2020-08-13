package comp3350.rrsys.objects;

/* Class: Reservation
 *
 * Object to hold a reservation and all its attached information
 */

public class Reservation {
    private int reservationID;
    private int customerID;
    private int tableID;
    private int numPeople;
    private DateTime startTime;
    private DateTime endTime;
    private static int counter = 1;
    public static final int MAX_PEOPLE = 12;
    public static final int MIN_PEOPLE = 1;
    public static final int MAX_TIME = 180;
    public static final int MIN_TIME = 30;

    // constructor for inserting a reservation
    public Reservation(int customerID, int tableID, int numPeople, DateTime startTime, DateTime endTime)
    {
        this.customerID = customerID;
        this.tableID = tableID;
        this.numPeople = numPeople;
        this.startTime = startTime;
        this.endTime = endTime;
        reservationID = -1;
    }

    // constructor for suggested reservations
    public Reservation(int tableID, int numPeople, DateTime startTime, DateTime endTime)
    {
        this.customerID = -1;
        this.tableID = tableID;
        this.numPeople = numPeople;
        this.startTime = startTime;
        this.endTime = endTime;
        reservationID = -1;
    }

    // setter
    // set the reservation ID only when insert a reservation
    public void setRID() { this.reservationID = counter++; }

    public void setRID(int reservationID) {
        this.reservationID = reservationID;
    }

    public void setTID(int tableID) {
        this.tableID = tableID;
    }

    public void setNumPeople(int num) {
        numPeople = num;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setTime(DateTime startTime, DateTime endTime)
    {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // getter
    public int getRID() {
        return reservationID;
    }

    public int getCID() {
        return customerID;
    }

    public int getTID() {
        return tableID;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public boolean equals(int reservationID) {
        return this.reservationID == reservationID;
    }
}