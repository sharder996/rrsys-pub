package comp3350.rrsys.objects;

import java.text.DecimalFormat;

public class Item {
    private int itemID;
    private String name;    // the name of the item
    private String type;    // the type of the item, e.g., breakfast, lunch, dinner, drink
    private String detail;  // the detail/description of the item
    // ArrayList<String> ingredients；
    private double price;   // the price of the item
    private static int counter = 0;

    public Item(String name, String type, String detail, double price) throws IllegalArgumentException
    {
        this.name = name;
        this.type = type;
        this.detail = detail;


//        if(price < 0.05 || price > 1000) {
//            throw new IllegalArgumentException("Invalid Price.");
//        }else{
//            DecimalFormat formatter = new DecimalFormat("#0.00");
//            this.price = Double.parseDouble(formatter.format(price));
//
//
//        }

        this.price = price;
    }

    public int getItemID() { return itemID; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getDetail() { return detail; }
    public double getPrice() { return price; }

    public void setItemID() { itemID = counter++; }
    public void setItemID(int itemID) { this.itemID = itemID; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setDetail(String detail) { this.detail = detail; }
    public void setPrice(double price) { this.price = price; }
}
