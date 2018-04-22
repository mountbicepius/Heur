package com.novaorbis.anirudh.heur.chatRoom;

import android.app.Activity;
import android.os.Bundle;
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

import com.novaorbis.anirudh.heur.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class convoActivity extends Activity {

    //Message Queue status
    public static final String EXTRA_REPLY = "com.example.android.msgList.MSG_SENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convo);
        Objects.requireNonNull(getActionBar()).setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.mipmap.icon);


        //Message Buffer
        RecyclerView mMessageCache = findViewById(R.id.messageRecyclerView);


        // Create the initial data list.
        final List<convosMsg> msgDtoList = new ArrayList<convosMsg>();
         convosMsg msgDto = new convosMsg(convosMsg.MSG_TYPE_RECEIVED, "hello" , timeStamp());
        msgDtoList.add(msgDto);

        //Layout for storing messages
        LinearLayoutManager obj = new LinearLayoutManager(this);
        obj.setStackFromEnd(true);
        mMessageCache.setLayoutManager(obj);

        // Create the data adapter with above data list.
        final sentAdapter chatAppMsgAdapter = new sentAdapter(msgDtoList);

        // Set data adapter to RecyclerView.
        mMessageCache.setAdapter(chatAppMsgAdapter);
        final EditText mSendMsg = findViewById(R.id.messageEditText);
        Button mSend = findViewById(R.id.sendButton);

        // Send Messages on click
        mSend.setOnClickListener((View v) -> {

                String msgContent = mSendMsg.getText().toString();
                if(!TextUtils.isEmpty(msgContent))
                {
                    // Add a new sent message to the list.
                    convosMsg msgSnt = new convosMsg(convosMsg.MSG_TYPE_SENT, msgContent, timeStamp());
                    msgDtoList.add(msgSnt);
                    int newMsgPosition = msgDtoList.size() - 1;
                    // Notify recycler view insert one new data.
                    chatAppMsgAdapter.notifyItemInserted(newMsgPosition);
                    //Assign Message values and save to LocalDb

                    // Scroll RecyclerView to the last message.
                    mMessageCache.scrollToPosition(newMsgPosition);
                    // Empty the input edit text box.
                    mSendMsg.setText(" ");
                    finish();
                }
        });
    }



    public long timeStamp()
    {
        return System.currentTimeMillis()/1000;
    }

    public  class convosMsg
    {
        public final static String MSG_TYPE_SENT = "MSG_TYPE_SENT";
        public final static String MSG_TYPE_RECEIVED = "MSG_TYPE_RECEIVED";
        // Message content.
        private String msgContent;
// Message type.
        private String msgType;
        public convosMsg(String msgType, String msgContent , long timestmp)
        {
            this.msgType = msgType;
            this.msgContent = msgContent;
        }
        public String getMsgContent()
        {
            return msgContent;
        }
        public void setMsgContent(String msgContent)
        {
            this.msgContent = msgContent;
        }
        public String getMsgType()
        {
            return msgType;

        }
        public void setMsgType(String msgType)
        {
            this.msgType =msgType;
        }
    }


    public class sentAdapter extends RecyclerView.Adapter<recievedMsg>
    {
        List<convosMsg> convosMsgList;
        public sentAdapter(List<convosMsg> msgList)
        {
            this.convosMsgList = msgList;
        }

        @Override
        public recievedMsg onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.bubble, parent, false);
            return new recievedMsg(view);
        }

        @Override
        public void onBindViewHolder(final recievedMsg holder, int position) {
            convosMsg text = this.convosMsgList.get(position);
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
                convosMsgList = new ArrayList<convosMsg>();
            }
            return convosMsgList.size();
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
