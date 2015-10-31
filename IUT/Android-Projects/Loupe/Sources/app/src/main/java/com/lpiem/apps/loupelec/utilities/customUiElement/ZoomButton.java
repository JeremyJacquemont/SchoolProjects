package com.lpiem.apps.loupelec.utilities.customUiElement;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.utilities.Config;
import com.lpiem.apps.loupelec.utilities.Utilities;

public class ZoomButton extends Button {
    /*
    Fields
     */
    private final Context mContext;

    /*
    Methods
     */

    /**
     * Constructors
     */
    public ZoomButton(Context context) {
        super(context);
        mContext = context;

        initButton();
    }
    public ZoomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initButton();
    }
    public ZoomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        initButton();
    }

    /**
     * Init button according sharedPreferences
     */
    public void initButton() {
        //Get informations with sharedPreferences
        Integer colorIntBackgroundPreference = Utilities.getIntPreference(mContext, mContext.getString(R.string.preference_key_button_background), 0);
        Integer colorIntFontPreference = Utilities.getIntPreference(mContext, mContext.getString(R.string.preference_key_font_color), 0);
        Integer sizeInt = Utilities.getIntPreference(mContext, mContext.getString(R.string.preference_key_zoom_controls_size), Config.DEFAULT_TEXT_SIZE);

        //Convert colors
        Integer colorIntBackground = Utilities.getColor(colorIntBackgroundPreference, 0, Config.DEFAULT_BACKGROUND_BUTTON);
        Integer colorIntFont = Utilities.getColor(colorIntFontPreference, 0, Config.DEFAULT_COLOR_FONT);

        //Set new informations
        this.setBackgroundColor(colorIntBackground);
        this.setTextSize(sizeInt);
        this.setTextColor(colorIntFont);
    }
}