package com.remigalvez.chappstick.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.remigalvez.chappstick.R;
import com.remigalvez.chappstick.adapter.AppIconAdapter;
import com.remigalvez.chappstick.objects.App;
import com.remigalvez.chappstick.objects.User;
import com.remigalvez.chappstick.util.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.OnClickListener;
import static android.widget.AdapterView.OnItemClickListener;

public class DeleteAppsActivity extends AppCompatActivity implements OnItemClickListener {
    private static final String TAG = "DeleteAppsActivity";

    private User mUser;
    private static DeleteAppsActivity activity;

    private ListView mAppListView;
    private Button mDeleteAppsDoneBtn;

    private List<App> mAppList;
    private AppIconAdapter mListAdapter;
    private List<App> mSelectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_apps);
        activity = this;
        mUser = User.getInstance();
        mSelectedItems = new ArrayList<>();

        initViews();
        // Assign variables
        mAppList = new ArrayList<>();
        mListAdapter = new AppIconAdapter(this, mAppList);
        // Link adapter to list view
        mAppListView.setAdapter(mListAdapter);
        // Display user apps
        addItemListToList(mUser.getApps());
    }

    private void initViews() {
        mAppListView = (ListView) findViewById(R.id.deleteAppsList);
        mDeleteAppsDoneBtn = (Button) findViewById(R.id.deleteAppsDoneBtn);
        mAppListView.setOnItemClickListener(this);
        mDeleteAppsDoneBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteApps(mSelectedItems);
                finish();
            }
        });
    }

    // Delete apps from array list
    private void deleteApps(List<App> apps) {
        mUser.removeFromAppList(apps);
        DatabaseUtils.removeAppsList(mSelectedItems);
        removeItemListToList(apps);
    }

    private void removeItemListToList(List<App> apps) {
        for (int i = 0; i < apps.size(); i++) {
            removeItemFromList(apps.get(i));
        }
    }

    private void removeItemFromList(App app) {
        if (mUser.getApps().contains(app)) {
            mAppList.remove(app);
            mListAdapter.notifyDataSetChanged();
        }
    }

    private void addItemListToList(List<App> apps) {
        for (int i = 0; i < apps.size(); i++) {
            addItemToList(apps.get(i));
        }
    }


    public void addItemToList(App app) {
        mAppList.add(app);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Handle click on app item
        App selectedApp = mAppList.get(position);
        if (!mSelectedItems.contains(selectedApp)) {
            mSelectedItems.add(selectedApp);
            view.setActivated(true);
        } else {
            for (int i = 0; i < mSelectedItems.size(); i++) {
                if (mSelectedItems.get(i).equals(selectedApp)) {
                    mSelectedItems.remove(i);
                }
            }
            view.setActivated(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete_apps, menu);
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

    public static DeleteAppsActivity getInstance() {
        return activity;
    }
}
