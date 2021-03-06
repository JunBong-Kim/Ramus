package com.hackathon.ramus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.hackathon.ramus.Adapters.ConfirmationHistoryAdapter;
import com.hackathon.ramus.Model.ConfirmationHistory;
import com.hackathon.ramus.Model.Seat;
import com.hackathon.ramus.Viewmodel.ConfirmationHistoryViewModel;
import com.hackathon.ramus.databinding.ActivityConfirmationHistoryBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.hackathon.ramus.Constants.INTENT_DATA_KEY;

public class ConfirmationHistoryActivity extends AppCompatActivity {

    private ActivityConfirmationHistoryBinding binding;
    private ConfirmationHistoryViewModel viewModel;
    private ConfirmationHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        observe();
    }

    private void init(){
        binding  =ActivityConfirmationHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ConfirmationHistoryViewModel.class);
        viewModel.init(ConfirmationHistoryActivity.this);

        binding.toolbar.setTitle("도서관 확진자 현황");
        setSupportActionBar(binding.toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ConfirmationHistoryAdapter(this);
        adapter.setItemClickListener(new ConfirmationHistoryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String key) {
                Intent intent = new Intent(getApplicationContext(), SpecificConfirmationHistoryActivity.class);
                intent.putExtra(INTENT_DATA_KEY,key);
                startActivity(intent);
            }
        });

        binding.recyclerView.setAdapter(adapter);


    }

    private void observe(){
        viewModel.getConfirmationSeatsArrayList().observe(this, new Observer<ArrayList<ConfirmationHistory>>() {
            @Override
            public void onChanged(ArrayList<ConfirmationHistory> seatArrayList) {
                Collections.sort(seatArrayList, new Comparator<ConfirmationHistory>() {
                    @Override
                    public int compare(ConfirmationHistory o1, ConfirmationHistory o2) {
                        if(o1.getConfirmationDay() < o2.getConfirmationDay())return 1;
                        return -1;

                    }
                });
                adapter.setArrayList(seatArrayList);
            }
        });
    }
}