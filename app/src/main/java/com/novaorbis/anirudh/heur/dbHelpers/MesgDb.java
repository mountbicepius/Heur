package com.novaorbis.anirudh.heur.dbHelpers;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Msgs.class}, version = 1)
public abstract class MesgDb extends RoomDatabase
{
    public abstract msgsDao dao();
    private static MesgDb INSTANCE;

    public static MesgDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MesgDb.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MesgDb.class, "Message_DB")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
