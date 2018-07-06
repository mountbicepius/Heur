package com.novaorbis.anirudh.heur;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.novaorbis.anirudh.heur.chatRoom.Contact;
import com.novaorbis.anirudh.heur.chatRoom.heurActivity;
import com.novaorbis.anirudh.heur.dbHelpers.ContactModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;


public class TalksActivity  extends AppCompatActivity
{
    private RecyclerView contactsRecyclerView;
    private chatServiceAdapter mAdapter;
    ViewPager mViewPager;
    private String senderid = FirebaseInstanceId.getInstance().getId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set main content view. On smaller screen devices this is a single pane view with one
        // fragment. One larger screen devices this is a two pane view with two fragments.
        setContentView(R.layout.activity_talks);
        contactsRecyclerView = findViewById(R.id.contact_list_recycler_view);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Send current users ID to server


        ContactModel model = ContactModel.get(getBaseContext());
        List<Contact> contacts = model.getContacts();

        mAdapter = new chatServiceAdapter(contacts);
        contactsRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder   {
        // each data item is just a string in this case
        ImageView mUserimage;
        CardView cv;
        TextView userName;
        TextView chatCrmb;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(v1 -> {
                Intent intent = new Intent(TalksActivity.this,heurActivity.class);
                intent.putExtra("EXTRA_CONTACT_JID", userName.getText());
                startActivity(intent);
            });
            mUserimage = v.findViewById(R.id.person_photo);
            cv =  v.findViewById(R.id.cv);
            userName = v.findViewById(R.id.person_name);
            chatCrmb = v.findViewById(R.id.chat_crmb);
        }
        public void setItem(String item) {
            userName.setText(item);
        }

    }
    public class chatServiceAdapter extends RecyclerView.Adapter<ViewHolder>
    {

        public List<Contact> dataList;
        public chatServiceAdapter(List<Contact> dataList)
        {
            this.dataList = dataList;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content,parent,false);
            return new ViewHolder(view);
        }



        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.userName.setText(dataList.get(position).jid);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

    }
    private class registerAsync extends AsyncTask<String , Void , Boolean>
    {
        Context mContext;
        registerAsync(Context context)
        {
            this.mContext = context;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            RequestQueue mRequestQueue = Volley.newRequestQueue(mContext);
            String url = "https://heur-messaging.herokuapp.com/msg";
            JSONObject object = new JSONObject();
            try {
                object.put("",)
                object.put("senderID",senderid);
                final String jsonBody = object.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return jsonBody == null ? null : jsonBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                mRequestQueue.add(stringRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
                return true;
        }
    }


}
