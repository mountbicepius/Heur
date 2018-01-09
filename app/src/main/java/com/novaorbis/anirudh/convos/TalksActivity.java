package com.novaorbis.anirudh.convos;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TalksActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<chatUser> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talks);
        mRecyclerView =findViewById(R.id.chatList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Initialize();
        mAdapter =new chatServiceAdapter(dataList);
        mRecyclerView.setAdapter(mAdapter);
    }
    private void Initialize()
    {
        this.dataList = new ArrayList<>();
        dataList.add(new chatUser(R.drawable.muser,"Main","This is an introductory chat crumb"));
        dataList.add(new chatUser(R.drawable.fuser,"New","This is an introductory chat crumb"));
    }
}
