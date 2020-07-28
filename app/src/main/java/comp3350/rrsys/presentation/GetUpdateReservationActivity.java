package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import comp3350.rrsys.R;
import comp3350.rrsys.business.AccessReservations;
import comp3350.rrsys.objects.Reservation;

public class GetUpdateReservationActivity extends Activity
{
    private AccessReservations accessReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_reservation_update);
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
        Intent returnHomeIntent = new Intent(GetUpdateReservationActivity.this, HomeActivity.class);
        GetUpdateReservationActivity.this.startActivity(returnHomeIntent);
    }


    public void buttonEnterOnClick(View v)
    {
        Reservation selected = null;
        EditText code = findViewById(R.id.editTextReservationCode);

        if(code.length() != 0)
        {
            try
            {
                String timeEnd;
                String timeStart;

                if(selected.getEndTime().getMinutes() <10)
                     timeEnd = selected.getEndTime().getHour() +":0" + selected.getEndTime().getMinutes();
                else
                    timeEnd = selected.getEndTime().getHour() +":" + selected.getEndTime().getMinutes();

                if(selected.getStartTime().getMinutes() <10)
                    timeStart = selected.getStartTime().getHour() + ":0" + selected.getStartTime().getMinutes();
                else
                    timeStart = selected.getStartTime().getHour() + ":" + selected.getStartTime().getMinutes();

                String resDate = (selected.getStartTime().getMonth()+1) +"/" + selected.getStartTime().getDate() + "/" + selected.getStartTime().getYear();

                Intent confirmIntent = new Intent(GetUpdateReservationActivity.this, GetChoiceUpdateReservationActivity.class);
                confirmIntent.putExtra("Date", resDate);
                confirmIntent.putExtra("TimeStart", timeStart);
                confirmIntent.putExtra("TimeEnd", timeEnd);
                confirmIntent.putExtra("Code", selected.getRID()+ "");
                confirmIntent.putExtra("People", selected.getNumPeople()+ "");
                GetUpdateReservationActivity.this.startActivity(confirmIntent);
            }
            catch(Exception e)
            {
                code.setError("Sorry we found no reservation with that reservation code");
                e.printStackTrace();
            }
        }
        else
        {
            if(code.length() ==0)
                code.setError("Enter date");
        }
    }
}
