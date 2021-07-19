package com.hackathon.ramus.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hackathon.ramus.databinding.BottomDialogConfirmSeatBinding;

public class ConfirmSeatDialog extends BottomSheetDialog {
    private Context context;
    private BottomDialogConfirmSeatBinding binding;
    private MyListener myListener;

    public ConfirmSeatDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        myListener = (MyListener) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BottomDialogConfirmSeatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListener.notifyPositiveButtonClick();
                dismiss();
            }
        });

        binding.buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
