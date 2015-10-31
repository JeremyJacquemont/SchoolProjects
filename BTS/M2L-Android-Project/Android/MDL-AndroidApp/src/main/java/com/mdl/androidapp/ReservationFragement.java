package com.mdl.androidapp;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.mdl.utils.Config;
import com.mdl.utils.EntryItem;
import com.mdl.utils.EntryListAdapter;
import com.mdl.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * ReservationFragement class
 */


public class ReservationFragement extends Fragment {
    private ListView lvReservation;

    private WeakReference<GetEntriesTask> asyncTaskWeakRef;
    private SharedPreferences pref;
    protected String username = "";


    /**
     * onCreateView
     * @param inflater current LayoutInflater
     * @param container current viewGroup
     * @param savedInstanceState bundle of instance
     * @return root view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.reservation_fragement, container, false);
        lvReservation = (ListView) root.findViewById(R.id.lv_reservation);
        return root;
    }

    /**
     * onCreate
     * @param savedInstanceState bundle of instance
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        pref = getActivity().getSharedPreferences("com.mdl.androidapp", Context.MODE_PRIVATE);
        if(pref != null){
            username = pref.getString(Config.KEY_USER_NAME, "");
        }

        startGetEntriesTask();
    }

    /**
     * onCreateOptionsMenu
     * @param menu current menu
     * @param inflater current menuInflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_add).setVisible(true);

    }

    /**
     * onOptionsMenu
     * @param item current item
     * @return super.onOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * startGetEntriesTask
     */
    private void startGetEntriesTask() {
        GetEntriesTask asyncTask = new GetEntriesTask(this, username);
        this.asyncTaskWeakRef = new WeakReference<GetEntriesTask>(asyncTask );
        asyncTask.execute();
    }

    /**
     * GetEntriesTask class
     */
    private class GetEntriesTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<ReservationFragement> fragmentWeakRef;
        private JSONParser jsonParser;
        private String error = "";
        private String username = "";
        private ArrayList<EntryItem> entriesItem;
        private EntryListAdapter adapter;

        /**
         * GetEntriesTask
         * @param fragment current fragment
         * @param username current username
         */
        private GetEntriesTask (ReservationFragement fragment, String username){
            this.username = username;
            jsonParser = new JSONParser();
            this.fragmentWeakRef = new WeakReference<ReservationFragement>(fragment);
        }

        /**
         * doInBackground
         * @param params parameters
         * @return null
         */
        @Override
        protected Void doInBackground(Void... params) {

            List<NameValuePair> user = new ArrayList<NameValuePair>();
            user.add(new BasicNameValuePair(Config.KEY_USER_NAME, this.username));

            JSONObject json = jsonParser.makeHttpRequest(Config.URL_GET_ENTRIES, "GET", user);

            try {
                int success = json.getInt(Config.KEY_SUCCESS);
                if(success == 1){
                    JSONArray entry = json.getJSONArray(Config.KEY_ENTRY);
                    entriesItem = new ArrayList<EntryItem>();

                    for (int i = 0; i < entry.length(); i++) {
                        JSONObject object = entry.getJSONObject(i);
                        entriesItem.add(new EntryItem(object.getInt(Config.KEY_ENTRY_ID), object.getString(Config.KEY_ENTRY_NAME), object.getInt(Config.KEY_ENTRY_START_DATE), object.getInt(Config.KEY_ENTRY_END_DATE)));
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
         * @param response response of doInBackground
         */
        @Override
        protected void onPostExecute(Void response) {
            super.onPostExecute(response);
            if(error != ""){
                Toast.makeText(getActivity(), getResources().getString(R.string.error_reservation)+error, Toast.LENGTH_SHORT).show();
            }else{
                if (this.fragmentWeakRef.get() != null && entriesItem != null) {
                    adapter = new EntryListAdapter(getActivity(), entriesItem);
                    lvReservation.setAdapter(adapter);
                }
            }
            getActivity().findViewById(R.id.rl_load_reservation_fragment).setVisibility(View.GONE);
            getActivity().findViewById(R.id.rl_reservation_fragment).setVisibility(View.VISIBLE);
        }
    }

}