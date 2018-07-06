/**
 * Author : Anirudh Srivastav
 * Date :Sunday 24th June 2018
 */
package com.novaorbis.anirudh.heur.chatRoom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.novaorbis.anirudh.heur.R;
import com.novaorbis.anirudh.heur.dbHelpers.msgHelper;

import java.util.ArrayList;
import java.util.List;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class heurActivity extends AppCompatActivity {

    //Message Queue status
    public static final String EXTRA_REPLY = "com.example.android.msgList.MSG_SENT";

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    //Firebase Instance Variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    //Firebase Instance Variables
    private DatabaseReference mFirebaseDatabaseReference;
    private String SENDER = FirebaseInstanceId.getInstance().getId();
    private String DBREF = "Messages";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convo);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.icon);

        ChatView mMessageCache = findViewById(R.id.message);
        mMessageCache.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {
                chatMessage.setType(ChatMessage.Type.SENT);
                //save message to device
                final List<ChatMessage> msgCache = new ArrayList<ChatMessage>();
                msgCache.add(chatMessage);
                new msgHelper().saveFavorites(getApplicationContext(),msgCache);
                new sendAsync(chatMessage).execute();
                return true;
            }
        });
        if (msgHelper.getFavorites(getApplicationContext()) != null)
        {
            mMessageCache.addMessages(msgHelper.getFavorites(getApplicationContext()));
        }



    }
   @SuppressLint("StaticFieldLeak")
   private class sendAsync extends AsyncTask<String ,Void,Boolean>  {

        ChatMessage senMsg;
        private sendAsync(ChatMessage chatMessage)
        {
            this.senMsg = chatMessage;
        }
        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                mFirebaseDatabaseReference.push()
                        .child(DBREF)
                        .setValue(senMsg);
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
        }
    }


    public long timeStamp()
    {
        return System.currentTimeMillis()/1000;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
