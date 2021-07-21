package com.hackathon.ramus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Viewmodel.OccupiedMainViewModel;
import com.hackathon.ramus.databinding.ActivityOccupiedMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.hackathon.ramus.Constants.DATA_USER_SEAT_NULL;
import static com.hackathon.ramus.Constants.INTENT_DATA_WEB_VIEW_TYPE;
import static com.hackathon.ramus.Constants.TYPE_DAEGU;
import static com.hackathon.ramus.Constants.TYPE_KB;
import static com.hackathon.ramus.Constants.TYPE_KNU;
import static com.hackathon.ramus.Constants.TYPE_MOHW;
import static com.hackathon.ramus.Constants.TYPE_NAVER;

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
        binding.layoutMain2Function.layoutFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //seatReservationTime 에 있는 시간을 현재 시간으로 바꾸고,
                //seatUserKey 도 NULL 로 바꾸고
                viewModel.updateSeatNewUserKeyAndNewEndTime(userSeat,DATA_USER_SEAT_NULL,System.currentTimeMillis());
                //userSeat 도 NULL 로 바꾼다
                viewModel.updateUserNewSeatKey(userKey,DATA_USER_SEAT_NULL);
            }
        });

        binding.layoutCoronaWeb.daeguWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra(INTENT_DATA_WEB_VIEW_TYPE,TYPE_DAEGU);
                startActivity(intent);
            }
        });

        binding.layoutCoronaWeb.knuWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewActivity.class);
                intent.putExtra(INTENT_DATA_WEB_VIEW_TYPE,TYPE_KNU);
                startActivity(intent);
            }
        });

        binding.layoutCoronaWeb.gbWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewActivity.class);
                intent.putExtra(INTENT_DATA_WEB_VIEW_TYPE,TYPE_KB);
                startActivity(intent);
            }
        });

        binding.layoutCoronaWeb.mohwWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewActivity.class);
                intent.putExtra(INTENT_DATA_WEB_VIEW_TYPE,TYPE_MOHW);
                startActivity(intent);
            }
        });

        binding.layoutCoronaWeb.imageDeaguSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DaeguCovidConfirmationActivity.class));
            }
        });


        binding.layoutCoronaWeb.naverWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewActivity.class);
                intent.putExtra(INTENT_DATA_WEB_VIEW_TYPE,TYPE_NAVER);
                startActivity(intent);
            }
        });

        binding.youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("https://www.youtube.com/channel/UCRk-80t6-G4_RE2hzDdUPTA")) // edit this url
                        .setPackage("com.google.android.youtube"));	// do not edit
            }
        });
        binding.instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.instagram.com/knu.library/";
                Intent Blog_Intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(Blog_Intent);
            }
        });
        binding.facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/knulib/";
                Intent Blog_Intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(Blog_Intent);
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
                    binding.layoutMain2Function.textViewUserName.setText(user.getUserName() );
                    userSeat = user.getUserSeat();

                    ArrayList<Seat> seats = user.getSeatHistoryList();
                    Seat recentSeat = seats.get(seats.size()-1);

                    binding.layoutMain2Function.textViewTime.setText(HH_mm_format_day(recentSeat.getSeatReservationStartTime())
                    +" ~ " + HH_mm_format_day(recentSeat.getSeatReservationEndTime()));

                    if(System.currentTimeMillis() > recentSeat.getSeatReservationEndTime()){
                        binding.layoutMain2Function.textViewSeat.setText("좌석을 반납 해 주세요!");
                        binding.layoutMain2Function.textViewSeat.setTextColor(Color.RED);
                    }else{
                        binding.layoutMain2Function.textViewSeat.setText(userSeat);
                    }
                }
            }
        });
    }

    private String HH_mm_format_day(long time){
        SimpleDateFormat dateFormat = new SimpleDateFormat(" HH:mm");
        return dateFormat.format(time);
    }


    private void init(){
        binding = ActivityOccupiedMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(OccupiedMainViewModel.class);
        viewModel.init(OccupiedMainActivity.this);
        userKey = SharedPreferenceManager.getEmail(OccupiedMainActivity.this, SharedPreferenceManager.KNU_EMAIL);
    }
}