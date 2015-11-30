package com.remigalvez.chappstick.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.remigalvez.chappstick.ParseUtils;
import com.remigalvez.chappstick.R;
import com.remigalvez.chappstick.objects.User;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText mUsernameET;
    private EditText mPasswordET;
    private Button mLoginBtn;
    private Button mSignUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ParseUtils.initParse(this);

        initViews();
    }

    private void initViews() {
        mUsernameET = (EditText) findViewById(R.id.username);
        mPasswordET = (EditText) findViewById(R.id.password);
        mSignUpBtn = (Button) findViewById(R.id.signUp);
        mUsernameET.setText("remigalvez");
        mPasswordET.setText("test");
        mLoginBtn = (Button) findViewById(R.id.login);
        // Set click listener for login button
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameET.getText().toString();
                String password = mPasswordET.getText().toString();
                login(username, password);
            }
        });
        // Set click listener for sign up button
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    private void login(String username, String password) {
        new User(username, password);
        Intent intent = new Intent(this, HomescreenActivity.class);
        this.startActivity(intent);
    }

    private void signup() {

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
