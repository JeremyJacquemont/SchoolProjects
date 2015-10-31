package com.lppierrejeremy.apps.filemanagement.DAO;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.lppierrejeremy.apps.filemanagement.DAO.DataSources.MediaDataSource;
import com.lppierrejeremy.apps.filemanagement.R;
import com.lppierrejeremy.apps.filemanagement.Utilities.Config;
import com.lppierrejeremy.apps.filemanagement.Utilities.Internet.DownloadMedia;
import com.lppierrejeremy.apps.filemanagement.Utilities.XML.XMLParser;

import java.util.ArrayList;
import java.util.Stack;

/**
 * MediaManager Class
 */
public class MediaManager {
    /*
    Constants
     */
    private final static String TAG = "MEDIA_MANAGER";

    /*
    Fields
     */
    private static MediaManager sInstance;
    private static ArrayList<MediaObject> mImagesList = new ArrayList<MediaObject>();
    private static ArrayList<MediaObject> mTextsList = new ArrayList<MediaObject>();
    private static ArrayList<MediaObject> mVideosList = new ArrayList<MediaObject>();
    private static ArrayList<MediaObject> mAudiosList = new ArrayList<MediaObject>();

    /*
    Methods
     */

    /**
     * getInstance
     * @return MediaManager
     */
    public static MediaManager getInstance() {
        if (sInstance == null) {
            sInstance = new MediaManager();
        }
        return sInstance;
    }

    public ArrayList<MediaObject> getImages() {
        return mImagesList;
    }
    public ArrayList<MediaObject> getTexts() {
        return mTextsList;
    }
    public ArrayList<MediaObject> getVideos() {
        return mVideosList;
    }
    public ArrayList<MediaObject> getAudios() { return  mAudiosList; }

    /**
     * setParameters for MediaObject
     * @param mediaObject MediaObject
     * @param mediaInfo Bundle
     * @return MediaObject
     */
    public MediaObject setParameters(MediaObject mediaObject, Bundle mediaInfo){
        mediaObject.name = mediaInfo.getString(XMLParser.KEY_NAME);
        mediaObject.path = mediaInfo.getString(XMLParser.KEY_DOWNLOAD_PATH);
        mediaObject.versionCode = Integer.decode(mediaInfo.getString(XMLParser.KEY_VERSION_CODE));
        mediaObject.type = mediaInfo.getString(XMLParser.KEY_TYPE);
        mediaObject.installed = MediaObject.KEY_NOT_INSTALLED;
        return mediaObject;
    }

    /**
     * Update Medias
     * @param context Context
     * @param mediasInfo Stack
     */
    public void updateMedias(Context context, Stack<Bundle> mediasInfo) {
        //Init variable
        ArrayList<MediaObject> mediaObjects = new ArrayList<MediaObject>();
        MediaDataSource dataSource = new MediaDataSource(context);

        //Loop for all MediaInfo
        for (Bundle media : mediasInfo) {
            //Get Media
            String nameMedia = media.getString(XMLParser.KEY_NAME);
            MediaObject getMedia = dataSource.getMedia(nameMedia);

            //If Media is not save in BDD
            if(getMedia == null){
                Log.d(TAG, context.getString(R.string.debug_key_file_not_exist));
                MediaObject mediaObject = dataSource.createMedia(setParameters(new MediaObject(), media));
                mediaObjects.add(mediaObject);
            }
            //Media is save in BDD
            else{
                Log.d(TAG, context.getString(R.string.debug_key_file_exist));

                //Check VersionCode
                int versionCode = Integer.decode(media.getString(XMLParser.KEY_VERSION_CODE));
                if(getMedia.getVersionCode() < versionCode){
                    //Update VersionCode
                    Log.d(TAG, context.getString(R.string.debug_key_file_update));
                    MediaObject mediaObject = dataSource.updateMedia(
                            getMedia,
                            media.getString(XMLParser.KEY_NAME),
                            media.getString(XMLParser.KEY_DOWNLOAD_PATH),
                            versionCode,
                            media.getString(XMLParser.KEY_TYPE),
                            MediaObject.KEY_INSTALLED,
                            MediaObject.KEY_UPDATED);
                    mediaObjects.add(mediaObject);
                }
                else{
                    mediaObjects.add(getMedia);
                }
            }
        }

        //Check for delete
        ArrayList<MediaObject> mediaObjectsBDD = dataSource.getAllMedia();
        ArrayList<MediaObject> toDelete = (ArrayList<MediaObject>) mediaObjectsBDD.clone();
        toDelete.removeAll(mediaObjects);

        if(toDelete.size() > 0){
           for(MediaObject mediaObjectDelete : toDelete){
               dataSource.deleteMedia(mediaObjectDelete);
               DownloadMedia.deleteFile(mediaObjectDelete);
           }
        }
    }

    /**
     * Delete file in disk
     * @param context Context
     * @param mediaObject MediaObject
     */
    public static void deleteFileInDisk(Context context, MediaObject mediaObject){
        //Delete File
        DownloadMedia.deleteFile(mediaObject);

        //Update in BDD
        MediaDataSource mediaDataSource = new MediaDataSource(context);
        mediaDataSource.updateMedia(
                mediaObject,
                mediaObject.getName(),
                mediaObject.getPath(),
                mediaObject.getVersionCode(),
                mediaObject.getType(),
                MediaObject.KEY_NOT_INSTALLED,
                MediaObject.KEY_NOT_UPDATED);
    }

    /**
     * Refresh all lists
     * @param context Context
     */
    public static void refreshList(Context context) {
        //Clear all lists
        mImagesList.clear();
        mTextsList.clear();
        mVideosList.clear();
        mAudiosList.clear();

        //Get all Medias
        MediaDataSource dataSource = new MediaDataSource(context);
        for(Config.MEDIA_TYPE type : Config.MEDIA_TYPE.values()){
            ArrayList<MediaObject> mediaObjects = dataSource.getAllMediaWithType(type);
            String currentType = type.getType();

            if(Config.MEDIA_TYPE.IMAGE.getType().equals(currentType)){
                mImagesList.addAll(mediaObjects);
            }
            else if(Config.MEDIA_TYPE.TEXT.getType().equals(currentType)){
                mTextsList.addAll(mediaObjects);
            }
            else if(Config.MEDIA_TYPE.VIDEO.getType().equals(currentType)){
                mVideosList.addAll(mediaObjects);
            }
            else if(Config.MEDIA_TYPE.AUDIO.getType().equals(currentType)){
                mAudiosList.addAll(mediaObjects);
            }
            else {
                Log.w(TAG, context.getString(R.string.error_unknown_type));
            }
        }
    }
}
