package comp3350.rrsys.objects;

public class SampleObject {
    private int Tid; //this can be string.
    private int capacity; // # of people

    public SampleObject(int Tid, int capacity){
        this.Tid = Tid;
        this.capacity = capacity;
    }

    public int getMax(){
        return capacity;
    }

    public int getTid(){
        return Tid;
    }
}
