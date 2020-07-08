package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import comp3350.rrsys.R;

public class ReviewReservationActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_reservation);

        TextView date = (TextView) findViewById(R.id.textDateInfo);
        TextView time = (TextView) findViewById(R.id.textTimeInfo);
        TextView code = (TextView) findViewById(R.id.textReservationCodeInfo);
        TextView numPeople = (TextView) findViewById(R.id.textNumberOfPeopleInfo);

        code.setText(getIntent().getStringExtra("Code"));
        time.setText(getIntent().getStringExtra("Time"));
        date.setText(getIntent().getStringExtra("Date"));
        numPeople.setText(getIntent().getStringExtra("People"));
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
