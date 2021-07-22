package com.hackathon.ramus.Repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.hackathon.ramus.Model.ConfirmationHistory;
import com.hackathon.ramus.Model.NotiElseModel;
import com.hackathon.ramus.Model.NotificationModel;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Model.User;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.Store;

import static com.hackathon.ramus.Constants.*;
import static com.hackathon.ramus.Repository.FireStoreLiveData.COLLECTION;
import static com.hackathon.ramus.Repository.FireStoreLiveData.DOCUMENT;
import static com.hackathon.ramus.Repository.FireStoreLiveData.QUERY;


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
        map.put(FILED_NAME_USER_STUDENT_NUMBER, user.getUserStudentNumber());
        map.put(FIELD_NAME_USER_USER_SEAT, DATA_USER_SEAT_NULL);
        db.collection(COLLECTION_NAME_OF_USERS).document(user.getUserKey()).set(map, SetOptions.merge());
    }

    public void updateFcmToken(String userKey, String token) {
        Map<String, Object> map = new HashMap<>();
        map.put(FILED_NAME_USER_FCM_TOKEN, token);
        db.collection(COLLECTION_NAME_OF_USERS).document(userKey).update(map);
    }

    public void updateUserNewSeatKey(String userKey, String userSeatKey) {
        Map<String, Object> map = new HashMap<>();
        map.put(FIELD_NAME_USER_USER_SEAT, userSeatKey);
        db.collection(COLLECTION_NAME_OF_USERS).document(userKey).update(map);
    }

    public void updateSeatNewUserKeyAndNewEndTime(String seatKey, String userKey, Long seatReservationEndTime) {
        Map<String, Object> map = new HashMap<>();
        map.put(FIELD_NAME_SEAT_USER_KEY, userKey);
        map.put(FIELD_NAME_SEAT_RESERVATION_END_TIME, seatReservationEndTime);
        db.collection(COLLECTION_NAME_OF_SEATS).document(seatKey).update(map);
    }

    public void initdata(Seat seat) {
        Map<String, Object> map = new HashMap<>();
        map.put(FIELD_NAME_SEAT_KEY, seat.getSeatKey());
        map.put(FIELD_NAME_SEAT_RESERVATION_END_TIME, seat.getSeatReservationEndTime());
        map.put(FIELD_NAME_SEAT_RESERVATION_START_TIME, seat.getSeatReservationStartTime());
        map.put(FIELD_NAME_SEAT_USER_KEY, seat.getSeatUserKey());
        map.put(FIELD_NAME_SEAT_ROOM_NAME, seat.getRoomName());
        db.collection(COLLECTION_NAME_OF_SEATS).document(seat.getSeatKey()).set(map, SetOptions.merge());
    }

    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public LiveData<User> getSpecificUserData(String userKey) {
        return new FireStoreLiveData<>(db.collection(COLLECTION_NAME_OF_USERS).document(userKey), User.class, DOCUMENT);
    }

    public LiveData<List<Seat>> getSpecificRoomListData(String roomName, String fieldName) {
        return new FireStoreLiveData<List<Seat>>(db.collection(COLLECTION_NAME_OF_SEATS).whereEqualTo(fieldName, roomName), Seat.class, QUERY);
    }


    public void setSeatData(Seat seat) {
        Map<String, Object> map = new HashMap<>();
        map.put(FIELD_NAME_SEAT_RESERVATION_END_TIME, seat.getSeatReservationEndTime());
        map.put(FIELD_NAME_SEAT_USER_KEY, seat.getSeatUserKey());
        map.put(FIELD_NAME_SEAT_KEY, seat.getSeatKey());

        db.collection(COLLECTION_NAME_OF_SEATS).document(seat.getSeatKey()).set(map, SetOptions.merge());
    }

    public LiveData isUserRegistered(String email) {
        return new FireStoreOnceLiveData(db.collection(COLLECTION_NAME_OF_USERS), User.class, email);
    }

    public void addSeatHistoryToUser(String userKey, Seat seatHistoryToAdd) {
        DocumentReference documentReference = db.collection(COLLECTION_NAME_OF_USERS).document(userKey);
        documentReference.update(FIELD_NAME_USER_SEAT_HISTORY, FieldValue.arrayUnion(seatHistoryToAdd));
    }

    public void addWarningHistoryToUser(String userKey, NotiElseModel notiElseModel){
        DocumentReference documentReference = db.collection(COLLECTION_NAME_OF_USERS).document(userKey);
        documentReference.update(FIELD_NAME_USER_NOTIFICATION_HISTORY, FieldValue.arrayUnion(notiElseModel));
    }
    public LiveData<ArrayList<ConfirmationHistory>> getConfirmationHistory(){
        return new FireStoreLiveData<ArrayList<ConfirmationHistory>>(db.collection(COLLECTION_NAME_OF_CONFIRMATION_HISTORY),ConfirmationHistory.class,COLLECTION);
    }

    public LiveData<ConfirmationHistory> getSpecificConfirmationHistory(String key){
        return new FireStoreLiveData<ConfirmationHistory>(db.collection(COLLECTION_NAME_OF_CONFIRMATION_HISTORY).document(key),ConfirmationHistory.class,DOCUMENT);
    }

    public void setConfirmationHistory(User confirmationUser,long confirmationTime){
        DocumentReference documentReference = db.collection(COLLECTION_NAME_OF_CONFIRMATION_HISTORY).document();
        String  key = documentReference.getId();

        Map<String,Object> map = new HashMap<>();

        map.put(FIELD_NAME_CONFIRMATION_HISTORY_KEY,key);
        map.put(FIELD_NAME_CONFIRMATION_USER_KEY,confirmationUser.getUserKey());

        long twoWeekAgo = confirmationTime - 3600000 * 14;

        ArrayList<Seat> seats = confirmationUser.getSeatHistoryList();
        Iterator<Seat> iter = seats.iterator();
        while(iter.hasNext()) {
            if(iter.next().getSeatReservationStartTime() < twoWeekAgo)iter.remove();
        }

        map.put(FIELD_NAME_CONFIRMATION_USER_HISTORY, seats);
        map.put(FIELD_NAME_CONFIRMATION_DAY,confirmationTime);
        documentReference.set(map);
    }



    /*
     public static final String FIELD_NAME_CONFIRMATION_HISTORY = "confirmationHistoryKey";
    public static final String FIELD_NAME_CONFIRMATION_USER_KEY = "confirmationUserKey";
    public static final String FIELD_NAME_CONFIRMATION_USER_HISTORY = "seatHistoryList";
    public static final String FIELD_NAME_CONFIRMATION_DAY = "confirmationDay";

    confirmationHistoryKey : Sting
    userKey : String
    seatHistoryList : ArrayList<Seat>
    confirmationDay : long’


     */



}

