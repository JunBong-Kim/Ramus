package com.hackathon.ramus.Viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hackathon.ramus.Repository.Repository;

public class EmailCheckViewModel extends ViewModel {
    private Context mContext;
    private MutableLiveData<Integer> errorCntMutableLiveData;

    public void init(Context context){
        if(mContext == null)mContext = context;
    }

    public MutableLiveData<Integer> getErrorCntMutableLiveData(){
        if(errorCntMutableLiveData == null){
            errorCntMutableLiveData = new MutableLiveData<>();
        }
        return errorCntMutableLiveData;
    }




}
