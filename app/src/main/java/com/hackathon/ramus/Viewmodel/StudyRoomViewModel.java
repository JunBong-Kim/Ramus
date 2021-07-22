package com.hackathon.ramus.Viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hackathon.ramus.Model.NotiElseModel;
import com.hackathon.ramus.Model.NotificationModel;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Repository.Repository;

import java.util.List;

public class StudyRoomViewModel extends ViewModel {
    private Context mContext;


    public void init(Context context) {
        if (mContext != null) {
            return;
        }
        mContext = context;
    }

    public LiveData<List<Seat>> getSpecificRoomListData(String roomName, String fieldName) {
        return Repository.getInstance(mContext).getSpecificRoomListData(roomName, fieldName);
    }

    public LiveData<User> getSpecificUserLiveData(String userKey) {
        return Repository.getInstance(mContext).getSpecificUserData(userKey);
    }

    public void addWarningHistoryToUser(String userKey, NotiElseModel notiElseModel) {
        Repository.getInstance(mContext).addWarningHistoryToUser(userKey, notiElseModel);
    }
}
