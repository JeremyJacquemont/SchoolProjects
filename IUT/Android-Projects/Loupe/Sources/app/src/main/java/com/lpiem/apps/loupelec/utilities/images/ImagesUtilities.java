package com.lpiem.apps.loupelec.utilities.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.lpiem.apps.loupelec.utilities.Config;
import com.lpiem.apps.loupelec.utilities.Utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ImagesUtilities Class
 */
public class ImagesUtilities {
    /*
    Constants
     */
    private final static String TAG = "ImagesUtilities";

    /**
     * Load bitmap with File
     * @param context Context
     * @param file File
     * @param imageView ImageView
     */
    public static void loadBitmap(Context context, File file, ImageView imageView) {
        //Get key for CacheImage
        final String imageKey = String.valueOf(file.getName());

        //Try to get image inside CacheImage
        final Bitmap bitmap = CacheImage.getBitmapFromMemCache(imageKey);

        //If bitmap is not null
        if (bitmap != null) {
            //Set directly
            imageView.setImageBitmap(bitmap);
        }
        else {
            //Create config (Set grayscale mode)
            Bitmap.Config conf = Bitmap.Config.ARGB_8888;

            //Create bitmap
            Bitmap placeHolderBitmap = Bitmap.createBitmap(Config.SIZE_PREVIEW, Config.SIZE_PREVIEW, conf);

            //Check if current work is not cancel
            if (cancelPotentialWork(file, imageView)) {
                //Create new BitmapWorkerTask
                final BitmapWorkerTask task = new BitmapWorkerTask(context, imageView);

                //Create new AsyncDrawable with BitmapWorkerTask
                final AsyncDrawable asyncDrawable =
                        new AsyncDrawable(context.getResources(), placeHolderBitmap, task);

                //Set AsyncDrawable in ImageView
                imageView.setImageDrawable(asyncDrawable);

                //Start AsyncDrawable
                task.execute(file);
            }
        }
    }

    /**
     * Load bitmap woth RessourceId
     * @param context Context
     * @param resId int
     * @param imageView ImageView
     */
    public static void loadBitmap(Context context, int resId, ImageView imageView){
        //Get key for CacheImage
        final String imageKey = String.valueOf(resId);

        //Try to get image inside CacheImage
        final Bitmap bitmap = CacheImage.getBitmapFromMemCache(imageKey);

        //If bitmap is not null
        if (bitmap != null) {
            //Set directly
            imageView.setImageBitmap(bitmap);
        }
        else{
            //Get resource
            File file = getResourceDrawable(context, resId);

            //Create config (Set grayscale mode)
            Bitmap.Config conf = Bitmap.Config.ARGB_8888;

            //Create bitmap
            Bitmap placeHolderBitmap = Bitmap.createBitmap(Config.SIZE_PREVIEW, Config.SIZE_PREVIEW, conf);

            //Check if current work is not cancel
            if (cancelPotentialWork(file, imageView)) {
                //Create new BitmapWorkerTask
                final BitmapWorkerTask task = new BitmapWorkerTask(context, imageView);

                //Create new AsyncDrawable with BitmapWorkerTask
                final AsyncDrawable asyncDrawable =
                        new AsyncDrawable(context.getResources(), placeHolderBitmap, task);

                //Set AsyncDrawable in ImageView
                imageView.setImageDrawable(asyncDrawable);

                //Start AsyncDrawable
                task.execute(file);
            }
        }
    }

    /**
     * Check if work is cancel
     * @param data File
     * @param imageView ImageView
     * @return boolean
     */
    private static boolean cancelPotentialWork(File data, ImageView imageView) {
        //Get BitmapWorkerTask
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        //Check if BitmapWorkerTask is not null
        if (bitmapWorkerTask != null) {
            //Get Data
            final File bitmapData = bitmapWorkerTask.getData();

            //Check if get data is equals with parameter data
            if (bitmapData == null || bitmapData != data) {
                bitmapWorkerTask.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Get BitmapWorkerTask
     * @param imageView ImageView
     * @return BitmapWorkerTask
     */
    public static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    /**
     * Calculate inSampleSize for save inside Cache
     * @param options BitmapFactory.Options
     * @param reqWidth int
     * @param reqHeight int
     * @return int
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * Decode Bitmap
     * @param data File
     * @param reqWidth int
     * @param reqHeight int
     * @return Bitmap
     */
    public static Bitmap decodeBitmap(File data, int reqWidth, int reqHeight) {
        //Create new BitmapFactory.Options
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        //Decode bitmap wth check dimensions
        BitmapFactory.decodeFile(data.getAbsolutePath(), options);

        //Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        //Desactivate inJustDecodeBounds option
        options.inJustDecodeBounds = false;

        //Decode with inSampleSize
        return BitmapFactory.decodeFile(data.getAbsolutePath(), options);
    }

    /**
     * Get drawable inside Resource
     * @param context Context
     * @param resId int
     * @return File
     */
    private static File getResourceDrawable(Context context, int resId){
        //Create new File object
        File file = new File(Utilities.getDataDir(context) + Config.NAME_PLACEHOLDER_TXT_FILE);

        //If file not exist
        if(!file.exists()) {
            //Put image inside device
            try {
                InputStream inputStream = context.getResources().openRawResource(resId);
                OutputStream out = new FileOutputStream(file);
                byte buf[] = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) > 0)
                    out.write(buf, 0, len);
                out.close();
                inputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "getResourceDrawable : An error occurred on get thumbnail");
            }
        }

        //Return file
        return file;
    }
}
