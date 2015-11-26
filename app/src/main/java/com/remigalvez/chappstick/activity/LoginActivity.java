package com.remigalvez.chappstick.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.remigalvez.chappstick.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText mUsernameET;
    private EditText mPasswordET;
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "1A5G2YPnZmvYoDII7wkZYoMPk0NKm3JKUAiXPsD2", "jFusc2WaHUGIDscXCjsIWiJQ00yv9i62jzsyIwjq");

        initViews();
    }

    private void initViews() {
        mUsernameET = (EditText) findViewById(R.id.username);
        mPasswordET = (EditText) findViewById(R.id.password);
        mLoginBtn = (Button) findViewById(R.id.login);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Login button clicked");
                Log.d(TAG, mUsernameET.getText().toString() + ":" + mPasswordET.getText().toString());
                ParseUser.logInInBackground(mUsernameET.getText().toString(), mPasswordET.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        Log.d(TAG, "Done!");
                        if (user != null) {
                            Log.d(TAG, "User found.");
                            Log.d(TAG, user.getObjectId());
                            Log.d(TAG, user.getUsername());
//                            User.instantiateUser(user);
                        } else {
                            Log.d(TAG, "No user found...");
                        }

                        if (e != null) {
                            e.printStackTrace();
                        } else {
                            Log.d(TAG, "No error!");
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
