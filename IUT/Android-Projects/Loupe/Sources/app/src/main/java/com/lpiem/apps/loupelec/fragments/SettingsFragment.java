package com.lpiem.apps.loupelec.fragments;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.utilities.Utilities;
import com.lpiem.apps.loupelec.utilities.colorPicker.ColorPickerPreference;
import com.lpiem.apps.loupelec.utilities.customUiElement.NumberPickerDialogPreference;

/**
 * SettingsFragment Class
 */
public class SettingsFragment extends PreferenceFragment
{
    /*
    Methods
     */

    /**
     * onCreate Method (Override)
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

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
        if(!Utilities.getBooleanPreference(getActivity(), getString(R.string.preference_key_ocr_imgTxt), true) &&
                !Utilities.getBooleanPreference(getActivity(), getString(R.string.preference_key_ocr_txtOnly), true))
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
}
