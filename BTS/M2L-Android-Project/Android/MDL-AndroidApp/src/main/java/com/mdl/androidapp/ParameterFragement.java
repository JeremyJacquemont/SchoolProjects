package com.mdl.androidapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.mdl.utils.Config;
import com.mdl.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * ParameterFragement class
 */

public class ParameterFragement extends PreferenceFragment {

    private static final boolean ALWAYS_SIMPLE_PREFS = false;
    private SharedPreferences pref;
    protected static String id = "";
    protected static Context ctx;
    private static SharedPreferences sp;

    /**
     * onCreate
     * @param savedInstanceState bundle of instance
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this.getActivity();
        sp = ctx.getSharedPreferences("com.mdl.androidapp", Context.MODE_PRIVATE);
        if(sp != null){
            id = sp.getString(Config.KEY_USER_ID, "");
        }

        setupSimplePreferencesScreen();
    }

    /**
     * setupSimplePreferencesScreen
     */
    private void setupSimplePreferencesScreen() {
        if (!isSimplePreferences(getActivity())) {
            return;
        }

        // Add fake for Header
        addPreferencesFromResource(R.xml.pref_fake);

        // Add 'User' preferences, and a corresponding header.
        PreferenceCategory fakeHeader = new PreferenceCategory(getActivity());
        fakeHeader = new PreferenceCategory(getActivity());
        fakeHeader.setTitle(R.string.pref_header_user);
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.pref_user);

        EditTextPreference etpName =  (EditTextPreference) findPreference("name_text");
        EditTextPreference etpMail =  (EditTextPreference) findPreference("mail_text");
        pref = getActivity().getSharedPreferences("com.mdl.androidapp", Context.MODE_PRIVATE);
        if(pref != null){
            etpName.setText(pref.getString(Config.KEY_USER_NAME, ""));
            etpMail.setText(pref.getString(Config.KEY_USER_EMAIL, ""));
        }

        // Add 'General' preferences, and a corresponding header.
        fakeHeader = new PreferenceCategory(getActivity());
        fakeHeader.setTitle(R.string.pref_header_general);
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.pref_general);

        // Bind the summaries of EditText/List/Dialog/Ringtone preferences to
        // their values. When their values change, their summaries are updated
        // to reflect the new value, per the Android Design guidelines.
        bindPreferenceSummaryToValue(findPreference("name_text"));
        bindPreferenceSummaryToValue(findPreference("mail_text"));
        bindPreferenceSummaryToValue(findPreference("about_text"));
        bindPreferenceSummaryToValue(findPreference("version_text"));
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    private static boolean isSimplePreferences(Context context) {
        return ALWAYS_SIMPLE_PREFS
                || Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
                || !isXLargeTablet(context);
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static boolean firstStart = true;
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            String stringKey = preference.getKey();
            if(!firstStart){
                if(stringKey.equals("name_text")){
                    try{
                        AsyncTask task = new SetUserTask(id, stringValue, null, null, null).execute();
                        preference.setSummary(task.get().toString());
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(Config.KEY_USER_NAME, task.get().toString());
                        editor.commit();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }else if(stringKey.equals("mail_text")){
                    try{
                        AsyncTask task = new SetUserTask(id, null, stringValue, null, null).execute();
                        preference.setSummary(task.get().toString());
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(Config.KEY_USER_EMAIL, task.get().toString());
                        editor.commit();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }else{
                firstStart = true;
                preference.setSummary(stringValue);
            }

            return true;
        }
    };

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * SetUserTask class
     */
    public static class SetUserTask extends AsyncTask<Void, String, String> {

        private JSONParser jsonParser;
        private String error = "";
        private String successMsg = "";
        private String value = "";
        private String id = "";
        private String name = "";
        private String email = "";
        private String oldPassword = "";
        private String newPassword = "";

        /**
         * SetUserTask
         * @param id id of user
         * @param name name of user
         * @param email email of user
         * @param oldPassword oldPassword of user
         * @param newPassword newPassword of user
         */
        public SetUserTask (String id, String name, String email, String oldPassword, String newPassword){
            this.id = id;
            this.name = name;
            this.email = email;
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
            jsonParser = new JSONParser();
        }

        /**
         * doInBackground
         * @param params parameters
         * @return response
         */
        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> paramsSet = new ArrayList<NameValuePair>();
            paramsSet.add(new BasicNameValuePair(Config.KEY_USER_ID, this.id));
            paramsSet.add(new BasicNameValuePair(Config.KEY_USER_NAME, this.name));
            paramsSet.add(new BasicNameValuePair(Config.KEY_USER_EMAIL, this.email));
            paramsSet.add(new BasicNameValuePair(Config.KEY_OLD_PASSWORD, this.oldPassword));
            paramsSet.add(new BasicNameValuePair(Config.KEY_NEW_PASSWORD, this.newPassword));

            JSONObject json = jsonParser.makeHttpRequest(Config.URL_SET_USER, "POST", paramsSet);

            try {
                int success = json.getInt(Config.KEY_SUCCESS);
                if(success == 1){
                    successMsg = json.getString(Config.KEY_MESSAGE);
                    value = json.getString(Config.KEY_VALUE);
                }else{
                    error = json.getString(Config.KEY_MESSAGE);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            return value;
        }

        /**
         * onPostExecute
         * @param response response of doInBackground
         */
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if(!error.equals("")){
                Toast.makeText(ctx, ctx.getResources().getString(R.string.error_parameter)+error, Toast.LENGTH_SHORT).show();
            }else if(!successMsg.equals("")){
                Toast.makeText(ctx, "Parameter - Enregistrement éffectué", Toast.LENGTH_SHORT).show();
            }
        }
    }

}