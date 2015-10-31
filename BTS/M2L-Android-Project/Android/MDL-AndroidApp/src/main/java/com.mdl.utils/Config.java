package com.mdl.utils;

/**
 * Config class
 */
public final  class Config {

    //URL API
    public static final String URL_PROD = "http://mdlandroidapp.eu01.aws.af.cm/";
    public static final String URL_DEV = "http://localhost/mdl/android/";
    public static final String URL = URL_PROD;
    public static final String URL_LOGIN = URL + "get_user.php";
    public static final String URL_GET_ENTRIES = URL + "get_entries.php";
    public static final String URL_GET_ENTRY = URL + "get_entry.php";
    public static final String URL_SET_USER = URL + "set_user.php";
    public static final String URL_GET_ROOMS = URL + "get_rooms.php";
    public static final String URL_DELETE_ENTRY = URL + "delete_entry.php";
    public static final String URL_SET_ENTRY = URL + "set_entry.php";

    //Global Message
    public static final String KEY_SUCCESS = "success";
    public static final String KEY_MESSAGE = "message";

    //User KEY
    public static final String KEY_USER = "user";
    public static final String KEY_USER_ID = "id";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_PASSWORD = "password";
    public static final String KEY_USER_LEVEL = "level";
    public static final String KEY_USER_EMAIL = "email";

    //Entry KEY
    public static final String KEY_ENTRY = "entry";
    public static final String KEY_ENTRY_ID = "id";
    public static final String KEY_ENTRY_START_DATE = "start_time";
    public static final String KEY_ENTRY_END_DATE = "end_time";
    public static final String KEY_ENTRY_NAME = "name";
    public static final String KEY_ENTRY_CREATE_BY = "create_by";
    public static final String KEY_ENTRY_ROOM_ID = "room_id";
    public static final String KEY_ENTRY_ROOM_NAME = "room_name";

    //Rooms KEY
    public static final String KEY_ROOM = "rooms";
    public static final String KEY_ROOM_ID = "id";
    public static final String KEY_ROOM_NAME = "name";

    //Parameter KEY
    public static final String KEY_OLD_PASSWORD = "old_password";
    public static final String KEY_NEW_PASSWORD = "new_password";
    public static final String KEY_VALUE = "value";
    public static final String KEY_STATUS = "status";
    public static final String KEY_STATUS_ADD = "ADD";
    public static final String KEY_STATUS_UPDATE = "UPDATE";
    public static final String KEY_LIMIT = "limit";
}
