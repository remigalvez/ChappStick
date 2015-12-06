package com.remigalvez.chappstick.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.remigalvez.chappstick.R;

public class SettingsActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText ccNumber;
    EditText ccExpMonth;
    EditText ccExpYear;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
    }

    private void initViews() {
        firstName = (EditText) findViewById(R.id.settingsFirstNameET);
        lastName = (EditText) findViewById(R.id.settingsLastNameET);
        ccNumber = (EditText) findViewById(R.id.ccNumberET);
        ccExpMonth = (EditText) findViewById(R.id.ccExpMonthET);
        ccExpYear = (EditText) findViewById(R.id.ccExpYearET);
        saveBtn = (Button) findViewById(R.id.settingsSaveBtn);
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
