package com.novaorbis.anirudh.heur.dbHelpers;

import android.content.Context;

import com.novaorbis.anirudh.heur.chatRoom.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactModel {

    private static ContactModel sContactModel;
    private List<Contact> mContacts;

    public static ContactModel get(Context context)
    {
        if(sContactModel == null)
        {
            sContactModel = new ContactModel(context);
        }
        return  sContactModel;
    }

    private ContactModel(Context context)
    {
        mContacts = new ArrayList<>();
        populateWithInitialContacts(context);

    }

    private void populateWithInitialContacts(Context context)
    {
        //Create the Foods and add them to the list;



    }

    public List<Contact> getContacts()
    {
        return mContacts;
    }

}
