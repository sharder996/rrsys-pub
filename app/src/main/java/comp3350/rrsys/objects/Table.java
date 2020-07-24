package comp3350.rrsys.objects;

public class Table
{
    private int tID;
    private int capacity; // # of people
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

    @Override
    public String toString()
    {
        return "Table " + tID + " Capacity " + capacity;
    }
}
