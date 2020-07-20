package comp3350.rrsys.objects;

public class Item {
    private String name;    // the name of the item
    private String type;    // the type of the item, e.g., breakfast, lunch, dinner, drink
    private String detail;  // the detail/description of the item
    // ArrayList<String> ingredients；
    private double price;   // the price of the item

    public Item(String name, String type, String detail, double price)
    {
        this.name = name;
        this.type = type;
        this.detail = detail;
        this.price = price;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public String getDetail() { return detail; }
    public double getPrice() { return price; }

    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setDetail(String detail) { this.detail = detail; }
    public void setPrice(double price) { this.price = price; }
}
