package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import comp3350.rrsys.R;
import comp3350.rrsys.objects.Reservation;

public class PreOrderMenuActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_menu);
        Reservation reservation = (Reservation)getIntent().getParcelableExtra("reservation");


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void buttonCancelOnClick(View v)
    {

    }

    public void buttonConfirmOnClick(View v)
    {

    }
}
