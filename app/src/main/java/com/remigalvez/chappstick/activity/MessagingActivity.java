package com.remigalvez.chappstick.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.remigalvez.chappstick.R;
import com.remigalvez.chappstick.objects.User;
import com.remigalvez.chappstick.Utils;
import com.remigalvez.chappstick.adapter.ChatAdapter;
import com.remigalvez.chappstick.asynctask.QueryServerAsyncTask.QueryCompletionListener;
import com.remigalvez.chappstick.objects.App;
import com.remigalvez.chappstick.objects.ChatMessage;
import com.remigalvez.chappstick.parse.ParseKey;
import com.remigalvez.chappstick.sensor.ShakeManager;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.remigalvez.chappstick.sensor.ShakeManager.*;

public class MessagingActivity extends AppCompatActivity implements QueryCompletionListener, ShakeListener {
    private static final String TAG = "MessagingActivity";

    private App mApp;
    private User mUser;

    private QueryCompletionListener mResponseListener = this;
    private ShakeManager mShakeManager;

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;

    private String reqPrefix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        mShakeManager = new ShakeManager(this);
        mShakeManager.setListener(this);

        mUser = User.getInstance();
        initControls();
        // Get extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String appId = extras.getString("appId");
            mApp = mUser.getAppFromId(appId);
        } else {
            // TODO: Handle error
            Log.d(TAG, "No app specified in extras");
        }

        reqPrefix = ParseKey.APP + "/" + mApp.getId() + "/";

        initApp();

    }

    @Override
    protected void onResume(){
        super.onResume();

        mShakeManager.turnOn();
    }

    @Override
    protected void onPause(){
        super.onPause();

        mShakeManager.turnOff();
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        RelativeLayout container = (RelativeLayout) findViewById(R.id.messageContainer);

        chatHistory = new ArrayList<>();
        adapter = new ChatAdapter(MessagingActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        sendBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String messageText = messageET.getText().toString();
                        Utils.request(reqPrefix + messageText, mResponseListener);
                        if (TextUtils.isEmpty(messageText)) {
                            return;
                        }
                        messageET.setText("");
                        ChatMessage message = createChatMessageObject(messageText, true);
                        displayMessage(message);
                    }
                });
    }
    
    private void initApp() {
        setTitle(mApp.getName());
        sendMessage("Hi " + mUser.getFirstName() + ", welcome to " + mApp.getName(), false);
        sendMessage(mApp.getWelcomeMessage(), false);
    }

    private ChatMessage createChatMessageObject(String message, boolean fromMe) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(adapter.getCount());
        chatMessage.setMessage(message);
        chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatMessage.setMe(fromMe);
        return chatMessage;
    }


    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    protected void sendMessage(String message, boolean me) {
        ChatMessage chatMessage = createChatMessageObject(message, me);
        displayMessage(chatMessage);
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_messaging, menu);
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
            Intent intent = new Intent(this, HomescreenActivity.class);
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void responseReceived(JSONObject data) {
        Log.d(TAG, data.toString());
        ChatMessage msg = new ChatMessage();
        msg.setId(adapter.getCount());
        msg.setMe(false);
        String message = "";
        try { message = data.getString("message"); } catch (Exception e) { message = "Unable to parse JSONObject."; }
        msg.setMessage(message);
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        displayMessage(msg);
        chatHistory.add(msg);
    }

    @Override
    public void noResponseReceived() {
        Log.d(TAG, "No Response...");
        ChatMessage msg = new ChatMessage();
        msg.setId(adapter.getCount());
        msg.setMe(false);
        msg.setMessage("No Response...");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        displayMessage(msg);
    }

    @Override
    public void shakeDetected() {
        adapter.clear();
        Log.d(TAG, "Shake detected!");
    }
}
