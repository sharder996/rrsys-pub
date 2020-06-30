package comp3350.rrsys.objects;

import java.util.UUID;

public class reservation {
    private String rID;
    private int numPeople;
    private int month;
    private int date;
    private String startTime; // eg. in the format 7:30
    private String endTime;
    private int period; // how long in min (15 min increments)
    private int tID;
    //private int cID; // customer ID

    public reservation(int numPeople, int month, int date, String startTime, String endTime, int tID){
        this.numPeople = numPeople;
        this.month = month;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        //this.cID = cID;
        this.tID = tID;
        generateRID();
    }

    public reservation(int numPeople, int month, int date, String startTime, int period){
        this.numPeople = numPeople;
        this.month = month;
        this.date = date;
        this.startTime = startTime;
        this.period = period;
    }

    //update reservation
    public void setNumPeople(int num){
        numPeople = num;
    }
    public void setData(int month, int date){
        this.month = month;
        this.date = date;
    }
    public void setTime(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setTID(int tID){
        this.tID = tID;
    }

    public int getNumPeople() { return numPeople; }

    public int getMonth(){
        return month;
    }

    public int getDate(){
        return date;
    }

    public String getTime(){
        return startTime;
    }

    public String getEndTime() { return endTime; }

    public int getPeriod() { return period; }

    public String getRID() { return rID; }

    public int getTID() { return tID; }

    //public int getCID() { return cID; }

    private void generateRID(){
        String uuid = UUID.randomUUID().toString();
        rID = uuid.substring(0,8);
    }

    public boolean equal(String reservation){
        boolean result = false;
        if(rID.equals(reservation)){
            result = true;
        }
        return result;
    }

    public String confirmation(){
        String s ="";
        s += "Reservation ID: " + rID + "\nNumer of People: " + numPeople
                + "\nData: " + month + " " + date + "\nTime: " +  startTime + " - " + endTime
                + "\nTable ID: " + tID;

        return s;
    }
}
