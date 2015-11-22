package com.remigalvez.chappstick.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.remigalvez.chappstick.R;
import com.remigalvez.chappstick.objects.App;
import com.remigalvez.chappstick.objects.ChatMessage;

import java.util.List;

/**
 * Created by Remi on 11/12/15.
 */
public class HomeIconAdapter extends BaseAdapter {

    private List<App> apps;
    private Activity mContext;

    public HomeIconAdapter(Activity context, List<App> appList) {
        this.mContext = context;
        this.apps = appList;
    }

    public void initApps() {

    }

    public void renderAppIcon() {

    }

    @Override
    public int getCount() {
        if (apps != null)
            apps.size();
        return 0;
    }

    @Override
    public App getItem(int position) {
        if (apps != null)
            return apps.get(position);
        return null;
    }

    public void add(App app) {
        apps.add(app);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        App app = getItem(position);
        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = vi.inflate(R.layout.home_icon, null);
            holder = mCreateViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setAlignment(holder, app);
        holder.appTitle.setText(app.getName());
        holder.appImg.setImageDrawable(app.getAppImgUrl());

        return convertView;
    }

    private void setAlignment(ViewHolder holder, App app) {
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) holder.content.getLayoutParams();
        layoutParams.gravity = Gravity.LEFT;
        holder.content.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams lp =
                (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        holder.content.setLayoutParams(lp);

        layoutParams = (LinearLayout.LayoutParams) holder.content.getLayoutParams();
        layoutParams.gravity = Gravity.LEFT;
        holder.appTitle.setLayoutParams(layoutParams);
    }

    private ViewHolder mCreateViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.appTitle = (TextView) v.findViewById(R.id.app_title);
        holder.content = (LinearLayout) v.findViewById(R.id.home_icon_content);
        holder.appImg = (ImageView) v.findViewById(R.id.app_icon);
        return holder;
    }

    private static class ViewHolder {
        public TextView appTitle;
        public ImageView appImg;
        public LinearLayout content;
    }
}
