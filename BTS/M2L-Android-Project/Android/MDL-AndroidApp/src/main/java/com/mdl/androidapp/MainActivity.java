package com.mdl.androidapp;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mdl.utils.NavDrawerItem;
import com.mdl.utils.NavDrawerListAdapter;

import java.util.ArrayList;

/**
 * MainActivity class
 */

public class MainActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;

    private CharSequence mTitle;

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private MenuItem mRefreshMenuItem;

    private SharedPreferences pref;

    /**
     * onCreate
     * @param savedInstanceState bundle of instance
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("com.mdl.androidapp", Context.MODE_PRIVATE);

        navMenuTitles = getResources().getStringArray(R.array.drawer_items);

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.drawer_items_icons);

        mTitle = navMenuTitles[0];
        mDrawerTitle = getString(R.string.drawer_opened);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setOnItemClickListener(new DrawerClickListener());

        navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));

        navMenuIcons.recycle();

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.drawer_opened, // nav drawer open - description for accessibility
                R.string.drawer_opened // nav drawer close - description for accessibility
        ){
            /**
             * onDrawerClosed
             * @param view current view
             */
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            /**
             * onDrawerOpened
             * @param drawerView current view
             */
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //Auto start HomeFragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new HomeFragement()).commit();
        setTitle(mTitle);
        mDrawerList.setItemChecked(0, true);
        mDrawerList.setSelection(0);
    }

    /**
     * DrawerClickListener class
     */
    private class DrawerClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showView(position);
        }
    }

    /**
     * showView
     * @param position position of fragment
     */
    private void showView(int position){
        Fragment fragment = null;
        boolean disconnect = false;
        ParameterFragement parameterFragement = null;
        switch (position){
            case 0:
                fragment = new HomeFragement();
                break;
            case 1:
                fragment = new ReservationFragement();
                break;
            case 2:
                parameterFragement = new ParameterFragement();
                break;
            case 3:
                disconnect = true;
                pref.edit().clear().commit();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .remove(getFragmentManager().findFragmentById(R.id.content_frame))
                    .replace(R.id.content_frame, fragment)
                    .commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }else if(parameterFragement!= null){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .remove(getFragmentManager().findFragmentById(R.id.content_frame))
                    .replace(R.id.content_frame, parameterFragement)
                    .commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }else{
            if(disconnect){
                Toast.makeText(this, getResources().getString(R.string.disconnect_sucessful), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, getResources().getString(R.string.error_fragement), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * onCreateOptionsMenu
     * @param menu current menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // add your refresh button to res/menu/main.xml
        getMenuInflater().inflate(R.menu.add_menu, menu);

        mRefreshMenuItem = menu.findItem(R.id.action_add);
        mRefreshMenuItem.setVisible(false);

        return true;
    }

    /**
     * onOptionsItemsSelected
     * @param item current item
     * @return boolean | super.onOptionsItemSelected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_add:
                addAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * addAction
     */
    private void addAction(){
        Intent i = new Intent(this, AddItemActivity.class);
        startActivity(i);
    }

    /**
     * onPrepareOptionsMenu
     * @param menu current menu
     * @return super.onPrepareOptionsMenu
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * setTitle
     * @param title title to set
     */
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * onPostCreate
     * @param savedInstanceState bundle of instance
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    /**
     * onConfigurationChanged
     * @param newConfig new configuration
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}