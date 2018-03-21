package com.novaorbis.anirudh.convos;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class convoActivity extends Activity {

    private RecyclerView mMessageCache;
    private EditText mSendText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convo);
        convosMsg message =new convosMsg();
        mMessageCache = findViewById(R.id.messageRecyclerView);
        Button mSend = findViewById(R.id.sendButton);
        mSendText = findViewById(R.id.messageEditText);
        LinearLayoutManager obj = new LinearLayoutManager(this);
        obj.setStackFromEnd(true);
        mMessageCache.setLayoutManager(obj);
        mMessageCache.setAdapter(new convoAdapter(getMessages()));
        mSend.setOnClickListener(v -> {
            message.Msg = mSendText.getText().toString();
            getMessages().add(message);

            mSendText.setText("");
        });
    }

    public List<convosMsg> getMessages() {
        return new ArrayList<convosMsg>();
    }

    public  class convosMsg
    {
        String Msg;
        long timestamp;
        public convosMsg ()
        {

        }
        public convosMsg (String msg,long timestamp)
        {
            this.Msg =msg;
            this.timestamp = timestamp;
        }
    }


    public class convoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        List<convosMsg> convosMsgList;
        public convoAdapter(List<convosMsg> msgList)
        {
            this.convosMsgList = msgList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layoutInflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.bubble2,parent,false);
            return new recievedMsg(layoutInflater);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TextView txt = (TextView) holder.itemView;
            txt.setText(convosMsgList.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return convosMsgList.size();
        }
    }
    public class recievedMsg extends RecyclerView.ViewHolder
    {
        TextView msg;

        public recievedMsg(View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.recieved);
        }
    }
}
