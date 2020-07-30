package comp3350.rrsys.objects;

import java.util.ArrayList;

public class Order
{
    private int reservationID;
    private ArrayList<Item> order;
    private double totalPrice;
    private String note;    // dietary requirements, etc.
    public static int PREPARATION_TIME = 1000 * 3600 * 1;  // amount of time required for order preparation

    public Order(int reservationID)
    {
        if(reservationID < 0)
        {
            throw new IllegalArgumentException("Invalid reservationID");
        }
        this.reservationID = reservationID;
        order = new ArrayList<>();
        totalPrice = 0;
    }

    public ArrayList<Item> getOrder() { return order; }
    public String getNote() { return note; }
    public double getPrice() { return totalPrice; }
    public int getReservationID() { return reservationID; }

    public void setNote(String note) { this.note = note; }

    public void addItem(Item newItem)
    {
        if(newItem != null)
        {
            order.add(newItem);
            totalPrice += newItem.getPrice();
        }
    }

    public void deleteItem(Item item)
    {
        if(order.contains(item))
        {
            order.remove(item);
            totalPrice -= item.getPrice();
        }
    }

    public int size() { return order.size(); }
}
