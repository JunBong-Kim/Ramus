package com.hackathon.ramus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hackathon.ramus.Adapters.StudyRoomAdapter;
import com.hackathon.ramus.Dialog.ConfirmSeatDialog;
import com.hackathon.ramus.Dialog.MyListener;
import com.hackathon.ramus.Dialog.WarningDialog;
import com.hackathon.ramus.Dialog.WarningListener;
import com.hackathon.ramus.Model.NotiElseModel;
import com.hackathon.ramus.Model.NotificationModel;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Model.SeatItem;
import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Notification.MyResponse;
import com.hackathon.ramus.Notification.SendNotification;
import com.hackathon.ramus.Viewmodel.StudyRoomViewModel;
import com.hackathon.ramus.databinding.ActivityStudyRoomBinding;
import com.hackathon.ramus.databinding.BottomDialogConfirmSeatBinding;
import com.hackathon.ramus.databinding.DialogBedStudentBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hackathon.ramus.Constants.COLLECTION_NAME_OF_USERS;
import static com.hackathon.ramus.Constants.DATA_USER_SEAT_NULL;
import static com.hackathon.ramus.Constants.SEAT_TYPE_NO;
import static com.hackathon.ramus.Constants.SEAT_TYPE_NO_THIN;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_LEFT_DOWN;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_LEFT_UP;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_RIGHT_DOWN;
import static com.hackathon.ramus.Constants.SEAT_TYPE_PILLAR_RIGHT_UP;
import static com.hackathon.ramus.Constants.SEAT_TYPE_YES;
import static com.hackathon.ramus.Constants.SEAT_TYPE_YES_SPACE;

public class StudyRoomActivity extends AppCompatActivity implements WarningListener, StudyRoomAdapter.ItemClickListener {
    private String TAG = "StudyRoomActivity";

    private ActivityStudyRoomBinding binding;
    private StudyRoomAdapter adapter;
    private StudyRoomViewModel viewModel;
    private String roomName;
    private String myUserKey = "";

    private ArrayList<SeatItem> seatItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        setSeatLayout();

        Intent intent = getIntent();
        roomName = intent.getStringExtra("roomname");
        myUserKey = intent.getStringExtra("seatuserkey");
        binding.roomName.setText(roomName);
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 9;
        //2랑 5 빈
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new StudyRoomAdapter(this, seatItems);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        observe();

    }


    private void showBottomSheetDialog(String userKey) {
        DialogBedStudentBinding binding = DialogBedStudentBinding.inflate(getLayoutInflater());
        Dialog dialog = new WarningDialog(StudyRoomActivity.this, binding, userKey);
        dialog.setContentView(binding.getRoot());
        dialog.show();
    }

    private void setSeatLayout() {
        int realnumber = 1;
        for (int i = 0; i < 171; i++) {
            String number;
            if (i < 10) number = "0" + i;
            else number = String.valueOf(i);

            Log.e(TAG, "setDummyData: " + number);
            int type = SEAT_TYPE_YES;
            if (i % 9 == 2 || i % 9 == 5) type = SEAT_TYPE_NO;

            if (i / 9 == 4) type = SEAT_TYPE_NO_THIN;
            if (i / 9 == 9) type = SEAT_TYPE_NO_THIN;
            if (i / 9 == 14) type = SEAT_TYPE_NO_THIN;

            if (i == 48 || i == 111) type = SEAT_TYPE_PILLAR_LEFT_UP;
            if (i == 49 || i == 112) type = SEAT_TYPE_PILLAR_RIGHT_UP;
            if (i == 57 || i == 120) type = SEAT_TYPE_PILLAR_LEFT_DOWN;
            if (i == 58 || i == 121) type = SEAT_TYPE_PILLAR_RIGHT_DOWN;
            if (i % 9 == 8) type = SEAT_TYPE_NO;

            if (type == SEAT_TYPE_YES) {
                String rn;
                if (realnumber < 10)
                    rn = "0" + realnumber;
                else
                    rn = String.valueOf(realnumber);
                if (i % 2 == 1) {
                    type = SEAT_TYPE_YES_SPACE;
                }
                seatItems.add(new SeatItem(rn, type));
                realnumber++;
            } else {
                seatItems.add(new SeatItem(number, type));
            }

        }

    }


    private void init() {
        binding = ActivityStudyRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(StudyRoomViewModel.class);
        viewModel.init(StudyRoomActivity.this);

        binding.backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void observe() {
        viewModel.getSpecificRoomListData(roomName.replaceAll(" ", ""), "roomName").observe(this, new Observer<List<Seat>>() {
            @Override
            public void onChanged(List<Seat> seats) {
                adapter.setSeatData(seats);
            }
        });
    }


    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.ani_left_back, R.anim.ani_right_back);
    }


    @Override
    public void onItemClick(View view, Seat seat) {
        if (myUserKey != null) {
            if (!seat.getSeatUserKey().equals(myUserKey) && seat.getSeatReservationEndTime() > System.currentTimeMillis()) {
                showBottomSheetDialog(seat.getSeatUserKey());
            }
        }
    }

    @Override
    public void warningType(String userKey, int type) {
        FirebaseFirestore.getInstance().collection(COLLECTION_NAME_OF_USERS).document(userKey).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                NotificationModel notiModel = new NotificationModel(classifiedTitle(type), classifiedBody(type));
                NotiElseModel elseModel = new NotiElseModel(type, userKey, System.currentTimeMillis());
                SendNotification sendNotification = new SendNotification(user.getUserFcmToken(), StudyRoomActivity.this);
                sendNotification.send(notiModel, elseModel, new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        viewModel.addWarningHistoryToUser(userKey, elseModel);
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    private String classifiedTitle(int type) {
        switch (type) {
            case 1:
                return "마스크를 착용해 주세요";
            case 2:
                return "시끄럽습니다.";
            case 3:
                return "너무 많이 비워두지마세요";
            default:
                return "";
        }
    }

    private String classifiedBody(int type) {
        switch (type) {
            case 1:
                return "마스크를 착용";
            case 2:
                return "조용해주세요.";
            case 3:
                return "공용의 자리입니다.";
            default:
                return "";
        }
    }
}