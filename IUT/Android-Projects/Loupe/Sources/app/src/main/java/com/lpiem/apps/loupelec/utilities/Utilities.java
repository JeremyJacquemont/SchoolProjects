package com.lpiem.apps.loupelec.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import com.lpiem.apps.loupelec.utilities.colorPicker.ColorPickerPreference;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Utilities Class
 */
public class Utilities {
    /*
    Global
     */

    /**
     * SetUp Window
     * @param activity Activity
     */
    public static void setUpWindow(Activity activity){
        //Remove TitleBar
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Set Window to FullScreen
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /*
    Files & Directories
     */

    /**
     * Get extension with path
     * @param path String
     * @return String
     */
    public static String getExtension(String path){
        int dot = path.lastIndexOf(".");
        return path.substring(dot + 1);
    }

    /**
     * Get Data directory for Application
     * @param context Context
     * @return String
     */
    public static String getDataDir(Context context){
        return context.getApplicationInfo().dataDir + File.separator;
    }

    /**
     * Get Files directory inside Data directory for Application
     * @param context Context
     * @return String
     */
    public static String getFilesDir(Context context){
        return context.getFilesDir().getAbsolutePath() + File.separator;
    }

    /**
     * Get TessData directory inside Data directory for Application
     * @param context Context
     * @return String
     */
    public static String getTessDataDir(Context context){
        return getDataDir(context) + Config.TESSDATA + File.separator;
    }

    /**
     * Extract text inside File
     * @param file File
     * @return String
     */
    public static String getTextInsideFile(File file){
        //Create StringBuilder and Scanner
        StringBuilder builder = new StringBuilder();
        Scanner scanner = null;

        try {
            //Open scanner
            scanner = new Scanner(file);

            //Read file
            while(scanner.hasNextLine()){
                builder.append(scanner.nextLine());
                builder.append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Close scanner
        finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        //Return extract text
        return builder.toString();
    }

    /*
    Preferences
     */

    /**
     * Get a string preference
     * @param context Context
     * @param key String
     * @param defaultValue String
     * @return String
     */
    public static String getStringPreference(Context context, String key, String defaultValue){
        return getSharedPreferences(context).getString(key, defaultValue);
    }

    /**
     * Get an int preference
     * @param context Context
     * @param key String
     * @param defaultValue int
     * @return int
     */
    public static int getIntPreference(Context context, String key, int defaultValue){
        return getSharedPreferences(context).getInt(key, defaultValue);
    }

    /**
     * Get a boolean preference
     * @param context Context
     * @param key String
     * @param defaultValue boolean
     * @return boolean
     */
    public static boolean getBooleanPreference(Context context, String key, boolean defaultValue){
        return getSharedPreferences(context).getBoolean(key, defaultValue);
    }

    /**
     * Get SharedPreferences
     * @param context Context
     * @return SharedPreferences
     */
    private static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set enable with condition many preferences
     * @param condition CheckBoxPreference
     * @param listPreference Preference...
     */
    public static void setEnablePreference(CheckBoxPreference condition, Preference... listPreference){
        for (Preference currentPreference : listPreference){
            currentPreference.setEnabled(condition.isChecked());
        }
    }

    /**
     * Get color (int value ) for set
     * @param colorValue int
     * @param condition int
     * @param defaultValueColor String
     * @return int
     */
    public static int getColor(int colorValue, int condition, String defaultValueColor){
        String colorBackground;
        if (colorValue != condition) {
            colorBackground = ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(colorValue)));
        } else {
            colorBackground = defaultValueColor;
        }

        return Color.parseColor(colorBackground);
    }
}
