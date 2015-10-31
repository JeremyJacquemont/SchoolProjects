package com.lppierrejeremy.apps.filemanagement.Utilities.XML;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * XMLDownloader Class
 */
public class XMLDownloader {
    /*
    Constants
     */
    private final static String TAG = "XMLDownloader";

    /*
    Methods
     */

    /**
     * Download XML File
     * @param url String
     * @return String
     */
    public static String downloadXMLFile(String url) {
        //Init variables
        InputStream stream;
        StringBuilder stringFromStream;

        try {
            //Get content with URL
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.connect();
            stream = conn.getInputStream();

            //Create Buffer and StringBuilder
            byte[] bytes = new byte[1000];
            stringFromStream = new StringBuilder();

            //Convert input stream to string
            int numRead;
            while ((numRead = stream.read(bytes)) >= 0) {
                stringFromStream.append(new String(bytes, 0, numRead));
            }
            conn.disconnect();

        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

        //Return content
        if (stringFromStream.length() > 0) {
            return stringFromStream.toString();
        } else {
            return null;
        }
    }
}
