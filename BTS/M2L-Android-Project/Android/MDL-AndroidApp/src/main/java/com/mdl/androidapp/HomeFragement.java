package com.mdl.androidapp;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
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
 * HomeFragement class
 */

public class HomeFragement extends Fragment {
    private TextView tvHome;
    private ListView lvHome;

    private WeakReference<GetEntriesTask> asyncTaskWeakRef;
    private SharedPreferences pref;
    protected String username = "";

    private static final int KEY_LIMIT = 3;

    /**
     * onCreate
     * @param savedInstanceState bundle of instance
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getActivity().getSharedPreferences("com.mdl.androidapp", Context.MODE_PRIVATE);
        if(pref != null){
            username = pref.getString(Config.KEY_USER_NAME, "");
        }

        startGetEntriesTask();
    }

    /**
     * onCreateView
     * @param inflater current layoutInflater
     * @param container current viewGroup
     * @param savedInstanceState bundle of instance
     * @return root view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragement, container, false);
        root.setDrawingCacheEnabled(false);
        root.invalidate();

        tvHome = (TextView) root.findViewById(R.id.tv_text_home);
        if(username != ""){
            tvHome.setText(getResources().getString(R.string.welome_text_home) + " " + username + " !");
        }

        lvHome = (ListView) root.findViewById(R.id.lv_home);

        return root;
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

        private WeakReference<HomeFragement> fragmentWeakRef;
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
        private GetEntriesTask (HomeFragement fragment, String username){
            this.username = username;
            jsonParser = new JSONParser();
            this.fragmentWeakRef = new WeakReference<HomeFragement>(fragment);
        }

        /**
         * doInBackground
         * @param params parameters
         * @return null
         */
        @Override
        protected Void doInBackground(Void... params) {

            List<NameValuePair> paramsEntries = new ArrayList<NameValuePair>();
            paramsEntries.add(new BasicNameValuePair(Config.KEY_USER_NAME, this.username));
            paramsEntries.add(new BasicNameValuePair(Config.KEY_LIMIT, KEY_LIMIT + ""));

            JSONObject json = jsonParser.makeHttpRequest(Config.URL_GET_ENTRIES, "GET", paramsEntries);

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
         * @param response void
         */
        @Override
        protected void onPostExecute(Void response) {
            super.onPostExecute(response);
            if(error != ""){
                Toast.makeText(getActivity(), getResources().getString(R.string.error_get_entries)+error, Toast.LENGTH_SHORT).show();
            }else{
                if (this.fragmentWeakRef.get() != null && entriesItem != null) {
                    Log.d("Entries", entriesItem.toString());
                    adapter = new EntryListAdapter(getActivity(), entriesItem);
                    lvHome.setAdapter(adapter);
                }
            }
            getActivity().findViewById(R.id.rl_load_home_fragement).setVisibility(View.GONE);
            getActivity().findViewById(R.id.rl_home_fragement).setVisibility(View.VISIBLE);
        }
    }
}