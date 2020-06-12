package com.example.assignment2;

import android.os.Bundle;

import com.example.assignment2.Fragments.Constraint;
import com.example.assignment2.Fragments.Frame;
import com.example.assignment2.Fragments.Linear;
import com.example.assignment2.Fragments.Relative;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.assignment2.ui.main.SectionsPagerAdapter;

public class Home extends AppCompatActivity
{

    private static final String TAG = "MyActivity";
    ViewPager viewPager;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_home);
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

            setBackButton();

            viewPager = findViewById(R.id.view_pager);
            viewPager.setAdapter(sectionsPagerAdapter);

            tabs = findViewById(R.id.tabs);
            tabs.setupWithViewPager(viewPager);
            setupTabIcons();

        }
        catch (Exception e)
        {
            Log.e(TAG, "onCreate: Error = " + e.toString());
        }

    }

    private void setupTabIcons()
    {
        try
        {
            tabs.getTabAt(0).setIcon(R.drawable.ic_assessment_black_24dp);
            tabs.getTabAt(1).setIcon(R.drawable.ic_av_timer_black_24dp);
            tabs.getTabAt(2).setIcon(R.drawable.ic_brightness_5_black_24dp);
            tabs.getTabAt(3).setIcon(R.drawable.ic_assignment_black_24dp);
        }
        catch (Exception e)
        {
            Log.e(TAG, "setupTabIcons: Error = " + e);
        }
    }



    void setBackButton()
    {
        try
        {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e)
        {
            Log.e(TAG, "setBackButton: Error = " + e.toString());
        }
    }

}