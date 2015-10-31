package com.lpiem.apps.loupelec.utilities.camera;

import android.content.Context;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.util.Log;

import com.lpiem.apps.loupelec.utilities.Config;
import com.lpiem.apps.loupelec.utilities.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * SavePicture Class
 */
public class SavePicture extends AsyncTask<byte[], Void, Void> {

    /*
    Constants
     */
    private static final String TAG = "SavePicture";

    /*
    Fields
     */
    private File mPictureFile;
    private final Context mContext;
    private final Camera.PictureCallback mPictureCallback;
    private String mTimestamp;

    /*
    Methods
     */

    /**
     * Constructor
     * @param context Context
     * @param pictureCallback PictureCallback
     */
    public SavePicture(Context context, Camera.PictureCallback pictureCallback){
        mContext = context;
        mPictureCallback = pictureCallback;
    }

    /**
     * doInBackground Method (Override)
     * @param picture byte[]
     * @return Void
     */
    @Override
    protected Void doInBackground(byte[]... picture) {
        //Get Output file
        mPictureFile = getOutputMediaFile();

        //Check file
        if (mPictureFile == null){
            Log.d(TAG, "Error creating media file, check storage permissions");
        }
        else {
            //Save Picture
            try {
                FileOutputStream fos = new FileOutputStream(mPictureFile);
                fos.write(picture[0]);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * onPostExecute Method (Override)
     * @param aVoid Void
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //Execute next method according instanceof
        if(mPictureCallback instanceof  CameraOCR){
            ((CameraOCR) mPictureCallback).executeOcr(mPictureFile, mTimestamp);
        }
        else if(mPictureCallback instanceof CameraPicture){
            ((CameraPicture) mPictureCallback).onPostExecute(mPictureFile);
        }
    }

    /**
     * Get new File
     * @return File
     */
    private File getOutputMediaFile(){
        //Create new folder
        File mediaStorageDir = new File(Utilities.getFilesDir(mContext));

        //Check if folder exist
        if (!mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        mTimestamp = CameraUtilities.getTimestamp();

        //return new file
        return new File(mediaStorageDir.getPath() + File.separator +
                Config.START_NAME_IMG + mTimestamp + Config.EXTENSION_IMG);
    }
}
