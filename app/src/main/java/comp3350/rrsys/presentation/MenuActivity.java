package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import comp3350.rrsys.R;
import comp3350.rrsys.business.AccessMenu;
import comp3350.rrsys.objects.Item;

public class MenuActivity extends Activity
{
    private AccessMenu accessMenu;
    private Map<String, List<Item>> parentListItems;
    private ExpandableListView menuItemsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        accessMenu = new AccessMenu();

        List<String> parentList = accessMenu.getMenuTypes();
        parentListItems = new LinkedHashMap<>();

        for(String parent : parentList)
        {
            ArrayList<Item> items = accessMenu.getMenuByType(parent);
            parentListItems.put(parent, items);
        }

        menuItemsListView = findViewById(R.id.menuList);
        final ExpandableListAdapter expandableListAdapter = new ListAdapter(this, parentList, parentListItems);
        menuItemsListView.setAdapter(expandableListAdapter);
    }

    public void buttonBackOnClick(View v)
    {
        Intent backPageIntent = new Intent(MenuActivity.this, HomeActivity.class);
        MenuActivity.this.startActivity(backPageIntent);
    }
}
