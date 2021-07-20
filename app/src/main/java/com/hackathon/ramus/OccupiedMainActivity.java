package com.hackathon.ramus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Viewmodel.OccupiedMainViewModel;
import com.hackathon.ramus.databinding.ActivityOccupiedMainBinding;

import static com.hackathon.ramus.Constants.DATA_USER_SEAT_NULL;

public class OccupiedMainActivity extends AppCompatActivity {
    private ActivityOccupiedMainBinding binding;
    private OccupiedMainViewModel viewModel;
    private String userKey,userSeat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        observe();
        setClickListeners();
    }

    private void setClickListeners(){
        binding.buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //seatReservationTime 에 있는 시간을 현재 시간으로 바꾸고,
                //seatUserKey 도 NULL 로 바꾸고
                viewModel.updateSeatNewUserKeyAndNewEndTime(userSeat,DATA_USER_SEAT_NULL,System.currentTimeMillis());
                //userSeat 도 NULL 로 바꾼다
                viewModel.updateUserNewSeatKey(userKey,DATA_USER_SEAT_NULL);
            }
        });
    }

    private void observe(){

        viewModel.getSpecificUserLiveData(userKey).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user.getUserSeat().equals(DATA_USER_SEAT_NULL)){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }else{
                    binding.textViewStatus.setText(user.getUserSeat() +"에서 공부 중입니다");
                    userSeat = user.getUserSeat();
                }
            }
        });
    }


    private void init(){
        binding = ActivityOccupiedMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(OccupiedMainViewModel.class);
        viewModel.init(OccupiedMainActivity.this);
        userKey = SharedPreferenceManager.getEmail(OccupiedMainActivity.this, SharedPreferenceManager.KNU_EMAIL);
    }
}