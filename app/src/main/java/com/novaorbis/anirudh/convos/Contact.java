package com.novaorbis.anirudh.convos;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Contact
{
    public String jid;

    public Contact(String contactJid )
    {
        jid = contactJid;
    }

    public String getJid()
    {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }
}
