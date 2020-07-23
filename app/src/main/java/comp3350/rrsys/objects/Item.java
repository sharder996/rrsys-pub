package comp3350.rrsys.objects;

import java.text.DecimalFormat;
import comp3350.rrsys.objects.Type;

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


        if(price < 0.05 || price > 500) {
            throw new IllegalArgumentException("Invalid Price.");
        }else{
            if(price % 1 == 0){//there is no decimal place in price
                DecimalFormat formatter = new DecimalFormat("#0.00");
                this.price = Double.parseDouble(formatter.format(price));
            }else{
                String strPrice = String.valueOf(price);

                int round = strPrice.charAt(strPrice.indexOf('.')+2) - '0';//get 10th decimal place
                DecimalFormat formatter = new DecimalFormat("#0.0");
                strPrice = formatter.format(price);

                if(round >= 5 && round < 7){
                    //formatter = new DecimalFormat("#.##");
                    strPrice = strPrice.substring(0,strPrice.length()-1);
                    strPrice = strPrice + "5";
                    this.price = Double.parseDouble(strPrice);
                }
                else if(round >=7) {
                    formatter = new DecimalFormat("#.#");
                    strPrice = formatter.format(price);
                    strPrice = strPrice + "0";
                    this.price = Double.parseDouble(strPrice);
                }else if(round >= 3 && round < 5){
                    strPrice = strPrice.substring(0,strPrice.length()-1);
                    strPrice = strPrice + "5";
                    this.price = Double.parseDouble(strPrice);
                }else{
                    strPrice = strPrice.substring(0,strPrice.length()-1);
                    strPrice = strPrice + "0";
                    this.price = Double.parseDouble(strPrice);
                }
            }

        }

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
    public void setPrice(double price) {
        if(price < 0.05 || price > 500) {
            throw new IllegalArgumentException("Invalid Price.");
        }else{
            if(price % 1 == 0){//there is no decimal place in price
                DecimalFormat formatter = new DecimalFormat("#0.00");
                    this.price = Double.parseDouble(formatter.format(price));
                }else{
                    String strPrice = String.valueOf(price);

                    int round = strPrice.charAt(strPrice.indexOf('.')+2) - '0';//get 10th decimal place
                    DecimalFormat formatter = new DecimalFormat("#0.0");
                    strPrice = formatter.format(price);

                    if(round >= 5 && round < 7){
                        //formatter = new DecimalFormat("#.##");
                        strPrice = strPrice.substring(0,strPrice.length()-1);
                        strPrice = strPrice + "5";
                    this.price = Double.parseDouble(strPrice);
                }
                else if(round >=7) {
                    formatter = new DecimalFormat("#.#");
                    strPrice = formatter.format(price);
                    strPrice = strPrice + "0";
                    this.price = Double.parseDouble(strPrice);
                }else if(round >= 3 && round < 5){
                    strPrice = strPrice.substring(0,strPrice.length()-1);
                    strPrice = strPrice + "5";
                    this.price = Double.parseDouble(strPrice);
                }else{
                    strPrice = strPrice.substring(0,strPrice.length()-1);
                    strPrice = strPrice + "0";
                    this.price = Double.parseDouble(strPrice);
                }
            }

        }}
}
