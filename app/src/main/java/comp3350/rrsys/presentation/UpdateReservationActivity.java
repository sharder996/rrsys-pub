package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import comp3350.rrsys.R;

public class UpdateReservationActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reservation);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void buttonBackOnClick(View v)
    {
        Intent backPageIntent = new Intent(UpdateReservationActivity.this,GetUpdateReservationActivity.class);
        UpdateReservationActivity.this.startActivity(backPageIntent);
    }

    public void buttonDoneOnClick(View v)
    {
        Intent confirmUpdatesIntent = new Intent(UpdateReservationActivity.this, ConfirmUpdatesActivity.class);
        UpdateReservationActivity.this.startActivity(confirmUpdatesIntent);
    }
}
