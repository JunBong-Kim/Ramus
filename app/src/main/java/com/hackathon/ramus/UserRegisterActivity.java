package com.hackathon.ramus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Observable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Repository.Repository;
import com.hackathon.ramus.Viewmodel.UserRegisterViewModel;
import com.hackathon.ramus.databinding.ActivityUserRegisterBinding;

import static com.hackathon.ramus.Constants.DATA_USER_SEAT_NULL;
import static com.hackathon.ramus.Constants.INTENT_DATA_EMAIL;

public class UserRegisterActivity extends AppCompatActivity {

    private ActivityUserRegisterBinding binding;
    private UserRegisterViewModel viewModel;
    private String userName,userStudentNumber;
    private int state=0;
    private String email;
    private Animation ani_left,ani_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         email = getIntent().getExtras().getString(INTENT_DATA_EMAIL);

        init();
        observe();
        setListener();
    }

    private void observe(){

        ProgressDialog mProgressDialog = ProgressDialog.show(UserRegisterActivity.this,"등록 확인", "잠시만 기다려주세요!",false,false);
        mProgressDialog.show();
        viewModel.isUserRegistered(email).observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                boolean isRegistered = (Boolean)o;
                if(isRegistered){
                    SharedPreferenceManager.setEmail(UserRegisterActivity.this,SharedPreferenceManager.KNU_EMAIL,email);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                    Toast.makeText(UserRegisterActivity.this, "등록이 된 이메일입니다.", Toast.LENGTH_SHORT).show();
                }else{
                    binding.layoutParent.setVisibility(View.VISIBLE);
                }
                mProgressDialog.dismiss();

            }
        });
    }

    private void setListener(){

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ani_left = new TranslateAnimation(0,-1000,0,0);
                ani_left.setDuration(180);
                ani_right = new TranslateAnimation(1000,0,0,0);
                ani_right.setDuration(180);
                if(state==0)
                {
                    userName = binding.editTextNumber.getText().toString();
                    binding.title.setText("");
                    binding.title.startAnimation(ani_left);
                    binding.editTextNumber.setText("");
                    binding.editTextNumber.startAnimation(ani_left);
                    ani_left.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) { }
                        @Override
                        public void onAnimationRepeat(Animation animation) { }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            binding.title.setText("학번을 입력해주세요");
                            binding.title.startAnimation(ani_right);
                            binding.editTextNumber.setText("");
                            binding.editTextNumber.setHint("2016113597");
                            binding.editTextNumber.startAnimation(ani_right);
                            state=1;
                        }
                    });

                }
                else if(state==1)
                {
                    userStudentNumber = binding.editTextNumber.getText().toString();
                    setSharedRefEmailAndFirebaseDb();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    overridePendingTransition(R.anim.ani_left,R.anim.ani_right);
                    finish();

                }
            }
        });
    }
    private void init(){
        binding = ActivityUserRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.layoutParent.setVisibility(View.GONE);

        binding.editTextNumber.requestFocus();
        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        viewModel = new ViewModelProvider(this).get(UserRegisterViewModel.class);
        viewModel.init(getApplicationContext());


    }

    private void setSharedRefEmailAndFirebaseDb(){
        SharedPreferenceManager.setEmail(UserRegisterActivity.this,SharedPreferenceManager.KNU_EMAIL,email);
        User user = new User(email,userName,userStudentNumber,"tempToken",DATA_USER_SEAT_NULL);
        Repository.getInstance(UserRegisterActivity.this).setUserData(user);
    }


}