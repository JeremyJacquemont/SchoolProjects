package com.lppierrejeremy.apps.filemanagement.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Surface;
import android.widget.Toast;

import com.lppierrejeremy.apps.filemanagement.DAO.MediaObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utilities Class
 */
public class Utilities {
    /*
    Constants
     */
    private final static String TAG = "Utilities";

    /*
    Methods
     */

    /**
     * SetUp ActionBar
     * @param actionBar ActionBar
     */
    public static void setUpActionBar(ActionBar actionBar){
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    /**
     * Get correct file path
     * @param path String
     * @return String
     */
    public static String getFilePath(String path){
        int indexSlash = Config.MEDIA_PATH.lastIndexOf('/');
        String dataDir = Config.MEDIA_PATH.substring(0, indexSlash);

        return dataDir + path;
    }

    /**
     * Get name of file
     * @param path String
     * @return String
     */
    public static String getNameFile(String path){
        int index = path.lastIndexOf("/");
        return path.substring(index + 1, path.length());
    }

    /**
     * Get path without name
     * @param path String
     * @return String
     */
    public static String getPathWithoutName(String path){
        int index = path.lastIndexOf("/");
        return path.substring(0, index);
    }

    /**
     * Show toast
     * @param context Context
     * @param message String
     */
    public static void showToast(final Context context, final String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Read Text inside File
     * @param mediaObject MediaObject
     * @return String
     */
    public static String readTxtFile(MediaObject mediaObject){
        File file = new File(Utilities.getFilePath(mediaObject.getPath()));
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return stringBuilder.toString();
    }

    /**
     * Lock Screen
     * @param activity Activity
     */
    public static void lockScreen(Activity activity){
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();

        switch(rotation) {
            case Surface.ROTATION_180:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                break;
            case Surface.ROTATION_270:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                break;
            case  Surface.ROTATION_0:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case Surface.ROTATION_90:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
        }
    }

    /**
     * UnLock Screen
     * @param activity Activity
     */
    public static void unlockScreen(Activity activity){
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}
