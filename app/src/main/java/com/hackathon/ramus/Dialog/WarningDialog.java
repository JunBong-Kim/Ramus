package com.hackathon.ramus.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hackathon.ramus.databinding.DialogBedStudentBinding;

public class WarningDialog extends Dialog {
    private Context context;
    private DialogBedStudentBinding binding;
    private WarningListener myListener;
    private String userKey;

    public WarningDialog(@NonNull Context context, DialogBedStudentBinding binding,String userKey) {
        super(context);
        this.context = context;
        this.binding = binding;
        this.myListener = (WarningListener) context;
        this.userKey=userKey;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.layoutMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListener.warningType(userKey,1);
                dismiss();
            }
        });
        binding.layoutNoize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListener.warningType(userKey,2);
                dismiss();
            }
        });
        binding.layoutNoSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListener.warningType(userKey,3);
                dismiss();
            }
        });

    }
}
