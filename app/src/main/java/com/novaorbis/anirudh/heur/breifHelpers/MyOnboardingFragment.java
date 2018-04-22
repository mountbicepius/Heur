package com.novaorbis.anirudh.heur.breifHelpers;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v17.leanback.app.OnboardingFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

@SuppressLint("ValidFragment")
class MyOnboardingFragment extends OnboardingFragment {
    public static final String COMPLETED_ONBOARDING_PREF_NAME = "completed_onboarding";

    @Override
    protected int getPageCount() {
        return 0;
    }

    @Override
    protected CharSequence getPageTitle(int pageIndex) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onFinishFragment() {
        super.onFinishFragment();
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = getDefaultSharedPreferences(getContext()).edit();
        editor.putBoolean(COMPLETED_ONBOARDING_PREF_NAME,true);
        editor.apply();
    }

    @Override
    protected CharSequence getPageDescription(int pageIndex) {
        return null;
    }

    @Nullable
    @Override
    protected View onCreateBackgroundView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Nullable
    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Nullable
    @Override
    protected View onCreateForegroundView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }
}
