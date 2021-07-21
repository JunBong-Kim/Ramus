package com.hackathon.ramus.Viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Repository.Repository;

public class MyHistoryViewModel extends ViewModel {

    private Context mContext;

    public void init(Context context){
        if(mContext == null)mContext = context;
    }

    public LiveData<User> getSpecificUserLiveData(String key) {
        return Repository.getInstance(mContext).getSpecificUserData(key);
    }
}
