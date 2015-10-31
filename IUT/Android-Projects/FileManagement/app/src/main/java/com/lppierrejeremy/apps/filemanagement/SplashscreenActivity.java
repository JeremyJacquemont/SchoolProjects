package com.lppierrejeremy.apps.filemanagement;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.lppierrejeremy.apps.filemanagement.Utilities.Config;

/**
 * SplashscreenActivity Class
 */
public class SplashscreenActivity extends Activity {

    /*
    Fields
     */
    private BackMediaBroadcast mBackMediaBroadcast;

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
        setContentView(R.layout.activity_splashscreen);

        //Create new Broadcast
        mBackMediaBroadcast = new BackMediaBroadcast();

        //Create IntentFilter
        IntentFilter intentFilter = new IntentFilter(Config.FINISH_BROADCAST);

        //Register a receiver
        registerReceiver(mBackMediaBroadcast, intentFilter);

        //Start Broadcast
        sendBroadcast(new Intent(Config.START_BROADCAST));
    }

    /**
     * onStop Method (Override)
     */
    @Override
    protected void onStop() {
        super.onStop();

        //Unregister receiver
        if(mBackMediaBroadcast != null)
            unregisterReceiver(mBackMediaBroadcast);
    }

    /**
     * BackMediaBroadcast Class
     */
    private class BackMediaBroadcast extends BroadcastReceiver {
        /**
         * onReceive Method (Override)
         * @param context Context
         * @param intent Intent
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action != null && action.equals(Config.FINISH_BROADCAST)) {
                Intent i = new Intent(SplashscreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }
    }
}
