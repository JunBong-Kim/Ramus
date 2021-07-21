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

import com.hackathon.ramus.Adapters.StudyRoomAdapter;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Model.SeatItem;
import com.hackathon.ramus.Viewmodel.StudyRoomViewModel;
import com.hackathon.ramus.databinding.ActivityStudyRoomBinding;

import java.util.ArrayList;
import java.util.List;

import static com.hackathon.ramus.Constants.DATA_USER_SEAT_NULL;

public class StudyRoomActivity extends AppCompatActivity {
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

        Intent intent = getIntent();
        roomName = intent.getStringExtra("roomname");

        Toast.makeText(this, "" + roomName, Toast.LENGTH_SHORT).show();
        //observe();

        // data to populate the RecyclerView with
        String[] data = new String[100];
        for(int i=0;i<100;i++){
            data[i] = String.valueOf(i);
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 5;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new StudyRoomAdapter(this, seatItems);
        adapter.setClickListener(new StudyRoomAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(StudyRoomActivity.this, "" + adapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

    }

    private void setDummyData(){

        /*
         private String seatKey;
    private String seatUserKey;
    private long seatReservationEndTime;
    private String roomName;
    private long seatReservationStartTime;
    private int viewType;


         */
        for(int i=0;i<100;i++){
            String number;
            if(i<10) number = "0"+i;
            else number = String.valueOf(i);

            seatItems.add(new SeatItem("제1열람실A"+number,DATA_USER_SEAT_NULL,
                    System.currentTimeMillis(),
                    "제1열람실A",
                    System.currentTimeMillis(),
                    i));
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


}