package comp3350.rrsys.objects;

/*
    reservation object accepts
        - reservationID (reservation ID)
        - customerID (customer ID)
        - tableID (table ID)
        - numPeople
        - startTime & endTime
 */
public class Reservation {
    private int reservationID;
    private int customerID;
    private int tableID;
    private int numPeople;
    private DateTime startTime;
    private DateTime endTime;
    private static int counter = 1;

    // constructor when insert a reservation
    public Reservation(int customerID, int tableID, int numPeople, DateTime startTime, DateTime endTime){
        this.reservationID = counter++;
        this.customerID = customerID;
        this.tableID = tableID;
        this.numPeople = numPeople;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // constructor when search for suggested reservations
    public Reservation(int tableID, int numPeople, DateTime startTime, DateTime endTime){
        this.tableID = tableID;
        this.numPeople = numPeople;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // setter
    public void setRID(int reservationID) { this.reservationID = reservationID; }
    public void setTID(int tableID){
        this.tableID = tableID;
    }
    public void setNumPeople(int num){
        numPeople = num;
    }

    public void setTime(DateTime startTime, DateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // getter
    public int getRID() { return reservationID; }
    public int getCID() { return customerID; }
    public int getTID() { return tableID; }
    public int getNumPeople() { return numPeople; }
    public DateTime getStartTime(){ return startTime; }
    public DateTime getEndTime() { return endTime; }

    public boolean equals(int reservationID){
        return this.reservationID == reservationID;
    }

    public String confirmation(){
        String s ="";
        s += "Reservation ID: " + reservationID + "\nNumer of People: " + numPeople +
                "\nTable ID: " + tableID + "\nTime: " + startTime.print() +" - " + endTime.print();
        return s;
    }
}