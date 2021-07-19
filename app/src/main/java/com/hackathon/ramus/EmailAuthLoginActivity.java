package com.hackathon.ramus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hackathon.ramus.databinding.ActivityEmailAuthLoginBinding;

public class EmailAuthLoginActivity extends AppCompatActivity {

    private String TAG = "EmailAuthLoginActivity";

    private ActivityEmailAuthLoginBinding binding;
    ActionCodeSettings actionCodeSettings;

    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " + mUser.isEmailVerified() );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

       /* sendEmailTest();

        Log.e(TAG, "onCreate: " + mUser.getEmail() );
        mUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG, "onSuccess:성공" );
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "email: " + e.getMessage() );
            }
        });
*/
        //mUser.isEmailVerified()
    }



    private void init(){
        binding = ActivityEmailAuthLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }

    private void sendEmailTest(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword("shut_malfoy@naver.com","123456")
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(EmailAuthLoginActivity.this, "" + authResult.getUser().getEmail(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.getMessage() );
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