package comp3350.rrsys.objects;

import java.text.DecimalFormat;

/* Class: Item
 *
 * Class that holds and manages food items for the menu
 */

public class Item
{
    private int itemID;
    private String name;    // the name of the item
    private String type;    // the type of the item, e.g., breakfast, lunch, dinner, drink
    private String detail;  // the detail/description of the item
    private String note;
    private double price;   // the price of the item
    private int quantity;   // quantity of item ordered
    private static final double MIN_PRICE = 0.05;
    private static final double MAX_PRICE = 500;
    public static final int MIN_QUANTITY = 1;
    public static final int MAX_QUANTITY = 15;

    public Item(int itemID, String name, String type, String detail, double price) throws IllegalArgumentException
    {
        this.name = name;
        this.type = type;
        this.detail = detail;
        this.quantity = MIN_QUANTITY;
        setItemID(itemID);
        setPrice(price);
        note = "";
    }

    public int getItemID() { return itemID; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getDetail() { return detail; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getNote() { return note; }

    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setDetail(String detail) { this.detail = detail; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public void setNote(String note)
    {
        char[] illegalChars = { '-','\'', '\"', '*', ';' };
        String cleansedNote = note;

        if(cleansedNote != null)
        {
            for(char c : illegalChars)
                cleansedNote = cleansedNote.replace(c, ' ');
        }
        this.note = cleansedNote.trim();
    }

    public void setItemID(int itemID)
    {
        if(itemID < 0)
        {
            throw new IllegalArgumentException("ItemID must be positive integer.");
        }
        this.itemID = itemID;
    }

    public void setPrice(double price)
    {
        if(price < MIN_PRICE || price > MAX_PRICE)
        {
            throw new IllegalArgumentException("Invalid Price.");
        }
        else
        {
            if(price % 1 == 0)
            {
                // there is no decimal place in price
                DecimalFormat formatter = new DecimalFormat("#.##");
                this.price = Double.parseDouble(formatter.format(price));
            }
            else
            {
                String strPrice = String.valueOf(price);
                if(strPrice.length() - strPrice.indexOf('.') <= 2)
                {
                    strPrice = strPrice + "0";
                }
                else
                {
                    int round = strPrice.charAt(strPrice.indexOf('.') + 2) - '0';//get 10th decimal place

                    DecimalFormat formatter = new DecimalFormat("#.##");
                    strPrice = formatter.format(price);

                    if(round >= 5 && round <= 7)
                    {
                        strPrice = strPrice.substring(0, strPrice.length() - 1);
                        strPrice = strPrice + "5";
                    }
                    else if(round > 7)
                    {
                        formatter = new DecimalFormat("#.#");
                        strPrice = formatter.format(price);
                    }
                    else if(round >= 3 && round < 5)
                    {
                        strPrice = strPrice.substring(0, strPrice.length() - 1);
                        strPrice = strPrice + "5";
                    }
                    else
                    {
                        strPrice = strPrice.substring(0, strPrice.length() - 1);
                        strPrice = strPrice + "0";
                    }
                }
                this.price = Double.parseDouble(strPrice);
            }
        }
    }

    // display item message in order (preorder the meal)
    @Override
    public String toString()
    {
        String message = name + "\n" + price + ", " + detail + " Quantity: " + quantity;
        if(note.trim().length() > 0)
            message += "\nNote: " + note;
        return message;
    }

    // display item message in menu (without quantity and note)
    public String display()
    {
        return name + "\n" + price + ", " + detail;
    }
}
