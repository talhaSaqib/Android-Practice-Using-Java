package com.example.assignment2.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.assignment2.Fragments.Constraint;
import com.example.assignment2.Fragments.Frame;
import com.example.assignment2.Fragments.Linear;
import com.example.assignment2.Fragments.Relative;
import com.example.assignment2.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter
{

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,
            R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment frag = null;
        switch(position)
        {
            case 0:
                frag = new Relative();
                break;
            case 1:
                frag = new Constraint();
                break;
            case 2:
                frag = new Frame();
                break;
            case 3:
                frag = new Linear();
                break;
        }
        return frag;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount()
    {
        return 4;
    }
}