package com.lppierrejeremy.apps.filemanagement.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lppierrejeremy.apps.filemanagement.DAO.MediaManager;
import com.lppierrejeremy.apps.filemanagement.DAO.MediaObject;
import com.lppierrejeremy.apps.filemanagement.R;
import com.lppierrejeremy.apps.filemanagement.Utilities.DetailUtilities;

/**
 * DetailFragment Class
 */
public class DetailFragment extends Fragment {
    /*
    Constants
     */
    public final static String KEY_MEDIA = "mediaDetailFragment";

    /*
    Fields
     */
    private Fragment mFragment;
    private MediaObject mMediaObject = null;

    /*
    Constructor
     */
    public DetailFragment() {}

    /*
    Methods
     */

    /**
     * newInstance
     * @param args Bundle
     * @return DetailFragment
     */
    public static DetailFragment newInstance(Bundle args){
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * onCreate Method (Override)
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set has options menu has true
        setHasOptionsMenu(true);

        //Init mFragment
        mFragment = this;

        //Get Arguments
        if (getArguments() != null) {
            mMediaObject = getArguments().getParcelable(KEY_MEDIA);
        }
    }

    /**
     * onCreate Method (Override)
     * @param menu Menu
     * @param inflater MenuInflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
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
        if(mMediaObject == null)
            return null;

        View view = inflater.inflate(R.layout.activity_detail, container, false);
        DetailUtilities.showContent(getActivity(), view, mMediaObject);

        return view;
    }

    /**
     * onOptionsItemSelected Method (Override)
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_action:
                confirmDeleteFile(mMediaObject);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Confirm Delete a File
     * @param mediaObject MediaObject
     */
    private void confirmDeleteFile(final MediaObject mediaObject) {
        //Create DialogInterface.OnClickListener
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        MediaManager.deleteFileInDisk(getActivity(), mediaObject);
                        getActivity().getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
                        getActivity().getSupportFragmentManager().getFragments().get(0).onResume();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        //Show Delete Message
        DetailUtilities.showDelete(getActivity(), dialogClickListener);
    }

}
