package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import comp3350.rrsys.R;
import comp3350.rrsys.business.AccessCustomers;
import comp3350.rrsys.business.AccessReservations;
import comp3350.rrsys.objects.Reservation;

public class GetReviewReservationActivity extends Activity
{
    private AccessCustomers accessCustomers;
    private AccessReservations accessReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_reservation_review);
        accessCustomers = new AccessCustomers();
        accessReservations = new AccessReservations();
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
        Reservation selected = null;
        EditText code = (EditText) findViewById(R.id.editTextReservationCode);

        if(code.length() != 0) {
            try {
                selected = accessReservations.getRandom(Integer.parseInt(code.getText().toString()));
                String time;

                if(selected.getEndTime().getMinutes() < 10 && selected.getStartTime().getMinutes() < 10){
                    time = selected.getStartTime().getHour() + ":0" + selected.getStartTime().getMinutes() + " - " + selected.getEndTime().getHour() + ":0" + selected.getEndTime().getMinutes();
                }
                else if(selected.getEndTime().getMinutes() < 10 ){
                    time = selected.getStartTime().getHour() + ":" + selected.getStartTime().getMinutes() + " - " + selected.getEndTime().getHour() + ":0" + selected.getEndTime().getMinutes();
                }
                else if(selected.getStartTime().getMinutes() < 10){
                    time = selected.getStartTime().getHour() + ":0" + selected.getStartTime().getMinutes() + " - " + selected.getEndTime().getHour() + ":" + selected.getEndTime().getMinutes();
                }
                else{
                    time = selected.getStartTime().getHour() + ":0" + selected.getStartTime().getMinutes() + " - " + selected.getEndTime().getHour() + ":" + selected.getEndTime().getMinutes();
                }

                String resDate = (selected.getStartTime().getMonth() + 1) + "/" + selected.getStartTime().getDate() + "/" + selected.getStartTime().getYear();

                Intent confirmIntent = new Intent(GetReviewReservationActivity.this, ReviewReservationActivity.class);
                confirmIntent.putExtra("Date", resDate);
                confirmIntent.putExtra("Time", time);
                confirmIntent.putExtra("Code", selected.getRID() + "");
                confirmIntent.putExtra("People", selected.getNumPeople() + "");
                GetReviewReservationActivity.this.startActivity(confirmIntent);
            } catch (Exception e) {
                code.setError("Sorry we found no reservation with that reservation code");
                e.printStackTrace();
            }
        }
        else
        {
            if(code.length() ==0)
            {
                code.setError("Enter reservation code");
            }
        }
    }
}
