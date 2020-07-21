package comp3350.rrsys.persistence;


import java.util.ArrayList;
import java.util.List;

import comp3350.rrsys.objects.Customer;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.objects.Reservation;
import comp3350.rrsys.objects.Table;

public interface DataAccess {

    void open(String string);

    void close();

    //dealing with datetime later -- sql has its own DateTime data type
    //void orderedInsert(ArrayList<Reservation> results, Reservation r, DateTime t);
    //private void setTable(int tID, int month, int day, int startIndex, int endIndex, boolean bool)
    //String insertReservation(Reservation r);

    Reservation getReservation(int reservationID);

    String deleteReservation(int rID);

    String updateReservation(int rID, Reservation curr);

    String getReservationSequential(List<Reservation> reservationResult);

    String insertReservation(Reservation r);

    ArrayList<Table> getTableSequential();

    Table getTableRandom(int tableID);

    String getCustomerSequential(List<Customer> customerResult);

    String insertCustomer(Customer customer);

    String insertCustomer(String firstName, String lastName, String phoneNumber);

    String addTable(int tableID, int size);

    String insertItem(Item newItem);

    String insertItem(int IID, String name, String type, String detail, double price);

    String getMenuSequential(ArrayList<Item> menuResult);

    ArrayList<Item> getMenuByType(String type);

    void orderedInsert(ArrayList<Reservation> results, Reservation r, DateTime t);
}
