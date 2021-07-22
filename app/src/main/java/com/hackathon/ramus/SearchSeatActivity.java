package com.hackathon.ramus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hackathon.ramus.Adapters.ViewPagerSliderAdapter;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Model.SeatItem;
import com.hackathon.ramus.Repository.Repository;
import com.hackathon.ramus.Viewmodel.EmailSignUpViewModel;
import com.hackathon.ramus.Viewmodel.SeatViewModel;
import com.hackathon.ramus.databinding.ActivityEmailSignUpBinding;
import com.hackathon.ramus.databinding.ActivitySearchSeatBinding;

import java.util.ArrayList;
import java.util.List;

import static com.hackathon.ramus.Constants.SEAT_TYPE_NO;
import static com.hackathon.ramus.Constants.SEAT_TYPE_NO_THIN;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_LEFT_DOWN;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_LEFT_UP;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_RIGHT_DOWN;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_RIGHT_UP;
import static com.hackathon.ramus.Constants.SEAT_TYPE_YES_SPACE;

public class SearchSeatActivity extends AppCompatActivity {
    private ActivitySearchSeatBinding binding;
    private SeatViewModel viewModel;
    private ConstraintLayout constraintLayout1, constraintLayout2;
    private Animation animation, animation2, animation3, animation4;
    private int[] emptySeats = new int[15];
    private String[] testString = {"제1열람실A", "제1열람실B", "제1열람실노트북석", "CRETECZONE", "S-Lounge", "캐럴", "제2열람실A", "제2열람실B"
            , "제2열람실C", "제3열람실A", "제3열람실B", "제3열람실C", "제4열람실A", "제4열람실B", "제4열람실C"};
    private String[] roomNames = {"제 1열람실 A", "제 1열람실 B", "제 1열람실 노트북석", "CRETEC ZONE", "S-Lounge", "캐럴", "제 2열람실 A", "제 2열람실 B"
            , "제 2열람실 C", "제 3열람실 A", "제 3열람실 B", "제 3열람실 C", "제 4열람실 A", "제 4열람실 B", "제 4열람실 C"};
    private TextView[] floorTexts = new TextView[5];
    private String[] floorStrings = {"B1", "1층", "2층", "3층", "4층"};
    private List<Integer> seatPossible = new ArrayList<>();
    FirebaseAuth mAuth;
    private int select;

    private int[] images = new int[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int abc = 1;
        for (int i = 0; i < 171; i++) {
            if (i % 9 == 2 || i % 9 == 5) continue;
            if (i / 9 == 4) continue;
            if (i / 9 == 9) continue;
            if (i / 9 == 14) continue;
            if (i == 48 || i == 111) continue;
            if (i == 49 || i == 112) continue;
            if (i == 57 || i == 120) continue;
            if (i == 58 || i == 121) continue;
            if (i % 9 == 8) continue;
            if (i % 2 == 0) {
                seatPossible.add(abc);
            }
            abc++;
        }
        getLiveData();
        init();
        setupHomeImage();
    }


    private void setupHomeImage() {
        images[0] = R.drawable.library_image1;
        images[1] = R.drawable.library_image2;
        images[2] = R.drawable.library_image3;
        images[3] = R.drawable.library_image4;

        binding.layoutRoom.viewpagerLibrary.setAdapter(new ViewPagerSliderAdapter(this, images));
        binding.layoutRoom.viewpagerLibrary.setOffscreenPageLimit(4);

        binding.layoutRoom.viewpagerLibrary.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
    }


