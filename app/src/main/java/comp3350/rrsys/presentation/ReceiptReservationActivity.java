package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import comp3350.rrsys.R;
import comp3350.rrsys.objects.Reservation;

public class ReceiptReservationActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_receipt);
        Reservation reservation = (Reservation)getIntent().getParcelableExtra("reservation");

        final TextView textReservationCode = findViewById(R.id.textReservationCode);
        textReservationCode.setText("" + reservation.getRID());

        final TextView textDateInfo = findViewById(R.id.textDateInfo);
        textDateInfo.setText((reservation.getStartTime().getMonth()+1) + "/" + reservation.getStartTime().getDate() + "/" + reservation.getStartTime().getYear());

        final TextView textTimeInfo = findViewById(R.id.textTimeInfo);
        textTimeInfo.setText(reservation.getStartTime().getHour() + ":" + reservation.getStartTime().getMinutes() + " - " + reservation.getEndTime().getHour() + ":" + reservation.getEndTime().getMinutes());

        final TextView textNumberOfPeopleInfo = findViewById(R.id.textNumberOfPeopleInfo);
        textNumberOfPeopleInfo.setText("" + reservation.getNumPeople());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_reservation_receipt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void buttonHomeOnClick(View v)
    {
        Intent returnHomeIntent = new Intent(ReceiptReservationActivity.this, HomeActivity.class);
        ReceiptReservationActivity.this.startActivity(returnHomeIntent);
    }
}
