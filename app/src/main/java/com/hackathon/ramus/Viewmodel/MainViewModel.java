package com.hackathon.ramus.Viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.core.Repo;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Repository.Repository;

public class MainViewModel extends ViewModel {

    private Context mContext;


    public void init(Context context){
        if(mContext != null){
            return;
        }
        mContext = context;
    }

    public LiveData<User> getSpecificUserLiveData(String key) {
        return Repository.getInstance(mContext).getSpecificUserData(key);
    }
    public void updateFcmToken(String userKey,String token){
        Repository.getInstance(mContext).updateFcmToken(userKey,token);
    }

    public void updateUserNewSeatKey(String userKey,String userSeatKey){
        Repository.getInstance(mContext).updateUserNewSeatKey(userKey,userSeatKey);
    }

    public void updateSeatNewUserKeyAndNewEndTime(String seatKey,String userKey,Long seatReservationEndTime){
        Repository.getInstance(mContext).updateSeatNewUserKeyAndNewEndTime(seatKey,userKey,seatReservationEndTime);
    }

    public void setSeatData(Seat seat) {
        Repository.getInstance(mContext).setSeatData(seat);
    }

    public void addSeatHistoryToUser(String userKey,Seat seatHistoryToAdd) {
        Repository.getInstance(mContext).addSeatHistoryToUser(userKey,seatHistoryToAdd);
    }

    public void setConfirmationHistory(User confirmationUser,long confirmationTime) {
        Repository.getInstance(mContext).setConfirmationHistory(confirmationUser,confirmationTime);
    }
}
