package com.hackathon.ramus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Repository.Repository;
import com.hackathon.ramus.databinding.ActivityUserRegisterBinding;

import static com.hackathon.ramus.Constants.INTENT_DATA_EMAIL;

public class UserRegisterActivity extends AppCompatActivity {

    private ActivityUserRegisterBinding binding;
    private String userName,userStudentNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        userName = "장진현";
        userStudentNumber = "2015113236";

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSharedRefEmailAndFirebaseDb();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

    }

    private void init(){
        binding = ActivityUserRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void setSharedRefEmailAndFirebaseDb(){
        String email = getIntent().getExtras().getString(INTENT_DATA_EMAIL);
        SharedPreferenceManager.setEmail(UserRegisterActivity.this,SharedPreferenceManager.KNU_EMAIL,email);
        User user = new User(email,userName,userStudentNumber,"tempToken");
        Repository.getInstance(UserRegisterActivity.this).setUserData(user);
    }

  /*

         private void startMainActivity(){
        SharedPreferenceManager.setFirstCheckBooleanData(StartActivity.this,SharedPreferenceManager.IS_FIRST_CHECK,false);
        startActivity(new Intent(getApplicationContext(), StartActivity.class));
        finish();
        }
         */
}