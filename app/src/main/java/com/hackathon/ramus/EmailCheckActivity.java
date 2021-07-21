package com.hackathon.ramus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.ramus.Viewmodel.EmailCheckViewModel;
import com.hackathon.ramus.databinding.ActivityEmailCheckBinding;
import com.hackathon.ramus.databinding.DialogEmailAuthCheckBinding;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.hackathon.ramus.Constants.INTENT_DATA_EMAIL;
import static com.hackathon.ramus.Constants.INTENT_DATA_NUMBER;
import static com.hackathon.ramus.EmailAuthLoginActivity.numberGen;


public class EmailCheckActivity extends AppCompatActivity {
    private ActivityEmailCheckBinding binding;
    private EmailCheckViewModel viewModel;

    private String answerNumber ;
    private String email;
    private int cnt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        answerNumber = getIntent().getExtras().getString(INTENT_DATA_NUMBER);
        email = getIntent().getExtras().getString(INTENT_DATA_EMAIL);
        init();
        setListeners();
        observe();
    }

    private void observe(){
        viewModel.getErrorCntMutableLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 3){
                    Toast.makeText(EmailCheckActivity.this, "3회 오류로 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
                    ActivityCompat.finishAffinity(EmailCheckActivity.this);
                }
            }
        });



    }

    private void setListeners(){

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputNumber = binding.editTextNumber.getText().toString();
                if(checkNumbers(inputNumber)){
                    startUserRegisterActivity();
                }else{
                    setWarningMessage();
                }
            }
        });

        binding.textViewRecertifiate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerNumber = getAnswerNumberAgain();
                sendMail();
            }
        });
    }

    private void sendMail(){

        String message = answerNumber +"를 입력해 주세요";
        String subject = "knu library 앱을 활용기 위한 인증 메일입니다";

        JavaMail javaMailAPI = new JavaMail(EmailCheckActivity.this,email,subject,message);
        javaMailAPI.execute();
    }


    private String getAnswerNumberAgain(){
        return numberGen(4,1);
    }

    private void init(){
        binding = ActivityEmailCheckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(EmailCheckViewModel.class);
        viewModel.init(this);


        binding.editTextNumber.requestFocus();
        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(600);
        animation.setStartOffset(430);
        binding.buttonRegister.setVisibility(View.VISIBLE);
        binding.buttonRegister.startAnimation(animation);


    }

    private void startUserRegisterActivity(){
        Intent intent = new Intent(getApplicationContext(),UserRegisterActivity.class);
        intent.putExtra(INTENT_DATA_EMAIL,email);
        startActivity(intent);
        overridePendingTransition(R.anim.ani_left,R.anim.ani_right);
        finish();
    }

    private void setWarningMessage(){
        cnt++;
        Toast.makeText(EmailCheckActivity.this, "오류 " +cnt +"회", Toast.LENGTH_SHORT).show();
        inflateDialog();
    }
    private void inflateDialog(){
        DialogEmailAuthCheckBinding dialogEmailAuthCheckBinding = DialogEmailAuthCheckBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(EmailCheckActivity.this);
        dialog.setContentView(dialogEmailAuthCheckBinding.getRoot());
        TextView dialog_text_view_title = dialog.findViewById(R.id.dialog_text_view_title);
        dialog_text_view_title.setText("3회 오류시 앱이 강제종료 됩니다\n " + "현재 " + cnt +"");
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewModel.getErrorCntMutableLiveData().setValue(cnt);
        dialogEmailAuthCheckBinding.signUpOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


    private boolean checkNumbers(String inputNumber){
        if(answerNumber.equals(inputNumber))return true;
        return false;
    }


    private  class JavaMail extends AsyncTask<Void,Void,Void> {

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

}