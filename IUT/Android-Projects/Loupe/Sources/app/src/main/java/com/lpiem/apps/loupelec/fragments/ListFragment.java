package com.lpiem.apps.loupelec.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.activities.MainActivity;
import com.lpiem.apps.loupelec.dao.datasources.KeywordDataSource;
import com.lpiem.apps.loupelec.dao.datasources.MediaDataSource;
import com.lpiem.apps.loupelec.dao.entities.Keyword;
import com.lpiem.apps.loupelec.dao.entities.Media;
import com.lpiem.apps.loupelec.interfaces.OnFragmentInteractionListener;
import com.lpiem.apps.loupelec.utilities.MessageActivity;
import com.lpiem.apps.loupelec.utilities.Utilities;
import com.lpiem.apps.loupelec.utilities.adapters.ListAdapter;
import com.lpiem.apps.loupelec.utilities.adapters.SpinnerAdapter;

import java.util.ArrayList;

/**
 * ListFragment Class
 */
public class ListFragment extends Fragment {

    /*
    Fields
     */
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Media> mListMedias;
    private OnFragmentInteractionListener mListener = null;
    private MediaDataSource mMediaDataSource;
    private KeywordDataSource mKeywordDataSource;
    private Spinner listSpinner;

    /*
    Methods
     */

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
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    /**
     * onViewCreated Method (Override)
     * @param view View
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        //Init DataSources
        mMediaDataSource = new MediaDataSource(getActivity());
        mKeywordDataSource = new KeywordDataSource(getActivity());

        //SetUp RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_listing);
        mRecyclerView.setHasFixedSize(true);

        //Create LayoutManager and set for recyclerView
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Get all medias
        mListMedias = new ArrayList<Media>();
        refreshMediaList();

        //Create adapter for recyclerView and set for recyclerView
        mAdapter = new ListAdapter(getActivity(), mListMedias);
        mRecyclerView.setAdapter(mAdapter);

        //SetUp spinner
        listSpinner = (Spinner) view.findViewById(R.id.sp_listing);
        setUpSpinner();

        //Get show buttons
        Boolean showImageButton = Utilities.getBooleanPreference(getActivity(), getString(R.string.preference_key_button_images), false);

        //SetUp back button
        View.OnClickListener backListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putInt(MainActivity.FRAGMENT_KEY, MainActivity.FRAGMENT_CAMERA);
                mListener.onFragmentInteraction(MessageActivity.SLIDE_PAGER, params);
            }
        };
        if(showImageButton){
            ImageButton backImageButton = (ImageButton) view.findViewById(R.id.imgbtn_listing_back);
            backImageButton.setVisibility(View.VISIBLE);
            backImageButton.setOnClickListener(backListener);
        }
        else {
            Button backButton = (Button) view.findViewById(R.id.btn_listing_back);
            backButton.setVisibility(View.VISIBLE);
            backButton.setOnClickListener(backListener);
        }
    }

    /**
     * SetUp spinner
     */
    private void setUpSpinner(){
        //Set onItemSelectedListener
        listSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                filterData((Keyword) adapterView.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing....
            }
        });

        //Update data inside spinner
        updateDataInsideSpinner();
    }

    /**
     * Update data inside spinner
     */
    private void updateDataInsideSpinner() {
        //Create Adapter for spinner
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, getDataForSpinner());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set Adapter
        listSpinner.setAdapter(spinnerAdapter);
    }

    /**
     * Get data for spinner
     * @return ArrayList<Keyword>
     */
    private ArrayList<Keyword> getDataForSpinner(){
        ArrayList<Keyword> keywordList = new ArrayList<Keyword>();
        keywordList.add(null);
        keywordList.addAll(getListKeywords());

        return keywordList;
    }

    /**
     * Filter data with a keyword
     * @param filterKeyword Keyword
     */
    private void filterData(Keyword filterKeyword){
        //If filterKeyword == null, reload all data
        if(filterKeyword == null){
            refreshFragment();
        }
        //Else, get filter data and set in new SpinnerAdapter
        else {
            final ArrayList<Media> filterMediaList = mMediaDataSource.getAllMediaWithKeyword(filterKeyword);
            setDataInAdapter(filterMediaList);
        }
    }

    /**
     * Refresh mediaList
     */
    private void refreshMediaList(){
        mListMedias =  mMediaDataSource.getAllMedia();
    }

    /**
     * Get All keywords
     * @return ArrayList<Keyword>
     */
    private ArrayList<Keyword> getListKeywords(){
        return (ArrayList<Keyword>) mKeywordDataSource.getAllKeywords();
    }

    /**
     * Refresh content in fragment
     */
    public void refreshFragment(){
        //Clear list medias
        mListMedias.clear();

        //Reload data
        refreshMediaList();

        //Set data in adapter
        setDataInAdapter(mListMedias);

        //Reset selected element
        listSpinner.setSelection(0);
    }

    /**
     * Set data in adapter with specific list
     * @param list ArrayList<Media>
     */
    private void setDataInAdapter(ArrayList<Media> list){
        //Reset adapter
        mAdapter = null;

        //Create new adapter with data
        mAdapter = new ListAdapter(getActivity(), list);

        //Notify adapter and recyclerView
        mAdapter.notifyDataSetChanged();
        mRecyclerView.invalidate();

        //Set adapter for recyclerView
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * onAttach Method (Override)
     * @param activity Activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    /**
     * onDetach Method (Override)
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * onResume Method (Override)
     */
    @Override
    public void onResume() {
        super.onResume();
        updateDataInsideSpinner();
    }
}
