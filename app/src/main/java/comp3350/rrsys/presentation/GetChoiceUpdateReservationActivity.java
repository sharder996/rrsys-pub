package comp3350.rrsys.presentation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import comp3350.rrsys.R;

public class GetChoiceUpdateReservationActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_choiceupdate_reservation);
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

    public void buttonPreorderOnClick(View view)
    {
        Intent GetMenu = new Intent(GetChoiceUpdateReservationActivity.this, MenuActivity.class);
        GetChoiceUpdateReservationActivity.this.startActivity(GetMenu);
    }

    public void buttonBackOnClick(View view)
    {
        Intent backPageIntent = new Intent(GetChoiceUpdateReservationActivity.this, GetUpdateReservationActivity.class);
        GetChoiceUpdateReservationActivity.this.startActivity(backPageIntent);
    }
}