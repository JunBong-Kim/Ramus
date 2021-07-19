package com.hackathon.ramus.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.ListenerRegistration;

public class LoginCheckLiveData<T> extends LiveData<T> {
    public static final String TAG = "LoginCheckLiveData";
    private FirebaseAuth mAuth;
    private ListenerRegistration registration;
    public LoginCheckLiveData(FirebaseAuth firebaseAuth){
        this.mAuth = firebaseAuth;
    }


    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
           setValue((T)firebaseAuth.getCurrentUser());
        }
    };

    @Override
    protected void onActive() {
        super.onActive();
        Log.e(TAG, "onActive: 동작" );
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Log.e(TAG, "onInactive: 동작" );
        mAuth.removeAuthStateListener(authStateListener);

    }
}
