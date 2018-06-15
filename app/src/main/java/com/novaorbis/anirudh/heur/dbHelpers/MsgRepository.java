package com.novaorbis.anirudh.heur.dbHelpers;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MsgRepository
{
    private msgsDao mWordDao;
    private LiveData<List<Msgs>> mAllWords;

    public MsgRepository(Application application) {
        MesgDb db = MesgDb.getDatabase(application);
        this.mWordDao = db.dao();
        this.mAllWords = mWordDao.getAllMsgs();
    }
    LiveData<List<Msgs>> getAllWords() {
        return mAllWords;
    }
    public void insert (Msgs word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Msgs, Void, Void> {

        private msgsDao mAsyncTaskDao;

        insertAsyncTask(msgsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Msgs... params) {
            mAsyncTaskDao.msgInsrt(params[0]);
            return null;
        }
    }
}

