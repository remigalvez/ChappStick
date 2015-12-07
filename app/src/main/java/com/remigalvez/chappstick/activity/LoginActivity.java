package com.remigalvez.chappstick.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.remigalvez.chappstick.PersistanceManager;
import com.remigalvez.chappstick.R;
import com.remigalvez.chappstick.objects.User;
import com.remigalvez.chappstick.sensor.LocationFinder;
import com.remigalvez.chappstick.util.DatabaseUtils;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private static LoginActivity activity;

    private PersistanceManager mPersistanceManager;

    private EditText mUsernameET;
    private EditText mPasswordET;
    private Button mLoginBtn;
    private Button mSignUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Store info accross sessions
        activity = this;
        new LocationFinder(this);

        DatabaseUtils.initParse(this);
        mPersistanceManager = new PersistanceManager(this);
        if (!mPersistanceManager.loadUsername().equals("")
                && !mPersistanceManager.loadPassword().equals("")) {
            login(mPersistanceManager.loadUsername(), mPersistanceManager.loadPassword());
        }
        initViews();
    }

    private void initViews() {
        getSupportActionBar().hide();
        mUsernameET = (EditText) findViewById(R.id.username);
        mPasswordET = (EditText) findViewById(R.id.password);
        mSignUpBtn = (Button) findViewById(R.id.signUp);
        mLoginBtn = (Button) findViewById(R.id.login);
        // Set click listener for login button
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameET.getText().toString();
                String password = mPasswordET.getText().toString();
                saveCredentials(username, password);
                login(username, password);
            }
        });
        // Set click listener for sign up button
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignupActivity();
            }
        });
    }

    private void saveCredentials(String username, String password) {
        mPersistanceManager.saveCredentials(username, password);
    }

    // Log in + Sign up logic
    private void login(String username, String password) {
        new User(username, password);
    }

    private void startSignupActivity() {
        Intent intent = new Intent(this, SignupActivity.class);
        this.startActivity(intent);
    }

    public void showToast(int stringResourceId){
        Toast toast = Toast.makeText(this,stringResourceId,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static Context getInstance() {
        return activity;
    }

}
