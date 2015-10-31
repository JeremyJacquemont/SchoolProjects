package com.lpiem.apps.loupelec.utilities.customUiElement;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

/**
 * CustomListPreference Class
 */
public class CustomListPreference extends ListPreference {
    /**
     * Constructors
     */
    public CustomListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CustomListPreference(Context context) {
        super(context);
    }

    /**
     * getSummary Method (Override)
     * @return CharSequence
     */
    @Override
    public CharSequence getSummary() {
        return this.getEntry() != null ? this.getEntry().toString() : "";
    }
}
