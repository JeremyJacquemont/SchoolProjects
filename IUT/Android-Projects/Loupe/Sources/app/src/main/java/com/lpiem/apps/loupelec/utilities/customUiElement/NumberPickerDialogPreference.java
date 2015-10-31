package com.lpiem.apps.loupelec.utilities.customUiElement;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.lpiem.apps.loupelec.R;
import com.lpiem.apps.loupelec.utilities.Config;
import com.lpiem.apps.loupelec.utilities.Utilities;

/**
 * NumberPickerDialogPreference Class
 */
public class NumberPickerDialogPreference extends DialogPreference {
    /*
    Fields
     */
    private static Context mContext;
    private NumberPicker mNumberPicker;

    /*
    Methods
     */

    /**
     * Constructor
     * @param context Context
     * @param attrs AttributeSet
     */
    public NumberPickerDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setDialogLayoutResource(R.layout.number_picker);
    }

    /**
     * onCreateDialogView Method (Override)
     * @return View
     */
    @Override
    protected View onCreateDialogView() {
        LayoutInflater inflater =
                (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.number_picker, null);

        //Get NumberPicker
        mNumberPicker = (NumberPicker) view.findViewById(R.id.np_choice);
        mNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        // Initialize state
        mNumberPicker.setMaxValue(Config.MAX_TEXT_SIZE);
        mNumberPicker.setMinValue(Config.MIN_TEXT_SIZE);
        mNumberPicker.setValue(getPersistedInt(Config.DEFAULT_TEXT_SIZE));
        mNumberPicker.setWrapSelectorWheel(false);

        //Return view
        return view;
    }

    //TODO

    /**
     * getSummary Method (Override)
     * @return CharSequence
     */
    @Override
    public CharSequence getSummary() {
        String summary = "";
        summary = String.format(summary, Utilities.getIntPreference(mContext, mContext.getString(R.string.preference_key_font_size), Config.DEFAULT_TEXT_SIZE));
        return summary;
    }

    /**
     * onDialogClosed Method (Override)
     * @param positiveResult boolean
     */
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            persistInt(mNumberPicker.getValue());
        }
    }
}
