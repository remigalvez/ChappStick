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
import com.remigalvez.chappstick.Utils;
import com.remigalvez.chappstick.adapter.ChatAdapter;
import com.remigalvez.chappstick.asynctask.QueryServerAsyncTask.QueryCompletionListener;
import com.remigalvez.chappstick.objects.App;
import com.remigalvez.chappstick.objects.ChatMessage;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessagingActivity extends AppCompatActivity implements QueryCompletionListener {
    private static final String TAG = "MessagingActivity";

    private App mApp;

    protected String mAppId;

    QueryCompletionListener mResponseListener = this;

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mAppId = extras.getString("appId");
        } else {
            this.mAppId = "";
        }

        initControls();

        initApp();

    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        RelativeLayout container = (RelativeLayout) findViewById(R.id.messageContainer);

        chatHistory = new ArrayList<ChatMessage>();
        adapter = new ChatAdapter(MessagingActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        sendBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String messageText = messageET.getText().toString();
                        Utils.request("", mResponseListener);
//                Utils.request(messageText, mResponseListener);
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
        Utils.request("App/" + mAppId, new QueryCompletionListener() {
            @Override
            public void responseReceived(JSONObject data) {
                mApp = App.createFromJSON(data);
                setTitle(mApp.getName());
                sendMessage(mApp.getWelcomeMessage(), false);
            }

            @Override
            public void noResponseReceived() {
                Log.d(TAG, "Couldn't get app");
            }
        });
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
        msg.setMessage(data.toString());
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
}
