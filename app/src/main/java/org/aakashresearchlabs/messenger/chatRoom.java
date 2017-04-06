package org.aakashresearchlabs.messenger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class chatRoom extends AppCompatActivity {
    private ListView messagesContainer;
    private EditText messageET;
    private TextView sendTextButton;
    private String user_name;
    private String temp_key;
    private String room_name;
    private ChatAdapter adapter;
    private DatabaseReference root;
    private ArrayList<ChatMessage> chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatHistory = new ArrayList<ChatMessage>();
        setContentView(R.layout.activity_chat_room);
        Toolbar chattoolbar = (Toolbar) findViewById(R.id.tool_chatroom);

        user_name = getIntent().getExtras().get("user_name").toString();
        room_name=getIntent().getExtras().get("room_name").toString();
        chattoolbar.setTitle("Chat Room");
        setSupportActionBar(chattoolbar);
        root = FirebaseDatabase.getInstance().getReference().child(room_name);
        initControls();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chatroom_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_viewMembers:
                Toast.makeText(this, "View Members", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_clearChat:
                Toast.makeText(this, "Clear Chat", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_report:
                Toast.makeText(this, "Report", Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendTextButton = (TextView) findViewById(R.id.sendButton);

        sendTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                } else {
                    Map<String, Object> map = new HashMap<String, Object>();
                    temp_key = root.push().getKey();
                    root.updateChildren(map);

                    DatabaseReference message_root = root.child(temp_key);
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("name", user_name);
                    map2.put("msg", messageText);
                    message_root.updateChildren(map2);
                }
//                ChatMessage chatMessage = new ChatMessage();
//                chatMessage.setId(122);//dummy
//                chatMessage.setMessage(messageText);
//                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//                chatMessage.setMe(true);
//
//                messageET.setText("");

//                displayMessage(chatMessage);
            }
        });
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                loadDummyHistory(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                loadDummyHistory(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        int flag=1;
//        int flag1=0;
//        int flag2=0;
//        while (i.hasNext()) {
//            ++flag1;
//        }
        while (i.hasNext()) {
            ChatMessage msg = new ChatMessage();
            msg.setId(flag);
            msg.setMe(true);
            msg.setMessage((String) ((DataSnapshot) i.next()).getValue());
            msg.setUser((String)((DataSnapshot)i.next()).getValue());
            msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatHistory.add(msg);
            flag++;
        }
            adapter = new ChatAdapter(chatRoom.this, new ArrayList<ChatMessage>());
            messagesContainer.setAdapter(adapter);

            for (int y = 0; y < chatHistory.size(); y++) {
                ChatMessage message = chatHistory.get(y);
                displayMessage(message);
            }
    }
}