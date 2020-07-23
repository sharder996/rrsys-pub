package comp3350.rrsys.presentation;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ListView;

        import java.util.ArrayList;

        import comp3350.rrsys.R;
        import comp3350.rrsys.business.AccessMenu;
        import comp3350.rrsys.objects.Item;

public class MenuActivity extends Activity
{
    ArrayList<Item> menuItems;
    AccessMenu accessMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        accessMenu = new AccessMenu();
        menuItems = new ArrayList<Item>();
        ListView listMenu = (ListView) findViewById(R.id.menuList);

        ArrayList<String> types = accessMenu.getMenuTypes();

        for(int i = 0; i < types.size(); i++){
            //listMenu.set
        }
    }

    public void buttonBackOnClick(View v)
    {
        Intent backPageIntent = new Intent(MenuActivity.this, HomeActivity.class);
        MenuActivity.this.startActivity(backPageIntent);
    }
}

