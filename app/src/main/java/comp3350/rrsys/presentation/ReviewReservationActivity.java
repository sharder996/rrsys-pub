package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import comp3350.rrsys.R;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.business.AccessOrders;

public class ReviewReservationActivity extends Activity
{
    private AccessOrders accessOrder;
    private Map<String, List<Item>> parentListItems;
    private ExpandableListView orderListView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_reservation);

        TextView date = findViewById(R.id.textDateInfo);
        TextView time = findViewById(R.id.textTimeInfo);
        TextView code = findViewById(R.id.textReservationCodeInfo);
        TextView numPeople = findViewById(R.id.textNumberOfPeopleInfo);

        code.setText(getIntent().getStringExtra("Code"));
        time.setText(getIntent().getStringExtra("Time"));
        date.setText(getIntent().getStringExtra("Date"));
        numPeople.setText(getIntent().getStringExtra("People"));

        //if there is an order
        accessOrder = new AccessOrders();
        if(accessOrder.getNextLineitem(Integer.parseInt(getIntent().getStringExtra("Code"))))
        {
            List<String> parentList = new ArrayList<>();
            parentList.add("Total Price: $" + accessOrder.getPrice(Integer.parseInt(getIntent().getStringExtra("Code")))
                    + " (" + accessOrder.getSize(Integer.parseInt(getIntent().getStringExtra("Code"))) + " items)");

            parentListItems = new LinkedHashMap<>();
            for (String parent : parentList)
            {
                ArrayList<Item> items = accessOrder.getOrder(Integer.parseInt(getIntent().getStringExtra("Code"))).getOrder();
                parentListItems.put(parent, items);
            }

            orderListView = findViewById(R.id.expandListView);
            final ExpandableListAdapter expandableListAdapter = new ListAdapter(this, parentList, parentListItems);
            orderListView.setAdapter(expandableListAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void buttonDoneOnClick(View v)
    {
        Intent returnHomeIntent = new Intent(ReviewReservationActivity.this, HomeActivity.class);
        ReviewReservationActivity.this.startActivity(returnHomeIntent);
    }
}
