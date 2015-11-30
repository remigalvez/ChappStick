package com.remigalvez.chappstick.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.remigalvez.chappstick.ParseUtils;
import com.remigalvez.chappstick.R;
import com.remigalvez.chappstick.objects.User;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private EditText mFirstNameET;
    private EditText mLastNameET;
    private EditText mEmailET;
    private EditText mPasswordET;
    private EditText mPassWordConfirmET;
    private Button mSignUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
    }

    private void initViews() {
        mFirstNameET = (EditText) findViewById(R.id.signupFirstName);
        mLastNameET = (EditText) findViewById(R.id.signupLastName);
        mEmailET = (EditText) findViewById(R.id.signupEmail);
        mPasswordET = (EditText) findViewById(R.id.signupPassword);
        mPassWordConfirmET = (EditText) findViewById(R.id.signupPasswordConfirmation);
        mSignUpBtn = (Button) findViewById(R.id.enterSignUpBtn);
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = mFirstNameET.getText().toString();
                String lastName = mLastNameET.getText().toString();
                String email = mEmailET.getText().toString();
                String password = mPasswordET.getText().toString();
                String passwordConfirm = mPassWordConfirmET.getText().toString();

                if (password.equals(passwordConfirm)) {
                    User user = new User(firstName, lastName, email, password);
                    signup(user);
                } else {
                    // TODO: Handle error
                    Log.d(TAG, "Passwords do not match!");
                }
            }
        });
    }

    private void signup(User user) {
        ParseUtils.signup(user, new ParseUtils.CompletionListener() {
            @Override
            public void responseReceived() {

            }
            @Override
            public void noResponseReceived() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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
