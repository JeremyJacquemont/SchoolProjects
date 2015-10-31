package com.lpiem.apps.loupelec.utilities.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;

import com.lpiem.apps.loupelec.utilities.Config;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CameraUtilities Class
 */
public class CameraUtilities {

    /*
    Constants
     */
    private static final String TAG = "CameraUtilities";

    /**
     * Check if hardware contains a camera
     * @param context Context
     * @return boolean
     */
    public static boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * Get Camera Instance
     * @return Camera
     */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){
            Log.d(TAG, "Error with get camera instance: " + e.getMessage());
        }
        return c;
    }

    /**
     * Get finger spacing
     * @param event MotionEvent
     * @return float
     */
    public static float getFingerSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    /**
     * Get width and height with correct ratio
     * @param size Camera.Size
     * @param cameraPreview CameraPreview
     * @return int[]
     */
    public static int[] getSizeWithRatio(Camera.Size size, CameraPreview cameraPreview) {
        float ratio = (float)size.width/size.height;
        int new_width, new_height;

        if(cameraPreview.getWidth()/cameraPreview.getHeight()<ratio){
            new_width = Math.round(cameraPreview.getHeight()*ratio);
            new_height = cameraPreview.getHeight();
        }
        else{
            new_width = cameraPreview.getWidth();
            new_height = Math.round(cameraPreview.getWidth()/ratio);
        }

        return new int[]{new_width, new_height};
    }

    /**
     * Get current timestamp
     * @return String
     */
    public static String getTimestamp(){
        return new SimpleDateFormat(Config.TIMESTAMP_FORMAT).format(new Date());
    }
}
