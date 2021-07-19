package com.hackathon.ramus;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.hackathon.ramus.databinding.ActivityCreateQrBinding;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class CreateQrActivity extends AppCompatActivity {
    private ActivityCreateQrBinding binding;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        text = "https://park-duck.tistory.commmmmmmmmmmmm";

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            binding.qrcode.setImageBitmap(bitmap);
        } catch (Exception e) {

        }
    }
}