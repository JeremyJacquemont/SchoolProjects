package com.lppierrejeremy.apps.filemanagement.Utilities.XML;

import android.os.Bundle;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * XMLParser Class
 */
public class XMLParser {
    /*
    Constants
     */
    private static final String TAG = "FileManagementXMLParser";
    public static final String KEY_XML_TAG = "media";
    public static final String KEY_TYPE = "type";
    public static final String KEY_NAME = "name";
    public static final String KEY_VERSION_CODE = "versionCode";
    public static final String KEY_DOWNLOAD_PATH = "path";

    /*
    Fields
     */
    private Stack<Bundle> mMediasList;

    /*
    Methods
     */

    /**
     * ListFileHandler Class
     */
    private class ListFileHandler extends DefaultHandler {

        /**
         * startElement Method (Override)
         * @param namespaceURI String
         * @param localName String
         * @param qName String
         * @param atts Attributes
         * @throws SAXException
         */
        @Override
        public void startElement(String namespaceURI, String localName,
                                 String qName, Attributes atts) throws SAXException {
            if (localName.equals(KEY_XML_TAG)) {
                Bundle b = new Bundle();
                b.putString(KEY_TYPE, atts.getValue(KEY_TYPE));
                b.putString(KEY_NAME, atts.getValue(KEY_NAME));
                b.putString(KEY_VERSION_CODE, atts.getValue(KEY_VERSION_CODE));
                b.putString(KEY_DOWNLOAD_PATH, atts.getValue(KEY_DOWNLOAD_PATH));
                mMediasList.push(b);
            }
        }
    }

    /**
     * Load Updater XML
     * @param xmlFile String
     * @return Stack
     */
    public Stack<Bundle> loadUpdaterXml(String xmlFile) {
        mMediasList = new Stack<Bundle>();

        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ListFileHandler dataHandler = new ListFileHandler();
            xr.setContentHandler(dataHandler);
            xr.parse(new InputSource(new StringReader(xmlFile)));

        } catch (FileNotFoundException e) {
            Log.w(TAG, e);
            return null;
        } catch (ParserConfigurationException e) {
            Log.w(TAG, e);
            return null;
        } catch (SAXException e) {
            Log.w(TAG, e);
            return null;
        } catch (IOException e) {
            Log.w(TAG, e);
            return null;
        }
        return mMediasList;
    }
}
