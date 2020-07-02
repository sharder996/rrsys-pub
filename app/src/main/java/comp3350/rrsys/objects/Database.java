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

    private static ArrayList<Customer> customerList;
    private static ArrayList<reservation> reservationList;

    public Database(){
        customerList = new ArrayList<>();
        reservationList = new ArrayList<>();
    }

    public void addCustomer(String fName, String lName, String pNum) throws IllegalArgumentException {
        try {
            customerList.add(new Customer(fName, lName, pNum));
            Collections.sort(customerList, new CustomerCompareById());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void addCustomer(Customer newCustomer) throws IllegalArgumentException {
        try {
            Collections.sort(customerList, new CustomerCompareById());
            if(Collections.binarySearch(customerList, newCustomer, new CustomerCompareById()) < 0) {
                customerList.add(newCustomer);
                Collections.sort(customerList, new CustomerCompareById());
            } else {
                throw new IllegalArgumentException("Customer already exists.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void addReservation(int numPeople, int month, int date, int time, int tableID){
        try{
            reservationList.add(new reservation(numPeople, month, date, time, tableID));
            Collections.sort(reservationList, new RIDCompareById());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void addReservation(reservation newReservation) throws IllegalArgumentException {
        try {
            Collections.sort(reservationList, new RIDCompareById());
            if(Collections.binarySearch(reservationList, newReservation, new RIDCompareById()) < 0) {
                reservationList.add(newReservation);
                Collections.sort(reservationList, new RIDCompareById());
            } else {
                throw new IllegalArgumentException("Reservation already exists.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}

/*  class CustomerSortById
 *  Implements Comparator to sort ArrayList of customers by cID
 *  Only method will compare the 2 IDs, returns negative if ID a is less than ID b
 *  returns 0 if IDs are equal
 *  returns positive if ID a is greater than ID b
 */
class CustomerCompareById implements Comparator<Customer>{
    public int compare(Customer a, Customer b){
        return a.getCID() - b.getCID();
    }
}

/*  class RIDCompareById
 *  Implements Comparator to sort ArrayList of RID objects by RID
 *  Only method will compare the 2 IDs, returns negative if ID a is less than ID b
 *  returns 0 if IDs are equal
 *  returns positive if ID a is greater than ID b
 */
class RIDCompareById implements Comparator<reservation>{
    public int compare(reservation a, reservation b){ return Integer.parseInt(a.getRID()) - Integer.parseInt(b.getRID()); }
}