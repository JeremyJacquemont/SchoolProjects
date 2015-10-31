package com.lpiem.apps.loupelec.utilities.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * CameraPreview Class
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    /*
    Constants
     */
    private final static String TAG = "CameraPreview";

    /*
    Fields
     */
    private Camera mCamera;

    /*
    Methods
     */

    /**
     * Constructors
     */
    public CameraPreview(Context context){
        super(context);
    }
    public CameraPreview(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }
    public CameraPreview(Context context, AttributeSet attributeSet, int i){
        super(context, attributeSet, i);
    }

    /**
     * SetUp Preview
     * @param camera Camera
     */
    public void setupPreview(Camera camera) {
        //Save camera
        mCamera = camera;

        //Get Holder and add callback
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    /**
     * surfaceCreated Method (Override)
     * @param holder SurfaceHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    /**
     * surfaceDestroyed Method (Override)
     * @param holder SurfaceHolder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        Log.i(TAG,"surfaceDestroyed");
    }

    /**
     * surfaceChanged Method (Override)
     * @param holder SurfaceHolder
     * @param format int
     * @param width int
     * @param height int
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Get Camera Parameters
        Camera.Parameters parameters = mCamera.getParameters();

        //Get ratio size
        int[] ratioSize = CameraUtilities.getSizeWithRatio(parameters.getPreviewSize(), this);

        //Set new layout parameters
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        layoutParams.width = ratioSize[0];
        layoutParams.height = ratioSize[1];
        this.setLayoutParams(layoutParams);

        //Restart preview
        mCamera.startPreview();
    }
}