package com.example.simpleaccounting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.zxing.pdf417.encoder.PDF417;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    private EditText countryCodeEdit, phoneNumberEdit;
    private Button mSendOTPBtn;
    ProgressDialog progressDialog;
    TextView processText;
    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private Boolean authFlag = false;

    //permission constants
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    //image pick constants
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    //arrays of permissions
    private String[] cameraPermissions; //camera and storage
    private String[] storagePermissions; // only storage
    //variables (will contain data to save)
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mSendOTPBtn = findViewById(R.id.send_codebtn);
        processText = findViewById(R.id.text_process);
        countryCodeEdit = findViewById(R.id.input_country_code);
        phoneNumberEdit = findViewById(R.id.input_phone);

        //init permission arrays
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        requestCameraPermission();

        //set by default text for country code
        countryCodeEdit.setText("91");

        auth = FirebaseAuth.getInstance();

        mSendOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize progress dialog
                progressDialog = new ProgressDialog(Login.this);
                //show progress dialog
                progressDialog.show();

                //set Content View
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent


                );

                String country_code = countryCodeEdit.getText().toString();
                String phone = phoneNumberEdit.getText().toString();
                String phoneNumber = "+" + country_code + "" + phone;
                if (!country_code.isEmpty() || !phone.isEmpty()) {
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                            .setPhoneNumber(phoneNumber)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(Login.this)
                            .setCallbacks(mCallBacks)
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                } else {
                    processText.setText("Please Enter Country Code and Phone Number");
                    processText.setTextColor(Color.RED);
                    processText.setVisibility(View.VISIBLE);
                }

            }

        });

        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                processText.setText(e.getMessage());
                processText.setTextColor(Color.RED);
                processText.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                //sometimes the code is not detected automatically
                //so user has to manually enter the code
                processText.setText("OTP has been Sent");
                processText.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent otpIntent = new Intent(Login.this, otp.class);
                        otpIntent.putExtra("auth", s);
                        startActivity(otpIntent);
                    }
                }, 10000);


            }
        };

    }


//    private void  requestStoragePermission(){
//        //request the storage permission
//        Toast.makeText(getContext(), "storage", Toast.LENGTH_SHORT).show();
//        ActivityCompat.requestPermissions(getActivity(),storagePermissions, STORAGE_REQUEST_CODE);
//    }

    private void requestCameraPermission() {
        //request the camera permission
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
        //Toast.makeText(getContext(),getActivity().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //result of permssion allowed/denied

        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    //if allowed returns true otherwise false
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    Toast.makeText(this, cameraAccepted + "", Toast.LENGTH_SHORT).show();


                }

            }
            break;


        }
    }

    @Override
    public void onBackPressed() {
        //dismiss progress dialog
        progressDialog.dismiss();

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void signIn(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    sendToMain();
                } else {
                    processText.setText(task.getException().getMessage());
                    processText.setTextColor(Color.RED);
                    processText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }
}