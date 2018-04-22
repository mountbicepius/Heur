package com.novaorbis.anirudh.convos.dbHelpers;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MsgDao
{
    @Query("SELECT * FROM messages ORDER BY timestamp ASC ")
    List<Msgs> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Msgs... msg);

    @Delete
    void delete(Msgs msg);

    @Query("DELETE from messages")
    void deleteAll();
}
