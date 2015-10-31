package com.lppierrejeremy.apps.filemanagement.DAO.DataSources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lppierrejeremy.apps.filemanagement.DAO.Helpers.CustomSQLiteHelper;
import com.lppierrejeremy.apps.filemanagement.DAO.MediaObject;
import com.lppierrejeremy.apps.filemanagement.Utilities.Config;

import java.util.ArrayList;

/**
 * MediaDataSource Class
 */
public class MediaDataSource {
    /*
    Constants
     */
    private static final String[] ALL_COLUMNS = {
            MediaObject.COLUMN_ID,
            MediaObject.COLUMN_NAME,
            MediaObject.COLUMN_PATH,
            MediaObject.COLUMN_TYPE,
            MediaObject.COLUMN_VERSION_CODE,
            MediaObject.COLUMN_INSTALLED,
            MediaObject.COLUMN_UPDATED
    };

    /*
    Fields
     */
    private SQLiteDatabase mDatabase;
    private final CustomSQLiteHelper mDbHelper;

    /*
    Methods
     */

    /**
     * Constructor
     * @param context Context
     */
    public MediaDataSource(Context context) {
        mDbHelper = CustomSQLiteHelper.getInstance(context);
    }

    /**
     * Open BDD
     * @throws SQLException
     */
    private void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    /**
     * Close BDD
     */
    private void close() {
        mDbHelper.close();
    }

    /**
     * Create new media
     * @param mediaObject MediaObject
     * @return Media
     */
    public MediaObject createMedia(MediaObject mediaObject) {
        return createMedia(
                mediaObject.getName(),
                mediaObject.getPath(),
                mediaObject.getVersionCode(),
                mediaObject.getType(),
                mediaObject.getInstalled(),
                mediaObject.getUpdated()
        );
    }

    /**
     * Create new media
     * @param name String
     * @param path String
     * @param versionCode int
     * @param type String
     * @param installed int
     * @param updated int
     * @return Media
     */
    public MediaObject createMedia(String name, String path, int versionCode, String type, int installed, int updated) {
        //Open BDD
        this.open();

        //Create values to insert
        ContentValues values = new ContentValues();
        values.put(MediaObject.COLUMN_NAME, name);
        values.put(MediaObject.COLUMN_VERSION_CODE, versionCode);
        values.put(MediaObject.COLUMN_PATH, path);
        values.put(MediaObject.COLUMN_TYPE, type);
        values.put(MediaObject.COLUMN_INSTALLED, installed);
        values.put(MediaObject.COLUMN_UPDATED, updated);

        //Insert into BDD and get id
        long insertId = mDatabase.insert(MediaObject.TABLE_MEDIA, null, values);

        //Query BDD
        Cursor cursor = mDatabase.query(MediaObject.TABLE_MEDIA,
                ALL_COLUMNS, MediaObject.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        //Get Media
        cursor.moveToFirst();
        MediaObject newMedia = cursorToMedia(cursor);

        //Close Cursor and BDD
        cursor.close();
        this.close();

        //Return Media
        return newMedia;
    }

    /**
     * Update specific media
     * @param media Media
     * @param name String
     * @param path String
     * @param versionCode int
     * @param type String
     * @param installed int
     * @param updated int
     * @return Media
     */
    public MediaObject updateMedia(MediaObject media, String name, String path, int versionCode, String type, int installed, int updated){
        //Open BDD
        this.open();

        //Create values to update
        ContentValues values = new ContentValues();
        values.put(MediaObject.COLUMN_NAME, name);
        values.put(MediaObject.COLUMN_PATH, path);
        values.put(MediaObject.COLUMN_VERSION_CODE, versionCode);
        values.put(MediaObject.COLUMN_TYPE, type);
        values.put(MediaObject.COLUMN_INSTALLED, installed);
        values.put(MediaObject.COLUMN_UPDATED, updated);

        //Update into BDD
        mDatabase.update(MediaObject.TABLE_MEDIA, values, MediaObject.COLUMN_ID + " = " + media.getId(), null);

        //Query BDD
        Cursor cursor = mDatabase.query(MediaObject.TABLE_MEDIA,
                ALL_COLUMNS, MediaObject.COLUMN_ID + " = " + media.getId(), null,
                null, null, null);

        //Get Media
        cursor.moveToFirst();
        MediaObject updateMedia = cursorToMedia(cursor);

        //Close Cursor and BDD
        cursor.close();
        this.close();

        //Return Media
        return updateMedia;
    }

    /**
     * Delete media
     * @param media Media
     */
    public void deleteMedia(MediaObject media) {
        //Open BDD
        this.open();

        //Get id and delete it
        long id = media.getId();
        mDatabase.delete(MediaObject.TABLE_MEDIA, MediaObject.COLUMN_ID + " = " + id, null);

        //Close BDD
        this.close();
    }

    /**
     * Get media with name
     * @param name String
     * @return Media
     */
    public MediaObject getMedia(String name){
        //Open BDD
        this.open();

        //Query BDD
        Cursor cursor = mDatabase.query(MediaObject.TABLE_MEDIA, ALL_COLUMNS,
                MediaObject.COLUMN_NAME + " = '" + name + "'", null, null, null, null);

        //Get Media
        cursor.moveToFirst();
        MediaObject media = cursorToMedia(cursor);

        //Close BDD
        this.close();

        //Return Media
        return media;
    }

    /**
     * Get all media
     * @return ArrayList<MediaObject>
     */
    public ArrayList<MediaObject> getAllMedia() {
        //Open BDD
        this.open();

        //Create empty list
        ArrayList<MediaObject> medias = new ArrayList<MediaObject>();

        //Query BDD
        Cursor cursor = mDatabase.query(MediaObject.TABLE_MEDIA,
                ALL_COLUMNS, null, null, null, null, null);

        //Get all media
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MediaObject media = cursorToMedia(cursor);
            medias.add(media);
            cursor.moveToNext();
        }

        //Close Cursor and BDD
        cursor.close();
        this.close();

        //Return medias
        return medias;
    }

    /**
     * Get all medias with specific type
     * @param type MEDIA_TYPE
     * @return ArrayList<MediaObject>
     */
    public ArrayList<MediaObject> getAllMediaWithType(Config.MEDIA_TYPE type) {
        //Open BDD
        this.open();

        //Create empty list
        ArrayList<MediaObject> medias = new ArrayList<MediaObject>();

        //Query BDD
        Cursor cursor = mDatabase.query(MediaObject.TABLE_MEDIA,
                ALL_COLUMNS, MediaObject.COLUMN_TYPE + " = '" + type.getType() + "'", null, null, null, null);

        //Get all medias
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MediaObject media = cursorToMedia(cursor);
            medias.add(media);
            cursor.moveToNext();
        }

        //Close Cursor and BDD
        cursor.close();
        this.close();

        //Return medias
        return medias;
    }

    /**
     * Transform cursor to Media
     * @param cursor Cursor
     * @return Media
     */
    private MediaObject cursorToMedia(Cursor cursor) {
        if(cursor == null || cursor.getCount() == 0)
            return null;
        else {
            MediaObject media = new MediaObject();
            media.setId(cursor.getLong(0));
            media.setName(cursor.getString(1));
            media.setPath(cursor.getString(2));
            media.setType(cursor.getString(3));
            media.setVersionCode(cursor.getInt(4));
            media.setInstalled(cursor.getInt(5));
            media.setUpdated(cursor.getInt(6));
            return media;
        }
    }
}