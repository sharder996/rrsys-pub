package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import comp3350.rrsys.R;
import comp3350.rrsys.objects.Reservation;

public class ConfirmUpdatesActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_updates);

        final TextView textReservationCode = findViewById(R.id.textReservationCode);
        textReservationCode.setText(getIntent().getStringExtra("Code"));

        final TextView textDateInfo = findViewById(R.id.textDateInfo);
        textDateInfo.setText(getIntent().getStringExtra("Date"));

        final TextView textTimeInfo = findViewById(R.id.textTimeInfo);
        textTimeInfo.setText(getIntent().getStringExtra("Time"));

        final TextView textNumberOfPeopleInfo = findViewById(R.id.textNumberOfPeopleInfo);
        textNumberOfPeopleInfo.setText(getIntent().getStringExtra("People"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void buttonHomeOnClick(View v)
    {
        Intent returnHomeIntent = new Intent(ConfirmUpdatesActivity.this, HomeActivity.class);
        ConfirmUpdatesActivity.this.startActivity(returnHomeIntent);
    }
}
