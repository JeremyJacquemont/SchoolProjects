package com.lpiem.apps.loupelec.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.fragments.SettingsFragment;
import com.lpiem.apps.loupelec.utilities.Utilities;
import com.lpiem.apps.loupelec.utilities.colorPicker.ColorPickerPreference;
import com.lpiem.apps.loupelec.utilities.customUiElement.NumberPickerDialogPreference;

/**
 * SettingsActivity Class
 */
public class SettingsActivity extends PreferenceActivity {
    /*
    Methods
     */

    /**
     * onCreate Method (Override)
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove ActionBar & Set fullscreen (Cf. CustomActivity)
        Utilities.setUpWindow(this);

        super.onCreate(savedInstanceState);

        //Init default values
        PreferenceManager.setDefaultValues(this, R.xml.prefs, false);

        //Create view
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            onCreatePreferenceActivity();
        } else {
            onCreatePreferenceFragment();
        }
    }


    /**
     * onCreatePreferenceActivity Method (Override)
     */
    @SuppressWarnings("deprecation")
    private void onCreatePreferenceActivity() {
        //Add preferences
        addPreferencesFromResource(R.xml.prefs);

        //Find all preferences
        final ColorPickerPreference buttonBackground = (ColorPickerPreference) findPreference(getString(R.string.preference_key_button_background));
        final ColorPickerPreference activityBackground = (ColorPickerPreference) findPreference(getString(R.string.preference_key_activity_background));
        final ColorPickerPreference fontColor = (ColorPickerPreference) findPreference(getString(R.string.preference_key_font_color));
        final NumberPickerDialogPreference fontSize = (NumberPickerDialogPreference) findPreference(getString(R.string.preference_key_font_size));
        final CheckBoxPreference toggleOcr = (CheckBoxPreference) findPreference(getString(R.string.preference_key_ocr_toogle));
        final CheckBoxPreference imgTxtOcr = (CheckBoxPreference) findPreference(getString(R.string.preference_key_ocr_imgTxt));
        final CheckBoxPreference txtOnlyOcr = (CheckBoxPreference) findPreference(getString(R.string.preference_key_ocr_txtOnly));
        final ListPreference languageOcr = (ListPreference) findPreference(getString(R.string.preference_key_ocr_languages));
        final CheckBoxPreference useImageButtons = (CheckBoxPreference) findPreference(getString(R.string.preference_key_button_images));
        final ListPreference useColorImageButtons = (ListPreference) findPreference(getString(R.string.preference_key_button_list_color));

        //Enable Alpha Slider
        buttonBackground.setAlphaSliderEnabled(true);
        activityBackground.setAlphaSliderEnabled(true);
        fontColor.setAlphaSliderEnabled(true);

        //Init
        Utilities.setEnablePreference(toggleOcr, imgTxtOcr, txtOnlyOcr, languageOcr);
        Utilities.setEnablePreference(useImageButtons, useColorImageButtons);
        if(!Utilities.getBooleanPreference(this, getString(R.string.preference_key_ocr_imgTxt), true) &&
                !Utilities.getBooleanPreference(this, getString(R.string.preference_key_ocr_txtOnly), true))
            imgTxtOcr.setChecked(true);

        //Set Listener
        buttonBackground.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(newValue))));
                return true;
            }
        });
        fontColor.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(newValue))));
                return true;
            }
        });
        fontSize.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(Integer.valueOf(String.valueOf(newValue)));
                return true;
            }
        });
        languageOcr.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(String.valueOf(newValue));
                return true;
            }
        });
        toggleOcr.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Utilities.setEnablePreference(toggleOcr, imgTxtOcr, txtOnlyOcr, languageOcr);
                return false;
            }
        });
        imgTxtOcr.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                txtOnlyOcr.setChecked(!imgTxtOcr.isChecked());
                return false;
            }
        });
        txtOnlyOcr.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                imgTxtOcr.setChecked(!txtOnlyOcr.isChecked());
                return false;
            }
        });
        useImageButtons.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Utilities.setEnablePreference(useImageButtons, useColorImageButtons);
                return false;
            }
        });
    }

    /**
     * onCreatePreferenceFragment Method (Override)
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void onCreatePreferenceFragment() {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    /**
     * onBackPressed Method (Override)
     */
    @Override
    public void onBackPressed() {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
