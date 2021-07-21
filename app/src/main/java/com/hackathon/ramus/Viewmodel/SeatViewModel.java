package com.hackathon.ramus.Viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.CollectionReference;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Repository.FireStoreLiveData;
import com.hackathon.ramus.Repository.Repository;

import java.util.List;

import static com.hackathon.ramus.Constants.COLLECTION_NAME_OF_SEATS;
import static com.hackathon.ramus.Constants.FIELD_NAME_SEAT_ROOM_NAME;
import static com.hackathon.ramus.Repository.FireStoreLiveData.COLLECTION;

public class SeatViewModel extends ViewModel {
    private Context mContext;


    public void init(Context context) {
        if (mContext != null) {
            return;
        }
        mContext = context;
    }

    public LiveData<List<Seat>> getSpecificRoomListData(String roomName) {
        return Repository.getInstance(mContext).getSpecificRoomListData(roomName);
    }
}
