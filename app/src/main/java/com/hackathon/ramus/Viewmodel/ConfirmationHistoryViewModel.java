package com.hackathon.ramus.Viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hackathon.ramus.Model.ConfirmationHistory;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Repository.Repository;

import java.util.ArrayList;

public class ConfirmationHistoryViewModel extends ViewModel {

    private Context mContext;

    public void init(Context context){
        if(mContext == null)mContext= context;
    }

    public LiveData<ArrayList<ConfirmationHistory>> getConfirmationSeatsArrayList(){
        return Repository.getInstance(mContext).getConfirmationHistory();
    }

}
