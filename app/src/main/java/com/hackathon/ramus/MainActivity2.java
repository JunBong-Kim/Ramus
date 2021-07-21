package com.hackathon.ramus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hackathon.ramus.Dialog.ConfirmSeatDialog;
import com.hackathon.ramus.Dialog.MyListener;
import com.hackathon.ramus.Model.NotificationData;
import com.hackathon.ramus.Model.NotificationModel;
import com.hackathon.ramus.Notification.APIService;
import com.hackathon.ramus.Notification.Client;
import com.hackathon.ramus.Notification.MyResponse;
import com.hackathon.ramus.Notification.SendNotification;
import com.hackathon.ramus.Viewmodel.Main2Viewmodel;
import com.hackathon.ramus.databinding.ActivityMain2Binding;

import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {
    private ActivityMain2Binding binding;
    private Main2Viewmodel viewModel;
    private final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(Main2Viewmodel.class);
        viewModel.init(this);


        binding.userEnrollBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendNotification sendNotification = new SendNotification("token");
                sendNotification.send(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {

                    }
                });
            }
        });

        binding.qrCodeMakeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, CreateQrActivity.class));
            }
        });

        binding.qrCodeScanBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator qrScan = new IntentIntegrator(MainActivity2.this);
                qrScan.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
                qrScan.setPrompt("Sample Text!");
                qrScan.initiateScan();
                //startActivity(new Intent(MainActivity2.this,ScanQrActivity.class));
            }
        });




        /*FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }
                Log.d(TAG,"success");
                String token = task.getResult();
                User user = new User("ISwqqTnlnCYM5JubH01n", "준봉", token);
                viewModel.setUserData(user);
            }
        });*/
    }





}