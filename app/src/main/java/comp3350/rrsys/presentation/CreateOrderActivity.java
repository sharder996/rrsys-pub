package comp3350.rrsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import comp3350.rrsys.R;
import comp3350.rrsys.objects.Customer;

public class CreateOrderActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_menu);
        }

        public void buttonBackOnClick(View v) {
            Intent backPageIntent = new Intent(CreateOrderActivity.this, HomeActivity.class);//Change to proper place once added in to UI
            CreateOrderActivity.this.startActivity(backPageIntent);
        }

    public void buttonDoneOnClick(View v)
    {
        Intent backPageIntent = new Intent(CreateOrderActivity.this, ConfirmOrderActivity.class);
        CreateOrderActivity.this.startActivity(backPageIntent);
    }
}


