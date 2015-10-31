package com.mdl.androidapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mdl.utils.Config;
import com.mdl.utils.JSONParser;
import com.mdl.utils.RoomItem;
import com.mdl.utils.Utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * EditItemActivity class
 */

public class EditItemActivity extends ActionBarActivity {

    private String timestampStart;
    private String timestampEnd;
    private Context context;
    private JSONObject entry;
    private Button btnStart;
    private Button btnEnd;
    private Spinner spinnerRooms;
    private TextView tvCreateBy;
    private EditText etName;
    protected static ArrayList<RoomItem> rooms;


    /**
     * onCreate
     * @param savedInstanceState bundle of instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout_item);

        if(getIntent().getStringExtra(Config.KEY_ENTRY) != ""){
            try {
                entry = new JSONObject(getIntent().getStringExtra(Config.KEY_ENTRY));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            finish();
        }

        context = this;
        rooms = new ArrayList<RoomItem>();

        etName = (EditText) findViewById(R.id.et_name);
        try {
            etName.setText(entry.getString(Config.KEY_ENTRY_NAME));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvCreateBy = (TextView) findViewById(R.id.tv_edit_create_by);
        try {
            tvCreateBy.setText(entry.getString(Config.KEY_ENTRY_CREATE_BY));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            timestampStart = Utils.formatDate(new Timestamp(Long.parseLong(entry.getString(Config.KEY_ENTRY_START_DATE))).getTime(), true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            timestampEnd = Utils.formatDate(new Timestamp(Long.parseLong(entry.getString(Config.KEY_ENTRY_END_DATE))).getTime(), true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnStart = (Button) findViewById(R.id.btn_edit_start_date);
        btnStart.setText(timestampStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = Utils.convertToDateWithString(btnStart.getText().toString());
                Utils.createDateTimeDialog(btnStart, context, date);
            }
        });

        btnEnd = (Button) findViewById(R.id.btn_edit_end_date);
        btnEnd.setText(timestampEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = Utils.convertToDateWithString(btnEnd.getText().toString());
                Utils.createDateTimeDialog(btnEnd, context, date);
            }
        });

        spinnerRooms = (Spinner) findViewById(R.id.sp_edit);

        new GetRooms(this, findViewById(R.id.sp_edit)).execute();

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(getResources().getString(R.string.title_edition_activity));
    }

    /**
     * onCreateOptionsMenu
     * @param menu current menu
     * @return super.onCreateOptionsMenu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
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
            case android.R.id.home:
                Intent i = new Intent(this, DetailItemActivity.class);
                i.putExtra(Config.KEY_ENTRY, getIntent().getStringExtra(Config.KEY_ENTRY));
                NavUtils.navigateUpTo(this, i);
                return true;
            case R.id.action_save:
                try {
                    new SaveEntry(
                            this,
                            entry.getInt(Config.KEY_ENTRY_ID),
                            Utils.convertToDateWithStrinWithoutTimezone(btnStart.getText().toString()).getTime()/1000,
                            Utils.convertToDateWithStrinWithoutTimezone(btnEnd.getText().toString()).getTime()/1000,
                            rooms.get(spinnerRooms.getSelectedItemPosition()).getId(),
                            tvCreateBy.getText().toString(),
                            etName.getText().toString()).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.action_cancel:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * GetRooms class
     */
    public final static class GetRooms extends AsyncTask<Void, Void, Void> {
        private JSONParser jsonParser;
        private String error = "";
        private Context context;
        private Spinner spinner;

        /**
         * GetRooms
         * @param context current context
         * @param spinnerView current spinner
         */
        public GetRooms(Context context, View spinnerView){
            this.context = context;
            this.jsonParser = new JSONParser();
            this.spinner = (Spinner) spinnerView;
        }

