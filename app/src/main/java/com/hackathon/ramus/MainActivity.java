package com.hackathon.ramus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hackathon.ramus.Dialog.ConfirmSeatDialog;
import com.hackathon.ramus.Dialog.MyListener;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Viewmodel.MainViewModel;
import com.hackathon.ramus.databinding.ActivityMainBinding;

import static com.hackathon.ramus.Constants.COLLECTION_NAME_OF_USERS;
import static com.hackathon.ramus.Constants.DATA_USER_SEAT_NULL;
import static com.hackathon.ramus.Constants.FIELD_NAME_SEAT_KEY;
import static com.hackathon.ramus.Constants.FIELD_NAME_SEAT_RESERVATION_END_TIME;
import static com.hackathon.ramus.Constants.FIELD_NAME_SEAT_USER_KEY;

public class MainActivity extends AppCompatActivity implements MyListener {

    private String TAG = "MainActivity";
    private String seatKey;
    private String userKey;
    private ActivityMainBinding binding;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                String token = task.getResult();
                viewModel.updateFcmToken(userKey, token);
            }
        });

        observe();
        setListeners();
    }

    private void setListeners(){
        binding.buttonQrOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator qrScan = new IntentIntegrator(MainActivity.this);
                qrScan.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
                qrScan.setPrompt("Sample Text!");
                qrScan.initiateScan();
            }
        });
    }

    private void init() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.init(MainActivity.this);

        userKey = SharedPreferenceManager.getEmail(MainActivity.this, SharedPreferenceManager.KNU_EMAIL);
    }

    private void observe() {
        viewModel.getSpecificUserLiveData(userKey).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(!user.getUserSeat().equals(DATA_USER_SEAT_NULL)) {
                    startActivity(new Intent(getApplicationContext(), OccupiedMainActivity.class));
                    finish();
                }
            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                // todo
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                seatKey = result.getContents();
                ConfirmSeatDialog dialog = new ConfirmSeatDialog(MainActivity.this);
                dialog.show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void notifyPositiveButtonClick(long seatReservationEndTime) {
        Log.e(TAG, "notifyPositiveButtonClick: "+seatReservationEndTime +"\n" +seatKey);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        seatKey = "1열람실23";
        db.collection(COLLECTION_NAME_OF_USERS).document(seatKey).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        String seatKey = String.valueOf(document.getData().get(FIELD_NAME_SEAT_KEY));
                        long seatReservationEndTime = Long.valueOf(String.valueOf(document.getData().get(FIELD_NAME_SEAT_RESERVATION_END_TIME)));

                        //자리가 있을때는, 상대방의 키 없애고,
                        if (seatReservationEndTime > System.currentTimeMillis())
                            viewModel.updateUserNewSeatKey(userKey, DATA_USER_SEAT_NULL);

                        //현재 좌석에 나의 키를 배정
                        viewModel.updateSeatNewUserKeyAndNewEndTime(seatKey, userKey, seatReservationEndTime);
                    }else{
                        viewModel.setSeatData(new Seat(seatKey,userKey,seatReservationEndTime));
                        viewModel.updateUserNewSeatKey(userKey,seatKey);
                    }
                }
            }
        });

        /*
        public static final String FIELD_NAME_SEAT_KEY = "seatKey";
    public static final String FIELD_NAME_SEAT_USER_KEY = "seatUserKey";
    public static final String FIELD_NAME_SEAT_RESERVATION_END_TIME = "seatReservationEndTime";

         */

    }
}