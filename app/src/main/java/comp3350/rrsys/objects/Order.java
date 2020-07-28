package comp3350.rrsys.objects;

import java.util.ArrayList;

public class Order
{
    private int OID;    // order ID
    private ArrayList<Item> order;
    private double totalPrice;
    //TODO: remove this if not being used this iteration
    private String note;    // dietary requirements, etc.

    public Order(int OID)
    {
        order = new ArrayList<>();
        totalPrice = 0;
    }

    public ArrayList<Item> getOrder() { return order; }
    public String getNote() { return note; }
    public double getPrice() { return totalPrice; }
    public int getOID() { return OID; }

    public void setNote(String note) { this.note = note; }
    public void setOID(int orderID) { this.OID = orderID; }

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
    public int size(){
        return order.size();
    }
}
