package com.lpiem.apps.loupelec.utilities.customUiElement;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.utilities.Config;
import com.lpiem.apps.loupelec.utilities.Utilities;

/**
 * CustomEditText Class
 */
public class CustomEditText extends EditText {
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
    public CustomEditText(Context context) {
        super(context);
        mContext = context;

        initEditText();
    }
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initEditText();
    }
    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        initEditText();
    }

    /**
     * Init editText according sharedPreferences
     */
    private void initEditText(){
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
