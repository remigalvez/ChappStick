package com.remigalvez.chappstick.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.remigalvez.chappstick.R;
import com.remigalvez.chappstick.User;
import com.remigalvez.chappstick.adapter.HomeIconAdapter;
import com.remigalvez.chappstick.objects.App;

import java.util.ArrayList;
import java.util.List;

public class HomescreenActivity extends AppCompatActivity {
    private static final String TAG = "HomescreenActivity";

    User user;

    private ListView appListView;

    private HomeIconAdapter adapter;
    private List<String> appsIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        user = new User("hello");
        mInitControls();
    }

    private void mInitControls() {
        appListView = (ListView) findViewById(R.id.appListView);

        appsIdList = new ArrayList<>();
        adapter = new HomeIconAdapter(HomescreenActivity.this, new ArrayList<App>());
        appListView.setAdapter(adapter);

        for (int i = 0; i < user.getApps().size(); i++) {

        }
    }

    private App createApp(String appTitle) {
        App app = new App();
        app.setCount(adapter.getCount());
        return null;
    }

    public void displayApp(App app) {
        adapter.add(app);
        adapter.notifyDataSetChanged();
//        scroll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conversation_list, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
