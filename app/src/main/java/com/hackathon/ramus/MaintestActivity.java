package com.hackathon.ramus;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.hackathon.ramus.Viewmodel.EmailSignUpViewModel;
import com.hackathon.ramus.databinding.ActivityMainTestBinding;

public class MaintestActivity extends AppCompatActivity {
    private ActivityMainTestBinding binding;
    private EmailSignUpViewModel viewModel;
    private  ConstraintLayout constraintLayout1,constraintLayout2;
    private Animation animation,animation2;
    FirebaseAuth mAuth;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init(){
        binding = ActivityMainTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.scrollviewMain.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
            int y =binding.scrollviewMain.getScrollY();
            if(y>360)
                binding.tapLine.setAlpha(1);
            else if(y<160)
                binding.tapLine.setAlpha(0);
            else if(y<360)
                binding.tapLine.setAlpha((float)y/(float)360);
        });
        binding.layoutMainFunction.layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MaintestActivity.this, SearchSeatActivity.class));
            }
        });

    }


}

