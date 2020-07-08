package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import comp3350.rrsys.R;

public class ConfirmUpdatesActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_updates);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void buttonBackOnClick(View v)
    {
        Intent backPageIntent = new Intent(ConfirmUpdatesActivity.this,UpdateReservationActivity.class);
        ConfirmUpdatesActivity.this.startActivity(backPageIntent);
    }

    public void buttonConfirmOnClick(View v)
    {
        Intent confirmUpdatesIntent = new Intent(ConfirmUpdatesActivity.this, HomeActivity.class);
        ConfirmUpdatesActivity.this.startActivity(confirmUpdatesIntent);
    }
}
