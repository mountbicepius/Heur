package com.novaorbis.anirudh.heur.chatRoom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class broadcastHandler extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final PendingResult pendingResult = goAsync();
        AsyncTask<String,Integer,String> asyncTask = new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                
                return null;
            }
        };
    }
}
