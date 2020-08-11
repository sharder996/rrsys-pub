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
    private int lineItem; //only ever incremented, same functionality as database
    public static int PREPARATION_TIME = 1000 * 3600 * 1;  // amount of time required for order preparation

    public Order(int reservationID)
    {
        if(reservationID < 0)
        {
            throw new IllegalArgumentException("Invalid reservationID");
        }
        this.reservationID = reservationID;
        order = new ArrayList<>();
        lineItem = 0;
    }

    public ArrayList<Item> getOrder() { return order; }
    public int getReservationID() { return reservationID; }
    public int size() { return order.size(); }

    public void addItem(Item newItem, String note)
    {
        if(newItem != null)
        {
            newItem.setLineItem(lineItem++);
            newItem.setNote(note);
            order.add(newItem);
        }
    }

    public void deleteItem(int lineItem)
    {
        for(int i = 0; i < getOrder().size(); i++)
        {
            if(getOrder().get(i).getLineItem() == lineItem)
            {
                order.remove(i);
                break;
            }
        }
    }

    public String getNote(int lineItem)
    {
        String noteResult = null;
        for(int i = 0; i < getOrder().size(); i++)
        {
            if(order.get(i).getLineItem() == lineItem)
            {
                noteResult = order.get(i).getNote();
                break;
            }
        }
        return noteResult;
    }

    public Item getItem(int lineItem)
    {
        Item itemResult = null;
        for(int i = 0; i < getOrder().size(); i++)
        {
            if(order.get(i).getLineItem() == lineItem)
            {
                itemResult = order.get(i);
                break;
            }
        }
        return itemResult;
    }

    public void setNote(String note, int lineItem)
    {
        for(int i = 0; i < getOrder().size(); i++)
        {
            if(order.get(i).getLineItem() == lineItem)
            {
                order.get(i).setNote(note);
                break;
            }
        }
    }

    public double getTotalPrice()
    {
        double total = 0.0;
        for(int i  = 0; i < order.size(); i++)
        {
            total += order.get(i).getPrice();
        }
        return total;
    }

}
