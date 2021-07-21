package com.hackathon.ramus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Viewmodel.EmailSignUpViewModel;
import com.hackathon.ramus.Viewmodel.SeatViewModel;
import com.hackathon.ramus.databinding.ActivityEmailSignUpBinding;
import com.hackathon.ramus.databinding.ActivitySearchSeatBinding;

import java.util.List;

public class SearchSeatActivity extends AppCompatActivity {
    private ActivitySearchSeatBinding binding;
    private SeatViewModel viewModel;
    private ConstraintLayout constraintLayout1, constraintLayout2;
    private Animation animation, animation2;
    private int[] emptySeats = new int[15];
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLiveData();
        init();
    }

    private void getLiveData() {
        viewModel = new SeatViewModel();
        viewModel.init(this);

        viewModel.getSpecificRoomListData("제1열람실A").observe(this, new Observer<List<Seat>>() {
            @Override
            public void onChanged(List<Seat> seats) {
                emptySeats[0] = seats.size();
            }
        });
        viewModel.getSpecificRoomListData("제1열람실B").observe(this, new Observer<List<Seat>>() {
            @Override
            public void onChanged(List<Seat> seats) {
                emptySeats[1] = seats.size();
            }
        });
        viewModel.getSpecificRoomListData("제1열람실노트북석").observe(this, new Observer<List<Seat>>() {
            @Override
            public void onChanged(List<Seat> seats) {
                emptySeats[2] = seats.size();
            }
        });

    }

    private void init() {
        binding = ActivitySearchSeatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        constraintLayout1 = findViewById(R.id.layout_floor);
        constraintLayout2 = findViewById(R.id.layout_room);
        animation = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.scale_down);
        binding.layoutFloor.B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayout1.getVisibility() == View.VISIBLE) {
                    startAni();
                    binding.layoutRoom.textviewFirst.setText("제1열람실 A (" + emptySeats[0] + "/100)");
                    binding.layoutRoom.textviewSecond.setText("제1열람실 B (" + emptySeats[1] + "/100)");
                    binding.layoutRoom.textviewThird.setText("제1열람실 노트북석 (" + emptySeats[2] + "/100)");

                }

            }
        });
        binding.layoutFloor.firstFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayout1.getVisibility() == View.VISIBLE) {
                    startAni();
                    binding.layoutRoom.textviewFirst.setText("CRETEC Zone (100/100)");
                    binding.layoutRoom.textviewSecond.setText("S-Lounge (66/100)");
                    binding.layoutRoom.textviewThird.setText("캐럴 (58/100)");
                }
            }
        });
        binding.layoutFloor.secondFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayout1.getVisibility() == View.VISIBLE) {
                    startAni();
                    binding.layoutRoom.textviewFirst.setText("제2열람실 A (100/100)");
                    binding.layoutRoom.textviewSecond.setText("제2열람실 B (66/100)");
                    binding.layoutRoom.textviewThird.setText("제2열람실 C (58/100)");
                }
            }
        });
        binding.layoutFloor.thirdFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayout1.getVisibility() == View.VISIBLE) {
                    startAni();
                    binding.layoutRoom.textviewFirst.setText("제3열람실 A (100/100)");
                    binding.layoutRoom.textviewSecond.setText("제3열람실 B (66/100)");
                    binding.layoutRoom.textviewThird.setText("제3열람실 C (58/100)");
                }
            }
        });
        binding.layoutFloor.fourthFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayout1.getVisibility() == View.VISIBLE) {
                    startAni();
                    binding.layoutRoom.textviewFirst.setText("제3열람실 A (100/100)");
                    binding.layoutRoom.textviewSecond.setText("제3열람실 B (66/100)");
                    binding.layoutRoom.textviewThird.setText("제3열람실 C (58/100)");
                }
            }
        });
        binding.backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (constraintLayout1.getVisibility() == View.VISIBLE) {
            finish();
        } else if (constraintLayout2.getVisibility() == View.VISIBLE) {
            constraintLayout1.setVisibility(View.VISIBLE);
            constraintLayout1.startAnimation(animation2);
            constraintLayout2.setVisibility(View.GONE);
            constraintLayout2.startAnimation(animation);
        }
    }

    private void startAni() {
        constraintLayout1.setVisibility(View.GONE);
        constraintLayout1.startAnimation(animation);
        constraintLayout2.setVisibility(View.VISIBLE);
        constraintLayout2.startAnimation(animation2);
    }

}

