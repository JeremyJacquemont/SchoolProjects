package com.lpiem.apps.loupelec.utilities.camera;

import android.hardware.Camera;
import android.widget.Toast;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.dao.datasources.MediaDataSource;
import com.lpiem.apps.loupelec.fragments.CameraFragment;
import com.lpiem.apps.loupelec.utilities.MessageActivity;
import com.lpiem.apps.loupelec.utilities.Utilities;
import com.lpiem.apps.loupelec.utilities.ocr.OCRUtilities;

import java.io.File;

/**
 * CameraOCR Class
 */
public class CameraOCR implements Camera.PictureCallback {
    /*
    Fields
     */
    private CameraFragment mFragment = null;
    private File mFilePicture;
    private final MediaDataSource mMediaDataSource;

    /*
    Methods
     */

    /**
     * Constructor
     * @param fragment CameraFragment
     */
    public CameraOCR(CameraFragment fragment){
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
        SavePicture asyncSavePicture = new SavePicture(mFragment.getActivity(), this);
        asyncSavePicture.execute(picture);

        //Restart Preview
        camera.startPreview();
    }

    /**
     * Execute OCR
     * @param file File
     * @param timestamp String
     */
    public void executeOcr(File file, String timestamp){
        //Save reference
        mFilePicture = file;

        //Show Message End capture
        Toast.makeText(mFragment.getActivity(), mFragment.getString(R.string.camera_toast_end_capture), Toast.LENGTH_SHORT).show();

        //Check if save image or not
        if(Utilities.getBooleanPreference(mFragment.getActivity(), mFragment.getString(R.string.preference_key_ocr_imgTxt), true)) {
            //Save image
            saveMedia(file);
        }

        //Show Message Start OCR
        Toast.makeText(mFragment.getActivity(), mFragment.getString(R.string.camera_toast_ocr_start), Toast.LENGTH_SHORT).show();

        //Execute OCR
        OCRUtilities ocrUtilities = new OCRUtilities(mFragment.getActivity(), timestamp, this);
        ocrUtilities.execute(file);
    }

    /**
     * onPostExecute OCR
     * @param file File
     */
    public void onPostExecute(File file){
        //Save OCR
        saveMedia(file);

        //Remove image
        if(!Utilities.getBooleanPreference(mFragment.getActivity(), mFragment.getString(R.string.preference_key_ocr_imgTxt), true)) {
            if(!mFilePicture.delete()){
                Toast.makeText(mFragment.getActivity(), mFragment.getString(R.string.error_occured), Toast.LENGTH_SHORT).show();
            }
        }

        //Show Message End OCR
        Toast.makeText(mFragment.getActivity(), mFragment.getString(R.string.camera_toast_ocr_end), Toast.LENGTH_SHORT).show();

        //Refresh List
        mFragment.getListener().onFragmentInteraction(MessageActivity.RELOAD_LIST, null);
    }

    /**
     * Save media in BDD
     * @param file File
     */
    private void saveMedia(File file){
        mMediaDataSource.createMedia(file.getName(), file.getAbsolutePath(), null);
    }
}
