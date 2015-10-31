package com.mdl.androidapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mdl.utils.Config;
import com.mdl.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * LoginActivity class
 */

public class LoginActivity extends Activity {

    private Button mButtonLogin;
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;

    private String username;
    private String password;

    private ProgressDialog pDialog;
    private JSONParser jsonParser;

    private SharedPreferences pref;

    /**
     * onCreate
     * @param savedInstanceState bundle of instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isOnline()){
            setContentView(R.layout.activity_login);

            mEditTextUsername = (EditText) findViewById(R.id.et_username_login);
            mEditTextPassword = (EditText) findViewById(R.id.et_password_login);

            mButtonLogin = (Button) findViewById(R.id.btn_connect_login);
            mButtonLogin.setOnClickListener(new clickHandler());

            pDialog = null;
            jsonParser = new JSONParser();

            pref = getSharedPreferences("com.mdl.androidapp", Context.MODE_PRIVATE);
            checkIsLogin();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.error_internet_title))
                    .setMessage(getResources().getString(R.string.error_internet_text))
                    .setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            pref = getSharedPreferences("com.mdl.androidapp", Context.MODE_PRIVATE);
            pref.edit().clear().commit();

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    /**
     * CheckIsLogin
     */
    private void checkIsLogin(){
        if(pref.contains(Config.KEY_USER_NAME)){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }

    /**
     * Check is Online
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    /**
     * onPause
     */
    @Override
    public void onPause()
    {
        super.onPause();
        if(pDialog != null)
            pDialog.dismiss();
    }

    /**
     * ClickHandler class
     */
    private class clickHandler implements View.OnClickListener{

        /**
         * onClick
         * @param v current view
         */
        @Override
        public void onClick(View v) {
            username = mEditTextUsername.getText().toString();
            password = mEditTextPassword.getText().toString();

            if(username.contentEquals("") || password.contentEquals(""))
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.error_login), Toast.LENGTH_SHORT).show();
            else {
                new Login().execute();
            }
        }
    }

    /**
     * Login class
     */
    private class Login extends AsyncTask<Void, Void, String>{

        private String error = "";

        /**
         * onPreExecute
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage(getResources().getString(R.string.login_loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * doInBackground
         * @param args parameters
         * @return response
         */
        @Override
        protected String doInBackground(Void... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Config.KEY_USER_NAME, username));
            params.add(new BasicNameValuePair(Config.KEY_USER_PASSWORD, password));

            JSONObject json = jsonParser.makeHttpRequest(Config.URL_LOGIN, "GET", params);

            try {
                int success = json.getInt(Config.KEY_SUCCESS);
                if(success == 1){
                    JSONArray userObj = json.getJSONArray(Config.KEY_USER);
                    JSONObject user = userObj.getJSONObject(0);

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Config.KEY_USER_ID, user.getString(Config.KEY_USER_ID));
                    editor.putString(Config.KEY_USER_NAME, user.getString(Config.KEY_USER_NAME));
                    editor.putString(Config.KEY_USER_EMAIL, user.getString(Config.KEY_USER_EMAIL));
                    editor.putString(Config.KEY_USER_LEVEL, user.getString(Config.KEY_USER_LEVEL));
                    editor.commit();
                }else{
                    error = json.getString(Config.KEY_MESSAGE);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        /**
         * onPostExecute
         * @param result result of doInBackground
         */
        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            if(!error.equals("")){
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
            }else{
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        }
    }
}
