package com.mdl.androidapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mdl.utils.Config;
import com.mdl.utils.JSONParser;
import com.mdl.utils.Utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * DetailItemActivity class
 */

public class DetailItemActivity extends ActionBarActivity {

    int idDetail = 0;
    protected JSONObject entry;

    /**
     * onCreate
     * @param savedInstanceState bundle of instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        if(getIntent().getStringExtra(Config.KEY_ENTRY_ID) != null){
            idDetail =  Integer.parseInt(getIntent().getStringExtra(Config.KEY_ENTRY_ID));
        }else if(getIntent().getStringExtra(Config.KEY_ENTRY) != null){
            String entry = getIntent().getStringExtra(Config.KEY_ENTRY);
            try {
                JSONObject jsonObject = new JSONObject(entry);
                if(jsonObject.getString(Config.KEY_ENTRY_ID) != null){
                    idDetail =  Integer.parseInt(jsonObject.getString(Config.KEY_ENTRY_ID));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (idDetail == 0){
            finish();
        }else{
            new GetEntryTask(this, idDetail).execute();
        }

        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * onCreateOptionsMenu
     * @param menu current menu
     * @return super.onCreateOptionsMenu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * onOptionsItemsSelected
     * @param item current item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_edit:
                Intent i = new Intent(this, EditItemActivity.class);
                i.putExtra(Config.KEY_ENTRY, entry.toString());
                startActivity(i);
                break;
            case R.id.action_delete:
                Delete(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * GetEntryTask class
     */
    private class GetEntryTask extends AsyncTask<Void, Void, String> {
        private JSONParser jsonParser;
        private String response = "";
        private String error = "";
        private int id = 0;
        private Context context;

        /**
         * GetEntryTask
         * @param context current context
         * @param id id of entry
         */
        private GetEntryTask (Context context, int id){
            this.context = context;
            this.id = id;
            jsonParser = new JSONParser();
        }

        /**
         * doInBackground
         * @param params parameters
         * @return response
         */
        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> entryInfos = new ArrayList<NameValuePair>();
            entryInfos.add(new BasicNameValuePair(Config.KEY_ENTRY_ID, this.id+""));

            JSONObject json = jsonParser.makeHttpRequest(Config.URL_GET_ENTRY, "GET", entryInfos);

            try {
                int success = json.getInt(Config.KEY_SUCCESS);
                if(success == 1){
                    entry = json.getJSONObject(Config.KEY_ENTRY);
                    response = entry.getString(Config.KEY_ENTRY_NAME);
                    Log.d("Entry", entry.toString());
                }else{
                    error = json.getString(Config.KEY_MESSAGE);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            return response;
        }

        /**
         * onPostExecute
         * @param response response of doInBackground
         */
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if(error != ""){
                Toast.makeText(context, getResources().getString(R.string.error_detail_item)+error, Toast.LENGTH_SHORT).show();
            }else{
                getActionBar().setTitle(response);
                try {
                    print(entry);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Print entry
         * @param entry entry to print
         * @throws JSONException
         */
        private void print(JSONObject entry) throws JSONException {
            TextView tvName = (TextView) findViewById(R.id.tv_title_name);
            Utils.underLineText(tvName);
            TextView tvDetailName = (TextView) findViewById(R.id.tv_detail_title_name);
            tvDetailName.setText(entry.getString(Config.KEY_ENTRY_NAME));

            TextView tvDateStart = (TextView) findViewById(R.id.tv_title_start_date);
            Utils.underLineText(tvDateStart);
            TextView tvDetailDateStart = (TextView) findViewById(R.id.tv_detail_title_start_date);
            tvDetailDateStart.setText(Utils.formatDate(Long.valueOf(entry.getString(Config.KEY_ENTRY_START_DATE)).longValue(), true));

            TextView tvDateEnd = (TextView) findViewById(R.id.tv_title_end_date);
            Utils.underLineText(tvDateEnd);
            TextView tvDetailDateEnd = (TextView) findViewById(R.id.tv_detail_title_end_date);
            tvDetailDateEnd.setText(Utils.formatDate(Long.valueOf(entry.getString(Config.KEY_ENTRY_END_DATE)).longValue(), true));

            TextView tvCreateBy = (TextView) findViewById(R.id.tv_title_create_by);
            Utils.underLineText(tvCreateBy);
            TextView tvDetailCreateBy = (TextView) findViewById(R.id.tv_detail_title_create_by);
            tvDetailCreateBy.setText(entry.getString(Config.KEY_ENTRY_CREATE_BY));

            TextView tvRoom = (TextView) findViewById(R.id.tv_title_room);
            Utils.underLineText(tvRoom);
            TextView tvDetailRoom = (TextView) findViewById(R.id.tv_detail_title_room);
            tvDetailRoom.setText(entry.getString(Config.KEY_ENTRY_ROOM_NAME));

            findViewById(R.id.rl_load_detail_activity).setVisibility(View.GONE);
            findViewById(R.id.rl_detail_activity).setVisibility(View.VISIBLE);
        }
    }

    /**
     * Delete
     * @param context current context
     */
    private void Delete(Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle(getResources().getString(R.string.confirmation_title_delete));
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.confirmation_text_delete))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        new DeleteEntry().execute();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * DeleteEntry class
     */
    public class DeleteEntry extends AsyncTask<Void, Void, Void>{
        private ProgressDialog pd;
        private JSONParser jsonParser = new JSONParser();
        private String error = "";

        /**
         * onPreExecute
         */
        @Override
        protected void onPreExecute(){
            pd = new ProgressDialog(DetailItemActivity.this);
            pd.setMessage(getResources().getString(R.string.delete_text));
            pd.show();
        }

        /**
         * doInBackground
         * @param params parameters
         * @return null
         */
        @Override
        protected Void doInBackground(Void... params) {
            List<NameValuePair> paramsDelete = new ArrayList<NameValuePair>();
            try {
                paramsDelete.add(new BasicNameValuePair(Config.KEY_ENTRY_ID, entry.getString(Config.KEY_ENTRY_ID)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject json = jsonParser.makeHttpRequest(Config.URL_DELETE_ENTRY, "POST", paramsDelete);

            try {
                int success = json.getInt(Config.KEY_SUCCESS);
                if(success != 1){
                    error = json.getString(Config.KEY_MESSAGE);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        /**
         * onPostExecute
         * @param aVoid void
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(pd.isShowing()){
                pd.dismiss();
            }
            if(error != ""){
                Toast.makeText(DetailItemActivity.this, error, Toast.LENGTH_SHORT).show();
            }else{
                Intent i = new Intent(DetailItemActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }
    }
}
