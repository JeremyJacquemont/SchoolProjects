package com.mdl.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdl.androidapp.DetailItemActivity;
import com.mdl.androidapp.R;

import java.util.ArrayList;

/**
 * EntryListAdapter class
 */

public class EntryListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<EntryItem> entriesItems;

    public EntryListAdapter(Context context, ArrayList<EntryItem> entriesItems){
        this.context = context;
        this.entriesItems = entriesItems;
    }

    @Override
    public int getCount() {
        return entriesItems.size();
    }

    @Override
    public Object getItem(int position) {
        return entriesItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.entry_item, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        txtTitle.setText(entriesItems.get(position).getTitle());

        TextView tvStartDate = (TextView) convertView.findViewById(R.id.tv_start_date_item);
        tvStartDate.setText(context.getString(R.string.text_start_date) + entriesItems.get(position).getDateStart());

        TextView tvEndDate = (TextView) convertView.findViewById(R.id.tv_end_date_item);
        tvEndDate.setText(context.getString(R.string.text_end_date) + entriesItems.get(position).getDateEnd());

        RelativeLayout rlLayout = (RelativeLayout) convertView.findViewById(R.id.rl_item);
        rlLayout.setTag(entriesItems.get(position).getId());
        rlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailItemActivity.class);
                i.putExtra(Config.KEY_ENTRY_ID, v.getTag().toString());
                context.startActivity(i);
            }
        });

        return convertView;
    }

}