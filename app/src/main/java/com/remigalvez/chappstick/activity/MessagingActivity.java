package com.remigalvez.chappstick.activity;

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
import com.remigalvez.chappstick.asynctask.QueryServerAsyncTask.QueryCompletionListener;
import com.remigalvez.chappstick.objects.ChatAdapter;
import com.remigalvez.chappstick.objects.ChatMessage;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class MessagingActivity extends AppCompatActivity implements QueryCompletionListener {
    private static final String TAG = "MessagingActivity";

    QueryCompletionListener mResponseListener = this;

    protected String mAppName;
    protected String mWelcomeMessage;

    protected int messageCount = 0;

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        initControls();
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);

        chatHistory = new ArrayList<ChatMessage>();
        adapter = new ChatAdapter(MessagingActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        sendBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "Button pressed.");
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

    private ChatMessage createChatMessageObject(String message, boolean fromMe) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(messageCount);
        chatMessage.setMessage(message);
        chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatMessage.setMe(fromMe);
        return chatMessage;
    }


    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
        messageCount++;
    }

    protected void sendWelcomeMessage(String welcomeMessage) {
        Log.d(TAG, welcomeMessage);
        ChatMessage welcomeChatMessage = createChatMessageObject(welcomeMessage, false);
        displayMessage(welcomeChatMessage);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void responseReceived(JSONObject data) {
        Log.d(TAG, data.toString());
        ChatMessage msg = new ChatMessage();
        msg.setId(messageCount);
        msg.setMe(false);
        msg.setMessage(data.toString());
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        displayMessage(msg);
        chatHistory.add(msg);
        messageCount++;
    }

    @Override
    public void noResponseReceived() {
        Log.d(TAG, "No Response...");
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(4);
        msg1.setMe(false);
        msg1.setMessage("No Response...");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        displayMessage(msg1);
        messageCount++;
    }
}
