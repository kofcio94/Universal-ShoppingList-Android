package com.hotmail.at.jablonski.michal.shoppinglist.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SwipeAdapter extends FragmentStatePagerAdapter {

    private int FRAGMENTS_COUNT;
    private String[] titles;
    private Fragment[] fragments;

    public SwipeAdapter(FragmentManager fm, String[] pageStripTitles, Fragment[] fragments) {
        super(fm);
        FRAGMENTS_COUNT = fragments.length;
        this.fragments = fragments;
        if (pageStripTitles.length > FRAGMENTS_COUNT)
            throw new IllegalArgumentException();
        this.titles = pageStripTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
