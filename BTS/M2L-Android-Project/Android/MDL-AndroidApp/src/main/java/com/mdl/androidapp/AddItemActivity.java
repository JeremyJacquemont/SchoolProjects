package com.mdl.androidapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * AddItemActivity class
 */

public class AddItemActivity extends ActionBarActivity {

    public static final long HOUR = 3600*1000;

    private Date dStart;
    private Date dEnd;
    private String timestampStart;
    private String timestampEnd;
    private SharedPreferences pref;
    private Context context;
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

        context = this;
        pref = getSharedPreferences("com.mdl.androidapp", MODE_PRIVATE);
        rooms = new ArrayList<RoomItem>();

        etName = (EditText) findViewById(R.id.et_name);

        dStart = new Date();
        dEnd = new Date(dStart.getTime() + 1 * HOUR);

        Calendar calendarStart = new GregorianCalendar(TimeZone.getTimeZone("GMT+1"));
        Calendar calendarEnd = new GregorianCalendar(TimeZone.getTimeZone("GMT+1"));
        calendarStart.setTime(dStart);
        calendarEnd.setTime(dEnd);

        timestampStart = Utils.formatDate(new Timestamp(calendarStart.getTime().getTime()/1000).getTime(), true);
        timestampEnd = Utils.formatDate(new Timestamp(calendarEnd.getTime().getTime()/1000).getTime(), true);

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

        tvCreateBy = (TextView) findViewById(R.id.tv_edit_create_by);
        tvCreateBy.setText(pref.getString(Config.KEY_USER_NAME, ""));

        new GetRooms(this, findViewById(R.id.sp_edit)).execute();

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(getResources().getString(R.string.title_add_activity));
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
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_save:
                new SaveEntry(
                        this,
                        Utils.convertToDateWithString(btnStart.getText().toString()).getTime()/1000,
                        Utils.convertToDateWithString(btnEnd.getText().toString()).getTime()/1000,
                        rooms.get(spinnerRooms.getSelectedItemPosition()).getId(),
                        tvCreateBy.getText().toString(),
                        etName.getText().toString()).execute();
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

        JSONParser jsonParser;
        String error = "";
        Context context;
        Spinner spinner;

        /**
         * GetRooms
         * @param context current context
         * @param spinnerView view
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

        Context context;
        long start_time;
        long end_time;
        int room_id;
        String create_by;
        String name;
        int id;

        ProgressDialog pd;
        JSONParser jsonParser = new JSONParser();
        String error = "";

        /**
         * SaveEntry
         * @param context current context
         * @param start_time time to start
         * @param end_time time to end
         * @param room_id id of room
         * @param create_by name of creator
         * @param name name of entry
         */
        public SaveEntry(Context context, long start_time, long end_time, int room_id, String create_by, String name){
            this.context = context;
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
            pd = new ProgressDialog(AddItemActivity.this);
            pd.setMessage(getResources().getString(R.string.add_text));
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
            paramsSave.add(new BasicNameValuePair(Config.KEY_STATUS, Config.KEY_STATUS_ADD));
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
                Toast.makeText(AddItemActivity.this, error, Toast.LENGTH_SHORT).show();
            }else{
                Intent i = new Intent(AddItemActivity.this, DetailItemActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Config.KEY_ENTRY_ID, id+"");
                startActivity(i);
            }
        }
    }
}
