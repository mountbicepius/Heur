package com.novaorbis.anirudh.heur;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.novaorbis.anirudh.heur.breifHelpers.Authentication;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements Authentication {

    public  boolean isAuth;

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TypeWriter tw = findViewById(R.id.titleH);
        final ProgressBar pb =findViewById(R.id.loaden);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.fade);
        findViewById(R.id.header).setAnimation(hyperspaceJumpAnimation);
        assert pb !=null;
        pb.isIndeterminate();
        tw.setText("");
        tw.setCharacterDelay(150);
        tw.animateText("Conversations ReImagined");
        // Using handler with postDelayed called runnable run method
        // Using background task to verify existence of user
        new Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(this);
            // Check if we need to display our OnboardingFragment
            /*if (!sharedPreferences.getBoolean(
                    MyOnboardingFragment.COMPLETED_ONBOARDING_PREF_NAME, false)) {
                // The user hasn't seen the OnboardingFragment yet, so show it
                startActivity(new Intent(this, OnboardingActivity.class));
            }*/
             Intent i = new Intent(MainActivity.this, TalksActivity.class);
                startActivity(i);

           // }
            //Intent intent =new Intent(MainActivity.this, TalksActivity.class);
            //startActivityForResult(intent,1);
            // close this activity
            finish();
        }, 4*1000);

    }
    public Byte uniqueToken()
    {
        String value= " ";
        try {
            value = UUID.randomUUID().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Byte.parseByte(value);
    }

    @Override
    public void isAuth() {

    }
}
