package com.hackathon.ramus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.hackathon.ramus.databinding.ActivityEmailAuthLoginBinding;

public class EmailAuthLoginActivity extends AppCompatActivity {

    private String TAG = "EmailAuthLoginActivity";

    private ActivityEmailAuthLoginBinding binding;
    ActionCodeSettings actionCodeSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        sendEmailTest();


    }



    private void init(){
        binding = ActivityEmailAuthLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("knulibrary-99259.firebaseapp.com")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setIOSBundleId("com.example.ios")
                        .setAndroidPackageName(
                                "com.hackathon.ramus",
                                true, /* installIfNotAvailable */
                                "12"    /* minimumVersion */)
                        .build();

    }

    private void sendEmailTest(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendSignInLinkToEmail("shut_malfoy@naver.com",actionCodeSettings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "Email sent.");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Email sent." + e.getMessage());

            }
        });


    }

    private void initViewModel(){

    }
}


/*

    private void initBinding(){
        binding = ActivityPhoneAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initViewModel(){

        viewModel = new ViewModelProvider(this).get(PhoneAuthViewModel.class);
        viewModel.init(PhoneAuthActivity.this);

    }


 */