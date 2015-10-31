package com.lpiem.apps.loupelec.utilities.camera;

import android.hardware.Camera;
import android.widget.Toast;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.dao.datasources.MediaDataSource;
import com.lpiem.apps.loupelec.fragments.CameraFragment;
import com.lpiem.apps.loupelec.utilities.MessageActivity;

import java.io.File;

/**
 * CameraPicture Class
 */
public class CameraPicture implements Camera.PictureCallback {
    /*
    Fields
     */
    private CameraFragment mFragment = null;
    private final MediaDataSource mMediaDataSource;

    /*
    Methods
     */

    /**
     * Constructor
     * @param fragment CameraFragment
     */
    public CameraPicture(CameraFragment fragment){
        mMediaDataSource = new MediaDataSource(fragment.getActivity());
        mFragment = fragment;
    }

    /**
     * onPictureTaken Method (Override)
     * @param picture byte[]
     * @param camera Camera
     */
    @Override
    public void onPictureTaken(byte[] picture, Camera camera) {
        //Execute AsyncTask
        new SavePicture(mFragment.getActivity(), this).execute(picture);

        //Restart Preview
        camera.startPreview();
    }

    /**
     * onPostExecute Capture
     * @param file File
     */
    public void onPostExecute(File file){
        //Create media in BDD
        mMediaDataSource.createMedia(file.getName(), file.getAbsolutePath(), null);

        //Show Message End capture
        Toast.makeText(mFragment.getActivity(), mFragment.getString(R.string.camera_toast_end_capture), Toast.LENGTH_SHORT).show();

        //Refresh list
        mFragment.getListener().onFragmentInteraction(MessageActivity.RELOAD_LIST, null);
    }
}
