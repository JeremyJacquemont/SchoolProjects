package com.lppierrejeremy.apps.filemanagement.Services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.lppierrejeremy.apps.filemanagement.DAO.MediaManager;
import com.lppierrejeremy.apps.filemanagement.R;
import com.lppierrejeremy.apps.filemanagement.Utilities.Config;
import com.lppierrejeremy.apps.filemanagement.Utilities.Internet.Connection;
import com.lppierrejeremy.apps.filemanagement.Utilities.XML.XMLDownloader;
import com.lppierrejeremy.apps.filemanagement.Utilities.XML.XMLParser;

import java.util.Stack;

/**
 * MediaService Class
 */
public class MediaService extends Service {
    /*
    Constants
     */
    private static final String TAG = "MediaService";

    /*
    Constructor
     */

    /**
     * MediaService Constructor
     */
    public MediaService() { }

    /**
     * onBind Method (Override)
     * @param intent Intent
     * @return IBinder
     */
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * onStartCommand Method (Override)
     * @param intent Intent
     * @param flags int
     * @param startId int
     * @return int
     */
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        //Execute new AsyncTask
        new AsyncTask<Void, Void, String >() {

            /**
             * doInBackground Method (Override)
             * @param voids Void...
             * @return String
             */
            @Override
            protected String doInBackground(Void... voids) {
                return downloadMediasInfo();
            }

            /**
             * onPostExecute Method (Override)
             * @param error String
             */
            @Override
            protected void onPostExecute(String error) {
                super.onPostExecute(error);

                //Log message
                if(error == null || error.equals(""))
                    Log.d(TAG, getString(R.string.xml_load_ok));
                else
                    Log.e(TAG, error);

                //Send Broadcast
                sendBroadcast(new Intent(Config.FINISH_BROADCAST));
            }
        }.execute();

        //Return super
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Download Medias info
     * @return String
     */
    private String  downloadMediasInfo() {
        try {
            //Init variables
            String error = null;

            //Check connection
            if (Connection.checkConnectivity(getApplicationContext())) {
                //Get XML File
                String xmlFile = XMLDownloader.downloadXMLFile(Config.MEDIA_URL);
                if (xmlFile != null && !xmlFile.equals(getString(R.string.xml_not_found))) {
                    //Get XML Parser
                    XMLParser xmlParser = new XMLParser();
                    Stack<Bundle> mediaList = xmlParser.loadUpdaterXml(xmlFile);

                    //Update Medias
                    MediaManager mediaManager = MediaManager.getInstance();
                    mediaManager.updateMedias(getApplicationContext(), mediaList);
                } else {
                    error = getString(R.string.error_xml_not_found);
                }
            } else {
                error = getString(R.string.error_no_connection);
            }

            return error;
        }
        catch (Exception e){
            Log.e(TAG, e.getMessage());
            return e.getMessage();
        }
    }
}