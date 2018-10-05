package com.novaorbis.anirudh.heur.chatRoom;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;

import com.novaorbis.anirudh.heur.PermissionUtils;
import com.novaorbis.anirudh.heur.R;


import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class HeurActivity extends Activity {

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heur);
        ChatView chatView = findViewById(R.id.delores);
        PermissionUtils.requestPermission(this, REQUEST_CODE ,Manifest.permission.CAMERA,false);
        chatView.addMessage(getResponse());
    }

    private ChatMessage getResponse() {
        return new ChatMessage(" ",timestamp(), ChatMessage.Type.RECEIVED);
    }
    private long timestamp()
    {
        return (System.currentTimeMillis()/1000);
    }


}
