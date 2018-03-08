package com.novaorbis.anirudh.convos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class TalksActivity  extends FragmentActivity
{
    private RecyclerView contactsRecyclerView;
    private chatServiceAdapter mAdapter;
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set main content view. On smaller screen devices this is a single pane view with one
        // fragment. One larger screen devices this is a two pane view with two fragments.
        setContentView(R.layout.activity_talks);
        contactsRecyclerView = findViewById(R.id.contact_list_recycler_view);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ContactModel model = ContactModel.get(getBaseContext());
        List<Contact> contacts = model.getContacts();

        mAdapter = new chatServiceAdapter(contacts);
        contactsRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder   {
        // each data item is just a string in this case
        ImageView mUserimage;
        CardView cv;
        TextView userName;
        TextView chatCrmb;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(v1 -> {
                Intent intent = new Intent(TalksActivity.this,convoActivity.class);
                intent.putExtra("EXTRA_CONTACT_JID", userName.getText());
                startActivity(intent);
            });
            mUserimage = v.findViewById(R.id.person_photo);
            cv =  v.findViewById(R.id.cv);
            userName = v.findViewById(R.id.person_name);
            chatCrmb = v.findViewById(R.id.chat_crmb);
        }
        public void setItem(String item) {
            userName.setText(item);
        }

    }
    public class chatServiceAdapter extends RecyclerView.Adapter<ViewHolder>
    {

        public List<Contact> dataList;
        public chatServiceAdapter(List<Contact> dataList)
        {
            this.dataList = dataList;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content,parent,false);
            return new ViewHolder(view);
        }



        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.userName.setText(dataList.get(position).jid);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

    }


}
