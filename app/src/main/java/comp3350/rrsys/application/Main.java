package comp3350.rrsys.application;

public class Main
{
    public static final String dbName = "RR";
    private static String dbPathName = "database/RR";

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

    public static void setDBPathName(String pathName)
    {
        System.out.println("Setting DB path to: " + pathName);
        dbPathName = pathName;
    }

    public static String getDBPathName()
    {
        if (dbPathName == null)
            return dbName;
        else
            return dbPathName;
    }
}
