package com.remigalvez.chappstick.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.remigalvez.chappstick.R;
import com.remigalvez.chappstick.objects.App;

import java.util.List;

/**
 * Created by Remi on 11/12/15.
 */
public class HomeIconAdapter extends BaseAdapter {
    public static final String TAG = "HomeIconAdapter";

    private Activity mContext;
    private List<App> mList;
    private LayoutInflater mLayoutInflater = null;

    public HomeIconAdapter(Activity context, List<App> list) {
        mContext = context;
        mList = list;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        AppListViewHolder holder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.list_layout, null);
            holder = new AppListViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (AppListViewHolder) v.getTag();
        }
        // Set app name
        holder.appName.setText(mList.get(position).getName());
        // Set app icon
        Ion.with(holder.appIcon).load(mList.get(position).getAppImgUrl());
        return v;
    }

    private class AppListViewHolder {
        public TextView appName;
        public ImageView appIcon;
        public AppListViewHolder(View base) {
            appName = (TextView) base.findViewById(R.id.appName);
            appIcon = (ImageView) base.findViewById(R.id.appIcon);
        }
    }
}
