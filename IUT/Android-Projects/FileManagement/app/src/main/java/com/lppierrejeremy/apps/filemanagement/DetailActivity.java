package com.lppierrejeremy.apps.filemanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lppierrejeremy.apps.filemanagement.DAO.MediaManager;
import com.lppierrejeremy.apps.filemanagement.DAO.MediaObject;
import com.lppierrejeremy.apps.filemanagement.Utilities.DetailUtilities;
import com.lppierrejeremy.apps.filemanagement.Utilities.Utilities;

/**
 * DetailActivity Class
 */
public class DetailActivity extends ActionBarActivity {
    /*
    Constants
     */
    public final static String KEY_MEDIA = "mediaDetailActivity";

    /*
    Fields
     */
    private MediaObject mMediaObject;
    private Context mContext;

    /**
     * onCreate Method (Override)
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Check Extras
        if(getIntent().getExtras() == null)
            finish();
        if(getIntent().getExtras().getParcelable(KEY_MEDIA) == null)
            finish();

        //Set Context
        mContext = this;

        //Get MediaObject
        mMediaObject = getIntent().getExtras().getParcelable(KEY_MEDIA);

        //SetUp ActionBar
        Utilities.setUpActionBar(getSupportActionBar());
        getSupportActionBar().setTitle(mMediaObject.getName());

        //Show content
        DetailUtilities.showContent(this, this.getWindow().getDecorView(), mMediaObject);
    }

    /**
     * onOptionsItemSelected Method (Override)
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.delete_action:
                confirmDeleteFile(mMediaObject);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * onCreateOptionsMenu Method (Override)
     * @param menu Menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
                        MediaManager.deleteFileInDisk(mContext, mediaObject);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        //Show Dialog for delete
        DetailUtilities.showDelete(this, dialogClickListener);
    }
}
