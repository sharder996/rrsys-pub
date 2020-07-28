package comp3350.rrsys.objects;

import java.util.ArrayList;

public class Order
{
    private int OID;    // order ID
    private ArrayList<Item> order;
    private double price;
    //TODO: remove this if not being used this iteration
    private String note;    // dietary requirements, etc.

    public Order(int OID)
    {
        order = new ArrayList<>();
        price = 0;
    }

    public ArrayList<Item> getOrder() { return order; }
    public String getNote() { return note; }
    public double getPrice() { return price; }
    public int getOID() { return OID; }

    public void setNote(String note) { this.note = note; }
    public void setOID(int orderID) { this.OID = orderID; }

    public void addItem(Item newItem)
    {
        order.add(newItem);
        price += newItem.getPrice();
    }

    public void deleteItem(Item item)
    {
        if(order.remove(item))
            price -= item.getPrice();
    }
    public int size(){
        return order.size();
    }
}
