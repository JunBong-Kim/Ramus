package com.hackathon.ramus.Viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Repository.Repository;

public class EmailAuthLoginViewModel extends ViewModel {

    private  Context mContext;

    public void init(Context context){
        if(mContext == null)mContext = context;
    }

    public FirebaseUser getUser(){
        return Repository.getInstance(mContext).getUser();
    }

    public LiveData<User> getSpecificUserLiveData(String userKey){
        return Repository.getInstance(mContext).getSpecificUserData(userKey);
    }


}
