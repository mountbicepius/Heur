package com.novaorbis.anirudh.heur.dbHelpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.novaorbis.anirudh.heur.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.intentservice.chatui.models.ChatMessage;

import static com.android.volley.Request.*;

public class msgHelper {


    public static final String PREFS_NAME = "Messages";
        public static final String FAVORITES = "Msg_Data";

        public msgHelper() {
            super();
        }

        // This four methods are used for maintaining favorites.
        public void saveFavorites(Context context, List<ChatMessage> favorites) {

            /**SharedPreferences settings;
                SharedPreferences.Editor editor;
                settings = context.getSharedPreferences(PREFS_NAME,
                        Context.MODE_PRIVATE);
                editor = settings.edit();
                Gson gson = new Gson();
                String jsonFavorites = gson.toJson(favorites);
                editor.putString(FAVORITES, jsonFavorites);
               editor.apply();
            */
       }

        public void addFavorite(Context context, String sender) {

            String url = "https://heur-messaging.herokuapp.com/msg/send?sender="+sender;
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JsonArrayRequest arrayRequest = new JsonArrayRequest(url,(JSONArray response) -> {
                try {
                    if(response!=null)
                    {
                       /** List<ChatMessage> favorites = getFavorites(context);
                            if (favorites == null){
                              favorites = new ArrayList<ChatMessage>();}
                            favorites.add(new ChatMessage(" ",timeStamp(), ChatMessage.Type.RECEIVED));
                        *   saveFavorites(context, favorites);
                        */
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                            }, Throwable::printStackTrace);
            requestQueue.add(arrayRequest);
        }

    public long timeStamp()
    {
        return System.currentTimeMillis()/1000;
    }

        public void removeFavorite(Context context, ChatMessage product) {
           /** ArrayList<ChatMessage> favorites = getFavorites(context);
                if (favorites != null) {
                    favorites.remove(product);
                    saveFavorites(context, favorites);
            *   }
            */
        }

        public static ArrayList<ChatMessage> getFavorites(Context context) {
           /** SharedPreferences settings;
            ArrayList<ChatMessage> favorites;

            settings = context.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);

            if (settings.contains(FAVORITES)) {
                String jsonFavorites = settings.getString(FAVORITES, null);
                Gson gson = new Gson();
                Type type= new TypeToken<ArrayList<ChatMessage>>(){}.getType();
                List<ChatMessage> favoriteItems = gson.fromJson(jsonFavorites,type);
                assert favoriteItems != null;
                favorites = new ArrayList<ChatMessage>(favoriteItems);
                return favorites;
            }
                return null;
            */
        }

}
