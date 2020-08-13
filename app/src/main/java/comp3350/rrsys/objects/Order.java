package comp3350.rrsys.objects;

import java.util.ArrayList;

/* Class: Order
 *
 * Class that holds orders for a reservation and manages them
 */

public class Order
{
    private int reservationID;
    private ArrayList<Item> order;
    public static int PREPARATION_TIME = 1000 * 3600 * 1;  // amount of time required for order preparation

    public Order(int reservationID)
    {
        if(reservationID < 0)
            throw new IllegalArgumentException("Invalid reservationID");
      
        this.reservationID = reservationID;
        order = new ArrayList<>();
    }

    public ArrayList<Item> getOrder() { return order; }
    public int getReservationID() { return reservationID; }
    public int getSize() { return order.size(); }

    public void addItem(Item newItem)
    {
        if(newItem != null)
            order.add(newItem);
    }

    public void addItem(Item newItem, int quantity, String note)
    {
        if(newItem != null)
        {
            newItem.setQuantity(quantity);
            newItem.setNote(note);
            order.add(newItem);
        }
    }

    public void deleteItem(Item item)
    {
        for(int i = 0; i < order.size(); i++)
        {
            if(order.get(i).equals(item))
            {
                order.remove(i);
                break;
            }
        }
    }

    public double getTotalPrice()
    {
        double total = 0.0;
        for(int i  = 0; i < order.size(); i++)
        {
            total += order.get(i).getPrice() * order.get(i).getQuantity();
            total = Math.round(total * 100.0) / 100.0;
        }
        return total;
    }
}
