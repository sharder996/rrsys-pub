package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import comp3350.rrsys.R;
import comp3350.rrsys.objects.Reservation;

public class CreateConfirmReservationActivity extends Activity{

    private Reservation reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_confirm_reservation);
        reservation = getIntent().getParcelableExtra("reservation");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_create_confirm_reservation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void buttonBackOnClick(View v)
    {
        Intent backPageIntent = new Intent(CreateConfirmReservationActivity.this, CreateReservationActivity.class);
        CreateConfirmReservationActivity.this.startActivity(backPageIntent);
    }


    public void buttonConfirmOnClick(View v)
    {
        Intent confirmReservationIntent = new Intent(CreateConfirmReservationActivity.this, ReceiptReservationActivity.class);
        CreateConfirmReservationActivity.this.startActivity(confirmReservationIntent);
    }

}
