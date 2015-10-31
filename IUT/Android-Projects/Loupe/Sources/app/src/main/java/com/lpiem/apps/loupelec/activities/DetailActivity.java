package com.lpiem.apps.loupelec.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.dao.datasources.KeywordDataSource;
import com.lpiem.apps.loupelec.dao.datasources.MediaDataSource;
import com.lpiem.apps.loupelec.dao.entities.Keyword;
import com.lpiem.apps.loupelec.dao.entities.Media;
import com.lpiem.apps.loupelec.utilities.Config;
import com.lpiem.apps.loupelec.utilities.Utilities;
import com.lpiem.apps.loupelec.utilities.adapters.SpinnerAdapter;
import com.lpiem.apps.loupelec.utilities.customUiElement.CustomActivity;
import com.lpiem.apps.loupelec.utilities.images.ZoomImageListener;

import java.io.File;
import java.util.ArrayList;

/**
 * DetailActivity Class
 */
public class DetailActivity extends CustomActivity {
    /*
    Constants
     */
    public static final String MEDIA_KEY = "media";

    /*
    Fields
     */
    private Context mContext;
    private File mMediaFile;
    private Media mMedia = null;
    private MediaDataSource mMediaDataSource;
    private KeywordDataSource mKeywordDataSource;

    /*
    Methods
     */

    /**
     * onCreate Method (Override)
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set content
        setContentView(R.layout.activity_detail);
        mContext = this;

        //Init DataSources
        mMediaDataSource = new MediaDataSource(this);
        mKeywordDataSource = new KeywordDataSource(this);

        //Get informations
        Bundle extras = getIntent().getExtras();
        if(extras == null || extras.getLong(MEDIA_KEY) == 0)
            finish();
        else {
            //Get file
            mMedia = mMediaDataSource.getMedia(extras.getLong(MEDIA_KEY));

            //Update View
            if(mMedia.getType().equals(Config.TXT_EXTENSION)){
                mMediaFile = new File(mMedia.getPath());
                TextView mPictureTv = (TextView) findViewById(R.id.tv_detail_picture);
                mPictureTv.setText(Utilities.getTextInsideFile(mMediaFile));
                ScrollView scrollViewTv = (ScrollView) findViewById(R.id.sv_detail_picture);
                scrollViewTv.setVisibility(View.VISIBLE);
            }
            else{
                mMediaFile = new File(mMedia.getPath());
                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_detail_picture);
                ImageView mPictureIv = (ImageView) findViewById(R.id.iv_detail_picture);
                mPictureIv.setImageURI(Uri.fromFile(mMediaFile));
                mPictureIv.setOnTouchListener(new ZoomImageListener(frameLayout, mPictureIv, ZoomImageListener.Anchor.CENTER));
                mPictureIv.setVisibility(View.VISIBLE);
            }

            //Get show buttons
            Boolean showImageButton = Utilities.getBooleanPreference(this, getString(R.string.preference_key_button_images), false);

            //SetUp back button
            View.OnClickListener backListener = new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            };
            if(showImageButton){
                ImageButton backImageButton = (ImageButton) findViewById(R.id.imgbtn_detail_back);
                backImageButton.setVisibility(View.VISIBLE);
                backImageButton.setOnClickListener(backListener);
            }
            else {
                Button backButton = (Button) findViewById(R.id.btn_detail_back);
                backButton.setVisibility(View.VISIBLE);
                backButton.setOnClickListener(backListener);
            }

            //SetUp edit button
            View.OnClickListener editListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choiceDialog();
                }
            };
            if(showImageButton){
                ImageButton editImageButton = (ImageButton) findViewById(R.id.imgbtn_detail_edit);
                editImageButton.setVisibility(View.VISIBLE);
                editImageButton.setOnClickListener(editListener);
            }
            else {
                Button editButton = (Button) findViewById(R.id.btn_detail_edit);
                editButton.setVisibility(View.VISIBLE);
                editButton.setOnClickListener(editListener);
            }

            //SetUp share button
            View.OnClickListener  shareListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareIntent();
                }
            };
            if(showImageButton){
                ImageButton shareImageButton = (ImageButton) findViewById(R.id.imgbtn_detail_share);
                shareImageButton.setVisibility(View.VISIBLE);
                shareImageButton.setOnClickListener(shareListener);
            }
            else {
                Button shareButton = (Button) findViewById(R.id.btn_detail_share);
                shareButton.setVisibility(View.VISIBLE);
                shareButton.setOnClickListener(shareListener);
            }

            //SetUp delete button
            View.OnClickListener deleteListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDelete();
                }
            };
            if(showImageButton){
                ImageButton deleteImageButton = (ImageButton) findViewById(R.id.imgbtn_detail_delete);
                deleteImageButton.setVisibility(View.VISIBLE);
                deleteImageButton.setOnClickListener(deleteListener);
            }
            else {
                Button deleteButton = (Button) findViewById(R.id.btn_detail_delete);
                deleteButton.setVisibility(View.VISIBLE);
                deleteButton.setOnClickListener(deleteListener);
            }
        }
    }

    /**
     * onBackPressed Method (Override)
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Show dialog for confirm delete
     */
    private void confirmDelete(){
        //Create dialog with builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.detail_delete_title));
        builder.setMessage(getString(R.string.detail_delete_message));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFile();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        //Extract dialog
        AlertDialog alertDialog = builder.create();

        //Show dialog
        alertDialog.show();
    }

