package comp3350.rrsys.objects;

import java.util.ArrayList;

public class Order {
    private int oID; // order ID
    private ArrayList<Item> order;
    private double price;
    private String note; // dietary requirements, etc.

    public Order()
    {
        order = new ArrayList<>();
        price = 0;
    }

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

    public ArrayList<Item> getOrder() { return order; }
    public double getPrice() { return price; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public int getOID() { return oID; }
    public void setOID(int oID) { this.oID = oID; }
}
