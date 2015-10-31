package com.lppierrejeremy.apps.filemanagement.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lppierrejeremy.apps.filemanagement.DAO.MediaObject;
import com.lppierrejeremy.apps.filemanagement.R;

import java.util.ArrayList;

/**
 * MediaItemAdapter Class
 */
public class MediaItemAdapter extends BaseAdapter {

    /*
    Fields
     */
    private Context mContext;
    private ArrayList<MediaObject> mMediaItems = new ArrayList<MediaObject>();

    /*
    Constructor
     */

    /**
     * MediaItemAdapter Constructor
     * @param context Context
     * @param mediaItems ArrayList
     */
    public MediaItemAdapter(Context context, ArrayList<MediaObject> mediaItems) {
        this.mContext = context;
        this.mMediaItems = mediaItems;
    }

    /*
    Methods
     */

    /**
     * setItems Method
     * @param mMediaItems ArrayList
     */
    public void setItems(ArrayList<MediaObject> mMediaItems) {
        this.mMediaItems = mMediaItems;
    }

    /**
     * getCount Method (Override)
     * @return int
     */
    @Override
    public int getCount() {
        return mMediaItems.size();
    }

    /**
     * getItem Method (Override)
     * @param position int
     * @return Object
     */
    @Override
    public Object getItem(int position) {
        return mMediaItems.get(position);
    }

    /**
     * getItemId Method (Override)
     * @param position int
     * @return long
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * getView Method (Override)
     * @param position int
     * @param view View
     * @param viewGroup ViewGroup
     * @return View
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            view = inflater.inflate(R.layout.media_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvVersion = (TextView) view.findViewById(R.id.tvVersionMediaItem);
            viewHolder.tvName = (TextView) view.findViewById(R.id.tvNameMediaItem);
            viewHolder.ivDownloadIcon = (ImageView) view.findViewById(R.id.ivIconDownloadMediaItem);
            viewHolder.ivRefreshIcon = (ImageView) view.findViewById(R.id.ivIconRefreshMediaItem);
            viewHolder.ivShowIcon = (ImageView) view.findViewById(R.id.ivIconShowMediaItem);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        MediaObject mediaObject = (MediaObject)this.getItem(position);
        viewHolder.tvVersion.setText(mediaObject.getVersionCode() + "");
        viewHolder.tvName.setText(mediaObject.getName());

        viewHolder.ivRefreshIcon.setVisibility(View.GONE);
        viewHolder.ivDownloadIcon.setVisibility(View.GONE);
        viewHolder.ivShowIcon.setVisibility(View.GONE);

        if(mediaObject.getInstalled() == MediaObject.KEY_INSTALLED
                && mediaObject.getUpdated() == MediaObject.KEY_NOT_UPDATED)
            viewHolder.ivShowIcon.setVisibility(View.VISIBLE);
        else if(mediaObject.getInstalled() == MediaObject.KEY_NOT_INSTALLED)
            viewHolder.ivDownloadIcon.setVisibility(View.VISIBLE);
        else if(mediaObject.getUpdated() == MediaObject.KEY_UPDATED)
            viewHolder.ivRefreshIcon.setVisibility(View.VISIBLE);

        return view;
    }

    /**
     * ViewHolder Class
     */
    private static class ViewHolder {
        TextView tvVersion;
        TextView tvName;
        ImageView ivDownloadIcon;
        ImageView ivRefreshIcon;
        ImageView ivShowIcon;
    }
}
