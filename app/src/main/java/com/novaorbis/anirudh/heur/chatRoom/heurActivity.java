package com.novaorbis.anirudh.heur.chatRoom;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;

import com.novaorbis.anirudh.heur.PermissionUtils;
import com.novaorbis.anirudh.heur.R;

import co.intentservice.chatui.ChatView;

public class HeurActivity extends Activity {

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heur);
        ChatView chatView = findViewById(R.id.delores);
        PermissionUtils.requestPermission(this, REQUEST_CODE ,Manifest.permission.CAMERA,false);
        //chatView.addMessage(getResponse());
    }
    /*private ChatMessage getResponse()
    {
        String getMsg= getResources().getString(R.string.)
        ChatMessage response;
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.GET, getMsg,  response1 -> {

        }, error -> {
            try{
                Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG)
                        .show();
            }
            catch (Exception e)
            {
                Log.d("Error",e.getMessage());
                e.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
        return
    }*/


}
