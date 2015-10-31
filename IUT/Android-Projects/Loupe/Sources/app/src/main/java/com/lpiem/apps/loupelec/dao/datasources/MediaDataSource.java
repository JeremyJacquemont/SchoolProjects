package com.lpiem.apps.loupelec.dao.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lpiem.apps.loupelec.dao.entities.Keyword;
import com.lpiem.apps.loupelec.dao.entities.Media;
import com.lpiem.apps.loupelec.dao.helpers.CustomSQLiteHelper;
import com.lpiem.apps.loupelec.utilities.Utilities;

import java.util.ArrayList;

/**
 * MediaDataSource Class
 */
public class MediaDataSource {
    /*
    Fields
     */
    private final Context mContext;
    private SQLiteDatabase database;
    private final CustomSQLiteHelper dbHelper;
    private final String[] allColumns = {
            Media.COLUMN_ID,
            Media.COLUMN_NAME,
            Media.COLUMN_PATH,
            Media.COLUMN_TYPE,
            Media.COLUMN_ID_KEYWORD
    };

    /*
    Methods
     */

    /**
     * Constructor
     * @param context Context
     */
    public MediaDataSource(Context context) {
        mContext = context;
        dbHelper = CustomSQLiteHelper.getInstance(context);
    }

    /**
     * Open BDD
     * @throws SQLException
     */
    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Close BDD
     */
    private void close() {
        dbHelper.close();
    }

    /**
     * Create new media
     * @param name String
     * @param path String
     * @param keyword Keyword
     * @return Media
     */
    public Media createMedia(String name, String path, Keyword keyword) {
        //Open BDD
        this.open();

        //Create values to insert
        ContentValues values = new ContentValues();
        values.put(Media.COLUMN_NAME, name);
        values.put(Media.COLUMN_PATH, path);
        values.put(Media.COLUMN_TYPE, Utilities.getExtension(path));
        if(keyword != null)
            values.put(Media.COLUMN_ID_KEYWORD, keyword.getId());

        //Insert into BDD and get id
        long insertId = database.insert(Media.TABLE_MEDIA, null, values);

        //Query BDD
        Cursor cursor = database.query(Media.TABLE_MEDIA,
                allColumns, Media.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        //Get Media
        cursor.moveToFirst();
        Media newMedia = cursorToMedia(cursor);

        //Close Cursor and BDD
        cursor.close();
        this.close();

        //Return Media
        return newMedia;
    }

    /**
     * Update keyword for specific media
     * @param media Media
     * @param keyword Keyword
     * @return Media
     */
    public Media updateKeywordForMedia(Media media, Keyword keyword){
        //Open BDD
        this.open();

        //Create values to update
        ContentValues values = new ContentValues();
        values.put(Media.COLUMN_ID_KEYWORD, keyword.getId());

        //Update into BDD
        database.update(Media.TABLE_MEDIA, values, Media.COLUMN_ID + " = " + media.getId(), null);

        //Query BDD
        Cursor cursor = database.query(Media.TABLE_MEDIA,
                allColumns, Media.COLUMN_ID + " = " + media.getId(), null,
                null, null, null);

        //Get Media
        cursor.moveToFirst();
        Media updateMedia = cursorToMedia(cursor);

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
    public void deleteMedia(Media media) {
        //Open BDD
        this.open();

        //Get id and delete it
        long id = media.getId();
        database.delete(Media.TABLE_MEDIA, Media.COLUMN_ID + " = " + id, null);

        //Close BDD
        this.close();
    }

    /**
     * Get media with id
     * @param id long
     * @return Media
     */
    public Media getMedia(long id){
        //Open BDD
        this.open();

        //Query BDD
        Cursor cursor = database.query(Media.TABLE_MEDIA, allColumns,
                Media.COLUMN_ID + " = " + id, null, null, null, null);

        //Get Media
        cursor.moveToFirst();
        Media media = cursorToMedia(cursor);

        //Close BDD
        this.close();

        //Return Media
        return media;
    }

    /**
     * Get all media
     * @return ArrayList<Media>
     */
    public ArrayList<Media> getAllMedia() {
        //Open BDD
        this.open();

        //Create empty list
        ArrayList<Media> medias = new ArrayList<Media>();

        //Query BDD
        Cursor cursor = database.query(Media.TABLE_MEDIA,
                allColumns, null, null, null, null, Media.COLUMN_ID + " DESC");

        //Get all media
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Media media = cursorToMedia(cursor);
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
     * Get all medias with specific keyword
     * @param keyword Keyword
     * @return ArrayList<Media>
     */
    public ArrayList<Media> getAllMediaWithKeyword(Keyword keyword) {
        //Open BDD
        this.open();

        //Create empty list
        ArrayList<Media> medias = new ArrayList<Media>();

        //Query BDD
        Cursor cursor = database.query(Media.TABLE_MEDIA,
                allColumns, Media.COLUMN_ID_KEYWORD + " = " + keyword.getId(), null, null, null, Media.COLUMN_ID + " DESC");

        //Get all medias
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Media media = cursorToMedia(cursor);
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
    private Media cursorToMedia(Cursor cursor) {
        Media media = new Media();
        media.setId(cursor.getLong(0));
        media.setName(cursor.getString(1));
        media.setPath(cursor.getString(2));
        media.setType(cursor.getString(3));
        media.setKeyword(getKeyword(cursor.getLong(4)));
        return media;
    }

    /**
     * Get keyword
     * @param id long
     * @return Keyword
     */
    private Keyword getKeyword(long id){
        KeywordDataSource keywordDataSource = new KeywordDataSource(mContext);
        return keywordDataSource.getKeyword(id);
    }

}
