package com.hackathon.ramus.Repository;

import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Optional;

public class FireStoreOnceLiveData extends LiveData {

    private ListenerRegistration registration;
    private CollectionReference colRef;
    private Class mClass;
    private DocumentReference docRef;
    private String key;


    public FireStoreOnceLiveData(CollectionReference colRef, Class mClass,String email){
        this.colRef = colRef;
        this.mClass = mClass;
        key = email;
    }

    OnCompleteListener<DocumentSnapshot> onCompleteListener = new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    setValue(true);
                } else {
                    setValue(false);
                }
            } else {
                    setValue(false);
            }
        }
    };



    @Override
    protected void onActive() {
        super.onActive();
        Task<DocumentSnapshot> task  = colRef.document(key).get().addOnCompleteListener(onCompleteListener);
    }


}
