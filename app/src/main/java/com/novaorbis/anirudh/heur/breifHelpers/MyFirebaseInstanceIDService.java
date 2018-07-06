package com.novaorbis.anirudh.heur.breifHelpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @SuppressLint("WrongThread")
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = null;
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.


    }


}
