package com.lpiem.apps.loupelec.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.fragments.CameraFragment;
import com.lpiem.apps.loupelec.fragments.ListFragment;
import com.lpiem.apps.loupelec.interfaces.OnFragmentInteractionListener;
import com.lpiem.apps.loupelec.utilities.MessageActivity;
import com.lpiem.apps.loupelec.utilities.animation.ZoomOutPageTransformer;
import com.lpiem.apps.loupelec.utilities.customUiElement.CustomActivity;
import com.lpiem.apps.loupelec.utilities.fragment.CustomFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity Class
 */
public class MainActivity extends CustomActivity implements OnFragmentInteractionListener {

    /*
    Constants
     */
    public final static String FRAGMENT_KEY = "fragment";
    public final static int FRAGMENT_CAMERA = 0;
    public final static int FRAGMENT_LISTING = 1;

    /*
    Fields
     */
    private CustomFragmentPagerAdapter mCustomFragmentPagerAdapter;
    private ViewPager mPager;

    /*
    Methods
     */

    /**
     * onCreate Method (Override)
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set Content
        setContentView(R.layout.activity_main);

        //Create list of fragments
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(Fragment.instantiate(this, CameraFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, ListFragment.class.getName()));

        //Create Custom PageAdapter
        mCustomFragmentPagerAdapter = new CustomFragmentPagerAdapter(super.getSupportFragmentManager(), fragments);

        //Find ViewPager & Set Adapter
        mPager = (ViewPager) findViewById(R.id.vp_main);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setAdapter(mCustomFragmentPagerAdapter);
    }

    /**
     * onResume Method (Override)
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(mCustomFragmentPagerAdapter != null)
            onFragmentInteraction(MessageActivity.RELOAD_LIST, null);
    }

    /**
     * onFragmentInteraction Method (Implement OnFragmentInteractionListener)
     * @param message MessageActivity
     */
    @Override
    public void onFragmentInteraction(MessageActivity message, Bundle params) {
        switch (message){
            case START_PARAMETERS:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case FINISH:
                finish();
                break;
            case RELOAD_LIST:
                ListFragment fragment = (ListFragment) mCustomFragmentPagerAdapter.getActiveFragment(mPager, FRAGMENT_LISTING);
                if(fragment != null)
                    fragment.refreshFragment();
                break;
            case SLIDE_PAGER:
                if(params != null){
                    mPager.setCurrentItem(params.getInt(FRAGMENT_KEY), true);
                }
                break;
            default:
                break;
        }
    }

    /**
     * onBackPressed Method (Override)
     */
    @Override
    public void onBackPressed() {
        if(mPager.getCurrentItem() == MainActivity.FRAGMENT_LISTING){
            Bundle params = new Bundle();
            params.putInt(MainActivity.FRAGMENT_KEY, MainActivity.FRAGMENT_CAMERA);
            onFragmentInteraction(MessageActivity.SLIDE_PAGER, params);
        }
        else{
            super.onBackPressed();
        }
    }
}
