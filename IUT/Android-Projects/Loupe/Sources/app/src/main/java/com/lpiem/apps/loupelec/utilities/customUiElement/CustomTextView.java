package com.lpiem.apps.loupelec.utilities.customUiElement;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.utilities.Config;
import com.lpiem.apps.loupelec.utilities.Utilities;

/**
 * CustomTextView Class
 */
public class CustomTextView extends TextView {
    /*
    Fields
     */
    private final Context mContext;

    /**
     * Constructors
     */
    public CustomTextView(Context context) {
        super(context);
        mContext = context;

        initTextView();
    }
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initTextView();
    }
    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        initTextView();
    }

    /**
     * Init textView according sharedPreferences
     */
    private void initTextView(){
        //Get informations with sharedPreferences
        Integer colorIntPreference = Utilities.getIntPreference(mContext, mContext.getString(R.string.preference_key_font_color), 0);
        Integer sizeInt = Utilities.getIntPreference(mContext, mContext.getString(R.string.preference_key_font_size), Config.DEFAULT_TEXT_SIZE);

        //Convert color
        Integer colorInt = Utilities.getColor(colorIntPreference, 0, Config.DEFAULT_COLOR_FONT);

        //Set informations
        this.setTextColor(colorInt);
        this.setTextSize(sizeInt);
    }

}
