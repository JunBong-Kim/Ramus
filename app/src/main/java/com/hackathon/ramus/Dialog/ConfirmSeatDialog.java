package com.hackathon.ramus.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hackathon.ramus.R;
import com.hackathon.ramus.databinding.BottomDialogConfirmSeatBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfirmSeatDialog extends BottomSheetDialog {
    private Context context;
    private BottomDialogConfirmSeatBinding binding;
    private MyListener myListener;
    private boolean canRegister = false;
    private String yyyy_mm_dd_string;

    private String TAG = "dialog";

    public ConfirmSeatDialog(@NonNull Context context,BottomDialogConfirmSeatBinding binding) {
        super(context);
        this.context = context;
        myListener = (MyListener) context;
        this.binding=binding;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate: yyyy_mm_dd_string" + yyyy_mm_dd_string);

        binding.timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Log.e(TAG, "onTimeChanged: " + hourOfDay + minute);
                int diffOfMinutes = getGapOfMinute(hourOfDay, minute);
                if (diffOfMinutes == -1) {
                    binding.textViewTimeGap.setText("현재시각 이전입니다");
                    canRegister = false;
                } else {
                    binding.textViewTimeGap.setText(diffOfMinutes / 60 + "시간" + diffOfMinutes % 60 + "분 사용합니다.\n" +
                            "종료시각: " + hourOfDay + "시" + minute + "분");
                    canRegister = true;

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String format_time1 = format1.format(System.currentTimeMillis());
                    yyyy_mm_dd_string = format_time1.substring(0, 10);

                    String tmp;
                    if (hourOfDay < 10) tmp = "0" + hourOfDay;
                    else tmp = String.valueOf(hourOfDay);
                    yyyy_mm_dd_string = yyyy_mm_dd_string + " " + tmp;
                    if (minute < 10) tmp = "0" + minute;
                    else tmp = String.valueOf(minute);
                    yyyy_mm_dd_string = yyyy_mm_dd_string + ":" + tmp + ":00";
                    Log.e(TAG, "result " + yyyy_mm_dd_string);

                }
            }
        });

        binding.buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canRegister) {
                    dismiss();
                    Toast.makeText(context, "현재 시간보다 이전시간을 선택하셨습니다!", Toast.LENGTH_SHORT).show();
                    return;
                }
                long milliseconds = 0;
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date d = f.parse(yyyy_mm_dd_string);
                    milliseconds = d.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                myListener.notifyPositiveButtonClick(milliseconds);
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


    private int getGapOfMinute(int setHour, int setMinute) {
        int calMinute = setHour * 60 + setMinute;

        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        String format_time1 = format1.format(System.currentTimeMillis());
        int currentMinute = Integer.parseInt(format_time1.substring(0, 2)) * 60 + Integer.parseInt(format_time1.substring(3));
        Log.e(TAG, "currentMinute: " + currentMinute + "//" + format_time1.substring(0, 2) + "//" + format_time1.substring(3));
        Log.e(TAG, "calMinute: " + calMinute);

        int getDiffMinutesOfSum = calMinute - currentMinute;

        if (getDiffMinutesOfSum < 0) return -1;
        return getDiffMinutesOfSum;
    }
}
