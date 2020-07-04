package comp3350.rrsys.objects;

public class Table {

    private int tID;
    private int capacity; // # of people
    boolean[][][] available; // whether the table is available for each period/increment
    private static int startTime = 8; // start hour
    private static int endTime = 22; // end hour
    private static int numIncrement = (endTime-startTime)*4;

    public Table(int tID, int capacity){
        this.tID = tID;
        this.capacity = capacity;
        available = new boolean[12][31][numIncrement];
        for(int month = 0; month < available.length; month++) {
            for (int day = 0; day < available[0].length; day++) {
                for (int time = 0; time < available[0][0].length; time++)
                    available[month][day][time] = true;
            }
        }
    }

    public int getTID() { return tID; }
    public int getCapacity() { return capacity; }

    public static int getTime() { return startTime; }
    public static int getNumIncrement() { return numIncrement; }

    public boolean getAvailable(int month, int day, int time) { return available[month][day][time]; }
    public void setAvailable(int month, int day, int time, boolean bool) { available[month][day][time] = bool; }
}
