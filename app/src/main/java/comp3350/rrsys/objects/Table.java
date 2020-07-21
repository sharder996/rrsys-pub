package comp3350.rrsys.objects;

public class Table
{
    private int tID;
    private int capacity; // # of people
    boolean[][][] available; // whether the table is available for each period/increment
    private static int startTime = 7; // start hour
    private static int endTime = 23; // end hour
    private static int numIncrement = (endTime-startTime)*4; // num of 15 min increments each day

    public Table(int tID, int capacity) throws IllegalArgumentException
    {
        if(tID < 0)
        {
            throw new IllegalArgumentException("tID must be positive integer.");
        }
        else if (capacity <= 0)
        {
            throw new IllegalArgumentException("Table capacity must be positive integer.");
        }
        else
        {
            this.tID = tID;
            this.capacity = capacity;
            available = new boolean[12][31][numIncrement];
            for (int month = 1; month <= available.length; month++) {
                for (int day = 1; day <= available[0].length; day++) {
                    for (int time = 0; time < available[0][0].length; time++)
                        available[month - 1][day - 1][time] = true;
                }
            }
        }
    }

    public Table(int tID, int capacity, boolean[][][] available) {
        this.tID = tID;
        this.capacity = capacity;
        this.available = new boolean[12][31][numIncrement];
        for (int month = 1; month <= available.length; month++) {
            for (int day = 1; day <= available[0].length; day++) {
                for (int time = 0; time < available[0][0].length; time++)
                    this.available[month - 1][day - 1][time] = available[month - 1][day - 1][time];
            }
        }
    }

    public boolean equals(int tID)
    {
        return this.tID == tID;
    }

    public int getTID() { return tID; }
    public int getCapacity() { return capacity; }

    public static int getStartTime() { return startTime; }
    public static int getEndTime() { return endTime; }
    public static int getNumIncrement() { return numIncrement; }

    public boolean getAvailable(int month, int day, int time) { return available[month-1][day-1][time]; }
    public boolean[][][] getAvailable() { return available; }
    public void setAvailable(int month, int day, int time, boolean bool) { available[month-1][day-1][time] = bool; }
    public void setAvailable(boolean[][][] available) {
        this.available = available;
    }

    @Override
    public String toString()
    {
        return "Table " + tID + " Capacity " + capacity;
    }
}
