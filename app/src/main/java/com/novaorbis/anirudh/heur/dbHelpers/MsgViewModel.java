package com.novaorbis.anirudh.heur.dbHelpers;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MsgViewModel extends AndroidViewModel {

    private MsgRepository mRepository;
    private LiveData<List<Msgs>> mLiveData;
    public MsgViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MsgRepository(application);
        mLiveData = mRepository.getAllWords();
    }
    LiveData<List<Msgs>> getAllWords()
    {
        return mLiveData;
    }
    public void insert(Msgs word) { mRepository.insert(word); }

}
