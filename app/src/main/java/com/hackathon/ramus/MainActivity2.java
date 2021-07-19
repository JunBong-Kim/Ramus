package com.hackathon.ramus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Viewmodel.Main2Viewmodel;
import com.hackathon.ramus.databinding.ActivityMain2Binding;

import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {
    private ActivityMain2Binding binding;
    private Main2Viewmodel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(Main2Viewmodel.class);
        viewModel.init(this);
      /*  binding.userEnrollBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=
            }
        });*/

       /* FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    User user = new User(1,1);

                } else {

                }
            }
        });*/

    }
}