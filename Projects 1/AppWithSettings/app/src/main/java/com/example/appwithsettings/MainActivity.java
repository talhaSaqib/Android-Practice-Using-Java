package com.example.appwithsettings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static com.example.appwithsettings.SettingsActivity.getSharedPreferencesListener;

public class MainActivity extends AppCompatActivity
{

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showSettingsToast(View view)
    {
        String signature = sharedPreferences.getString("signature", "");
        Toast.makeText(this, signature, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onResume()
    {
        super.onResume();
        sharedPreferences.registerOnSharedPreferenceChangeListener(getSharedPreferencesListener());
    }

    @Override
    public void onPause()
    {
        super.onPause();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(getSharedPreferencesListener());
    }

}
