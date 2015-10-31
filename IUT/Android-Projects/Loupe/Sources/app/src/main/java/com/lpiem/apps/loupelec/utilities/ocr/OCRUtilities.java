package com.lpiem.apps.loupelec.utilities.ocr;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.utilities.Config;
import com.lpiem.apps.loupelec.utilities.Utilities;
import com.lpiem.apps.loupelec.utilities.camera.CameraOCR;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * OCRUtilities Class
 */
public class OCRUtilities {

    /*
    Constants
     */
    private static final String TAG = "OCRUtilities";

    /*
    Fields
     */
    private final Context mContext;
    private final String mTimestamp;
    private final Camera.PictureCallback mPictureCallback;

    /*
    Methods
     */

    /**
     * Constructor
     * @param context Context
     * @param timestamp String
     */
    public OCRUtilities(Context context, String timestamp, Camera.PictureCallback pictureCallback){
        mContext = context;
        mTimestamp = timestamp;
        mPictureCallback = pictureCallback;

        initialization();
    }

    /**
     * Initialization
     */
    private void initialization() {
        //Check if folders exists
        String dataPath = Utilities.getDataDir(mContext);
        String tessdataPath = Utilities.getTessDataDir(mContext);

        if (!(new File(tessdataPath)).exists()) {
            createDirectory(new String[]{dataPath, tessdataPath});
            copyAssets();
        }
    }

    /**
     * Execute Method
     * @param file File
     */
    public void execute(File file){
        //Get path
        String path = file.getAbsolutePath();

        //Create Bitmap Options
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        //Decode bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        //Check rotation
        try {
            ExifInterface exif = new ExifInterface(path);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            int rotate = 0;
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

            if (rotate != 0) {
                // Getting width & height of the given image.
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
            }

        } catch (IOException e) {
            Log.e(TAG, "Couldn't correct orientation: " + e.toString());
        }

        //Create new TessBaseAPI
        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.setDebug(true);
        baseApi.init(Utilities.getDataDir(mContext), getLanguage());

        //Set bitmap to TessBaseAPI
        baseApi.setImage(bitmap);

        //Get Result to TessBaseAPI
        String resultText = baseApi.getUTF8Text();

        //Close TessBaseAPI
        baseApi.end();

        //Trim result text
        resultText = resultText.trim();

        //Put result
        File ocrFile = putResultOcr(resultText);

        //Call end
        if(mPictureCallback instanceof CameraOCR){
            ((CameraOCR) mPictureCallback).onPostExecute(ocrFile);
        }
    }

    /**
     * Create directories
     * @param paths String[]
     */
    private void createDirectory(String[] paths){
        for (String path : paths) {
            File dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.v(TAG, "ERROR: Creation of directory " + path + " failed");
                    return;
                } else {
                    Log.v(TAG, "Created directory " + path);
                }
            }
        }
    }

    /**
     * Copy All Assets
     */
    private void copyAssets() {
        //Get AssetManager
        AssetManager assetManager = mContext.getAssets();
        String[] files = null;

        //Get list with all files
        try {
            files = assetManager.list(Config.TESSDATA);
        } catch (IOException e) {
            Log.e(TAG, "Failed to get asset file list.", e);
        }

        //Loop in files for copy
        if(files == null || files.length == 0)
            return;

        for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(Config.TESSDATA + File.separator + filename);
                File outFile = new File(Utilities.getTessDataDir(mContext) + File.separator + filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch (IOException e) {
                Log.e(TAG, "Failed to copy asset file: " + filename, e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Error on close for InputStream");
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Error on close for OutputStream");
                    }
                }
            }

        }
    }

    /**
     * Copy file
     * @param in InputStream
     * @param out OutputStream
     * @throws IOException
     */
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    /**
     * Put txt file with result
     * @param result String
     */
    private File putResultOcr(String result){
        //Create new file
        File ocrFile = new File(Utilities.getFilesDir(mContext) + Config.START_NAME_OCR + mTimestamp + Config.EXTENSION_OCR);

        //Put file into external memory
        try {
            if (ocrFile.createNewFile()){
                FileOutputStream fOut = new FileOutputStream(ocrFile);
                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);
                myOutWriter.append(result);
                myOutWriter.close();
                fOut.close();
            }else{
                Log.e(TAG, "Error on file creation");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ocrFile;
    }

    /**
     * Get language inside SharedPreferences
     * @return String
     */
    private String getLanguage(){
        return Utilities.getStringPreference(mContext, mContext.getString(R.string.preference_key_ocr_languages), Config.LANG);
    }
}
