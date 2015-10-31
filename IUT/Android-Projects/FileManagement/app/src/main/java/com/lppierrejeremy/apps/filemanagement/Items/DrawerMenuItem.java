package com.lppierrejeremy.apps.filemanagement.Items;

import android.graphics.drawable.Drawable;

/**
 * DrawerMenuItem Class
 */
public class DrawerMenuItem {
    /*
    Fields
     */
    private String mName;
    private Drawable mDrawable;

    /*
    Constructor
     */

    /**
     * DrawerMenuItem Constructor
     * @param name String
     * @param drawable Drawable
     */
    public DrawerMenuItem(String name, Drawable drawable){
        this.setName(name);
        this.setDrawable(drawable);
    }

    /*
    Methods
     */
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        this.mName = name;
    }
    public Drawable getDrawable() {
        return mDrawable;
    }
    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
    }
}
