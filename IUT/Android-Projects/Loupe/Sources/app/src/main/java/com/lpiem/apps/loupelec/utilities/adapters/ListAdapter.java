package com.lpiem.apps.loupelec.utilities.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.activities.DetailActivity;
import com.lpiem.apps.loupelec.dao.entities.Media;
import com.lpiem.apps.loupelec.utilities.Config;
import com.lpiem.apps.loupelec.utilities.Utilities;
import com.lpiem.apps.loupelec.utilities.images.CacheImage;
import com.lpiem.apps.loupelec.utilities.images.ImagesUtilities;

import java.io.File;
import java.util.ArrayList;

/**
 * ListAdapter Class
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    /*
    Fields
     */
    private static ArrayList<Media> mDataset;
    private static Context sContext;

    /*
    Methods
     */

    /**
     * Constructor
     * @param context Context
     * @param dataset ArrayList<Media>
     */
    public ListAdapter(Context context, ArrayList<Media> dataset) {
        mDataset = dataset;
        sContext = context;

        //Init cache for images
        CacheImage.initCacheImage();
    }

    /**
     * onCreateViewHolder Method (Override)
     * @param parent ViewGroup
     * @param viewType int
     * @return ViewHolder
     */
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        return new ViewHolder(v);
    }

    /**
     * onBindViewHolder Method (Override)
     * @param holder ViewHolder
     * @param position int
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Get current file
        File currentFile = new File(mDataset.get(position).getPath());

        //Get extension of current file
        String extension = Utilities.getExtension(currentFile.getAbsolutePath());

        //Load bitmap
        if(extension.equals(Config.TXT_EXTENSION)){
            ImagesUtilities.loadBitmap(sContext, R.drawable.txt_placeholder, holder.mThumbnail);
        }
        else {
            ImagesUtilities.loadBitmap(sContext, currentFile, holder.mThumbnail);
        }

        //Set tag
        holder.mThumbnail.setTag(mDataset.get(position).getId());
    }

    /**
     * getItemCount Method (Override)
     * @return int
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * ViewHolder Class
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /*
        Fields
         */
        public final ImageView mThumbnail;

        /*
        Methods
         */

        /**
         * Constructor
         * @param v View
         */
        public ViewHolder(View v) {
            super(v);

            //Get ImageView and set OnClickListener
            mThumbnail = (ImageView) v.findViewById(R.id.iv_thumbnail_item);
            mThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(sContext, DetailActivity.class);
                    i.putExtra(DetailActivity.MEDIA_KEY, (Long)mThumbnail.getTag());
                    sContext.startActivity(i);
                }
            });
        }
    }
}