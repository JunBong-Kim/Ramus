package com.hackathon.ramus.Viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

public class EmailSignUpViewModel extends ViewModel {

    private Context mContext;

    public void init(Context context){
        if(mContext == null){
            mContext = context;
        }
    }



}
