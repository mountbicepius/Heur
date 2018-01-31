package com.novaorbis.anirudh.convos;

import android.app.Activity;
import android.os.Bundle;

public class convoActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convo);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
