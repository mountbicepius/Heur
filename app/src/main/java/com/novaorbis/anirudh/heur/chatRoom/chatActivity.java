/**
 * Author : Anirudh Srivastav
 * Date :Sunday 24th June 2018
 */
package com.novaorbis.anirudh.heur.chatRoom;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.novaorbis.anirudh.heur.R;
import com.novaorbis.anirudh.heur.dbHelpers.msgHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;


public class chatActivity extends AppCompatActivity {

    //Message Queue status
    public static final String EXTRA_REPLY = "com.novaorbis.anirudh.heur.briefhelpers";
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = "Themonic";
    //Chat UI
    ChatView mMessageCache;

    //Firebase Instance Variables
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser mFirebaseUser= mFirebaseAuth.getCurrentUser();
    //Firebase Instance Variables
    //private DatabaseReference mFirebaseDatabaseReference;
    private String mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.mUser= getIntent().getStringExtra("EXTRA_CONTACT_JID");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.icon);
        getSupportActionBar().setTitle(mUser);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager = null;
                notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
                notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                        channelName, NotificationManager.IMPORTANCE_LOW));
        }
        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]
        //mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();


        mMessageCache = findViewById(R.id.message);
        mMessageCache.setOnSentMessageListener(chatMessage -> {
            chatMessage.setType(ChatMessage.Type.SENT);
            //save message to device
            final List<ChatMessage> msgCache = new ArrayList<ChatMessage>();
            msgCache.add(chatMessage);
            new msgHelper().saveFavorites(getApplicationContext(),msgCache);
            msgSend(chatMessage,getDeviceID(mUser));
            return true;
        });
        if (msgHelper.getFavorites(getApplicationContext()) != null)
        {
            mMessageCache.addMessages(msgHelper.getFavorites(getApplicationContext()));
        }
    }
    public String getDeviceID(String mUsername)
    {
        final String[] deviceTokenof = new String[1];
        String url = getResources().getString(R.string.devices);
        RequestQueue devReq = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, (JSONArray response) -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object= response.getJSONObject(i);
                        deviceTokenof[0]=object.getString(mUsername);
                   }
                 catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },error ->{
            Toast.makeText(this,error.getMessage(),Toast.LENGTH_SHORT)
                    .show();
        });
        devReq.add(arrayRequest);
        Log.d(TAG,deviceTokenof[0]);
        /*
        *ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
              deviceTokenof[0] = Objects.requireNonNull(dataSnapshot.child("Users").child(mUsername).getValue()).toString();
                Log.d(TAG,deviceTokenof[0]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mFirebaseDatabaseReference.addValueEventListener(postListener);*/
        return deviceTokenof[0];

    }

   @TargetApi(Build.VERSION_CODES.CUPCAKE)
   @SuppressLint("StaticFieldLeak")
   /*public class sendAsync extends AsyncTask<String ,Void,Void>  {

        ChatMessage senMsg;
        String deviceToken=" ";
        sendAsync(ChatMessage chatMessage,String token)
        {
            this.senMsg = chatMessage;
            this.deviceToken =  token;
        }
        @Override
        protected Void doInBackground(String... strings) {
            try {

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


       @Override
       protected void onPostExecute(Void aVoid) {
           super.onPostExecute(aVoid);
       }
   }*/

    public long timeStamp()
    {
        return System.currentTimeMillis()/1000;
    }
    public void msgSend(ChatMessage senMsg,String deviceToken)
    {
        RequestQueue sendMsg = Volley.newRequestQueue(chatActivity.this);
        StringRequest msgRequest = new StringRequest(Request.Method.POST,getResources().getString(R.string.msgs), (String response) -> {
            try {
                Toast.makeText(chatActivity.this, response, Toast.LENGTH_SHORT)
                        .show();
                //Log.d(TAG, deviceToken);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(chatActivity.this,error.getMessage(),Toast.LENGTH_SHORT)
                    .show();
        }){
            /**
             * Returns a Map of parameters to be used for a POST or PUT request.  Can throw
             * {@link AuthFailureError} as authentication may be required to provide these values.
             *
             * <p>Note that you can directly override {@link #getBody()} for custom data.</p>
             *
             * @throws AuthFailureError in the event of auth failure
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                final Map<String, String> params = new HashMap<>();
                params.put("from", Objects.requireNonNull(mFirebaseUser.getDisplayName()));
                params.put("to",mUser);
                params.put("token",deviceToken);
                params.put("msgs",senMsg.getMessage());
                return super.getParams();
            }
        };
        sendMsg.add(msgRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE)
        {
            mMessageCache.addMessage(new ChatMessage(data.getStringExtra(EXTRA_REPLY),timeStamp(),ChatMessage.Type.RECEIVED));
        }
        else{
            Toast.makeText(getApplicationContext(),"Message failed", Toast.LENGTH_SHORT)
            .show();
        }
    }
}
