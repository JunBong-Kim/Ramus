package com.hackathon.ramus.Viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Repository.Repository;

public class Main2Viewmodel extends ViewModel {
    private Context mContext;

    public void init(Context context) {
        if (mContext != null ) {
            return;
        }
        mContext = context;
    }
    public FirebaseUser getUser(){
        return Repository.getInstance(mContext).getUser();
    }

    public void setUserData(User user){
        Repository.getInstance(mContext).setUserData(user);
    }
}
