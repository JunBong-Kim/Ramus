package com.hackathon.ramus.Repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hackathon.ramus.Model.Seat;

import java.util.ArrayList;

public class FireStoreLiveData<T> extends LiveData<T> {
    public static final String TAG = "FireStoreLiveData";
    public static final int COLLECTION = 0;
    public static final int DOCUMENT = 1;
    public static final int QUERY = 2;


    private ListenerRegistration registration;
    private CollectionReference colRef;
    private Class mClass;
    private DocumentReference docRef;
    private Query query;
    private int mType;


    public FireStoreLiveData(CollectionReference colRef, Class mClass, int type) {
        this.colRef = colRef;
        this.mClass = mClass;
        this.mType = type;
    }

    public FireStoreLiveData(DocumentReference docRef, Class mClass, int type) {
        this.docRef = docRef;
        this.mClass = mClass;
        this.mType = type;

    }

    public FireStoreLiveData(Query query, Class mClass, int type) {
        this.query = query;
        this.mClass = mClass;
        this.mType = type;
    }


    EventListener<QuerySnapshot> querySnapshotEventListener = new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

            if (error != null) {
                Log.e(TAG, "Listen failed.", error);
                return;
            }

            if (value != null) {
                ArrayList<T> itemList = new ArrayList<>();

                for (DocumentSnapshot snapshot : value.getDocuments()) {
                    T item = (T) snapshot.toObject(mClass);
                    itemList.add(item);
                }

                setValue((T) itemList);
            }
        }
    };

    EventListener<DocumentSnapshot> documentSnapshotEventListener = new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
            if (error != null) {
                Log.e(TAG, "Listen failed.", error);
                return;
            }

            if (value != null) {
                T item = (T) value.toObject(mClass);
                setValue((T) item);
            }
        }
    };


    @Override
    protected void onActive() {
        super.onActive();
        switch (mType) {
            case COLLECTION:
                registration = colRef.addSnapshotListener(querySnapshotEventListener);
                break;
            case DOCUMENT:
                registration = docRef.addSnapshotListener(documentSnapshotEventListener);
                break;
            case QUERY:
                registration = query.addSnapshotListener(querySnapshotEventListener);
                break;


        }
        Log.e(TAG, "onActive:  등록 ");
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (!hasActiveObservers()) {
            registration.remove();
            registration = null;
            Log.e(TAG, "onInactive:  해제 ");
        }
    }


}
