package comp3350.rrsys.presentation;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.content.Context;
import android.content.res.AssetManager;
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

    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.dbName);

        } catch (IOException ioe) {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }

    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];
            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }
}