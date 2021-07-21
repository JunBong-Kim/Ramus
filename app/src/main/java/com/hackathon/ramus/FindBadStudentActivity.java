package com.hackathon.ramus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import com.google.firebase.auth.FirebaseAuth;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Viewmodel.SeatViewModel;
import com.hackathon.ramus.databinding.ActivitySearchSeatBinding;

import java.util.List;

public class FindBadStudentActivity extends AppCompatActivity {
    private ActivitySearchSeatBinding binding;
    private SeatViewModel viewModel;
    private ConstraintLayout constraintLayout1, constraintLayout2;
    private Animation animation, animation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLiveData();
        init();
    }

    private void getLiveData() {
        viewModel = new SeatViewModel();
        viewModel.init(this);

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
                    binding.layoutRoom.textviewFirst.setText("제1열람실 A");
                    binding.layoutRoom.textviewSecond.setText("제1열람실 B");
                    binding.layoutRoom.textviewThird.setText("제1열람실 노트북석");
                }

            }
        });
        binding.layoutFloor.firstFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayout1.getVisibility() == View.VISIBLE) {
                    startAni();
                    binding.layoutRoom.textviewFirst.setText("CRETEC Zone");
                    binding.layoutRoom.textviewSecond.setText("S-Lounge");
                    binding.layoutRoom.textviewThird.setText("캐럴");
                }
            }
        });
        binding.layoutFloor.secondFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayout1.getVisibility() == View.VISIBLE) {
                    startAni();
                    binding.layoutRoom.textviewFirst.setText("제2열람실 A");
                    binding.layoutRoom.textviewSecond.setText("제2열람실 B");
                    binding.layoutRoom.textviewThird.setText("제2열람실 C");
                }
            }
        });
        binding.layoutFloor.thirdFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayout1.getVisibility() == View.VISIBLE) {
                    startAni();
                    binding.layoutRoom.textviewFirst.setText("제3열람실 A");
                    binding.layoutRoom.textviewSecond.setText("제3열람실 B");
                    binding.layoutRoom.textviewThird.setText("제3열람실 C");
                }
            }
        });
        binding.layoutFloor.fourthFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayout1.getVisibility() == View.VISIBLE) {
                    startAni();
                    binding.layoutRoom.textviewFirst.setText("제4열람실 A");
                    binding.layoutRoom.textviewSecond.setText("제4열람실 B");
                    binding.layoutRoom.textviewThird.setText("제4열람실 C");
                }
            }
        });
        binding.backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.layoutRoom.firstRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roomName = getRoomName(binding.layoutRoom.textviewFirst);
                Intent intent = new Intent(FindBadStudentActivity.this, StudyRoomActivity.class);
                intent.putExtra("roomname", roomName);
                startActivity(intent);
            }
        });
        binding.layoutRoom.secondRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roomName = getRoomName(binding.layoutRoom.textviewSecond);
                Intent intent = new Intent(FindBadStudentActivity.this, StudyRoomActivity.class);
                intent.putExtra("roomname", roomName);
                startActivity(intent);
            }
        });
        binding.layoutRoom.thirdRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roomName = getRoomName(binding.layoutRoom.textviewThird);
                Intent intent = new Intent(FindBadStudentActivity.this, StudyRoomActivity.class);
                intent.putExtra("roomname", roomName);
                startActivity(intent);
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


    private String getRoomName(TextView textView) {
        String a = textView.getText().toString();
        a = a.replaceAll(" ", "");
        return a;
    }
}

