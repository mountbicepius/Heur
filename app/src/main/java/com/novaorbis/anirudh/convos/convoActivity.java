package com.novaorbis.anirudh.convos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class convoActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convo);
        RecyclerView chats = findViewById(R.id.convos);
        chats.setHasFixedSize(true);
        chats.setLayoutManager(new LinearLayoutManager(this));
        EditText sent = findViewById(R.id.msgTxt);
        TextView recieved = findViewById(R.id.recieved);
        List<chats> chtLst= new ArrayList<chats>();
         ImageButton btn = findViewById(R.id.sndBtn);
         btn.setOnClickListener(v -> {
             chtLst.add(new chats(sent.getText().toString(),recieved.getText().toString()));
                chats.setAdapter(new ChatAdapter(chtLst,this));
         });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.recieved);
        }

        void bind(chats message) {
            messageText.setText(message.recd);


            // Insert the profile image from the URL into the ImageView.
        }
    }
    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.recieved);
        }

        void bind(chats message) {
            messageText.setText(message.sent);


            // Insert the profile image from the URL into the ImageView.
        }
    }
    public class chats
    {
        String sent;
        String recd;
        public chats(String sent, String recd)
        {
            this.sent = sent;
            this.recd = recd;
        }
    }



    private class ChatAdapter extends RecyclerView.Adapter{

        private static final int VIEW_TYPE_MESSAGE_SENT = 1;
        private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;


        private List<chats> msgs;
        String sent;
        String recd;
        private Context ctxt;
        public ChatAdapter(List<chats> chatsList, Context ctxt)
        {
            this.msgs = chatsList;
            this.ctxt =ctxt;
        }

        @Override
        public int getItemViewType(int position) {
            chats message = (chats) msgs.get(position);

            if (getCallingActivity().getClassName().equals(this.getClass().getSimpleName())) {
                // If the current user is the sender of the message
                return VIEW_TYPE_MESSAGE_SENT;
            } else {
                // If some other user sent the message
                return VIEW_TYPE_MESSAGE_RECEIVED;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == VIEW_TYPE_MESSAGE_SENT)
            {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.bubble, parent, false);
                return new SentMessageHolder(view);
            }
            else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.bubble2, parent, false);
                return new ReceivedMessageHolder(view);
            }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            chats message = (chats) msgs.get(position);

            switch (holder.getItemViewType()) {
                case VIEW_TYPE_MESSAGE_SENT:
                    ((SentMessageHolder) holder).bind(message);
                    break;
                case VIEW_TYPE_MESSAGE_RECEIVED:
                    ((ReceivedMessageHolder) holder).bind(message);
            }
        }


        @Override
        public int getItemCount() {
            return msgs.size();
        }
    }

}
