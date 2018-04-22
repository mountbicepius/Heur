package com.novaorbis.anirudh.heur.chatRoom;

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
