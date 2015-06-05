package com.example.vvellaichamy.nanodegreeappportfolio;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class HomeScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EF5350")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        Context context = getApplicationContext();
        CharSequence text = "";

        switch (view.getId()) {
            case R.id.btnSpotifyStreamer:
                text = "This will open spotify app";
                break;
            case R.id.btnScoresApp:
                text = "This will open scores app";
                break;
            case R.id.btnLibrary:
                text = "This will open library app";
                break;
            case R.id.btnBuildItBigger:
                text = "This will open build it bigger app";
                break;
            case R.id.btnXYZReader:
                text = "This will open xyz reader app";
                break;
            case R.id.btnMyApp:
                text = "This will open my app";
                break;
        }
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