    /**
     * Delete file
     */
    private void deleteFile(){
        //Delete into BDD
        mMediaDataSource.deleteMedia(mMedia);

        //Delete into device
        if(mMediaFile.delete()){
            finish();
        }
        else{
            Toast.makeText(mContext, getString(R.string.error_occured), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Show dialog for choice option (Keyword)
     */
    private void choiceDialog(){
        //Create dialog
        final Dialog choiceDialog = new Dialog(this);
        choiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        choiceDialog.setContentView(R.layout.dialog_choice_keyword);

        //Set buttons
        Button addButtonDialog = (Button) choiceDialog.findViewById(R.id.btn_add_choice_keyword);
        addButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceDialog.dismiss();
                addDialog();
            }
        });
        Button useButtonDialog = (Button) choiceDialog.findViewById(R.id.btn_use_choice_keyword);
        useButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceDialog.dismiss();
                useDialog();
            }
        });

        //Show dialog
        choiceDialog.show();
    }

    /**
     * Show dialog for add (Keyword)
     */
    private void addDialog(){
        //Create dialog
        final Dialog addDialog = new Dialog(this);
        addDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addDialog.setContentView(R.layout.dialog_add_keyword);

        //Set buttons
        Button buttonValidateDialog = (Button) addDialog.findViewById(R.id.btn_validate_add_keyword_dialog);
        buttonValidateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addKeywordAndSet(addDialog);
            }
        });
        Button buttonCancelDialog = (Button) addDialog.findViewById(R.id.btn_cancel_add_keyword_dialog);
        buttonCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog.dismiss();
            }
        });

        //Show dialog
        addDialog.show();
    }

    /**
     * Show dialog for use (Keyword)
     */
    private void useDialog() {
        //Create dialog
        final Dialog useDialog = new Dialog(this);
        useDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        useDialog.setContentView(R.layout.dialog_use_keyword);

        Spinner spinnerDialog = (Spinner) useDialog.findViewById(R.id.sp_use_keyword_dialog);

        //Get data for spinner
        ArrayList<Keyword> keywordList = new ArrayList<Keyword>();
        if(mMedia.getKeyword() == null)
            keywordList.add(null);
        keywordList.addAll(mKeywordDataSource.getAllKeywords());

        //Set Adapter for spinner
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, keywordList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDialog.setAdapter(spinnerAdapter);
        spinnerDialog.setSelection(spinnerAdapter.getPosition(mMedia.getKeyword()));

        //Set buttons
        Button buttonValidateDialog = (Button) useDialog.findViewById(R.id.btn_validate_use_keyword_dialog);
        buttonValidateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateKeyword(useDialog);
            }
        });
        Button buttonCancelDialog = (Button) useDialog.findViewById(R.id.btn_cancel_use_keyword_dialog);
        buttonCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useDialog.dismiss();
            }
        });

        //Show dialog
        useDialog.show();
    }

    /**
     * Add new keyword in BDD and set in current media
     * @param dialog Dialog
     */
    private void addKeywordAndSet(Dialog dialog) {
        EditText editText = (EditText) dialog.findViewById(R.id.et_add_keyword_dialog);
        String value = editText.getText().toString();

        if(value == null || value.equals("")){
            Toast.makeText(this, getString(R.string.dialog_add_error), Toast.LENGTH_SHORT).show();
        }
        else{
            Keyword newKeyword = mKeywordDataSource.createKeyword(value);
            mMedia = mMediaDataSource.updateKeywordForMedia(mMedia, newKeyword);
            dialog.dismiss();
        }
    }

    /**
     * Update current media with selected keyword
     * @param dialog Dialog
     */
    private void updateKeyword(Dialog dialog){
        Spinner spinner = (Spinner) dialog.findViewById(R.id.sp_use_keyword_dialog);
        Keyword keyword = (Keyword) spinner.getSelectedItem();

        if(keyword == null){
            Toast.makeText(this, getString(R.string.dialog_use_error), Toast.LENGTH_SHORT).show();
        }
        else{
            mMedia = mMediaDataSource.updateKeywordForMedia(mMedia, keyword);
            dialog.dismiss();
        }
    }

    /**
     * Call share intent
     */
    private void shareIntent() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " - " + getString(R.string.share));
        if(Utilities.getExtension(mMediaFile.getAbsolutePath()).equals(Config.TXT_EXTENSION)){
            shareIntent.setType(Config.TXT_TYPE);
        }
        else {
            shareIntent.setType(Config.IMG_TYPE);
        }
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mMediaFile));
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share)));
    }

}
