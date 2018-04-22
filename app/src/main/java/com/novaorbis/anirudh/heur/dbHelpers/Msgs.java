package com.novaorbis.anirudh.convos.dbHelpers;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "messages")
public class Msgs
{
    @PrimaryKey
    @NonNull
    public int ID;

    @ColumnInfo(name = "Message_Type")
    public String MSG_TYPE;

    @ColumnInfo(name = "Message_Content")
    public String MSG_VAL;

    @ColumnInfo(name = "TimeStamp")
    public long timestmp;
}


