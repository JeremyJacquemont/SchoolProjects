package com.lppierrejeremy.apps.filemanagement.Broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lppierrejeremy.apps.filemanagement.Services.MediaService;
import com.lppierrejeremy.apps.filemanagement.Utilities.Config;

/**
 * MediaBroadcast Class
 */
public class MediaBroadcast extends BroadcastReceiver {
    /*
    Methods
     */
    public MediaBroadcast() { }

    /**
     * onReceive Method (Override)
     * @param context Context
     * @param intent Intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action != null &&
                (action.equals(Config.START_BROADCAST) || action.equals(Config.START_BROADCAST_SYSTEM))) {
            context.startService(new Intent(context, MediaService.class));
        }
    }
}
