package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import comp3350.rrsys.R;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Order;

public class ReceiptReservationActivity extends Activity
{
    int rID, numPeople, year, month, day, startHour, startMinute, endHour, endMinute;
    DateTime start;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_receipt);
        rID = Integer.parseInt(getIntent().getStringExtra("reservationID"));
        numPeople = Integer.parseInt(getIntent().getStringExtra("numPeople"));
        year = Integer.parseInt(getIntent().getStringExtra("year"));
        month = Integer.parseInt(getIntent().getStringExtra("month"));
        day = Integer.parseInt(getIntent().getStringExtra("day"));
        startHour = Integer.parseInt(getIntent().getStringExtra("startHour"));
        startMinute = Integer.parseInt(getIntent().getStringExtra("startMinute"));
        endHour = Integer.parseInt(getIntent().getStringExtra("endHour"));
        endMinute = Integer.parseInt(getIntent().getStringExtra("endMinute"));
        start = new DateTime(new GregorianCalendar(year, month, day, startHour, startMinute));

        final TextView textReservationCode = findViewById(R.id.textReservationCode);
        textReservationCode.setText("" + rID);

        final TextView textDateInfo = findViewById(R.id.textDateInfo);
        textDateInfo.setText((month+1) + "/" + day + "/" + year);

        final TextView textTimeInfo = findViewById(R.id.textTimeInfo);
        String timeInfo;
        if(startMinute < 10 && endMinute < 10)
            timeInfo = String.format(startHour + ":0%d - " + endHour + ":0%d", startMinute, endMinute);
        else if(startMinute < 10)
            timeInfo = String.format(startHour + ":0%d - " + endHour + ":%d", startMinute, endMinute);
        else if(endMinute < 10)
            timeInfo = String.format(startHour + ":%d - " + endHour + ":0%d", startMinute, endMinute);
        else
            timeInfo = String.format(startHour + ":%d - " + endHour + ":%d", startMinute, endMinute);
        textTimeInfo.setText(timeInfo);

        final TextView textNumberOfPeopleInfo = findViewById(R.id.textNumberOfPeopleInfo);
        textNumberOfPeopleInfo.setText("" + numPeople);
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
        if(start.getCalendar().getTimeInMillis() - Calendar.getInstance().getTimeInMillis() > Order.PREPARATION_TIME)
        {
            Intent preOrderIntent = new Intent(ReceiptReservationActivity.this, CreateOrderActivity.class);
            preOrderIntent.putExtra("activity", "ReceiptReservationActivity");
            preOrderIntent.putExtra("reservationID", Integer.toString(rID));
            preOrderIntent.putExtra("year", year + "");
            preOrderIntent.putExtra("month", month + "");
            preOrderIntent.putExtra("day", day + "");
            preOrderIntent.putExtra("startHour", startHour + "");
            preOrderIntent.putExtra("startMinute", startMinute + "");
            preOrderIntent.putExtra("endHour", endHour + "");
            preOrderIntent.putExtra("endMinute", endMinute + "");
            preOrderIntent.putExtra("numPeople", numPeople + "");
            ReceiptReservationActivity.this.startActivity(preOrderIntent);
        }
        else
        {
            Messages.warning(this, "Error: Not enough time till reservation");
        }
    }
}