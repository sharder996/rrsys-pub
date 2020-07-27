package comp3350.rrsys.objects;

import java.util.ArrayList;

public class Menu {
    private ArrayList<Item> menu;

    public Menu()
    {
        menu = new ArrayList<>();
    }

    public void addItem(Item newItem) throws Exception {
        boolean duplicate = false;
        for(int i = 0; i < menu.size(); i++){
            if(menu.get(i).equal(newItem)){
                duplicate = true;
                break;
            }
        }

        if(!duplicate) {
            menu.add(newItem);
        }else{
            throw new Exception("Duplicate Item");
        }
    }

    public ArrayList<Item> getMenu() { return menu; }

    public ArrayList<Item> getType(String type)
    {
        ArrayList<Item> items = new ArrayList<>();
        for(int i = 0; i < menu.size(); i++)
        {
            if(menu.get(i).getType().equals(type))
                items.add(menu.get(i));
        }
        return items;
    }

}
