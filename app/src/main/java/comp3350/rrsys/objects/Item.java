package comp3350.rrsys.objects;

import java.text.DecimalFormat;

public class Item {
    private int itemID;
    private String name;    // the name of the item
    private String type;    // the type of the item, e.g., breakfast, lunch, dinner, drink
    private String detail;  // the detail/description of the item

    // ArrayList<String> ingredients；
    private double price;   // the price of the item

    public Item(int itemID, String name, String type, String detail, double price) throws IllegalArgumentException
    {

        this.name = name;
        this.type = type;
        this.detail = detail;

        if(itemID < 0)
        {
            throw new IllegalArgumentException("ItemID must be positive integer.");
        }
        this.itemID = itemID;
        if(price < 0.05 || price > 500) {
            throw new IllegalArgumentException("Invalid Price.");
        }else{
            if(price % 1 == 0){//there is no decimal place in price
                DecimalFormat formatter = new DecimalFormat("#.##");
                this.price = Double.parseDouble(formatter.format(price));
            }else{
                String strPrice = String.valueOf(price);
                if (strPrice.length() - strPrice.indexOf('.') <= 2) {
                    strPrice = strPrice + "0";
                } else {
                    int round = strPrice.charAt(strPrice.indexOf('.') + 2) - '0';//get 10th decimal place

                    DecimalFormat formatter = new DecimalFormat("#.##");
                    strPrice = formatter.format(price);

                    if (round >= 5 && round <= 7) {
                        strPrice = strPrice.substring(0, strPrice.length() - 1);
                        strPrice = strPrice + "5";
                    } else if (round > 7) {
                        formatter = new DecimalFormat("#.#");
                        strPrice = formatter.format(price);

                        //strPrice = strPrice + "0";
                    } else if (round >= 3 && round < 5) {
                        strPrice = strPrice.substring(0, strPrice.length() - 1);
                        strPrice = strPrice + "5";

                    } else {
                        strPrice = strPrice.substring(0, strPrice.length() - 1);
                        strPrice = strPrice + "0";
                    }
                }//end else
                this.price = Double.parseDouble(strPrice);
            }

        }//end if

    }

    public int getItemID() { return itemID; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getDetail() { return detail; }
    public double getPrice() { return price; }

    public void setItemID(int itemID) {
        if(itemID < 0){
            throw new IllegalArgumentException("ItemID must be positive integer.");
        }
        this.itemID = itemID;
    }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setDetail(String detail) { this.detail = detail; }
    public void setPrice(double price) {
        if(price < 0.05 || price > 500) {
            throw new IllegalArgumentException("Invalid Price.");
        }else{
            if(price % 1 == 0){//there is no decimal place in price
                DecimalFormat formatter = new DecimalFormat("#.##");
                this.price = Double.parseDouble(formatter.format(price));
            }else{
                String strPrice = String.valueOf(price);
                if (strPrice.length() - strPrice.indexOf('.') <= 2) {
                    strPrice = strPrice + "0";
                } else {
                    int round = strPrice.charAt(strPrice.indexOf('.') + 2) - '0';//get 10th decimal place

                    DecimalFormat formatter = new DecimalFormat("#.##");
                    strPrice = formatter.format(price);

                    if (round >= 5 && round <= 7) {
                        strPrice = strPrice.substring(0, strPrice.length() - 1);
                        strPrice = strPrice + "5";
                    } else if (round > 7) {
                        formatter = new DecimalFormat("#.#");
                        strPrice = formatter.format(price);
                        //System.out.println(strPrice);
                        //strPrice = strPrice + "0";
                    } else if (round >= 3 && round < 5) {
                        strPrice = strPrice.substring(0, strPrice.length() - 1);
                        strPrice = strPrice + "5";

                    } else {
                        strPrice = strPrice.substring(0, strPrice.length() - 1);
                        strPrice = strPrice + "0";
                    }
                }//end else
                this.price = Double.parseDouble(strPrice);
            }

        }//end if
    }
    public boolean equal(Item newItem){
        boolean result = false;
        if(newItem.getItemID() == this.itemID && newItem.getName().equals(this.name) && newItem.getType().equals(this.type)
        && newItem.getDetail().equals(this.detail) && newItem.getPrice() == this.price){
            result = true;
        }
        return result;
    }
}
