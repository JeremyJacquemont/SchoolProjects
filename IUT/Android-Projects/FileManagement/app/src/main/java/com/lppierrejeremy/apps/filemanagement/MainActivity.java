package com.lppierrejeremy.apps.filemanagement;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.lppierrejeremy.apps.filemanagement.Adapters.DrawerMenuAdapter;
import com.lppierrejeremy.apps.filemanagement.DAO.MediaManager;
import com.lppierrejeremy.apps.filemanagement.DAO.MediaObject;
import com.lppierrejeremy.apps.filemanagement.Fragments.DetailFragment;
import com.lppierrejeremy.apps.filemanagement.Fragments.MediaFragment;
import com.lppierrejeremy.apps.filemanagement.Interfaces.OnCallListener;
import com.lppierrejeremy.apps.filemanagement.Items.DrawerMenuItem;
import com.lppierrejeremy.apps.filemanagement.Utilities.Config;
import com.lppierrejeremy.apps.filemanagement.Utilities.Utilities;

import java.util.ArrayList;

/**
 * MainActivity Class
 */
public class MainActivity extends ActionBarActivity implements OnCallListener {
    /*
    Fields
     */
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ArrayList<DrawerMenuItem> mDrawerMenuItems = new ArrayList<DrawerMenuItem>();
    private String[] mDrawerTitles;
    private MediaManager mMediaManager;
    private boolean isOpening;
    private boolean useDualPane;

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
        setContentView(R.layout.activity_main);

        //Get MediaManager && Refresh list
        mMediaManager = MediaManager.getInstance();
        MediaManager.refreshList(this);

        //Enable / Disable dual pane
        useDualPane = getResources().getBoolean(R.bool.dual_pane);
        if(useDualPane) {
            findViewById(R.id.detail_frame).setVisibility(View.VISIBLE);
        }

        //Defines all titles
        mTitle = mDrawerTitle = getTitle();

        //Set MenuItems
        mDrawerTitles = getResources().getStringArray(R.array.menu_drawer_title);
        String[] drawerDrawables = getResources().getStringArray(R.array.menu_drawer_image);
        for(int i = 0; i < mDrawerTitles.length; i++){
            mDrawerMenuItems.add(new DrawerMenuItem(mDrawerTitles[i], getResources().getDrawable(getResources().getIdentifier(drawerDrawables[i], "drawable", getPackageName()))));
        }

        //Get all Drawer elements
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.app_name) {

            /**
             * onDrawerClosed Method (Override)
             * @param view View
             */
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            /**
             * onDrawerOpened Method (Override)
             * @param drawerView View
             */
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }

            /**
             * onDrawerSlide Method (Override)
             * @param drawerView View
             * @param slideOffset float
             */
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (slideOffset < .7f && !isOpening) {
                    isOpening = true;
                }
                if (slideOffset == 0) {
                    isOpening = false;
                }
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        Utilities.setUpActionBar(getSupportActionBar());

        //Set Elements with Adapter
        mDrawerList.setAdapter(new DrawerMenuAdapter(this, mDrawerMenuItems));

        //Set Listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    /**
     * onResume Method (Override)
     */
    @Override
    protected void onResume() {
        super.onResume();

        //Reset all views
        ((FrameLayout)findViewById(R.id.detail_frame)).removeAllViews();
    }

    /**
     * onCreateOptionsMenu Method (Override)
     * @param menu Menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * onPostCreate Method (Override)
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    /**
     * onConfigurationChanged Method (Override)
     * @param newConfig Configuration
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * onOptionsItemSelected Method (Override)
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /**
     * onCall Method (Override)
     * @param mediaObject MediaObject
     */
    @Override
    public void onCall(MediaObject mediaObject) {
        //If use dual pane
        if(useDualPane){
            //Create Fragment
            Fragment fragment;
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.KEY_MEDIA, mediaObject);
            fragment = DetailFragment.newInstance(args);
            if(fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                fragmentTransaction.replace(R.id.detail_frame, fragment).commit();
            }
        }
        else{
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.KEY_MEDIA, mediaObject);
            startActivity(intent);
        }
    }

    /**
     * DrawerItemClickListener Class
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        /**
         * onItemClick Method (Override)
         * @param parent AdapterView
         * @param view View
         * @param position int
         * @param id long
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /**
     * Select Item on DrawerList
     * @param position int
     */
    private void selectItem(int position) {
        Fragment fragment;
        Bundle args = new Bundle();

        //Switch with position
        switch (position){
            case 0: /* Image Fragment */
                args.putString(MediaFragment.MEDIA_TYPE, Config.MEDIA_TYPE.IMAGE.getType());
                args.putParcelableArrayList(MediaFragment.MEDIA_LIST, mMediaManager.getImages());
                break;
            case 1: /* Text Fragment */
                args.putString(MediaFragment.MEDIA_TYPE, Config.MEDIA_TYPE.TEXT.getType());
                args.putParcelableArrayList(MediaFragment.MEDIA_LIST, mMediaManager.getTexts());
                break;
            case 2: /* Video Fragment */
                args.putString(MediaFragment.MEDIA_TYPE, Config.MEDIA_TYPE.VIDEO.getType());
                args.putParcelableArrayList(MediaFragment.MEDIA_LIST, mMediaManager.getVideos());
                break;
            case 3: /* Audio Fragment */
                args.putString(MediaFragment.MEDIA_TYPE, Config.MEDIA_TYPE.AUDIO.getType());
                args.putParcelableArrayList(MediaFragment.MEDIA_LIST, mMediaManager.getAudios());
            default:
                break;
        }

        fragment = MediaFragment.newInstance(args);
        if(fragment != null) {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.content_frame, fragment).commit();

            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(mDrawerTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        else{
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    /**
     * setTitle Method (Override)
     * @param title CharSequence
     */
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
}
