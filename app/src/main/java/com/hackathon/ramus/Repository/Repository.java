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
        db.collection(COLLECTION_NAME_OF_USERS).document(user.getUserKey()).set(map, SetOptions.merge());
    }


    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public LiveData<User> getSpecificUserData(String userKey) {
        return new FireStoreLiveData<>(db.collection(COLLECTION_NAME_OF_USERS).document(userKey), User.class, DOCUMENT);
    }

}
