package com.lpiem.apps.loupelec.utilities.customUiElement;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.utilities.Config;
import com.lpiem.apps.loupelec.utilities.Utilities;

/**
 * CustomActivity Class
 */
public abstract class CustomActivity extends ActionBarActivity {

    /*
    Methods
     */

    /**
     * onCreate Method (Override)
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove ActionBar & Set fullscreen
        Utilities.setUpWindow(this);

        //Init color for background
        initColorBackground();
    }

    /**
     * Init color for background according sharedPreferences
     */
    public void initColorBackground() {
        //Get View
        View view = this.getWindow().getDecorView();

        //Get Color in preferences
        Integer colorIntBackgroundPreference = Utilities.getIntPreference(this, getString(R.string.preference_key_activity_background), 0);

        //Convert color
        Integer colorIntBackground = Utilities.getColor(colorIntBackgroundPreference, 0, Config.DEFAULT_BACKGROUND_ACTIVITY);

        //Set color
        view.setBackgroundColor(colorIntBackground);
    }
}
