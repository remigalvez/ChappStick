package com.remigalvez.chappstick.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.remigalvez.chappstick.R;
import com.remigalvez.chappstick.adapter.AppIconAdapter;
import com.remigalvez.chappstick.objects.App;
import com.remigalvez.chappstick.objects.User;
import com.remigalvez.chappstick.util.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.OnClickListener;

public class AddAppsActivity extends AppCompatActivity implements OnItemClickListener {
    private static final String TAG = "AddAppsActivity";

    private User mUser;
    private static AddAppsActivity activity;

    private ListView mAppListView;
    private Button mAddAppsDoneBtn;
    private Button mAddAppsDeleteBtn;

    private List<App> mAppList;
    private AppIconAdapter mListAdapter;
    private List<App> mSelectedItems;

    public interface CompletionListener {
        void responseReceived(List<App> apps);
        void noResponseReceived();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apps);
        activity = this;
        // TODO: Handle null pointer?
        mUser = User.getInstance();
        mSelectedItems = new ArrayList<>();

        DatabaseUtils.getAllApps(new CompletionListener() {
            @Override
            public void responseReceived(List<App> apps) {
                addItemListToList(apps);
            }

            @Override
            public void noResponseReceived() {
                Log.d(TAG, "No response received...");
                showToast(R.string.noResponse);
            }
        });

        initViews();
        // Assign variables
        mAppList = new ArrayList<>();
        mListAdapter = new AppIconAdapter(this, mAppList);
        // Link adapter to list view
        mAppListView.setAdapter(mListAdapter);
    }

    private void showToast(int stringResourceId){
        Toast toast = Toast.makeText(this,stringResourceId,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void initViews() {
        mAppListView = (ListView) findViewById(R.id.addAppsList);
        mAddAppsDoneBtn = (Button) findViewById(R.id.addAppsDoneBtn);
        mAddAppsDeleteBtn = (Button) findViewById(R.id.addAppsDeleteBtn);
        mAppListView.setOnItemClickListener(this);
        mAddAppsDoneBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.addAppList(mSelectedItems);
                DatabaseUtils.appendAppsList(mSelectedItems);
                addItemListToList(mSelectedItems);
                finish();
            }
        });
        mAddAppsDeleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDeleteAppsActivity();
            }
        });
    }

    private void startDeleteAppsActivity() {
        Intent intent = new Intent(this, DeleteAppsActivity.class);
        this.startActivity(intent);
    }

    private void addItemListToList(List<App> apps) {
        for (int i = 0; i < apps.size(); i++) {
            addItemToList(apps.get(i));
        }
    }


    public void addItemToList(App app) {
        if (!mUser.getApps().contains(app)) {
            mAppList.add(app);
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Handle click on app item
//        mUser.addApp(mAppList.get(position));
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
        getMenuInflater().inflate(R.menu.menu_add_apps, menu);
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

    public static AddAppsActivity getInstance() {
        return activity;
    }

}
