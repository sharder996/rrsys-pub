package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import comp3350.rrsys.R;

public class CreateOrderActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
    }

    public void buttonBackOnClick(View v)
    {
        //TODO: ominous stray comment left here
        // Change to proper place once added in to UI
        Intent backPageIntent = new Intent(CreateOrderActivity.this, HomeActivity.class);
        CreateOrderActivity.this.startActivity(backPageIntent);
    }

    public void buttonDoneOnClick(View v)
    {
        Intent backPageIntent = new Intent(CreateOrderActivity.this, ConfirmOrderActivity.class);
        CreateOrderActivity.this.startActivity(backPageIntent);
    }
}


