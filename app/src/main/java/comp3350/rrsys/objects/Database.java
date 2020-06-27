package comp3350.rrsys.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * Class: Database
 *
 * Currently is stub format. Holds records of information on app user data. Tables are Customer,
 * Reservations (to do) and Menu (to do). Sorts on insert on ID. 2 ways of inserting into object into
 * each list, by entering all fields or by whole object.
 *
 * Throws: IllegalArgumentException:
 * Object insert into list will throw exception caught from object creation, normally due to invalid
 * character on field value enforcement.
 */
public class Database {
    private static ArrayList<Customer> customerList = new ArrayList<>();

    public void addCustomer(String fName, String lName, String pNum) throws IllegalArgumentException {
        try {
            Customer newCustomer = new Customer(fName, lName, pNum);
            customerList.add(newCustomer);
            Collections.sort(customerList, new SortById());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void addCustomer(Customer newCustomer) throws IllegalArgumentException {
        try {
            if(Collections.binarySearch(customerList, newCustomer, new SortById()) < 0) {
                customerList.add(newCustomer);
                Collections.sort(customerList, new SortById());
            } else {
                throw new IllegalArgumentException("Customer already exists.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}

/*  class sortById
 *  Implements Comparator to sort ArrayList of customers by cID
 *  Only method will compare the 2 IDs, returns negative if ID a is less than ID b
 *  returns 0 if IDs are equal
 *  returns positive if ID a is greater than ID b
 */
class SortById implements Comparator<Customer>{
    public int compare(Customer a, Customer b){
        return a.getcID() - b.getcID();
    }
}
