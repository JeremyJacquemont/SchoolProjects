package com.lpiem.apps.loupelec.utilities.images;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * CacheImage Class
 */
public class CacheImage {
    /*
    Fields
     */
    private static LruCache<String, Bitmap> mMemoryCache = null;

    /*
    Methods
     */

    /**
     * Init cache image
     */
    public static void initCacheImage(){
        //Get max memory for device
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        //Set cache size equal 1/8 of max memory
        final int cacheSize = maxMemory / 8;

        //Init LruCache
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    /**
     * Add bitmap in LruCache
     * @param key String
     * @param bitmap Bitmap
     */
    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if(mMemoryCache == null)
            return;
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * Get bitmap in LruCache
     * @param key String
     * @return Bitmap
     */
    public static Bitmap getBitmapFromMemCache(String key) {
        if(mMemoryCache == null)
            return null;
        else
            return mMemoryCache.get(key);
    }

}
