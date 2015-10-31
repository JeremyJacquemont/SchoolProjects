package com.lppierrejeremy.apps.filemanagement.Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.lppierrejeremy.apps.filemanagement.DAO.MediaObject;
import com.lppierrejeremy.apps.filemanagement.R;

import java.io.File;

/**
 * DetailUtilities Class
 */
public class DetailUtilities {
    /*
    Constants
     */
    private static final String TAG = "DetailUtilities";

    /*
    Methods
     */

    /**
     * Show Content
     * @param context Context
     * @param view View
     * @param mediaObject MediaObject
     */
    public static void showContent(Context context, View view, MediaObject mediaObject){
        //Get Widgets
        TextView tvDetail = (TextView) view.findViewById(R.id.tv_detail);
        ImageView ivDetail = (ImageView) view.findViewById(R.id.iv_detail);
        VideoView vvDetail = (VideoView) view.findViewById(R.id.vv_detail);

        //Get Type
        String mediaType = mediaObject.getType();

        //Show correct data with type
        if(Config.MEDIA_TYPE.IMAGE.getType().equals(mediaType)){
            File file = new File(Utilities.getFilePath(mediaObject.getPath()));
            ivDetail.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            ivDetail.setVisibility(View.VISIBLE);
        }
        else if(Config.MEDIA_TYPE.TEXT.getType().equals(mediaType)){
            tvDetail.setText(Utilities.readTxtFile(mediaObject));
            tvDetail.setVisibility(View.VISIBLE);
        }
        else if(Config.MEDIA_TYPE.VIDEO.getType().equals(mediaType) ||
                Config.MEDIA_TYPE.AUDIO.getType().equals(mediaType)){
            setUpPlayer(context, vvDetail, mediaObject);
        }
        else {
            Log.w(TAG, context.getString(R.string.error_unknown_type));
        }
    }

    /**
     * SetUp Player (Video / Audio)
     * @param context Context
     * @param videoView VideoView
     * @param mediaObject MediaObject
     */
    private static void setUpPlayer(Context context, VideoView videoView, MediaObject mediaObject){
        //Create MediaController
        MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(videoView);

        //Set up VideoView
        File file = new File(Utilities.getFilePath(mediaObject.getPath()));
        videoView.setMediaController(mediaController);
        videoView.setVideoPath(file.getAbsolutePath());
        videoView.setVisibility(View.VISIBLE);
    }

    /**
     * Show Dialog Delete
     * @param context Context
     * @param onClickListener DialogInterface.OnClickListener
     */
    public static void showDelete(final Context context, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
            .setMessage(context.getString(R.string.confirm_action))
            .setPositiveButton(R.string.positive_button, onClickListener)
            .setNegativeButton(R.string.negative_button, onClickListener)
            .show();
    }

}
