package com.lppierrejeremy.apps.filemanagement.Utilities.Internet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Surface;
import android.widget.ListView;

import com.lppierrejeremy.apps.filemanagement.DAO.DataSources.MediaDataSource;
import com.lppierrejeremy.apps.filemanagement.DAO.MediaManager;
import com.lppierrejeremy.apps.filemanagement.DAO.MediaObject;
import com.lppierrejeremy.apps.filemanagement.R;
import com.lppierrejeremy.apps.filemanagement.Utilities.Config;
import com.lppierrejeremy.apps.filemanagement.Utilities.Utilities;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * DownloadMedia Class
 */
public class DownloadMedia extends AsyncTask<Void, Void, String> {
    /*
    Constants
     */
    private static final String TAG = "DownloadMedia";

    /*
    Fields
     */
    private Context mContext;
    private MediaObject mMediaObject;
    private ListView mListView;
    private ProgressDialog mProgressDialog;
    private String mUrl;
    private File mFile;

    /*
    Constructor
     */

    /**
     * DownloadMedia Constructor
     * @param context Context
     * @param mediaObject MediaObject
     * @param listView ListView
     */
    public DownloadMedia(Context context, MediaObject mediaObject, ListView listView){
        this.mContext = context;
        this.mMediaObject = mediaObject;
        this.mListView = listView;
    }

    /**
     * onPreExecute Method (Override)
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //Lock Screen
        Utilities.lockScreen(((Activity)mContext));

        //Set Up ProgressDialog
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(mContext.getString(R.string.download_file));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    /**
     * doInBackground Method (Override)
     * @param params Void...
     * @return String
     */
    @Override
    protected String doInBackground(Void... params) {
        //Check connection
        if(!Connection.checkConnectivity(mContext))
            return mContext.getString(R.string.error_no_connection);

        //Delete file if exist
        deleteFile(mMediaObject);

        //Init variables
        this.mUrl = getMediaUrl(mMediaObject);
        this.mFile = getMediaFile(mMediaObject);

        //Save with type
        String mediaType = mMediaObject.getType();
        return saveMedia(mediaType);
    }

    /**
     * onPostExecute Method (Override)
     * @param error String
     */
    @Override
    protected void onPostExecute(String error) {
        super.onPostExecute(error);

        //Dismiss ProgressDialog
        mProgressDialog.dismiss();

        //UnLock Screen
        Utilities.unlockScreen(((Activity)mContext));

        //Check if has an error
        if(error == null || error.equals("")) {
            Utilities.showToast(mContext, mContext.getString(R.string.download_ok));

            //Save in BDD
            MediaDataSource mediaDataSource = new MediaDataSource(mContext);
            mediaDataSource.updateMedia(
                    mMediaObject,
                    mMediaObject.getName(),
                    mMediaObject.getPath(),
                    mMediaObject.getVersionCode(),
                    mMediaObject.getType(),
                    MediaObject.KEY_INSTALLED,
                    MediaObject.KEY_NOT_UPDATED);

            //Refresh list
            MediaManager.refreshList(mContext);

            //Invalidates Views inside ListView
            mListView.invalidateViews();
        }
        else
            Utilities.showToast(mContext, error);
    }

    /**
     * Delete file
     * @param mediaObject MediaObject
     */
    public static void deleteFile(MediaObject mediaObject) {
        File file = getMediaFile(mediaObject);

        if(file.exists()) {
            file.delete();
        }
    }

    /**
     * Get Media File
     * @param mediaObject MediaObject
     * @return File
     */
    private static File getMediaFile(MediaObject mediaObject){
        //Get Path
        String pathUrl = mediaObject.getPath();
        String fullPathUrl = Utilities.getFilePath(pathUrl);

        //Get File informations
        String name = Utilities.getNameFile(fullPathUrl);
        String dirPath = Utilities.getPathWithoutName(fullPathUrl);

        //Create directories
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();

        //Return file
        return new File(dirPath, name);
    }

    /**
     * Get Media URL
     * @param mediaObject MediaObject
     * @return String
     */
    private static String getMediaUrl(MediaObject mediaObject){
        return Config.REMOTE_URL + mediaObject.getPath();
    }

    /**
     * Save Media
     * @param type String
     * @return String
     */
    private String saveMedia(String type){
        //Init Variables
        String error = null;
        final DefaultHttpClient client = new DefaultHttpClient();
        final HttpGet httpGet = new HttpGet(mUrl);

        try {
            //Get Response from client
            HttpResponse response = client.execute(httpGet);

            //Check StatusCode
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                //Get Entity
                final HttpEntity entity = response.getEntity();
                if (entity != null) {
                    //Init InputStream
                    InputStream inputStream = null;
                    try {

                        //Get InputStream from Entity
                        inputStream = entity.getContent();

                        //Execute Write Media
                        if (Config.MEDIA_TYPE.IMAGE.getType().equals(type))
                            error = writeImage(inputStream);
                        else if (Config.MEDIA_TYPE.TEXT.getType().equals(type) ||
                                Config.MEDIA_TYPE.VIDEO.getType().equals(type) ||
                                Config.MEDIA_TYPE.AUDIO.getType().equals(type))
                            error = writeFile(inputStream);
                        else
                            error = mContext.getString(R.string.error_download_write_file);

                    } finally {
                        //Close
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        entity.consumeContent();
                    }
                }
            }
            //Media is not found
            else if(statusCode == HttpStatus.SC_NOT_FOUND){
                error = mContext.getString(R.string.error_download_not_found);
            }
            //Others errors
            else{
                error = mContext.getString(R.string.error_download_unknown_error);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            httpGet.abort();
            error = mContext.getString(R.string.error_download_unknown_error);
        } finally {
            client.getConnectionManager().shutdown();
        }

        //Check if length file > 0
        if(mFile.length() == 0){
            mFile.delete();
        }

        //Return error
        return error;
    }

    /**
     * Write File (except Image File)
     * @param inputStream InputStream
     * @return String
     */
    private String writeFile(InputStream inputStream){
        //Init Variables
        String error = null;
        FileOutputStream fileOutputStream = null;

        try {
            //Set FileOutputStream
            fileOutputStream = new FileOutputStream(mFile);

            //Create Buffer
            byte[] buffer = new byte[1024];
            int bufferLength;

            //Write File
            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutputStream.write(buffer, 0, bufferLength);

            }
        }
        //File not found
        catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
            error = mContext.getString(R.string.error_download_write_file);
        }
        //IOException
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
            error = mContext.getString(R.string.error_download_write_file);
        }
        finally {
            //Close
            if(fileOutputStream != null)
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
        }

        //Return error
        return error;
    }

    /**
     * Write Image File
     * @param inputStream InputStream
     * @return String
     */
    private String writeImage(InputStream inputStream){
        //Init Variables
        String error = null;
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        //Check bitmap
        if(bitmap != null) {
            //Create FileOutputStream
            FileOutputStream fileOutputStream = null;

            try {
                //Set FileOutputStream && Save Image
                fileOutputStream = new FileOutputStream(mFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            }
            //File not found
            catch (FileNotFoundException e) {
                Log.e(TAG, e.getMessage());
                error = mContext.getString(R.string.error_download_write_file);
            }
            finally {
                //Close
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
        else{
            error = mContext.getString(R.string.error_download_write_file);
        }

        return error;
    }
}