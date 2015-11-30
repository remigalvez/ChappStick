package com.remigalvez.chappstick.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.remigalvez.chappstick.R;
import com.remigalvez.chappstick.User;
import com.remigalvez.chappstick.adapter.HomeIconAdapter;
import com.remigalvez.chappstick.objects.App;

import java.util.ArrayList;
import java.util.List;

public class HomescreenActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final String TAG = "HomescreenActivity";

    private User mUser;
    private static HomescreenActivity activity;

    private ListView mAppListView;

    private List<App> mAppList;
    private HomeIconAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        activity = this;

        initViews();
        // Assign variables
        mAppList = new ArrayList<>();
        mListAdapter = new HomeIconAdapter(this, mAppList);
        // Link adapter to list view
        mAppListView.setAdapter(mListAdapter);
    }

    private void initViews() {
        mAppListView = (ListView) findViewById(R.id.appList);
        mAppListView.setOnItemClickListener(this);
    }

    public void addItemToList(App app) {
        mAppList.add(app);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Handle click on app item
        Intent intent = new Intent(this, MessagingActivity.class);
        intent.putExtra("appId", mAppList.get(position).getId());
        this.startActivity(intent);
    }

    public void setUser(User user) {
        mUser = user;
    }

    public static HomescreenActivity getInstance() {
        return activity;
    }
}
