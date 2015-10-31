package com.lpiem.apps.loupelec.dao.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lpiem.apps.loupelec.dao.entities.Keyword;
import com.lpiem.apps.loupelec.dao.helpers.CustomSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * KeywordDataSource Class
 */
public class KeywordDataSource {

    /*
    Fields
     */
    private SQLiteDatabase database;
    private final CustomSQLiteHelper dbHelper;
    private final String[] allColumns = {
            Keyword.COLUMN_ID,
            Keyword.COLUMN_NAME
    };

    /*
    Methods
     */

    /**
     * Constructor
     * @param context Context
     */
    public KeywordDataSource(Context context) {
        dbHelper = CustomSQLiteHelper.getInstance(context);
    }

    /**
     * Open Database
     * @throws SQLException
     */
    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Close Database
     */
    private void close() {
        dbHelper.close();
    }

    /**
     * Create new Keyword with a name
     * @param name String
     * @return Keyword
     */
    public Keyword createKeyword(String name) {
        //Open database
        this.open();

        //Create values to insert
        ContentValues values = new ContentValues();
        values.put(Keyword.COLUMN_NAME, name);

        //Insert in BDD and get id
        long insertId = database.insert(Keyword.TABLE_KEYWORD, null, values);

        //Query BDD
        Cursor cursor = database.query(Keyword.TABLE_KEYWORD,
                allColumns, Keyword.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        //Get Keyword
        cursor.moveToFirst();
        Keyword newKeyword = cursorToKeyword(cursor);

        //Close Cusor & BDD
        cursor.close();
        this.close();

        //Return keyword
        return newKeyword;
    }

    /**
     * Delete keyword
     * @param keyword Keyword
     */
    @SuppressWarnings("unused")
    public void deleteKeyword(Keyword keyword) {
        //Open BDD
        this.open();

        //Get id and delete it
        long id = keyword.getId();
        database.delete(Keyword.TABLE_KEYWORD, Keyword.COLUMN_ID + " = " + id, null);

        //Close BDD
        this.close();
    }

    /**
     * Get keyword with id
     * @param id long
     * @return Keyword
     */
    public Keyword getKeyword(long id){
        //Check if id != 0
        if(id == 0)
            return null;

        //Open BDD
        this.open();

        //Query BDD
        Cursor cursor = database.query(Keyword.TABLE_KEYWORD, allColumns,
                Keyword.COLUMN_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();

        //Get keyword
        Keyword keyword = cursorToKeyword(cursor);

        //Close BDD
        this.close();

        //Return Keyword
        return keyword;
    }

    /**
     * Get all keywords
     * @return List<Keyword>
     */
    public List<Keyword> getAllKeywords() {
        //Open BDD
        this.open();

        //Create empty list
        List<Keyword> keywords = new ArrayList<Keyword>();

        //Query BDD
        Cursor cursor = database.query(Keyword.TABLE_KEYWORD,
                allColumns, null, null, null, null, null);

        //Get all keywords
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Keyword keyword = cursorToKeyword(cursor);
            keywords.add(keyword);
            cursor.moveToNext();
        }

        //Close Cursor and BDD
        cursor.close();
        this.close();

        //Return keywords
        return keywords;
    }

    /**
     * Transform cursor to keyword
     * @param cursor Cursor
     * @return Keyword
     */
    private Keyword cursorToKeyword(Cursor cursor) {
        Keyword keyword = new Keyword();
        keyword.setId(cursor.getLong(0));
        keyword.setName(cursor.getString(1));
        return keyword;
    }

}
