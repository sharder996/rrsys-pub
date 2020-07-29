package comp3350.rrsys.objects;

import java.util.ArrayList;

public class Order
{
    private int reservationID;
    private int orderID;
    private ArrayList<Item> order;
    private double totalPrice;
    //TODO: remove this if not being used this iteration
    private String note;    // dietary requirements, etc.
    private static int counter = 1;

    public Order(int reservationID)
    {
        this.reservationID = reservationID;
        orderID = counter++;
        order = new ArrayList<>();
        totalPrice = 0;
    }

    public ArrayList<Item> getOrder() { return order; }
    public String getNote() { return note; }
    public double getPrice() { return totalPrice; }
    public int getOrderID() { return orderID; }
    public int getReservationID() { return reservationID; }

    public void setNote(String note) { this.note = note; }
    public void setOrderID(int orderID) { this.orderID = orderID; }

    public void addItem(Item newItem)
    {
        order.add(newItem);
        totalPrice += newItem.getPrice();
    }

    public void deleteItem(Item item)
    {
        if(order.remove(item))
            totalPrice -= item.getPrice();
    }

    public int size() { return order.size(); }
}
