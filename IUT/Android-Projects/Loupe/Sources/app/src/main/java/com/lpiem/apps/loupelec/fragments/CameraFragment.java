package com.lpiem.apps.loupelec.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.activities.MainActivity;
import com.lpiem.apps.loupelec.interfaces.OnFragmentInteractionListener;
import com.lpiem.apps.loupelec.utilities.Config;
import com.lpiem.apps.loupelec.utilities.MessageActivity;
import com.lpiem.apps.loupelec.utilities.Utilities;
import com.lpiem.apps.loupelec.utilities.camera.CameraOCR;
import com.lpiem.apps.loupelec.utilities.camera.CameraPicture;
import com.lpiem.apps.loupelec.utilities.camera.CameraPreview;
import com.lpiem.apps.loupelec.utilities.camera.CameraUtilities;
import com.lpiem.apps.loupelec.utilities.customUiElement.ZoomButton;

import java.util.List;

/**
 * CameraFragment Class
 */
public class CameraFragment extends Fragment{

    /*
    Fields
     */
    private Camera mCamera = null;
    private Camera.Parameters mCameraParameters = null;
    private CameraPicture mCameraPicture = null;
    private OnFragmentInteractionListener mListener = null;
    private CameraOCR mCameraPictureOcr;
    public OnFragmentInteractionListener getListener(){
        return mListener;
    }

    /*
    Methods
     */

