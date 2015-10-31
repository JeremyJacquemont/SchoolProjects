package com.lpiem.apps.loupelec.utilities.customUiElement;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.utilities.Config;
import com.lpiem.apps.loupelec.utilities.Utilities;

public class CustomImageButton extends ImageButton {

    private Context mContext;

    public CustomImageButton(Context context) {
        super(context);
        mContext = context;

        initButton(null);
    }

    public CustomImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initButton(attrs);
    }

    public CustomImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        initButton(attrs);
    }

    /**
     * Init button according sharedPreferences
     */
    public void initButton(AttributeSet attributeSet) {

        TypedArray typedArray = mContext.obtainStyledAttributes(attributeSet, R.styleable.CustomImageButton);
        String imageName = typedArray.getString(R.styleable.CustomImageButton_imageName);
        String colorButton = Utilities.getStringPreference(mContext, mContext.getString(R.string.preference_key_button_list_color), Config.DEFAULT_COLOR_BUTTON);

        int id = mContext.getResources().getIdentifier(imageName + "_" + colorButton, "drawable", mContext.getPackageName());
        this.setImageResource(id);

        //Get informations with sharedPreferences
        Integer colorIntBackgroundPreference = Utilities.getIntPreference(mContext, mContext.getString(R.string.preference_key_button_background), 0);

        //Convert colors
        Integer colorIntBackground = Utilities.getColor(colorIntBackgroundPreference, 0, Config.DEFAULT_BACKGROUND_BUTTON);

        //Set new informations
        this.setBackgroundColor(colorIntBackground);
    }
}
