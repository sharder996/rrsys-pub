package comp3350.rrsys.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import comp3350.rrsys.R;
import comp3350.rrsys.objects.Reservation;

public class GetChoiceUpdateReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_choiceupdate_reservation);
    }

    public void buttonUpdateOnClick(View view) {
        Intent confirmIntent = new Intent(GetChoiceUpdateReservationActivity.this, GetUpdateReservationActivity.class);

        confirmIntent.putExtra("Date", getIntent().getStringExtra("Date"));
        confirmIntent.putExtra("TimeStart", getIntent().getStringExtra("Date"));
        confirmIntent.putExtra("TimeEnd", getIntent().getStringExtra("Date"));
        confirmIntent.putExtra("Code", getIntent().getStringExtra("Code"));
        confirmIntent.putExtra("People", getIntent().getStringExtra("People"));
    }

    public void buttonPreorderOnClick(View view) {
        Intent GetMenu = new Intent(GetChoiceUpdateReservationActivity.this, MenuActivity.class);
        GetChoiceUpdateReservationActivity.this.startActivity(GetMenu);

    }


    public void buttonBackOnClick(View view) {
        Intent backPageIntent = new Intent(GetChoiceUpdateReservationActivity.this, HomeActivity.class);
        GetChoiceUpdateReservationActivity.this.startActivity(backPageIntent);
    }
}