    /**
     * onCreateView Method (Override)
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    /**
     * onViewCreated Method (Override)
     * @param view View
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        //Check camera
        if(!CameraUtilities.checkCameraHardware(getActivity())) {
            showErrorDialog();
        }
        else{
            //Get Camera
            mCamera = CameraUtilities.getCameraInstance();

            //SetUp Camera
            setUpCamera();

            //SetUp Preview
            CameraPreview cameraPreview = (CameraPreview) view.findViewById(R.id.fl_camera_preview);
            cameraPreview.setupPreview(mCamera);

            //Create Camera Picture
            mCameraPicture = new CameraPicture(this);
            mCameraPictureOcr = new CameraOCR(this);

            //Set Zoom Actions
            setUpZoomActions(view);

            //Get show buttons
            Boolean showImageButton = Utilities.getBooleanPreference(getActivity(), getString(R.string.preference_key_button_images), false);

            //SetUp Capture Button
            View.OnClickListener captureListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utilities.getBooleanPreference(getActivity(), getString(R.string.preference_key_ocr_toogle), false)){
                        executeCapture(mCameraPictureOcr);
                    }
                    else {
                        executeCapture(mCameraPicture);
                    }
                }
            };
            if(showImageButton){
                ImageButton captureImageButton = (ImageButton) view.findViewById(R.id.imgbtn_camera_capture);
                captureImageButton.setVisibility(View.VISIBLE);
                captureImageButton.setOnClickListener(captureListener);
            }
            else {
                Button captureButton = (Button) view.findViewById(R.id.btn_camera_capture);
                captureButton.setVisibility(View.VISIBLE);
                captureButton.setOnClickListener(captureListener);
            }


            //SetUp Gallery Button
            View.OnClickListener galleryListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle params = new Bundle();
                    params.putInt(MainActivity.FRAGMENT_KEY, MainActivity.FRAGMENT_LISTING);
                    mListener.onFragmentInteraction(MessageActivity.SLIDE_PAGER, params);
                }
            };
            if(showImageButton){
                ImageButton galleryImageButton = (ImageButton) view.findViewById(R.id.imgbtn_camera_gallery);
                galleryImageButton.setVisibility(View.VISIBLE);
                galleryImageButton.setOnClickListener(galleryListener);
            }
            else {
                Button galleryButton = (Button) view.findViewById(R.id.btn_camera_gallery);
                galleryButton.setVisibility(View.VISIBLE);
                galleryButton.setOnClickListener(galleryListener);
            }

            //SetUp Parameters
            View.OnClickListener parametersListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onFragmentInteraction(MessageActivity.START_PARAMETERS, null);
                }
            };
            if(showImageButton){
                ImageButton parametersImageButton = (ImageButton) view.findViewById(R.id.imgbtn_camera_prefs);
                parametersImageButton.setVisibility(View.VISIBLE);
                parametersImageButton.setOnClickListener(parametersListener);
            }
            else {
                Button parametersButton = (Button) view.findViewById(R.id.btn_camera_prefs);
                parametersButton.setVisibility(View.VISIBLE);
                parametersButton.setOnClickListener(parametersListener);
            }
        }
    }

    /**
     * Execute capture
     * @param pictureCallback PictureCallback
     */
    private void executeCapture(final Camera.PictureCallback pictureCallback){
        //Play Sound
        playSound();

        //Show Message Start capture
        Toast.makeText(getActivity(), getString(R.string.camera_toast_start_capture), Toast.LENGTH_SHORT).show();

        //Execute takePicture
        if (getAutoFocusStatus()){
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if(success) camera.takePicture(null, null, pictureCallback);
                }
            });
        }else{
            mCamera.takePicture(null, null, pictureCallback);
        }
    }

    /**
     * Play sound
     */
    private void playSound()
    {
        SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
        int shutterSound = soundPool.load(getActivity(), R.raw.camera_click, 0);
        soundPool.play(shutterSound, 1f, 1f, 0, 0, 1);
    }

    /**
     * Get status focus
     * @return boolean
     */
    private boolean getAutoFocusStatus(){
        return mCamera.getParameters().getFocusMode().equals(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    /**
     * onDestroy Method (Override)
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mCamera != null)
            mCamera.release();
    }

    /**
     * onAttach Method (Override)
     * @param activity Activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    /**
     * onDetach Method (Override)
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Show alertDialog
     */
    private void showErrorDialog(){
        //Create Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.camera_missing_title));
        builder.setMessage(getString(R.string.camera_missing_content));
        builder.setNeutralButton(getString(R.string.camera_missing_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onFragmentInteraction(MessageActivity.FINISH, null);
            }
        });

        //Show Dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Set parameters for camera
     */
    private void setUpCamera(){
        //Get Parameters
        mCameraParameters = mCamera.getParameters();

        //Set Focus Mode
        List<String> focusModes = mCameraParameters.getSupportedFocusModes();
        if(focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE))
            mCameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        else if(focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO))
            mCameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        else if(focusModes.contains(Camera.Parameters.FOCUS_MODE_MACRO))
            mCameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
        else if(focusModes.contains(Camera.Parameters.FOCUS_MODE_FIXED))
            mCameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);

        //Set Flash
        List<String> flashModes = mCameraParameters.getSupportedFlashModes();
        boolean flashEnable = Utilities.getBooleanPreference(getActivity(), getString(R.string.preference_key_camera_flash), false);
        if(flashModes != null && flashModes.size() > 0 &&
                flashModes.contains(Camera.Parameters.FLASH_MODE_ON) && flashEnable) {
            mCameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
        }
        else
            mCameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

        //Set Zoom
        mCameraParameters.setZoom(Config.CAMERA_MIN_ZOOM);

        //Set Parameters
        mCamera.setParameters(mCameraParameters);
    }

    /**
     * Set actions for Zoom
     * @param view View
     */
    private void setUpZoomActions(View view) {
        //Set Zoom In (+)
        Button buttonIn = (ZoomButton) view.findViewById(R.id.btn_camera_zoom_in);
        buttonIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controlsZoomAction(Config.EventZoom.ZOOM_IN);
            }
        });

        //Set Zoom Out (-)
        Button buttonOut = (ZoomButton) view.findViewById(R.id.btn_camera_zoom_out);
        buttonOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controlsZoomAction(Config.EventZoom.ZOOM_OUT);
            }
        });

        //Set Zoom (Touch)
        view.setOnTouchListener(createZoomTouchListener());
    }

    /**
     * Create OnTouchListener for pinchToZoom
     * @return OnTouchListener
     */
    private View.OnTouchListener createZoomTouchListener(){
        return new View.OnTouchListener() {
            /**
             * Fields
             */
            private float mDist;

            /**
             * onTouch Method (Override)
             * @param view View
             * @param event MotionEvent
             * @return boolean
             */
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // Get the pointer ID
                int action = event.getAction();

                //Check if get more to 1 point
                if (event.getPointerCount() > 1) {
                    // handle multi-touch events
                    if (action == MotionEvent.ACTION_POINTER_DOWN) {
                        mDist = CameraUtilities.getFingerSpacing(event);
                    } else if (action == MotionEvent.ACTION_MOVE && mCameraParameters.isZoomSupported()) {
                        mCamera.cancelAutoFocus();
                        touchZoomAction(event, mCameraParameters);
                    }
                }
                return true;
            }

            /**
             * Set Zoom for Camera with pinchToZoom
             * @param event MotionEvent
             * @param params Camera.Parameters
             */
            private void touchZoomAction(MotionEvent event, Camera.Parameters params) {
                //Get zoom & maxZoom
                int maxZoom = params.getMaxZoom();
                int zoom = params.getZoom();

                //Calculate distance
                float newDist = CameraUtilities.getFingerSpacing(event);

                if (newDist > mDist) {
                    //zoom in
                    if (zoom < maxZoom)
                        zoom++;
                } else if (newDist < mDist) {
                    //zoom out
                    if (zoom > 0)
                        zoom--;
                }

                //Set zoom
                mDist = newDist;
                params.setZoom(zoom);
                mCamera.setParameters(params);
            }
        };
    }

    /**
     * Set zoom for Camera with EventZoom
     * @param event EventZoom
     */
    private void controlsZoomAction(Config.EventZoom event){
        //Get zoom & maxZoom
        int maxZoom = mCameraParameters.getMaxZoom();
        int zoom = mCameraParameters.getZoom();

        //Get correct zoom
        switch (event){
            case ZOOM_IN:
                zoom = (zoom < maxZoom) ? Math.min(zoom + Config.CAMERA_INCREMENT_ZOOM, maxZoom) : maxZoom;
                break;
            case ZOOM_OUT:
                zoom = (zoom < 0) ? 0 : Math.max(zoom - Config.CAMERA_INCREMENT_ZOOM, 0);
                break;
            default:
                break;
        }

        //Set zoom
        mCameraParameters.setZoom(zoom);
        mCamera.setParameters(mCameraParameters);
    }
}
