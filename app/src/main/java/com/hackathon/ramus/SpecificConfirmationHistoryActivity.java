package com.hackathon.ramus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hackathon.ramus.Adapters.SpecificConfirmationAdapter;
import com.hackathon.ramus.Model.ConfirmationHistory;
import com.hackathon.ramus.Viewmodel.SpecificConfirmationHistoryViewModel;
import com.hackathon.ramus.databinding.ActivitySpecificConfirmationHistoryBinding;

import static com.hackathon.ramus.Constants.INTENT_DATA_KEY;

public class SpecificConfirmationHistoryActivity extends AppCompatActivity {
    private ActivitySpecificConfirmationHistoryBinding binding;
    private SpecificConfirmationHistoryViewModel viewModel;
    private SpecificConfirmationAdapter adapter;
    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        Intent intent = getIntent();
        key = intent.getExtras().get(INTENT_DATA_KEY).toString();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new SpecificConfirmationAdapter(this);
        binding.recyclerView.setAdapter(adapter);


         viewModel.getSpecificConfirmationHistory(key).observe(this, new Observer<ConfirmationHistory>() {
             @Override
             public void onChanged(ConfirmationHistory confirmationHistory) {
                 adapter.setList(confirmationHistory.getSeatHistoryList());
             }
         });

    }



    private void init(){
        binding = ActivitySpecificConfirmationHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SpecificConfirmationHistoryViewModel.class);
        viewModel.init(this);
    }


}