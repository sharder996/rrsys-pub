package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import comp3350.rrsys.R;

public class GetUpdateReservationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_reservation_update);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void buttonHomeOnClick(View v)
    {
        Intent returnHomeIntent = new Intent(GetUpdateReservationActivity.this, HomeActivity.class);
        GetUpdateReservationActivity.this.startActivity(returnHomeIntent);
    }


    public void buttonEnterOnClick(View v)
    {
        Intent confirmIntent = new Intent(GetUpdateReservationActivity.this, UpdateReservationActivity.class);
        GetUpdateReservationActivity.this.startActivity(confirmIntent);
    }
}
