package com.lpiem.apps.loupelec.utilities.images;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

/**
 * AsyncDrawable Class
 */
public class AsyncDrawable extends BitmapDrawable {
    /*
    Fields
     */
    private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

    /**
     * Constructor
     * @param res Resources
     * @param bitmap Bitmap
     * @param bitmapWorkerTask BitmapWorkerTask
     */
    public AsyncDrawable(Resources res, Bitmap bitmap,
                         BitmapWorkerTask bitmapWorkerTask) {
        super(res, bitmap);

        //Init WeakReference
        bitmapWorkerTaskReference =
                new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
    }

    /**
     * Get BitmapWorkerTask
     * @return BitmapWorkerTask
     */
    public BitmapWorkerTask getBitmapWorkerTask() {
        return bitmapWorkerTaskReference.get();
    }
}
