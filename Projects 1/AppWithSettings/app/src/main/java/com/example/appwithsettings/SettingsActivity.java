package com.example.appwithsettings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity
{
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        context = SettingsActivity.this;

        // To add, attach, detach, remove, replace a fragment we use a transaction via FragmentManager
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat
    {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
        {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    public static SharedPreferences.OnSharedPreferenceChangeListener getSharedPreferencesListener()
    {
        SharedPreferences.OnSharedPreferenceChangeListener listener =
                new SharedPreferences.OnSharedPreferenceChangeListener()
                {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
                    {
                        if (key.equals("reply"))
                        {
                            Toast.makeText(context, sharedPreferences.getString(key, ""), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

        return listener;
    }

}