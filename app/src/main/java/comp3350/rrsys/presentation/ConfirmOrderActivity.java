package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import comp3350.rrsys.R;

public class ConfirmOrderActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void buttonBackOnClick(View v) {
        Intent backPageIntent = new Intent(ConfirmOrderActivity.this, CreateOrderActivity.class);
        ConfirmOrderActivity.this.startActivity(backPageIntent);
    }

    public void buttonConfirmOnClick(View v)
    {
        Intent backPageIntent = new Intent(ConfirmOrderActivity.this, HomeActivity.class);//Change to proper place once added in to UI
        ConfirmOrderActivity.this.startActivity(backPageIntent);
    }
}


