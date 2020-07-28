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
        Reservation reservation = getIntent().getParcelableExtra("reservation");

        final TextView textReservationCode = findViewById(R.id.textReservationCode);
        textReservationCode.setText("" + reservation.getRID());

        final TextView textDateInfo = findViewById(R.id.textDateInfo);
        textDateInfo.setText((reservation.getStartTime().getMonth()+1) + "/" + reservation.getStartTime().getDate() + "/" + reservation.getStartTime().getYear());

        final TextView textTimeInfo = findViewById(R.id.textTimeInfo);
        String timeInfo;
        if(reservation.getStartTime().getMinutes() < 10 && reservation.getEndTime().getMinutes() < 10)
            timeInfo = String.format(reservation.getStartTime().getHour() + ":0%d - " + reservation.getEndTime().getHour() + ":0%d", reservation.getStartTime().getMinutes(), reservation.getEndTime().getMinutes());
        else if(reservation.getStartTime().getMinutes() < 10)
            timeInfo = String.format(reservation.getStartTime().getHour() + ":0%d - " + reservation.getEndTime().getHour() + ":%d", reservation.getStartTime().getMinutes(), reservation.getEndTime().getMinutes());
        else if(reservation.getEndTime().getMinutes() < 10)
            timeInfo = String.format(reservation.getStartTime().getHour() + ":%d - " + reservation.getEndTime().getHour() + ":0%d", reservation.getStartTime().getMinutes(), reservation.getEndTime().getMinutes());
        else
            timeInfo = String.format(reservation.getStartTime().getHour() + ":%d - " + reservation.getEndTime().getHour() + ":%d", reservation.getStartTime().getMinutes(), reservation.getEndTime().getMinutes());
        textTimeInfo.setText(timeInfo);

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

    public void buttonPreOrderOnClick(View v)
    {
        Intent preOrderIntent = new Intent(ReceiptReservationActivity.this, CreateOrderActivity.class);
        ReceiptReservationActivity.this.startActivity(preOrderIntent);
    }
}
