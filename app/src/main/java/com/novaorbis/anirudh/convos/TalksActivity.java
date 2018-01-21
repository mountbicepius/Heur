package com.novaorbis.anirudh.convos;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TalksActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
   private ListView mConvers;
    private List<chatUser> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talks);
        mConvers = findViewById(R.id.chats);
        mConvers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
    private void Initialize()
    {
        this.dataList = new ArrayList<>();
        dataList.add(new chatUser(R.drawable.muser,"Main","This is an introductory chat crumb"));
        dataList.add(new chatUser(R.drawable.fuser,"New","This is an introductory chat crumb"));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
