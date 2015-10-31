package com.lppierrejeremy.apps.filemanagement.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lppierrejeremy.apps.filemanagement.Items.DrawerMenuItem;
import com.lppierrejeremy.apps.filemanagement.R;

import java.util.ArrayList;

/**
 * DrawerMenuAdapter Class
 */
public class DrawerMenuAdapter extends BaseAdapter {
    /*
    Fields
     */
    private Context mContext;
    private ArrayList<DrawerMenuItem> mDrawerMenuItems = new ArrayList<DrawerMenuItem>();

    /*
    Constructor
     */

    /**
     * DrawerMenuAdapter Constructor
     * @param context Context
     * @param drawerMenuItems ArrayList
     */
    public DrawerMenuAdapter(Context context, ArrayList<DrawerMenuItem> drawerMenuItems){
        this.mContext = context;
        this.mDrawerMenuItems = drawerMenuItems;
    }

    /*
    Methods
     */

    /**
     * getCount Method (Override)
     * @return int
     */
    @Override
    public int getCount() {
        return mDrawerMenuItems.size();
    }

    /**
     * getItem Method (Override)
     * @param position int
     * @return Object
     */
    @Override
    public Object getItem(int position) {
        return mDrawerMenuItems.get(position);
    }

    /**
     * getItemId Method (Override)
     * @param position int
     * @return long
     */
    @Override
    public long getItemId(int position) {
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
        ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater layoutInflater = ((Activity) mContext).getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.drawer_item_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tvDrawerItem);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.ivDrawerItem);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DrawerMenuItem drawerMenuItem = mDrawerMenuItems.get(position);

        if(drawerMenuItem != null){
            viewHolder.textView.setText(drawerMenuItem.getName());
            viewHolder.imageView.setImageDrawable(drawerMenuItem.getDrawable());
        }

        return convertView;
    }

    /**
     * ViewHolder Class
     */
    private static class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
