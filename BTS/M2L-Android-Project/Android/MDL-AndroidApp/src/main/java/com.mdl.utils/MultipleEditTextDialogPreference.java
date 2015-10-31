package com.mdl.utils;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mdl.androidapp.ParameterFragement;
import com.mdl.androidapp.R;

/**
 * MultipleEditTextDialogPreference class
 */

public class MultipleEditTextDialogPreference extends DialogPreference{

    private static Context ctx;
    private EditText et1;
    private EditText et2;

    public MultipleEditTextDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
        setDialogLayoutResource(R.layout.multiple_edit_text);
    }

    @Override
    protected void onBindDialogView(View view) {
        et1 = (EditText) view.findViewById(R.id.et_title1);
        et2 = (EditText) view.findViewById(R.id.et_title2);

        super.onBindDialogView(view);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if(!positiveResult)
            return;
        String oldPassword = et1.getText().toString();
        String newPassword = et2.getText().toString();
        String id = ctx.getSharedPreferences("com.mdl.androidapp", Context.MODE_PRIVATE).getString(Config.KEY_USER_ID, "");
        try{
            new ParameterFragement.SetUserTask(id, null, null, oldPassword, newPassword).execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        super.onDialogClosed(positiveResult);
    }
}
