package com.lpiem.apps.loupelec.utilities.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.lpiem.apps.loupelec.utilities.Config;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * BitmapWorkerTask Class
 */
public class BitmapWorkerTask extends AsyncTask<File, Void, Bitmap> {
    /*
    Fields
     */
    private final WeakReference<ImageView> imageViewReference;
    private final Context mContext;
    private File data;

    /*
    Methods
     */

    /**
     * Constructor
     * @param context Context
     * @param imageView ImageView
     */
    public BitmapWorkerTask(Context context, ImageView imageView) {
        mContext = context;

        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    /**
     * Get data
     * @return File
     */
    public File getData() {
        return data;
    }

    /**
     * doInBackground Method (Override)
     * @param params File...
     * @return Bitmap
     */
    @Override
    protected Bitmap doInBackground(File... params) {
        //Get bitmap
        data = params[0];

        //Decode image
        return ImagesUtilities.decodeBitmap(data, Config.SIZE_PREVIEW, Config.SIZE_PREVIEW);
    }

    // Once complete, see if ImageView is still around and set bitmap.

    /**
     * onPostExecute Method (Override)
     * @param bitmap Bitmap
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        //Check if cancelled
        if (isCancelled()) {
            bitmap = null;
        }

        //Check bitmap != null
        if (bitmap != null) {
            //Get ImageView reference
            final ImageView imageView = imageViewReference.get();

            //Get BitmapWorkerTask
            final BitmapWorkerTask bitmapWorkerTask =
                    ImagesUtilities.getBitmapWorkerTask(imageView);

            //If imageView and bitmapWorkerTask is not null, set bitmap for imageView
            if (this == bitmapWorkerTask && imageView != null) {
                //Construct drawable array
                Drawable[] layers = new Drawable[2];
                layers[0] = imageView.getDrawable();
                layers[1] = new BitmapDrawable(mContext.getResources(),bitmap);

                //Create transition drawable
                TransitionDrawable transition = new TransitionDrawable(layers);

                //Set TransitionDrawable inside imageView
                imageView.setImageDrawable(transition);

                //Start animation
                transition.startTransition(Config.TRANSITION_DURATION);

                //Add image in Memory Cache
                CacheImage.addBitmapToMemoryCache(String.valueOf(data.getName()), bitmap);
            }
        }
    }
}
