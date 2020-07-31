package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;
import java.util.GregorianCalendar;

import comp3350.rrsys.R;
import comp3350.rrsys.objects.DateTime;
import comp3350.rrsys.objects.Order;

public class GetChoiceUpdateReservationActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_choice_update_reservation);
    }

    public void buttonUpdateOnClick(View view)
    {
        Intent confirmIntent = new Intent(GetChoiceUpdateReservationActivity.this, UpdateReservationActivity.class);

        confirmIntent.putExtra("Date", getIntent().getStringExtra("Date"));
        confirmIntent.putExtra("TimeStart", getIntent().getStringExtra("TimeStart"));
        confirmIntent.putExtra("TimeEnd", getIntent().getStringExtra("TimeEnd"));
        confirmIntent.putExtra("Code", getIntent().getStringExtra("Code"));
        confirmIntent.putExtra("People", getIntent().getStringExtra("People"));

        GetChoiceUpdateReservationActivity.this.startActivity(confirmIntent);
    }

    public void buttonPreOrderOnClick(View view)
    {
        int year = Integer.parseInt(getIntent().getStringExtra("year"));
        int month =  Integer.parseInt(getIntent().getStringExtra("month"));
        int day = Integer.parseInt(getIntent().getStringExtra("day"));
        int startHour =  Integer.parseInt(getIntent().getStringExtra("startHour"));
        int startMinute = Integer.parseInt(getIntent().getStringExtra("startMinute"));
        DateTime startTime = new DateTime(new GregorianCalendar(year, month, day, startHour, startMinute));
        if(startTime.getCalendar().getTimeInMillis() - Calendar.getInstance().getTimeInMillis() > Order.PREPARATION_TIME)
        {
            Intent getMenu = new Intent(GetChoiceUpdateReservationActivity.this, CreateOrderActivity.class);
            getMenu.putExtra("year", getIntent().getStringExtra("year"));
            getMenu.putExtra("month", getIntent().getStringExtra("month"));
            getMenu.putExtra("day", getIntent().getStringExtra("day"));
            getMenu.putExtra("startHour", getIntent().getStringExtra("startHour"));
            getMenu.putExtra("startMinute", getIntent().getStringExtra("startMinute"));
            getMenu.putExtra("reservationID", getIntent().getStringExtra("reservationID"));
            getMenu.putExtra("activity", "GetChoiceUpdateReservationActivity");
            getMenu.putExtra("Date", getIntent().getStringExtra("Date"));
            getMenu.putExtra("TimeStart", getIntent().getStringExtra("TimeStart"));
            getMenu.putExtra("TimeEnd", getIntent().getStringExtra("TimeEnd"));
            getMenu.putExtra("Code", getIntent().getStringExtra("Code"));
            getMenu.putExtra("numPeople", getIntent().getStringExtra("numPeople"));
            GetChoiceUpdateReservationActivity.this.startActivity(getMenu);
        }
        else
        {
            Messages.warning(this, "Error: Not enough time till reservation");
        }
    }

    public void buttonBackOnClick(View view)
    {
        Intent backPageIntent = new Intent(GetChoiceUpdateReservationActivity.this, GetUpdateReservationActivity.class);
        GetChoiceUpdateReservationActivity.this.startActivity(backPageIntent);
    }
}