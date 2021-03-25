package com.example.simpleaccounting;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.zxing.Result;

import java.util.Scanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    int MY_PERMISSION_REQUEST_CAMERA=0;
    ZXingScannerView scannerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerview = new ZXingScannerView(this);
        setContentView(scannerview);
    }

    @Override
    public void handleResult(Result result) {
        AddItemFragment.barcodeEt.setText(result.getText());
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerview.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!=
        PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSION_REQUEST_CAMERA);
        }

        scannerview.setResultHandler(this);
        scannerview.startCamera();

    }
}