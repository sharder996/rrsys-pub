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
    String getReservationSequential(ArrayList<Reservation> reservationResult);

    Reservation getReservation(int rID);

    String insertReservation(Reservation r);

    String updateReservation(int rID, Reservation curr);

    String deleteReservation(int rID);

    int getNextReservationID();

    // Table Functions:
    String getTableSequential(ArrayList<Table> tableResult);

    Table getTable(int tableID);

    // Customer Functions:
    String getCustomerSequential(List<Customer> customerResult);

    String insertCustomer(Customer customer);

    String deleteCustomer(Customer customer); // only for testing purpose

    // Menu Functions:
    ArrayList<String> getMenuTypes();

    ArrayList<Item> getMenu();

    ArrayList<Item> getMenuByType(String type);

    // Order Functions:
    Order getOrder(int resID);

    String insertItemIntoOrder(int resID, Item item);

    String removeOrder(int resID);

    double getPrice(int resID);
}
