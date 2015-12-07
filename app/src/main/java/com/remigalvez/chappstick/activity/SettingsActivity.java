package com.remigalvez.chappstick.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.remigalvez.chappstick.PersistanceManager;
import com.remigalvez.chappstick.R;
import com.remigalvez.chappstick.objects.User;
import com.remigalvez.chappstick.util.DatabaseUtils;

public class SettingsActivity extends AppCompatActivity {

    private User mUser;

    PersistanceManager mPersistanceManager;

    private EditText mFirstNameET;
    private EditText mLastNameET;
    private EditText mCcNumberET;
    private EditText mCcExpMonthET;
    private EditText mCcExpYearET;
    private Button mSaveBtn;
    private Button mLogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mUser = User.getInstance();
        mPersistanceManager = new PersistanceManager(this);

        initViews();
        setUserFields();

    }

    private void setUserFields() {
        mFirstNameET.setText(mUser.getFirstName());
        mLastNameET.setText(mUser.getLastName());
    }

    private void initViews() {
        getSupportActionBar().hide();
        mFirstNameET  = (EditText) findViewById(R.id.settingsFirstNameET);
        mLastNameET = (EditText) findViewById(R.id.settingsLastNameET);
        mCcNumberET = (EditText) findViewById(R.id.ccNumberET);
        mCcExpMonthET = (EditText) findViewById(R.id.ccExpMonthET);
        mCcExpYearET = (EditText) findViewById(R.id.ccExpYearET);
        mSaveBtn = (Button) findViewById(R.id.settingsSaveBtn);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.setFirstName(mFirstNameET.getText().toString());
                mUser.setLastName(mLastNameET.getText().toString());
                DatabaseUtils.saveUserChanges(mUser);
                String ccNumber = mCcNumberET.getText().toString();
                String ccMonth = mCcExpMonthET.getText().toString();
                String ccYear = mCcExpYearET.getText().toString();
                storeCcOnDevice(ccNumber, ccMonth, ccYear);
                finish();
            }
        });
        mLogoutBtn = (Button) findViewById(R.id.settingsLogoutBtn);
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        setCcInfo();
    }

    private void logout() {
        User.reset();
        mPersistanceManager.clear();
        startLoginActivity();
    }

    private void startLoginActivity() {
        Intent openMainActivity= new Intent(SettingsActivity.this, LoginActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(openMainActivity);
    }

    private void setCcInfo() {
        mCcNumberET.setText(mPersistanceManager.loadCreditCardNumber());
        mCcExpMonthET.setText(mPersistanceManager.loadCreditCardMonth());
        mCcExpYearET.setText(mPersistanceManager.loadCreditCardYear());
    }

    private void storeCcOnDevice(String ccNumber, String ccMonth, String ccYear) {
        mPersistanceManager.saveCreditCard(ccNumber, ccMonth, ccYear);
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
