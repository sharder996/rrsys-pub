package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import comp3350.rrsys.R;

public class GetReviewReservationActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_reservation_review);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void buttonHomeOnClick(View v)
    {
        Intent returnHomeIntent = new Intent(GetReviewReservationActivity.this, HomeActivity.class);
        GetReviewReservationActivity.this.startActivity(returnHomeIntent);
    }


    public void buttonEnterOnClick(View v)
    {
        Intent confirmIntent = new Intent(GetReviewReservationActivity.this, ReviewReservationActivity.class);
        GetReviewReservationActivity.this.startActivity(confirmIntent);
    }
}
