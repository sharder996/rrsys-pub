package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import comp3350.rrsys.R;
import comp3350.rrsys.objects.Item;
import comp3350.rrsys.business.AccessOrders;

public class ReviewReservationActivity extends Activity
{
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

        AccessOrders accessOrder = new AccessOrders();
        ExpandableListView orderList = findViewById(R.id.orderedList);

        HashMap<String, ArrayList<Item>> order = new HashMap<>();
        ArrayList<Item> orderInfo = accessOrder.getOrder(Integer.parseInt(getIntent().getStringExtra("Code")));

        order.put("Ordered " + orderInfo.size() + "  " + accessOrder.getPrice(Integer.parseInt(getIntent().getStringExtra("Code"))), orderInfo);

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
