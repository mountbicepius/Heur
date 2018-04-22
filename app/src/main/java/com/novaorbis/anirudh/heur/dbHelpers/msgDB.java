package com.novaorbis.anirudh.convos.dbHelpers;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Msgs.class}, version = 1)
public abstract class msgDB extends RoomDatabase
{
    public abstract MsgDao msgdao();
    private static msgDB INSTANCE;
    static msgDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (msgDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            msgDB.class, "convosdb")
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MsgDao mDao;

        PopulateDbAsync(msgDB db) {
            mDao = db.msgdao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();

            return null;
        }
    }
}
