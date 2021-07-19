package com.hackathon.ramus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import static com.hackathon.ramus.Constants.INTENT_DATA_EMAIL;
import static com.hackathon.ramus.Constants.INTENT_DATA_NUMBER;


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

                /*
        viewModel = new ViewModelProvider(this).get(EmailAuthLoginViewModel.class);
        viewModel.init(this);
                 */


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

}