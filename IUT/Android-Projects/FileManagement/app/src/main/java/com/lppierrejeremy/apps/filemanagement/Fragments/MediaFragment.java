package com.lppierrejeremy.apps.filemanagement.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lppierrejeremy.apps.filemanagement.Adapters.MediaItemAdapter;
import com.lppierrejeremy.apps.filemanagement.DAO.MediaManager;
import com.lppierrejeremy.apps.filemanagement.DAO.MediaObject;
import com.lppierrejeremy.apps.filemanagement.Interfaces.OnCallListener;
import com.lppierrejeremy.apps.filemanagement.R;
import com.lppierrejeremy.apps.filemanagement.Utilities.Config;
import com.lppierrejeremy.apps.filemanagement.Utilities.Internet.DownloadMedia;

import java.util.ArrayList;

/**
 * MediaFragment Class
 */
public class MediaFragment extends Fragment {
    /*
    Constants
     */
    public static final String TAG = "MediaFragment";
    public static final String MEDIA_LIST = "media_list";
    public static final String MEDIA_TYPE = "media_type";

    /*
    Fields
     */
    private ArrayList<MediaObject> mMediaItems = new ArrayList<MediaObject>();
    private MediaItemAdapter mMediaItemAdapter = null;
    private String mType;
    private ListView mListView;
    private OnCallListener mCallback;

    /*
    Constructor
     */
    public MediaFragment() {}

    /*
    Methods
     */

    /**
     * newInstance
     * @param args Bundle
     * @return MediaFragment
     */
    public static MediaFragment newInstance(Bundle args){
        MediaFragment fragment = new MediaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * onAttach Method (Override)
     * @param activity Activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mCallback = (OnCallListener) activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "must implement OnCallListener");
        }
    }

    /**
     * onCreate Method (Override)
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Arguments
        if (getArguments() != null) {
            mMediaItems = getArguments().getParcelableArrayList(MEDIA_LIST);
            mType = getArguments().getString(MEDIA_TYPE);
        }
    }

    /**
     * onResume Method (Override)
     */
    @Override
    public void onResume() {
        super.onResume();

        if(mMediaItemAdapter != null) {
            //Refresh Medias list
            MediaManager mediaManager = new MediaManager();
            MediaManager.refreshList(getActivity());

            //Re-set MediaItems
            if (Config.MEDIA_TYPE.TEXT.getType().equals(mType))
                mMediaItems = mediaManager.getTexts();
            else if (Config.MEDIA_TYPE.IMAGE.getType().equals(mType))
                mMediaItems = mediaManager.getImages();
            else if (Config.MEDIA_TYPE.VIDEO.getType().equals(mType))
                mMediaItems = mediaManager.getVideos();
            else if (Config.MEDIA_TYPE.AUDIO.getType().equals(mType))
                mMediaItems = mediaManager.getAudios();
            else
                Log.w(TAG, getString(R.string.error_unknown_type));

            //Set new Items in Adapter
            mMediaItemAdapter.setItems(mMediaItems);

            //NotifyDataSetChanged
            mMediaItemAdapter.notifyDataSetChanged();
        }
    }

    /**
     * onCreateView Method (Override)
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medias, container, false);

        //Get ListView
        mListView = (ListView) view.findViewById(R.id.lv_mediafragment);

        //Init Adapter and set adapter in ListView
        mMediaItemAdapter = new MediaItemAdapter(getActivity(), mMediaItems);
        mListView.setAdapter(mMediaItemAdapter);

        //Set OnItemClickListener
        mListView.setOnItemClickListener(itemClickListener);

        //Return view
        return view;
    }

    /**
     * AdapterView.OnItemClickListener
     */
    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MediaObject mediaObject = (MediaObject) parent.getItemAtPosition(position);

            //Check if media is installed
            if(mediaObject.getInstalled() == MediaObject.KEY_INSTALLED
                    && mediaObject.getUpdated() == MediaObject.KEY_NOT_UPDATED){
                mCallback.onCall(mediaObject);
            }
            else{
                new DownloadMedia(getActivity(), mediaObject, mListView).execute();
            }
        }
    };
}
