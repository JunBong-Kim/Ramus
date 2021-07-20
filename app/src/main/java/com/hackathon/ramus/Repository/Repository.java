package com.hackathon.ramus.Repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Model.User;

import java.util.HashMap;

import java.util.Map;

import static com.hackathon.ramus.Constants.*;
import static com.hackathon.ramus.Repository.FireStoreLiveData.DOCUMENT;


public class Repository {
    private static Repository instance;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Context mContext;
    private final String TAG = "Repository";

    public static Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public void setUserData(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put(FILED_NAME_USER_NAME, user.getUserName());
        map.put(FILED_NAME_USER_FCM_TOKEN, user.getUserFcmToken());
        map.put(FILED_NAME_USER_KEY, user.getUserKey());
        map.put(FILED_NAME_USER_STUDENT_NUMBER,user.getUserStudentNumber());
        map.put(FIELD_NAME_USER_USER_SEAT,DATA_USER_SEAT_NULL);
        db.collection(COLLECTION_NAME_OF_USERS).document(user.getUserKey()).set(map, SetOptions.merge());
    }
    public void updateFcmToken(String userKey,String token){
        Map<String,Object> map = new HashMap<>();
        map.put(FILED_NAME_USER_FCM_TOKEN,token);
        db.collection(COLLECTION_NAME_OF_USERS).document(userKey).update(map);
    }

    public void updateUserNewSeatKey(String userKey,String userSeatKey){
        Map<String,Object> map = new HashMap<>();
        map.put(FIELD_NAME_USER_USER_SEAT,userSeatKey);
        db.collection(COLLECTION_NAME_OF_USERS).document(userKey).update(map);
    }
    public void  updateSeatNewUserKeyAndNewEndTime(String seatKey,String userKey,Long seatReservationEndTime){
        Map<String,Object> map = new HashMap<>();
        map.put(FIELD_NAME_SEAT_USER_KEY,userKey);
        map.put(FIELD_NAME_SEAT_RESERVATION_END_TIME,seatReservationEndTime);
        db.collection(COLLECTION_NAME_OF_SEATS).document(seatKey).update(map);
    }

    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public LiveData<User> getSpecificUserData(String userKey) {
        return new FireStoreLiveData<>(db.collection(COLLECTION_NAME_OF_USERS).document(userKey), User.class, DOCUMENT);
    }

    public void setSeatData(Seat seat) {
        Map<String, Object> map = new HashMap<>();
        map.put(FIELD_NAME_SEAT_RESERVATION_END_TIME, seat.getSeatReservationEndTime());
        map.put(FIELD_NAME_SEAT_USER_KEY, seat.getSeatUserKey());
        map.put(FIELD_NAME_SEAT_KEY, seat.getSeatKey());

        db.collection(COLLECTION_NAME_OF_SEATS).document(seat.getSeatKey()).set(map, SetOptions.merge());
    }

}
