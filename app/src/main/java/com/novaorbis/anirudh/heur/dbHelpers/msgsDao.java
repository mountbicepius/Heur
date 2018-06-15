package com.novaorbis.anirudh.heur.dbHelpers;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface msgsDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void msgInsrt(Msgs msg);

    @Query("DELETE from messages")
    void deleteAll();

    @Query("SELECT * from messages ORDER BY Message_Content ASC")
    LiveData<List<Msgs>> getAllMsgs();

}
