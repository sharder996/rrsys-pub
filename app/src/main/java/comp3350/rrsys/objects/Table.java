package comp3350.rrsys.objects;

/* Class: Table
 *
 * Object for a table in the restaurant that includes all table information
 */

public class Table
{
    private int tID;
    private int capacity;   // # of people
    public static final int START_TIME = 7; // start hour
    public static final int END_TIME = 23;  // end hour
    public static final int MIN_TIME_INTERVAL = 15; // smallest block that time can be broken down to
    public static final int INTERVALS_PER_DAY = (END_TIME-START_TIME) * MIN_TIME_INTERVAL; // num of 15 min increments each day

    public Table(int tID, int capacity) throws IllegalArgumentException
    {
        if(tID < 0)
        {
            throw new IllegalArgumentException("tID must be positive integer.");
        }
        else if(capacity <= 0)
        {
            throw new IllegalArgumentException("Table capacity must be positive integer.");
        }
        else
        {
            this.tID = tID;
            this.capacity = capacity;
        }
    }

    public int getTID() { return tID; }
    public int getCapacity() { return capacity; }

    @Override
    public String toString()
    {
        return "Table " + tID + " Capacity " + capacity;
    }
}
