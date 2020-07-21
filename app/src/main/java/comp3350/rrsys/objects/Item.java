package comp3350.rrsys.objects;

public class Item {
    private int iID;
    private String name;    // the name of the item
    private String type;    // the type of the item, e.g., breakfast, lunch, dinner, drink
    private String detail;  // the detail/description of the item
    // ArrayList<String> ingredients；
    private double price;   // the price of the item
    private static int counter = 0;

    public Item(String name, String type, String detail, double price)
    {
        this.name = name;
        this.type = type;
        this.detail = detail;
        this.price = price;
    }

    public int getIID() { return iID; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getDetail() { return detail; }
    public double getPrice() { return price; }

    public void setIID() { iID = counter++; }
    public void setIID(int iID) { this.iID = iID; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setDetail(String detail) { this.detail = detail; }
    public void setPrice(double price) { this.price = price; }
}
