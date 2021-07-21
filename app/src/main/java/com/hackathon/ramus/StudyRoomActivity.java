package com.hackathon.ramus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hackathon.ramus.Adapters.StudyRoomAdapter;
import com.hackathon.ramus.Dialog.ConfirmSeatDialog;
import com.hackathon.ramus.Dialog.MyListener;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Model.SeatItem;
import com.hackathon.ramus.Viewmodel.StudyRoomViewModel;
import com.hackathon.ramus.databinding.ActivityStudyRoomBinding;
import com.hackathon.ramus.databinding.BottomDialogConfirmSeatBinding;

import java.util.ArrayList;
import java.util.List;

import static com.hackathon.ramus.Constants.DATA_USER_SEAT_NULL;
import static com.hackathon.ramus.Constants.SEAT_TYPE_NO;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR;
import static com.hackathon.ramus.Constants.SEAT_TYPE_YES;

public class StudyRoomActivity extends AppCompatActivity implements MyListener {
    private String TAG = "StudyRoomActivity";

    private ActivityStudyRoomBinding binding;
    private StudyRoomAdapter adapter;
    private StudyRoomViewModel viewModel;
    private String roomName;

    private ArrayList<SeatItem> seatItems= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        setDummyData();

        Intent intent = getIntent();
        roomName = intent.getStringExtra("roomname");

        //observe();

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 7;
        //2랑 5 빈
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new StudyRoomAdapter(this, seatItems);
        adapter.setClickListener(new StudyRoomAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, SeatItem seatItem) {
               Toast.makeText(StudyRoomActivity.this, " " + seatItem.getRoomName() , Toast.LENGTH_SHORT).show();
                if(seatItem.getSeatReservationEndTime() > System.currentTimeMillis()) {
                    if (seatItem.getSeatUserKey() != null) {
                        showBottomSheetDialog();
                    }
                }

            }
        });
        recyclerView.setAdapter(adapter);

    }



    private void showBottomSheetDialog(){
        BottomDialogConfirmSeatBinding binding = BottomDialogConfirmSeatBinding.inflate(getLayoutInflater());
        BottomSheetDialog dialog = new ConfirmSeatDialog(StudyRoomActivity.this, binding);
        dialog.setContentView(binding.getRoot());
        dialog.show();
        dialog.setCancelable(false);
    }

    private void setDummyData(){

        for(int i=0;i<100;i++){
            String number;
            if(i<10) number = "0"+i;
            else number = String.valueOf(i);

            Log.e(TAG, "setDummyData: " + number );
            int type = SEAT_TYPE_YES;
            if(i%7 == 2 || i%7 == 5)type = SEAT_TYPE_NO;
            if(i%21 == 10 || i%21 ==11 || i%21 ==  17 || i%21 == 18)type = SEAT_TYPE_PILLAR;
            seatItems.add(new SeatItem("제1열람실A"+number,
                    DATA_USER_SEAT_NULL,
                    System.currentTimeMillis()+10000000,
                    "제1열람실A",
                    System.currentTimeMillis(),
                    type));
        }

    }

    private void init(){
        binding = ActivityStudyRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(StudyRoomViewModel.class);
        viewModel.init(StudyRoomActivity.this);

    }

    private void observe(){
        viewModel.getSpecificRoomListData(roomName,"roomName").observe(this, new Observer<List<Seat>>() {
            @Override
            public void onChanged(List<Seat> seats) {
                for(int i=0;i<seats.size();i++) Log.e(TAG, "onChanged: "+seats.get(i).getRoomName());
            }
        });
    }

    @Override
    public void notifyPositiveButtonClick(long seatReservationEndTime) {

    }
}