package com.novaorbis.anirudh.heur.dbHelpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.intentservice.chatui.models.ChatMessage;

public class msgHelper {


    public static final String PREFS_NAME = "Messages";
        public static final String FAVORITES = "Msg_Data";

        public msgHelper() {
            super();
        }

        // This four methods are used for maintaining favorites.
        public void saveFavorites(Context context, List<ChatMessage> favorites) {
            SharedPreferences settings;
            SharedPreferences.Editor editor;
            settings = context.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);
            editor = settings.edit();
            Gson gson = new Gson();
            String jsonFavorites = gson.toJson(favorites);
            editor.putString(FAVORITES, jsonFavorites);
            editor.apply();
        }

        public void addFavorite(Context context, ChatMessage product) {
            List<ChatMessage> favorites = getFavorites(context);
            if (favorites == null)
                favorites = new ArrayList<ChatMessage>();
            favorites.add(product);
            saveFavorites(context, favorites);
        }

        public void removeFavorite(Context context, ChatMessage product) {
            ArrayList<ChatMessage> favorites = getFavorites(context);
            if (favorites != null) {
                favorites.remove(product);
                saveFavorites(context, favorites);
            }
        }

        public static ArrayList<ChatMessage> getFavorites(Context context) {
            SharedPreferences settings;
            List<ChatMessage> favorites;

            settings = context.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);

            if (settings.contains(FAVORITES)) {
                String jsonFavorites = settings.getString(FAVORITES, null);
                Gson gson = new Gson();
                Type type= new TypeToken<ArrayList<ChatMessage>>(){}.getType();
                List<ChatMessage> favoriteItems = gson.fromJson(jsonFavorites,type);
                assert favoriteItems != null;
                favorites = new ArrayList<ChatMessage>(favoriteItems);
            } else
                return null;

            return (ArrayList<ChatMessage>) favorites;
        }

}
