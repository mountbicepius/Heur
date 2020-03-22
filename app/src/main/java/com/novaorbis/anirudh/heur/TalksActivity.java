package com.novaorbis.anirudh.heur;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.novaorbis.anirudh.heur.chatRoom.HeurActivity;
import com.novaorbis.anirudh.heur.chatRoom.chatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TalksActivity  extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{
    private static final int REQ = 1;
    private chatServiceAdapter mAdapter;
    //private DividerItemDecoration dividerItemDecoration;
    //ViewPager mViewPager;

    private String device;
    public static final String ANONYMOUS = "anonymous";
    private List<Contact> mContacts;
    private FirebaseUser mFirebaseUser;
    private String TAG = "Help";
    private FirebaseInstanceId mFCM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talks);
        FirebaseApp.initializeApp(this);
        device = FirebaseInstanceId.getInstance().getToken();
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        mFCM = FirebaseInstanceId.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        RecyclerView contactsRecyclerView = (RecyclerView) findViewById(R.id.contact_list_recycler_view);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Set default username is anonymous.
        String mUsername = ANONYMOUS;
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        //Initialize Firebase Auth


        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
        }
        //Render values to screen
        final FloatingActionButton fab = findViewById(R.id.fab);
        mContacts = new ArrayList<>();
        mAdapter = new chatServiceAdapter(mContacts);
        populateWithInitialContacts(this);
        saveDeviceInfo();
        contactsRecyclerView.setAdapter(mAdapter);
        fab.setOnClickListener(ocl ->{
          final Intent intent = new Intent(TalksActivity.this, HeurActivity.class);
          intent.putExtra("MessageTo", this.device);
          startActivity(intent);
          finish();
        });
    }
    private void populateWithInitialContacts(Context context)
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //Create the Foods and add them to the list;
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        String url = getResources().getString(R.string.users);
        JsonArrayRequest stringRequest= new JsonArrayRequest(url, (JSONArray response) -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object= response.getJSONObject(i);
                    if(!Objects.requireNonNull(mFirebaseUser.getEmail()).equals(object.getString("email")))
                    {
                    mContacts.add(new Contact(object.getString("friendName")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
            mAdapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }, error -> {
            Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT)
                    .show();
            progressDialog.dismiss();
        });
        requestQueue.add(stringRequest);
    }
    private void saveDeviceInfo()
    {
        /*Map<String, String> devList = new HashMap<>();
        devList.put("Name",mFirebaseUser.getDisplayName());
        devList.put("DeviceID", mFCM.getToken());*/
        //Add Device reference
        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabaseReference.child("Users")
                .child(Objects.requireNonNull(mFirebaseUser.getDisplayName()))
                .setValue(mFCM.getToken());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder   {
        // each data item is just a string in this case
        ImageView mUserimage;
        CardView cv;
        TextView userName;
        TextView chatCrmb;
        String device;
        public ViewHolder(View v, String deviceToken) {
            super(v);
            v.setOnClickListener(v1 -> {
                Intent intent = new Intent(TalksActivity.this, chatActivity.class);
                intent.putExtra("EXTRA_CONTACT_JID", userName.getText());
                startActivity(intent);
            });
                this.device = deviceToken;
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
        public String devToken;
        public chatServiceAdapter(List<Contact> dataList)
        {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content,parent,false);
            return new ViewHolder(view,devToken);
        }



        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.userName.setText(dataList.get(position).jid);
            //devToken = dataList.get(position).deviceID;
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

    public class Contact
    {
        public String jid;
        public String deviceID;

        public Contact()
        {

        }
        public Contact(String senderID)
        {
            this.jid = senderID;

        }


    }

}
