package comp3350.rrsys.objects;

import android.os.Parcel;
import android.os.Parcelable;

/*
    reservation object accepts
        - reservationID (reservation ID)
        - customerID (customer ID)
        - tableID (table ID)
        - numPeople
        - startTime & endTime
 */
public class Reservation implements Parcelable
{
    private int reservationID;
    private int customerID;
    private int tableID;
    private int numPeople;
    private DateTime startTime;
    private DateTime endTime;
    private static int counter = 1;

    // constructor for insert a reservation or search suggested reservations
    public Reservation(int customerID, int tableID, int numPeople, DateTime startTime, DateTime endTime)
    {
        this.customerID = customerID;
        this.tableID = tableID;
        this.numPeople = numPeople;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Reservation(Parcel in)
    {

    }

    // setter
    // set the reservation ID only when insert a reservation
    public void setRID() { this.reservationID = counter++; }
    public void setRID(int reservationID) { this.reservationID = reservationID; }
    public void setTID(int tableID){
        this.tableID = tableID;
    }
    public void setNumPeople(int num){
        numPeople = num;
    }

    public void setTime(DateTime startTime, DateTime endTime)
    {
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

    public String confirmation()
    {
        String s ="";
        s += "Reservation ID: " + reservationID + "\nNumber of People: " + numPeople +
                "\nTable ID: " + tableID + "\nTime: " + startTime.toString() +" - " + endTime.toString();
        return s;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

    }

    public static final Parcelable.Creator<Reservation> CREATOR = new Parcelable.Creator<Reservation>()
    {
        @Override
        public Reservation createFromParcel(Parcel source)
        {
            return new Reservation(source);
        }

        @Override
        public Reservation[] newArray(int size)
        {
            return new Reservation[size];
        }
    };
}