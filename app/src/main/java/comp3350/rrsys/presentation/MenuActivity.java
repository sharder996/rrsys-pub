package comp3350.rrsys.presentation;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;

        import comp3350.rrsys.R;

public class MenuActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    public void buttonBackOnClick(View v)
    {
        Intent backPageIntent = new Intent(MenuActivity.this, HomeActivity.class);
        MenuActivity.this.startActivity(backPageIntent);
    }
}

