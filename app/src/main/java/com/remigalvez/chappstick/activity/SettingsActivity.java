package com.remigalvez.chappstick.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.remigalvez.chappstick.R;
import com.remigalvez.chappstick.objects.User;
import com.remigalvez.chappstick.util.DatabaseUtils;

public class SettingsActivity extends AppCompatActivity {

    private User mUser;

    private EditText mFirstNameET;
    private EditText mLastNameET;
    private EditText mCcNumberET;
    private EditText mCcExpMonthET;
    private EditText mCcExpYearET;
    private Button mSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mUser = User.getInstance();

        initViews();
        setUserFields();

    }

    private void setUserFields() {
        mFirstNameET.setText(mUser.getFirstName());
        mLastNameET.setText(mUser.getLastName());
    }

    private void initViews() {
        mFirstNameET  = (EditText) findViewById(R.id.settingsFirstNameET);
        mLastNameET = (EditText) findViewById(R.id.settingsLastNameET);
        mCcNumberET = (EditText) findViewById(R.id.ccNumberET);
        mCcExpMonthET = (EditText) findViewById(R.id.ccExpMonthET);
        mCcExpYearET = (EditText) findViewById(R.id.ccExpYearET);
        mSaveBtn = (Button) findViewById(R.id.settingsSaveBtn);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Save changes to Parse
                mUser.setFirstName(mFirstNameET.getText().toString());
                mUser.setLastName(mLastNameET.getText().toString());
                DatabaseUtils.saveUserChanges(mUser);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
