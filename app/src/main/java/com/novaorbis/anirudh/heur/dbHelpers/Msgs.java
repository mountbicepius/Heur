package com.novaorbis.anirudh.heur.dbHelpers;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "messages")
public class Msgs
{
    public final static String MSG_TYPE_SENT = "MSG_TYPE_SENT";
    public final static String MSG_TYPE_RECEIVED = "MSG_TYPE_RECEIVED";


    public Msgs(){

    }

    public Msgs(String msgType, String msgContent, long timestamp)
    {
        this.MSG_VAL  = msgContent;
        this.MSG_TYPE = msgType;
        this.timestmp = timestamp;
    }
    @PrimaryKey
    @NonNull
    public int ID;

    @ColumnInfo(name = "Message_Type")
    public String MSG_TYPE;

    @ColumnInfo(name = "Message_Content")
    public String MSG_VAL;

    @ColumnInfo(name = "TimeStamp")
    public long timestmp;

    public String getMsgContent()
    {
        return MSG_VAL;
    }
    public void setMsgContent(String msgContent)
    {
        this.MSG_VAL = msgContent;
    }
    public String getMsgType()  { return MSG_TYPE; }
    public void setMsgType(String msgType)
    {
        this.MSG_TYPE =msgType;
    }
    public long getTimestmp() {
        return timestmp;
    }
}


