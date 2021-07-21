package com.hackathon.ramus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Viewmodel.StudyRoomViewModel;
import com.hackathon.ramus.databinding.ActivityStudyRoomBinding;

import java.util.List;

public class StudyRoomActivity extends AppCompatActivity {
    private ActivityStudyRoomBinding binding;
    private Button[] seats = new Button[100];
    private GridLayout.LayoutParams params;
    private String roomName;
    private StudyRoomViewModel viewModel;
    private List<Seat> seatsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudyRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        roomName = intent.getStringExtra("roomname");
        init();

       /* binding.seatGridlayout.getLayoutParams().columnSpec =
                GridLayout.spec(GridLayout.UNDEFINED, 1f);*/
        //  binding.seatGridlayout.addView(make4Button(seats[0], seats[1], seats[2], seats[3]));
        // binding.seatGridlayout.addView(make4Button(seats[4], seats[5], seats[6], seats[7]));
        //  binding.seatGridlayout.addView(make4Button(seats[8], seats[9], seats[10], seats[11]));
        //  binding.seatGridlayout.addView(make4Button(seats[12], seats[13], seats[14], seats[15]));
    }

    private void init() {
        viewModel = new StudyRoomViewModel();
        viewModel.init(this);

        for (int i = 0; i < 100; i++) {
            seats[i] = new Button(this);
            int finalI = i;
            seats[i].setText(i + 1 + "");
            binding.seatGridlayout.addView(seats[i]);
        }

        viewModel.getSpecificRoomListData(roomName).observe(this, new Observer<List<Seat>>() {
            @Override
            public void onChanged(List<Seat> seatList) {
                seatsData=seatList;
                for(int i=0;i<seatList.size();i++){
                    int index = Integer.parseInt(seatList.get(i).getSeatKey().substring(roomName.length()));
                    if(seatList.get(i).getSeatReservationEndTime()<System.currentTimeMillis()){
                        seats[index-1].setText("예약가능");
                    }else{
                        seats[index-1].setText("예약불가");
                    }

                }
            }
        });
        /*params = new GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f));
        params.width = 0;
        for (int i = 0; i < 120; i++) {
            seats[i] = new Button(getApplicationContext());
            seats[i].setText((i + 1) + "");
            binding.seatGridlayout.addView(seats[i]);
            //seats[i].setX(binding.seatGridlayout.getX() / 16);
            //seats[i].setLayoutParams(params);
        }*/
    }

    private void makeBlock() {

    }

    private LinearLayout make4Button(Button b1, Button b2, Button b3, Button b4) {
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setBackgroundColor(Color.RED);
        linearLayout1.addView(b1);
        linearLayout1.addView(b2);
        linearLayout1.setClickable(true);

        LinearLayout linearLayout2 = new LinearLayout(getApplicationContext());
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout2.setBackgroundColor(Color.BLACK);
        linearLayout2.addView(b3);
        linearLayout2.addView(b4);
        linearLayout2.setClickable(true);

        linearLayout.addView(linearLayout1);
        linearLayout.addView(linearLayout2);
        linearLayout.setLayoutParams(params);
        return linearLayout;
    }
}