package comp3350.rrsys.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import comp3350.rrsys.R;
import comp3350.rrsys.application.Main;

public class HomeActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Main.startUp();

        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        Main.shutDown();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void buttonCreateOnClick(View v)
    {
        Intent createReservationIntent = new Intent(HomeActivity.this, CreateReservationActivity.class);
        HomeActivity.this.startActivity(createReservationIntent);
    }

    public void buttonUpdateOnClick(View v)
    {
        Intent updateReservationIntent = new Intent(HomeActivity.this, GetUpdateReservationActivity.class);
        HomeActivity.this.startActivity(updateReservationIntent);
    }

    public void buttonReviewOnClick(View v)
    {
        Intent reviewReservationIntent = new Intent(HomeActivity.this, GetReviewReservationActivity.class);
        HomeActivity.this.startActivity(reviewReservationIntent);
    }
}