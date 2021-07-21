package com.hackathon.ramus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Viewmodel.EmailAuthLoginViewModel;
import com.hackathon.ramus.databinding.ActivityEmailAuthLoginBinding;
import com.hackathon.ramus.databinding.DialogEmailAuthCheckBinding;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.hackathon.ramus.Constants.INTENT_DATA_EMAIL;
import static com.hackathon.ramus.Constants.INTENT_DATA_NUMBER;
import static com.hackathon.ramus.SharedPreferenceManager.IS_EMAIL_REGISTERED;

public class EmailAuthLoginActivity extends AppCompatActivity {

    private String TAG = "EmailAuthLoginActivity";

    private ActivityEmailAuthLoginBinding binding;
    private EmailAuthLoginViewModel viewModel;
    String randomNumber = numberGen(4,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autoLoginCheck();
        init();
        checkForFirstStart();
        setClickListeners();





    }

    private void setClickListeners(){
        binding.emailRegister.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }

    private void sendMail(){
        String mail = binding.emailRegister.editTextEmail.getText().toString() +"@knu.ac.kr";
        String message = randomNumber +"를 입력해 주세요";
        String subject = "knu library 앱을 활용기 위한 인증 메일입니다";

        JavaMail javaMailAPI = new JavaMail(EmailAuthLoginActivity.this,mail,subject,message);
        javaMailAPI.execute();
    }



    private void checkForFirstStart(){
        boolean isEmailRegistered = SharedPreferenceManager.getFirstCheckBooleanData(getApplicationContext(),IS_EMAIL_REGISTERED);
        if(!isEmailRegistered)return;
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    private void init(){
        binding = ActivityEmailAuthLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(EmailAuthLoginViewModel.class);
        viewModel.init(this);

        ConstraintLayout constraintLayoutl1 = findViewById(R.id.start);
        ConstraintLayout constraintLayoutl2 = findViewById(R.id.email_register);
        binding.start.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.emailRegister.editTextEmail.requestFocus();
                constraintLayoutl1.setVisibility(View.GONE);
                constraintLayoutl2.setVisibility(View.VISIBLE);
                InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                Animation animation = new AlphaAnimation(0, 1);
                animation.setDuration(300);

                binding.emailRegister.buttonRegister.setVisibility(View.VISIBLE);
                binding.emailRegister.buttonRegister.startAnimation(animation);
            }
        });


    }

    public static String numberGen(int len, int dupCd ) {

        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수

        for(int i=0;i<len;i++) {

            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));

            if(dupCd==1) {
                //중복 허용시 numStr에 append
                numStr += ran;
            }else if(dupCd==2) {
                //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if(!numStr.contains(ran)) {
                    //중복된 값이 없으면 numStr에 append
                    numStr += ran;
                }else {
                    //생성된 난수가 중복되면 루틴을 다시 실행한다
                    i-=1;
                }
            }
        }
        return numStr;
    }


    private class JavaMail extends AsyncTask<Void,Void,Void> {

        //Add those line in dependencies
        //implementation files('libs/activation.jar')
        //implementation files('libs/additionnal.jar')
        //implementation files('libs/mail.jar')

        //Need INTERNET permission

        //Variables
        private Context mContext;
        private Session mSession;

        private String mEmail;
        private String mSubject;
        private String mMessage;

        private ProgressDialog mProgressDialog;

        //Constructor
        public JavaMail(Context mContext, String mEmail, String mSubject, String mMessage) {
            this.mContext = mContext;
            this.mEmail = mEmail;
            this.mSubject = mSubject;
            this.mMessage = mMessage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(mContext,"메일 전송", "잠시만 기다려 주십시...",false,false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();

            Intent intent = new Intent(getApplicationContext(),EmailCheckActivity.class);
            intent.putExtra(INTENT_DATA_NUMBER,randomNumber);
            intent.putExtra(INTENT_DATA_EMAIL,this.mEmail);
            startActivity(intent);
            overridePendingTransition(R.anim.ani_left,R.anim.ani_right);
            finish();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Creating properties
            Properties props = new Properties();

            //Configuring properties for gmail
            //If you are not using gmail you may need to change the values
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            //Creating a new session
            mSession = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        //Authenticating the password
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("library.knu.ramus@gmail.com"
                                    ,"wkdwlsgus");
                        }
                    });

            try {
                //Creating MimeMessage object
                MimeMessage mm = new MimeMessage(mSession);
                //Setting sender address
                mm.setFrom(new InternetAddress("library.knu.ramus@gmail.com"));
                //Adding receiver
                mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
                //Adding subject
                mm.setSubject(mSubject);
                //Adding message
                mm.setText(mMessage);
                //Sending email
                Transport.send(mm);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void autoLoginCheck(){
         String email = SharedPreferenceManager.getEmail(EmailAuthLoginActivity.this,SharedPreferenceManager.KNU_EMAIL);
        if(email.equals(SharedPreferenceManager.DEFAULT_EMAIL_VALUE))return;
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();

    }



}

