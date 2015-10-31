package com.lpiem.apps.loupelec.utilities.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.lpiem.apps.loupelec.dao.entities.Keyword;
import com.lpiem.apps.loupelec.utilities.customUiElement.CustomTextView;

import java.util.ArrayList;

/**
 * SpinnerAdapter Class
 */
public class SpinnerAdapter extends ArrayAdapter<Keyword> {

    /*
    Fields
     */
    private final Context context;
    private final ArrayList<Keyword> values;

    /*
    Methods
     */

    /**
     * Constructor
     * @param context Context
     * @param textViewResourceId int
     */
    @SuppressWarnings("unused")
    public SpinnerAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
        this.values = new ArrayList<Keyword>();
    }

    /**
     * Constructor
     * @param context Context
     * @param textViewResourceId int
     * @param values ArrayList<Keyword>
     */
    public SpinnerAdapter(Context context, int textViewResourceId,
                       ArrayList<Keyword> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    /**
     * getCount Method (Override)
     * @return int
     */
    @Override
    public int getCount(){
        return values.size();
    }

    /**
     * getItem Method (Override)
     * @param position int
     * @return Keyword
     */
    @Override
    public Keyword getItem(int position){
        return values.get(position);
    }

    /**
     * getItemId Method (Override)
     * @param position int
     * @return long
     */
    @Override
    public long getItemId(int position){
        return position;
    }

    /**
     * getView Method (Override)
     * @param position int
     * @param convertView View
     * @param parent ViewGroup
     * @return View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomTextView label = new CustomTextView(context);
        label.setText(values.get(position) != null ? values.get(position).getName(): "");
        return label;
    }

    /**
     * getDropDownView Method (Override)
     * @param position int
     * @param convertView View
     * @param parent ViewGroup
     * @return View
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        CustomTextView label = new CustomTextView(context);
        label.setText(values.get(position) != null ? values.get(position).getName(): "");
        return label;
    }
}