package com.example.assignment2.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment2.Fragments.NestedFragments.NestedFragment;
import com.example.assignment2.Fragments.NestedFragments.NestedFragment1;
import com.example.assignment2.R;
import com.google.android.material.tabs.TabLayout;


public class Relative extends Fragment
{
    private static final String TAG = "MyActivity";
    TabLayout tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_relative, container, false);

        try
        {
            NestedSectionsPagerAdapter mSectionsPagerAdapter = new NestedSectionsPagerAdapter(getChildFragmentManager());

            ViewPager mViewPager = view.findViewById(R.id.view_pager1);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            tabs = view.findViewById(R.id.tabs1);
            tabs.setupWithViewPager(mViewPager);

            setupTabIcons();
        }
        catch (Exception e)
        {
            Log.e(TAG, "onCreateView: Error = " + e);
        }

        return view;
    }

    private void setupTabIcons()
    {
        try
        {
            tabs.getTabAt(0).setIcon(R.drawable.ic_center_focus_strong_black_24dp);
            tabs.getTabAt(1).setIcon(R.drawable.ic_equalizer_black_24dp);
        }
        catch (Exception e)
        {
            Log.e(TAG, "setupTabIcons: Error = " + e);
        }
    }

}
    class NestedSectionsPagerAdapter extends FragmentPagerAdapter
    {

        public NestedSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment frag = null;
            switch (position) {
                case 0:
                    frag = new NestedFragment();
                    break;
                case 1:
                    frag = new NestedFragment1();
                    break;
            }
            return frag;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }





