package comp3350.rrsys.application;

public class Main
{
    public static final String dbName = "RR";

    public static void main(String[] args)
    {
        startUp();

        shutDown();

        System.out.println("Successfully reached end of execution");
    }

    public static void startUp()
    {
        Services.createDataAccess(dbName);
    }

    public static void shutDown()
    {
        Services.closeDataAccess();
    }
}
