package com.lppierrejeremy.apps.filemanagement.Utilities;

import android.os.Environment;

import java.io.File;

public class Config {
    /*
    URLs
     */
    public static final String MEDIA_URL = "http://lionel.banand.free.fr/lp_iem/updaterLPIEM.php?serial=AAA&type=medias";
    public static final String REMOTE_URL = "http://lionel.banand.free.fr/lp_iem";

    /*
    Paths
     */
    public static final String MEDIA_PATH = Environment.getExternalStorageDirectory() + File.separator + "filemanagement" + File.separator;
    public static final String DATABASE_NAME =  Config.MEDIA_PATH + "database.db";

    /*
    Message Broadcast
     */
    public static final String START_BROADCAST_SYSTEM = "android.intent.action.BOOT_COMPLETED";
    public static final String START_BROADCAST = "com.lppierrejeremy.apps.filemanagement.START_BROADCAST";
    public static final String FINISH_BROADCAST = "com.lppierrejeremy.apps.filemanagement.FINISH_BROADCAST";

    /**
     * MEDIA_TYPE
     */
    public static enum MEDIA_TYPE{
        IMAGE("image"),
        TEXT("texte"),
        AUDIO("audio"),
        VIDEO("video");

        private String mType;
        private MEDIA_TYPE(String type){
            mType = type;
        }
        public String getType(){
            return mType;
        }
    }

}
