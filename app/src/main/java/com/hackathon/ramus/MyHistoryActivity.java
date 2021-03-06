package com.hackathon.ramus;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hackathon.ramus.Adapters.MySeatHistoryAdapter;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Model.User;
import com.hackathon.ramus.Viewmodel.MyHistoryViewModel;
import com.hackathon.ramus.databinding.ActivityMyHistoryBinding;

import java.util.Collections;
import java.util.Comparator;

import static com.hackathon.ramus.Constants.INTENT_DATA_EMAIL;

public class MyHistoryActivity extends AppCompatActivity {
    private String TAG = "MyHistoryActivity";
    private ActivityMyHistoryBinding binding ;
    private MyHistoryViewModel viewModel;
    private MySeatHistoryAdapter adapter;
    private String userKey;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        userKey = getIntent().getExtras().get(INTENT_DATA_EMAIL).toString();
        observe();
    }

    private void observe(){
        viewModel.getSpecificUserLiveData(userKey).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.e(TAG, "onChanged: "  + user.getSeatHistoryList().size() );
                Collections.sort(user.getSeatHistoryList(), new Comparator<Seat>() {
                    @Override
                    public int compare(Seat o1, Seat o2) {
                        if(o1.getSeatReservationEndTime()<o2.getSeatReservationEndTime())return 1;
                        return -1;
                    }
                });
                adapter.setHistories(user.getSeatHistoryList());
            }
        });
    }

    private void init(){
        binding = ActivityMyHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MyHistoryViewModel.class);
        viewModel.init(MyHistoryActivity.this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MySeatHistoryAdapter(MyHistoryActivity.this);

        binding.recyclerView.setAdapter(adapter);

        binding.toolbar.setTitle("????????????");
        setSupportActionBar(binding.toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
