package com.novaorbis.anirudh.convos;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;


public class chatServiceAdapter extends RecyclerView.Adapter<chatServiceAdapter.ViewHolder>
{
    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        // each data item is just a string in this case
        ImageView mUserimage;
        CardView cv;
        TextView userName;
        TextView chatCrmb;
         String mItem;
        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            mUserimage = v.findViewById(R.id.person_photo);
            cv =  v.findViewById(R.id.cv);
            userName = v.findViewById(R.id.person_name);
            chatCrmb = v.findViewById(R.id.chat_crmb);
        }
        public void setItem(String item) {
            mItem = item;
            userName.setText(item);
        }
        @Override
        public void onClick(View v) {
            //Log.d(TAG, "OnClick"+getAdapterPosition()+mItem);
            new Activity().startActivity(new Intent(new TalksActivity().getApplicationContext(),convoActivity.class));
        }
    }
    public List<chatUser> dataList;
    public chatServiceAdapter(List<chatUser> dataList)
    {
        this.dataList = dataList;

    }

    @Override
    public chatServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(chatServiceAdapter.ViewHolder holder, int position) {
        holder.mUserimage.setImageResource(dataList.get(position).imageId);
        holder.userName.setText(dataList.get(position).username);
        holder.chatCrmb.setText(dataList.get(position).chatcrumb);
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
