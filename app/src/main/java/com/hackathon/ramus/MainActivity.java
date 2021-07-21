package com.hackathon.ramus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hackathon.ramus.Dialog.ConfirmSeatDialog;
import com.hackathon.ramus.Dialog.MyListener;
import com.hackathon.ramus.Model.DaeguCovidNews;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Viewmodel.MainViewModel;
import com.hackathon.ramus.databinding.ActivityMainBinding;
import com.hackathon.ramus.databinding.BottomDialogConfirmSeatBinding;

import java.lang.reflect.Field;

import java.util.Observable;

import static com.hackathon.ramus.Constants.COLLECTION_NAME_OF_USERS;
import static com.hackathon.ramus.Constants.DATA_USER_SEAT_NULL;
import static com.hackathon.ramus.Constants.FIELD_NAME_SEAT_KEY;
import static com.hackathon.ramus.Constants.FIELD_NAME_SEAT_RESERVATION_END_TIME;
import static com.hackathon.ramus.Constants.INTENT_DATA_EMAIL;
import static com.hackathon.ramus.Constants.INTENT_DATA_WEB_VIEW_TYPE;
import static com.hackathon.ramus.Constants.TYPE_DAEGU;
import static com.hackathon.ramus.Constants.TYPE_KB;
import static com.hackathon.ramus.Constants.TYPE_KNU;
import static com.hackathon.ramus.Constants.TYPE_MOHW;
import static com.hackathon.ramus.Constants.TYPE_NAVER;

public class MainActivity extends AppCompatActivity implements MyListener {

    private String TAG = "MainActivity";
    private String seatKey;
    private String userKey;
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    Observer<User> observer;

    private boolean flag = false;
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
        Log.e(TAG, "onCreate: 몇번이 호출 되는 거지 " );

        observe();
        setListeners();
    }

    private void setListeners(){
        binding.layoutMainFunction.layoutQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator qrScan = new IntentIntegrator(MainActivity.this);
                qrScan.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
                qrScan.setPrompt("Sample Text!");
                qrScan.initiateScan();
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
        binding.layoutMainFunction.layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SearchSeatActivity.class));
            }
        });
        binding.layoutMainFunction.laoutMyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MyHistoryActivity.class);
                intent.putExtra(INTENT_DATA_EMAIL,userKey);
                startActivity(intent);
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
        observer = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(!user.getUserSeat().equals(DATA_USER_SEAT_NULL)) {
                    if(flag)return;
                    flag = true;
                    Log.e(TAG, "startActivity 호출" );
                    startActivity(new Intent(getApplicationContext(), OccupiedMainActivity.class));
                    finish();

                }else{
                    binding.layoutMainFunction.textViewUserName.setText(user.getUserName());
                    binding.layoutMainFunction.textViewStudentId.setText(user.getUserStudentNumber());
                }
            }
        };

        viewModel.getSpecificUserLiveData(userKey).observe(this, observer);
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
                BottomDialogConfirmSeatBinding binding=BottomDialogConfirmSeatBinding.inflate(getLayoutInflater());
                BottomSheetDialog dialog = new ConfirmSeatDialog(this,binding);
                dialog.setContentView(binding.getRoot());
                dialog.show();
                dialog.setCancelable(false);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void notifyPositiveButtonClick(long seatReservationEndTime) {
        Log.e(TAG, "notifyPositiveButtonClick: "+seatReservationEndTime +"\n" +seatKey);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        seatKey = "CRETECZONE10";
        //여기에 이제 qr찍은 string 이 들어감 원래는, 임시로 1열람실23

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
                    String roomNumber = seatKey.replaceAll("\\D+","");
                    Seat seat = new Seat(seatKey,userKey,seatReservationEndTime,roomNumber,System.currentTimeMillis());
                    viewModel.addSeatHistoryToUser(userKey,seat);
                }
            }
        });


    }
}