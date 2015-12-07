package com.remigalvez.chappstick.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.remigalvez.chappstick.R;
import com.remigalvez.chappstick.adapter.AppIconAdapter;
import com.remigalvez.chappstick.objects.App;
import com.remigalvez.chappstick.objects.User;

import java.util.ArrayList;
import java.util.List;

public class HomescreenActivity extends AppCompatActivity implements OnItemClickListener {
    private static final String TAG = "HomescreenActivity";

    private static boolean ACTIVE;

    private User mUser;
    private static HomescreenActivity activity;

    private ListView mAppListView;
    private Button mAddAppsBtn;

    private List<App> mAppList;
    private AppIconAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        activity = this;
        setActive(true);

        initViews();
        // Assign variables
        mAppList = new ArrayList<>();
        mListAdapter = new AppIconAdapter(this, mAppList);
        // Link adapter to list view
        mAppListView.setAdapter(mListAdapter);
    }

    private void initViews() {
        mAppListView = (ListView) findViewById(R.id.userAppList);
        mAddAppsBtn = (Button) findViewById(R.id.userAddAppsBtn);
        mAppListView.setOnItemClickListener(this);
        mAddAppsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addApps();
            }
        });
    }

    private void addApps() {
        Intent intent = new Intent(this, AddAppsActivity.class);
        activity.startActivity(intent);
    }

    public void addItemToList(App app) {
        mAppList.add(app);
        mListAdapter.notifyDataSetChanged();
    }

    public void removeItemFromList(App app) {
        mAppList.remove(app);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Handle click on app item
        Intent intent = new Intent(this, MessagingActivity.class);
        intent.putExtra("appId", mAppList.get(position).getId());
        this.startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        setActive(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setActive(true);
        refreshDisplay();
    }

    private void refreshDisplay() {
        if (mUser != null) {
            mAppList.clear();
            List<App> userAppList = mUser.getApps();
            for (int i = 0; i < userAppList.size(); i++) {
                mAppList.add(userAppList.get(i));
            }
            mListAdapter.notifyDataSetChanged();
        } else {
            // TODO: Handle null user
            Log.d(TAG, "User does not exist... Yet.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homescreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUser(User user) {
        mUser = user;
    }

    public static boolean isActive() {
        return ACTIVE;
    }

    public static void setActive(boolean active) {
        ACTIVE = active;
    }

    public static HomescreenActivity getInstance() {
        return activity;
    }
}