        /**
         * doInBackground
         * @param params parameters
         * @return null
         */
        @Override
        protected Void doInBackground(Void... params) {

            List<NameValuePair> paramsRooms = new ArrayList<NameValuePair>();
            JSONObject json = jsonParser.makeHttpRequest(Config.URL_GET_ROOMS, "GET", paramsRooms);

            try {
                int success = json.getInt(Config.KEY_SUCCESS);
                if(success == 1){
                    JSONArray roomsObject = json.getJSONArray(Config.KEY_ROOM);

                    for (int i = 0; i < roomsObject.length(); i++) {
                        JSONObject object = roomsObject.getJSONObject(i);
                        rooms.add(new RoomItem(object.getInt(Config.KEY_ROOM_ID), object.getString(Config.KEY_ROOM_NAME)));
                    }

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
         * @param aVoid void
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(error != ""){
                Toast.makeText(context, context.getResources().getString(R.string.error_rooms)+error, Toast.LENGTH_SHORT).show();
            }else{
                List<String> list = new ArrayList<String>();
                for (RoomItem room : rooms) {
                    list.add(room.getName());
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                        (context, android.R.layout.simple_spinner_item,list);

                dataAdapter.setDropDownViewResource
                        (android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(dataAdapter);
            }
        }
    }

    /**
     * SaveEntry class
     */
    public class SaveEntry extends AsyncTask<Void, Void, Void>{

        private Context context;
        private long start_time;
        private long end_time;
        private int room_id;
        private String create_by;
        private String name;
        private int id;

        private ProgressDialog pd;
        private JSONParser jsonParser = new JSONParser();
        private String error = "";

        /**
         * SaveEntry
         * @param context current context
         * @param id id of entry
         * @param start_time time to start
         * @param end_time time to end
         * @param room_id id of room
         * @param create_by name of creator
         * @param name name of entry
         */
        public SaveEntry(Context context, int id, long start_time, long end_time, int room_id, String create_by, String name){
            this.context = context;
            this.id = id;
            this.start_time = start_time;
            this.end_time = end_time;
            this.room_id = room_id;
            this.create_by = create_by;
            this.name = name;
        }

        /**
         * onPreExecute
         */
        @Override
        protected void onPreExecute(){
            pd = new ProgressDialog(EditItemActivity.this);
            pd.setMessage(context.getResources().getString(R.string.update_text));
            pd.show();
        }

        /**
         * doInBackground
         * @param params parameters
         * @return null
         */
        @Override
        protected Void doInBackground(Void... params) {
            List<NameValuePair> paramsSave = new ArrayList<NameValuePair>();
            paramsSave.add(new BasicNameValuePair(Config.KEY_STATUS, Config.KEY_STATUS_UPDATE));
            paramsSave.add(new BasicNameValuePair(Config.KEY_ENTRY_ID, id+""));
            paramsSave.add(new BasicNameValuePair(Config.KEY_ENTRY_START_DATE, start_time + ""));
            paramsSave.add(new BasicNameValuePair(Config.KEY_ENTRY_END_DATE, end_time + ""));
            paramsSave.add(new BasicNameValuePair(Config.KEY_ENTRY_ROOM_ID, room_id + ""));
            paramsSave.add(new BasicNameValuePair(Config.KEY_ENTRY_CREATE_BY, create_by + ""));
            paramsSave.add(new BasicNameValuePair(Config.KEY_ENTRY_NAME, name + ""));

            JSONObject json = jsonParser.makeHttpRequest(Config.URL_SET_ENTRY, "POST", paramsSave);

            try {
                int success = json.getInt(Config.KEY_SUCCESS);
                if(success != 1){
                    error = json.getString(Config.KEY_MESSAGE);
                }else{
                    id = json.getInt(Config.KEY_ENTRY_ID);
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
                Toast.makeText(EditItemActivity.this, error, Toast.LENGTH_SHORT).show();
            }else{
                Intent i = new Intent(EditItemActivity.this, DetailItemActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Config.KEY_ENTRY_ID, id+"");
                startActivity(i);
            }
        }
    }
}
