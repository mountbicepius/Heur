package com.novaorbis.anirudh.heur.chatRoom;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.novaorbis.anirudh.heur.R;
import com.novaorbis.anirudh.heur.dbHelpers.Msgs;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class convoActivity extends AppCompatActivity {

    //Message Queue status
    public static final String EXTRA_REPLY = "com.example.android.msgList.MSG_SENT";

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private List<Msgs> msgList =new ArrayList<Msgs>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convo);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.icon);
        //Message Buffer
        //Layout for storing messages
        LinearLayoutManager obj = new LinearLayoutManager(this);
        obj.setStackFromEnd(true);
        RecyclerView mMessageCache = findViewById(R.id.messageRecyclerView);
        mMessageCache.setLayoutManager(obj);
        // Create the data adapter with above data list.
        final sentAdapter chatAppMsgAdapter = new sentAdapter(this , msgList);
        // Set data adapter to RecyclerView.
        mMessageCache.setAdapter(chatAppMsgAdapter);
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.

        final EditText mSendMsg = findViewById(R.id.messageEditText);
        Button mSend = findViewById(R.id.sendButton);
        // Send Messages on click
        mSend.setOnClickListener((View v) -> {
                String msgContent = mSendMsg.getText().toString();

                if(!TextUtils.isEmpty(msgContent))
                {
                    Msgs msgSnt = new Msgs(Msgs.MSG_TYPE_SENT, msgContent, timeStamp());
                    new sendAsync(msgSnt).execute();
                    int newMsgPosition = msgList.size() - 1;
                    // Notify recycler view insert one new data.
                    chatAppMsgAdapter.notifyItemInserted(newMsgPosition);
                    //Assign Message values and save to LocalDb
                    // Scroll RecyclerView to the last message.
                    mMessageCache.scrollToPosition(newMsgPosition);
                    // Empty the input edit text box.
                    mSendMsg.setText(" ");

                }
        });
    }
    public class sendAsync extends AsyncTask<URL,String ,Void>
    {
        Msgs msgSnt;
        public sendAsync(Msgs obj)
        {
            this.msgSnt = obj;
        }

        @Override
        protected Void doInBackground(URL... urls) {
            // Add a new sent message to the list.
            assert msgSnt !=null;
            msgList.add(msgSnt);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    public long timeStamp()
    {
        return System.currentTimeMillis()/1000;
    }

//RecyclerView Adapter class
    public class sentAdapter extends RecyclerView.Adapter<recievedMsg>
    {
        List<Msgs> convosMsgList;
        private Context ctxt;
        public sentAdapter(Context ctxt , List<Msgs> dataList)
        {
             this.ctxt = ctxt;
             this.convosMsgList = dataList;
        }

        @Override
        public recievedMsg onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.bubble, parent, false);
            return new recievedMsg(view);
        }

        @Override
        public void onBindViewHolder(final recievedMsg holder, int position) {
            Msgs text = this.convosMsgList.get(position);
            // If the message is a received message.
            if(text.MSG_TYPE_RECEIVED.equals(text.getMsgType())) {
            // Show received message in left linearlayout.
                    holder.leftMsgLayout.setVisibility(LinearLayout.VISIBLE);
                    holder.leftMsgTextView.setText(text.getMsgContent());
            // Remove left linearlayout.The value should be GONE, can not be INVISIBLE // Otherwise each iteview's distance is too big.
                holder.rightMsgLayout.setVisibility(LinearLayout.GONE);
                    }
            // If the message is a sent message.
            else if(text.MSG_TYPE_SENT.equals(text.getMsgType())) {
            // Show sent message in right linearlayout.
              holder.rightMsgLayout.setVisibility(LinearLayout.VISIBLE);
              holder.rightMsgTextView.setText(text.getMsgContent());
            // Remove left linearlayout.The value should be GONE, can not be INVISIBLE // Otherwise each iteview's distance is too big.
             holder.leftMsgLayout.setVisibility(LinearLayout.GONE);
             }
        }

        @Override
        public int getItemCount() {
            if(convosMsgList==null)
            {
                convosMsgList = new ArrayList<Msgs>();
            }
            return convosMsgList.size();
        }
        void setWords(List<Msgs> words){
            convosMsgList = words;
            notifyDataSetChanged();
        }

    }
    public class recievedMsg extends RecyclerView.ViewHolder
    {
        LinearLayout leftMsgLayout;
        LinearLayout rightMsgLayout;
        TextView leftMsgTextView;
        TextView rightMsgTextView;

        public recievedMsg(View itemView) {
            super(itemView);
            if(itemView!=null) {
                leftMsgLayout = (LinearLayout) itemView.findViewById(R.id.chat_left_msg_layout);
                rightMsgLayout = (LinearLayout) itemView.findViewById(R.id.chat_right_msg_layout);
                leftMsgTextView = (TextView) itemView.findViewById(R.id.recieved);
                rightMsgTextView = (TextView) itemView.findViewById(R.id.sent);
            }
        }
    }

}
