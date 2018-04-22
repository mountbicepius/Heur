package com.novaorbis.anirudh.convos.dbHelpers;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

public class msgRepo
{
    private MsgDao msgDao;
    private LiveData<List<Msgs>> listLiveData;

    private msgRepo(Application mApplication)
    {
        msgDB db = msgDB.getDatabase(mApplication);
        msgDao =db.msgdao();
        listLiveData = (LiveData<List<Msgs>>) msgDao.findAll();
    }
    public void Insert(Msgs msg)
    {
        new insertAsync(msgDao).execute(msg);
    }
private static class insertAsync extends AsyncTask<Msgs,Void ,Void>
    {
        private MsgDao mAsync;
        private insertAsync(MsgDao mDao)
        {
            mAsync = mDao;
        }
        @Override
        protected Void doInBackground(Msgs... msgs) {
            mAsync.insert(msgs[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
public class  MesgViewModel extends AndroidViewModel
{
    private msgRepo inst;
    private LiveData<List<Msgs>> mAllMsgs;

    public MesgViewModel(@NonNull Application application) {
        super(application);
        inst = new msgRepo(application);
        mAllMsgs = inst.listLiveData;
    }
    public void insert(Msgs word) { inst.Insert(word); }
}
}

