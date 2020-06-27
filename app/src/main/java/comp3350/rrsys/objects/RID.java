package comp3350.rrsys.objects;

import java.util.UUID;

public class RID {
    private String RID;
    private int NumPeople;
    private int month;
    private int date;
    private int time;
    private int TID; // table id

    //private boolean availability; //start and end time.

    public RID(int NumPeople, int month, int date, int time, int TID){
        this.NumPeople = NumPeople;
        this.month = month;
        this.date = date;
        this.time = time;
        this.TID = TID;
        this.RID = generateRID();
    }
    //update reservation
    public void setNumpeople(int num){
        NumPeople = num;
    }
    public void setData(int month, int date){
        this.month = month;
        this.date = date;
    }
    public void setTID(int TID){
        this.TID = TID;
    }

    public int getMonth(){
        return month;
    }

    public int getDate(){
        return date;
    }

    public int getTime(){
        return time;
    }

    public String getRID() { return RID; }

    private String generateRID(){
        String uuid = UUID.randomUUID().toString();
        RID = uuid.substring(0,8);

        return RID;
    }


    public boolean equal(String reservation){
        boolean result = false;
        if(RID.equals(reservation)){
            result = true;
        }

        return result;
    }

    public String confirmation(){
        String s ="";
        s += "Reservation ID: " + RID + "\nNumer of People: " + NumPeople
                + "\nData: " + month + " " + date + "\nTime: " +  time
                + "\nTable ID: " + TID;

        return s;
    }
}
