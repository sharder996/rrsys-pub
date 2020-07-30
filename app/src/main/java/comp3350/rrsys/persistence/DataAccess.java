package comp3350.rrsys.persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Order;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;

public interface DataAccess
{
    void open(String string);

    void close();

    // Reservation Functions:
    Reservation getReservation(int rID);

    String deleteReservation(int rID);

    String updateReservation(int rID, Reservation curr);

    String insertReservation(Reservation r);

    String getReservationSequential(List<Reservation> reservationResult);

    void orderedInsert(ArrayList<Reservation> results, Reservation r, DateTime t);

    int getNextReservationID();

    // Table Functions:
    String getTableSequential(ArrayList<Table> tableResult);

    Table getTableRandom(int tableID);

    boolean[] getAvailable(int TID, DateTime time);

    String addTable(int tableID, int size);

    // Customer Functions:
    String getCustomerSequential(List<Customer> customerResult);

    String insertCustomer(Customer customer);

    String insertCustomer(String firstName, String lastName, String phoneNumber);

    // Menu Functions:
    ArrayList<String> getMenuTypes();

    ArrayList<Item> getMenu();

    ArrayList<Item> getMenuByType(String type);

    // DateTime Functions:
    DateTime getDateTime(DateTime time, int index);

    //Order Functions:
    ArrayList<Item> getOrder(int reservationID);

    String insertOrder(Order order);

    double getPrice(int ReservationID);

    int getSize(int ReservationID);
}
