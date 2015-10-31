package com.lpiem.apps.loupelec.dao.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lpiem.apps.loupelec.dao.entities.Keyword;
import com.lpiem.apps.loupelec.dao.entities.Media;
import com.lpiem.apps.loupelec.utilities.Utilities;

/**
 * CustomSQLiteHelper Class
 */
public class CustomSQLiteHelper extends SQLiteOpenHelper {

    /*
    Constants
     */
    private static final String TAG = "CustomSQLiteHelper";
    private static final int DATABASE_VERSION = 1;

    /*
    Fields
     */
    private static String DATABASE_NAME;
    private static CustomSQLiteHelper sInstance;

    /*
    Methods
     */

    /**
     * Get Instance
     * @param context Context
     * @return CustomSQLiteHelper
     */
    public static CustomSQLiteHelper getInstance(Context context) {
        DATABASE_NAME =  Utilities.getDataDir(context) + "database.db";

        if (sInstance == null) {
            sInstance = new CustomSQLiteHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor
     * @param context Context
     */
    private CustomSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * onCreate Method (Override)
     * @param sqLiteDatabase SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Media Table
        sqLiteDatabase.execSQL(Media.DATABASE_CREATE);

        //Create Keyword Table
        sqLiteDatabase.execSQL(Keyword.DATABASE_CREATE);
    }

    /**
     * onUpgrade Method (Override)
     * @param sqLiteDatabase SQLiteDatabase
     * @param oldVersion int
     * @param newVersion int
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //Log event
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);

        //Delete Media Table
        sqLiteDatabase.execSQL(Media.DATABASE_DELETE);

        //Delete Keyword Table
        sqLiteDatabase.execSQL(Keyword.DATABASE_DELETE);

        //Create Tables
        onCreate(sqLiteDatabase);
    }
}
