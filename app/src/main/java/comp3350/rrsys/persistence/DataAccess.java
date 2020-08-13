package comp3350.rrsys.persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.rrsys.objects.Customer;
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

    String getReservationSequential(ArrayList<Reservation> reservationResult);

    int getNextReservationID();

    // Table Functions:
    String getTableSequential(ArrayList<Table> tableResult);

    Table getTableRandom(int tableID);

    // Customer Functions:
    String getCustomerSequential(List<Customer> customerResult);

    String insertCustomer(Customer customer);

    // Menu Functions:
    ArrayList<String> getMenuTypes();

    ArrayList<Item> getMenu();

    ArrayList<Item> getMenuByType(String type);

    //Order Functions:
    Order getOrder(int resID);

    String removeOrder(int resID);

    String insertItemIntoOrder(int resID, Item item);

    double getPrice(int resID);
}
