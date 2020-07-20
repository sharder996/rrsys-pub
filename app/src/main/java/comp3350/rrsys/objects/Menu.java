package comp3350.rrsys.objects;

import java.util.ArrayList;

public class Menu {
    private ArrayList<Item> menu;
    //private ArrayList<Item> drink;

    public Menu()
    {
        menu = new ArrayList<>();
    }

    public void addItem(Item newItem){
        menu.add(newItem);
    }

    public ArrayList<Item> getMenu() { return menu; }
}
