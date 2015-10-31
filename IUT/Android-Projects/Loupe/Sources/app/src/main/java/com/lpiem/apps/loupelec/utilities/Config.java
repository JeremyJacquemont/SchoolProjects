package com.lpiem.apps.loupelec.utilities;

/**
 * Config Class
 */
public class Config {
    /*
    Global
     */
    public static final String DEFAULT_BACKGROUND_ACTIVITY = "#000000";
    public static final String DEFAULT_BACKGROUND_BUTTON = "#000000";
    public static final String DEFAULT_COLOR_FONT = "#ffffff";
    public static final int DEFAULT_TEXT_SIZE = 40;
    public static final int MAX_TEXT_SIZE = 60;
    public static final int MIN_TEXT_SIZE = 25;
    public static final String TXT_TYPE = "text/plain";
    public static final String IMG_TYPE = "image/jpeg";
    public static final String DEFAULT_COLOR_BUTTON ="light";

    /*
    List Configuration
     */
    public static final int SIZE_PREVIEW = 300;
    public static final int TRANSITION_DURATION = 250;
    public static final String NAME_PLACEHOLDER_TXT_FILE = "txt_thumbnail.jpg";
    public static final String TXT_EXTENSION = "txt";

    /*
    Image Configuration
     */
    public static final String START_NAME_IMG = "IMG_";
    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final String EXTENSION_IMG = ".jpg";

    /*
    OCR Configuration
     */
    public static final String START_NAME_OCR = "OCR_";
    public static final String EXTENSION_OCR = ".txt";
    public static final String LANG = "fra";
    public static final String TESSDATA = "tessdata";

    /*
    Camera Configuration
     */
    public static final int CAMERA_MIN_ZOOM = 0;
    public static final int CAMERA_INCREMENT_ZOOM = 5;

    public static enum EventZoom{
        ZOOM_IN,
        ZOOM_OUT
    }
}
