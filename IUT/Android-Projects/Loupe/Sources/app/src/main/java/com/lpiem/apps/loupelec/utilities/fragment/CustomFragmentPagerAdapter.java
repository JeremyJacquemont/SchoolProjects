package com.lpiem.apps.loupelec.utilities.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * CustomFragmentPagerAdapter Class
 */
public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    /*
    Fields
     */
    private final FragmentManager mFragmentManager;
    private final List<Fragment> mFragments;

    /*
    Methods
     */

    /**
     * Constructor
     * @param fm FragmentManager
     * @param fragments List<Fragment>
     */
    public CustomFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragmentManager = fm;
        this.mFragments = fragments;
    }

    /**
     * getItem Method (Override)
     * @param position int
     * @return Fragment
     */
    @Override
    public Fragment getItem(int position) {
        return this.mFragments.get(position);
    }

    /**
     * getCount Method (Override)
     * @return int
     */
    @Override
    public int getCount() {
        return this.mFragments.size();
    }

    /**
     * Get active fragment
     * @param container ViewPager
     * @param position int
     * @return Fragment
     */
    public Fragment getActiveFragment(ViewPager container, int position) {
        String name = makeFragmentName(container.getId(), position);
        return  mFragmentManager.findFragmentByTag(name);
    }

    /**
     * Get name of fragment inside ViewPager
     * @param viewId int
     * @param index int
     * @return String
     */
    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }
}