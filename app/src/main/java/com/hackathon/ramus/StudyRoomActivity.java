package com.hackathon.ramus;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hackathon.ramus.databinding.ActivityStudyRoomBinding;

public class StudyRoomActivity extends AppCompatActivity {
    private ActivityStudyRoomBinding binding;
    private Button[] seats = new Button[120];
    private GridLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudyRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

       /* binding.seatGridlayout.getLayoutParams().columnSpec =
                GridLayout.spec(GridLayout.UNDEFINED, 1f);*/
        //  binding.seatGridlayout.addView(make4Button(seats[0], seats[1], seats[2], seats[3]));
        // binding.seatGridlayout.addView(make4Button(seats[4], seats[5], seats[6], seats[7]));
        //  binding.seatGridlayout.addView(make4Button(seats[8], seats[9], seats[10], seats[11]));
        //  binding.seatGridlayout.addView(make4Button(seats[12], seats[13], seats[14], seats[15]));
    }

    private void init() {

        for (int i = 0; i < 15; i++) {
            GridLayout.Spec rowSpec = GridLayout.spec(i, 1, GridLayout.FILL);

            for (int j = 0; j < 4; j++) {
                GridLayout.Spec colSpec = GridLayout.spec(j, 1, GridLayout.FILL);

                seats[j + j * i] = new Button(this);
                seats[j + j * i].setText("[ " + i + " | " + j + " ]");
                seats[j + j * i].setGravity(Gravity.CENTER);

                GridLayout.LayoutParams myGLP = new GridLayout.LayoutParams();
                myGLP.rowSpec = rowSpec;
                myGLP.columnSpec = colSpec;

                binding.seatGridlayout.addView(seats[j + j * i], myGLP);
            }
        }
        /*params = new GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f));
        params.width = 0;
        for (int i = 0; i < 120; i++) {
            seats[i] = new Button(getApplicationContext());
            seats[i].setText((i + 1) + "");
            binding.seatGridlayout.addView(seats[i]);
            //seats[i].setX(binding.seatGridlayout.getX() / 16);
            //seats[i].setLayoutParams(params);
        }*/
    }

    private void makeBlock() {

    }

    private LinearLayout make4Button(Button b1, Button b2, Button b3, Button b4) {
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setBackgroundColor(Color.RED);
        linearLayout1.addView(b1);
        linearLayout1.addView(b2);
        linearLayout1.setClickable(true);

        LinearLayout linearLayout2 = new LinearLayout(getApplicationContext());
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout2.setBackgroundColor(Color.BLACK);
        linearLayout2.addView(b3);
        linearLayout2.addView(b4);
        linearLayout2.setClickable(true);

        linearLayout.addView(linearLayout1);
        linearLayout.addView(linearLayout2);
        linearLayout.setLayoutParams(params);
        return linearLayout;
    }
}