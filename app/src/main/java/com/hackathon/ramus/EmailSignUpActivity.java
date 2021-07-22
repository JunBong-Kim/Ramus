package com.hackathon.ramus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hackathon.ramus.Viewmodel.EmailAuthLoginViewModel;
import com.hackathon.ramus.Viewmodel.EmailSignUpViewModel;
import com.hackathon.ramus.databinding.ActivityEmailSignUpBinding;

import org.w3c.dom.Text;

public class EmailSignUpActivity extends AppCompatActivity {
    private ActivityEmailSignUpBinding binding;
    private EmailSignUpViewModel viewModel;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        setClickListeners();
    }

    private void init(){
        binding = ActivityEmailSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(EmailSignUpViewModel.class);
        viewModel.init(this);
    }

    private void setClickListeners(){
        binding.buttonCreateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextKnuEmail.getText().toString();
                String password = binding.editTextKnuPassword.getText().toString();

                if(!areEmailAndPassWordValid(email,password)){
                    Toast.makeText(EmailSignUpActivity.this, "양식을 확인해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                //여기서 다이얼로그 띄우고 , 비밀번호와 아이디 확인 시켜주고, 그리고 확인버튼 누른 후 createUser() 호

                createUser(email,password);
            }
        });

    }

    private boolean areEmailAndPassWordValid(String email,String password){


        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))return false;
        //학교메일만 로직 추가
        if(password.length() < 6)return false;
        //비밀번호는 6자리 이상

        return true;
    }


    private void createUser(String email,String password){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        authResult.getUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EmailSignUpActivity.this, "메일함 확인 바랍니다!", Toast.LENGTH_SHORT).show();
                                ActivityCompat.finishAffinity(EmailSignUpActivity.this);
                                startActivity(new Intent(getApplicationContext(),EmailAuthLoginActivity.class));
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EmailSignUpActivity.this, "이미 등록 되어 았는 메일입니다. 관리자 문의 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

