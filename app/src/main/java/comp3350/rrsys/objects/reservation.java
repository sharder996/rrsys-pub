package comp3350.rrsys.objects;


/*
    reservation object accepts
        - rID (reservation ID)
        - cID (customer ID)
        - tID (table ID)
        - month ()
        -

 */
public class reservation {
    private int reservationID;
    private int numPeople;
    private int customerID;
    private int month;
    private int date;
    DateTime startTime; // eg. in the format 7:30
    DateTime endTime;
    //private int period; // how long in min (15 min increments)
    private int tableID;
    private static int counter = 1;
    //private int cID; // customer ID

    public reservation(int reservationID, int customerID, int tableID, int numPeople, DateTime startTime, DateTime endTime){
            this.reservationID = counter++;
            this.customerID = customerID;
            this.tableID = tableID;
            this.numPeople = numPeople;
            this.startTime = startTime;
            this.endTime = endTime;
    }

    //update reservation
    public void setNumPeople(int num){
        numPeople = num;
    }

    public void setTime(DateTime startTime, DateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setTID(int tID){
        this.tableID = tID;
    }

    public void setReservationID(int reservationID) { this.reservationID = reservationID; }

    public int getNumPeople() { return numPeople; }

    public DateTime getStartTimeTime(){ return startTime; }

    public DateTime getEndTime() { return endTime; }

    public int getRID() { return reservationID; }

    public int getTID() { return tableID; }


    public boolean equal(int reservationID){
        boolean result = false;
        if(this.reservationID == reservationID){
            result = true;
        }
        return result;
    }

    public String confirmation(){
        String s ="";
        s += "Reservation ID: " + reservationID + "\nNumer of People: " + numPeople
                + "\nTable ID: " + tableID + "Time: " + startTime.print() +" - " + endTime.print();

        return s;
    }
}