    private void getLiveData() {
        viewModel = new SeatViewModel();
        viewModel.init(this);

        for (int i = 0; i < 15; i++) {
            int finalI = i;
            viewModel.getSpecificRoomListData(testString[i], "roomName").observe(this, new Observer<List<Seat>>() {
                @Override
                public void onChanged(List<Seat> seats) {
                    int cnt = 0;
                    long time = System.currentTimeMillis();
                    for (int j = 0; j < seats.size(); j++) {
                        if (seats.get(j).getSeatReservationEndTime() < time && seatPossible.contains(j + 1)) {
                            cnt++;
                        }
                    }
                    emptySeats[finalI] = cnt;
                    if (finalI / 3 == select) {
                        if (finalI % 3 == 0) {
                            binding.layoutRoom.first.prgressbar.setProgress((int) (((double) cnt / 44.0) * 100));
                            binding.layoutRoom.first.peopleRemain.setText("" + cnt);
                        } else if (finalI % 3 == 1) {
                            binding.layoutRoom.second.prgressbar.setProgress((int) (((double) cnt / 44.0) * 100));
                            binding.layoutRoom.second.peopleRemain.setText("" + cnt);
                        } else {
                            binding.layoutRoom.third.prgressbar.setProgress((int) (((double) cnt / 44.0) * 100));
                            binding.layoutRoom.third.peopleRemain.setText("" + cnt);
                        }
                    }
                }
            });
        }


    }

