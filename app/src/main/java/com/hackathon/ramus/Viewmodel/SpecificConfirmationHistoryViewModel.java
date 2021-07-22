package com.hackathon.ramus.Viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hackathon.ramus.Model.ConfirmationHistory;
import com.hackathon.ramus.Repository.Repository;

public class SpecificConfirmationHistoryViewModel extends ViewModel {

    private Context mContext;
    public void init(Context context){
        if(mContext == null)mContext= context;
    }
    public LiveData<ConfirmationHistory> getSpecificConfirmationHistory(String key){
        return Repository.getInstance(mContext).getSpecificConfirmationHistory(key);
    }
}
