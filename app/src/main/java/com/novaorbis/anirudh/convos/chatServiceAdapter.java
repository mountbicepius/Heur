package com.novaorbis.anirudh.convos;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class chatServiceAdapter extends RecyclerView.Adapter<chatServiceAdapter.ViewHolder>
{
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView mUserimage;
        CardView cv;
        TextView userName;
        TextView chatCrmb;

        public ViewHolder(View v) {
            super(v);
            mUserimage = (ImageView) v.findViewById(R.id.person_photo);
            cv = (CardView) v.findViewById(R.id.cv);
            userName = (TextView) v.findViewById(R.id.person_name);
            chatCrmb = (TextView) v.findViewById(R.id.chat_crmb);
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
