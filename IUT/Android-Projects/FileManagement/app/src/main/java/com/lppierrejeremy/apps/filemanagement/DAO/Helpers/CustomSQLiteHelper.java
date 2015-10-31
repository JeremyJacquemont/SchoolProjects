package com.lppierrejeremy.apps.filemanagement.DAO.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lppierrejeremy.apps.filemanagement.DAO.MediaObject;
import com.lppierrejeremy.apps.filemanagement.R;
import com.lppierrejeremy.apps.filemanagement.Utilities.Config;

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
    private static CustomSQLiteHelper sInstance;

    private Context mContext;

    /*
    Methods
     */

    /**
     * Get Instance
     * @param context Context
     * @return CustomSQLiteHelper
     */
    public static CustomSQLiteHelper getInstance(Context context) {
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
        super(context, Config.DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    /**
     * onCreate Method (Override)
     * @param sqLiteDatabase SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Media Table
        sqLiteDatabase.execSQL(MediaObject.DATABASE_CREATE);
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
        String log = mContext.getString(R.string.debug_key_update_bdd);
        log = log.replace(mContext.getString(R.string.debug_key_bdd_old), String.valueOf(oldVersion));
        log = log.replace(mContext.getString(R.string.debug_key_bdd_new), String.valueOf(newVersion));
        Log.w(TAG, log);

        //Delete Media Table
        sqLiteDatabase.execSQL(MediaObject.DATABASE_DELETE);

        //Create Tables
        onCreate(sqLiteDatabase);
    }
}