    private void init() {

        binding = ActivitySearchSeatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        floorTexts[0] = binding.layoutFloor.textviewB1;
        floorTexts[1] = binding.layoutFloor.textviewFirst;
        floorTexts[2] = binding.layoutFloor.textviewSecond;
        floorTexts[3] = binding.layoutFloor.textviewThird;
        floorTexts[4] = binding.layoutFloor.textviewFourth;
        for (int i = 0; i < 5; i++) {
            int floorTotal = 0;
            for (int j = 0; j < 3; j++) {
                floorTotal += emptySeats[i * 3 + j];
            }
            if (floorTotal / (132.0) * 100 > 50) {
                floorTexts[i].setBackground(getApplicationContext().getDrawable(R.drawable.rec_full));
                floorTexts[i].setText(floorStrings[i] + " 혼잡");
            } else {
                floorTexts[i].setBackground(getApplicationContext().getDrawable(R.drawable.rec));
                floorTexts[i].setText(floorStrings[i] + " 자리 많음");
            }
        }

        constraintLayout1 = findViewById(R.id.layout_floor);
        constraintLayout2 = findViewById(R.id.layout_room);
        animation = AnimationUtils.loadAnimation(this, R.anim.ani_right);
        animation.setDuration(450);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.ani_left);
        animation2.setDuration(450);
        animation3 = new TranslateAnimation(0, 1300, 0, 0);
        animation3.setDuration(450);
        animation4 = new TranslateAnimation(-1300, 0, 0, 0);
        animation4.setDuration(450);
        binding.layoutFloor.B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayout1.getVisibility() == View.VISIBLE) {
                    startAni();

                    binding.layoutRoom.textviewFirst.setText("제1열람실 A");
                    binding.layoutRoom.textviewSecond.setText("제1열람실 B");
                    binding.layoutRoom.textviewThird.setText("제1열람실 노트북석");

                    binding.layoutRoom.first.peopleRemain.setText("" + emptySeats[0]);
                    binding.layoutRoom.first.prgressbar.setProgress((int) (((double) emptySeats[0] / 44.0) * 100));
                    binding.layoutRoom.second.peopleRemain.setText("" + emptySeats[1]);
                    binding.layoutRoom.second.prgressbar.setProgress((int) (((double) emptySeats[1] / 44.0) * 100));
                    binding.layoutRoom.third.peopleRemain.setText("" + emptySeats[2]);
                    binding.layoutRoom.third.prgressbar.setProgress((int) (((double) emptySeats[2] / 44.0) * 100));

                   /* //만석이면
                    binding.layoutRoom.first.prgressbar.setProgressStartColor(getColor(R.color.red_progress));
                    binding.layoutRoom.first.prgressbar.setProgressEndColor(getColor(R.color.red_progress));

                    //만석아니면
                    //binding.layoutRoom.first.prgressbar.setProgressStartColor(getColor(R.color.blue_progress));
                    //binding.layoutRoom.first.prgressbar.setProgressEndColor(getColor(R.color.blue_progress));*/

                    select = 0;
                }

            }
        });
        binding.layoutFloor.firstFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayout1.getVisibility() == View.VISIBLE) {
                    startAni();
                    binding.layoutRoom.textviewFirst.setText("CRETECZONE");
                    binding.layoutRoom.textviewSecond.setText("S-Lounge");
                    binding.layoutRoom.textviewThird.setText("캐럴");
                    select = 1;


                    binding.layoutRoom.first.peopleRemain.setText("" + emptySeats[3]);
                    binding.layoutRoom.first.prgressbar.setProgress((int) (((double) emptySeats[3] / 44.0) * 100));
                    binding.layoutRoom.second.peopleRemain.setText("" + emptySeats[4]);
                    binding.layoutRoom.second.prgressbar.setProgress((int) (((double) emptySeats[4] / 44.0) * 100));
                    binding.layoutRoom.third.peopleRemain.setText("" + emptySeats[5]);
                    binding.layoutRoom.third.prgressbar.setProgress((int) (((double) emptySeats[5] / 44.0) * 100));
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
                    select = 2;

                    binding.layoutRoom.first.peopleRemain.setText("" + emptySeats[6]);
                    binding.layoutRoom.first.prgressbar.setProgress((int) (((double) emptySeats[6] / 44.0) * 100));
                    binding.layoutRoom.second.peopleRemain.setText("" + emptySeats[7]);
                    binding.layoutRoom.second.prgressbar.setProgress((int) (((double) emptySeats[7] / 44.0) * 100));
                    binding.layoutRoom.third.peopleRemain.setText("" + emptySeats[8]);
                    binding.layoutRoom.third.prgressbar.setProgress((int) (((double) emptySeats[8] / 44.0) * 100));
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
                    select = 3;

                    binding.layoutRoom.first.peopleRemain.setText("" + emptySeats[9]);
                    binding.layoutRoom.first.prgressbar.setProgress((int) (((double) emptySeats[9] / 44.0) * 100));
                    binding.layoutRoom.second.peopleRemain.setText("" + emptySeats[10]);
                    binding.layoutRoom.second.prgressbar.setProgress((int) (((double) emptySeats[10] / 44.0) * 100));
                    binding.layoutRoom.third.peopleRemain.setText("" + emptySeats[11]);
                    binding.layoutRoom.third.prgressbar.setProgress((int) (((double) emptySeats[11] / 44.0) * 100));
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
                    select = 4;

                    binding.layoutRoom.first.peopleRemain.setText("" + emptySeats[12]);
                    binding.layoutRoom.first.prgressbar.setProgress((int) (((double) emptySeats[12] / 44.0) * 100));
                    binding.layoutRoom.second.peopleRemain.setText("" + emptySeats[13]);
                    binding.layoutRoom.second.prgressbar.setProgress((int) (((double) emptySeats[13] / 44.0) * 100));
                    binding.layoutRoom.third.peopleRemain.setText("" + emptySeats[14]);
                    binding.layoutRoom.third.prgressbar.setProgress((int) (((double) emptySeats[14] / 44.0) * 100));
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
                Intent intent = new Intent(SearchSeatActivity.this, StudyRoomActivity.class);
                intent.putExtra("roomname", roomName);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_left, R.anim.ani_right);
            }
        });
        binding.layoutRoom.secondRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roomName = getRoomName(binding.layoutRoom.textviewSecond);
                Intent intent = new Intent(SearchSeatActivity.this, StudyRoomActivity.class);
                intent.putExtra("roomname", roomName);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_left, R.anim.ani_right);
            }
        });
        binding.layoutRoom.thirdRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roomName = getRoomName(binding.layoutRoom.textviewThird);
                Intent intent = new Intent(SearchSeatActivity.this, StudyRoomActivity.class);
                intent.putExtra("roomname", roomName);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_left, R.anim.ani_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (constraintLayout1.getVisibility() == View.VISIBLE) {
            finish();
        } else if (constraintLayout2.getVisibility() == View.VISIBLE) {
            constraintLayout1.setVisibility(View.VISIBLE);
            constraintLayout1.startAnimation(animation4);
            constraintLayout2.setVisibility(View.GONE);
            constraintLayout2.startAnimation(animation3);
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
        //      a = a.replaceAll(" ", "");
        return a;
    }
}

