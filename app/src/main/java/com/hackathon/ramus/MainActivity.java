package com.hackathon.ramus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Viewmodel.MainViewModel;
import com.hackathon.ramus.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

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
                Toast.makeText(MainActivity.this, "" + user.getUserName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}