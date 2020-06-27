package comp3350.rrsys.objects;

public class SampleObject {
    private int TID; //this can be string.
    private int capacity; // # of people

    public SampleObject(int TID, int capacity){
        this.TID = TID;
        this.capacity = capacity;
    }

    public int getMax(){
        return capacity;
    }

    public int getTid(){
        return TID;
    }
}
