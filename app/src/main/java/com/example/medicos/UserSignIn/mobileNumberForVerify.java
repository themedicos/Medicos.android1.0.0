package com.example.medicos.UserSignIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.example.medicos.R;
import com.example.medicos.databinding.ActivityMobileNumberForVerifyBinding;
import com.example.medicos.validateOTP;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;


public class mobileNumberForVerify extends AppCompatActivity {

    private ActivityMobileNumberForVerifyBinding binding;

    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    public String mVerificationId;
    private static final String TAG = "MAIN_TAG";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog pd;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMobileNumberForVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        phone = binding.realOtp.getText().toString().trim();

        binding.getOtp.setOnClickListener(new View.OnClickListener() {
            private FirebaseAuth mAuth;

            @Override
            public void onClick(View v) {
                if (!binding.realOtp.getText().toString().trim().isEmpty()) {
                    if ((binding.realOtp.getText().toString().trim()).length() == 10) {
                        Toast.makeText(mobileNumberForVerify.this, "please wait it may take few seconds to send OTP ....", Toast.LENGTH_SHORT).show();

//                        binding.Timer.setText(String.valueOf(1));

                        new CountDownTimer(180000,100){

                            @Override
                            public void onTick(long millisUntilFinished) {
                                binding.Timer.setText(MessageFormat.format("{0}", millisUntilFinished / 1000,""));
                                binding.Second.setText("Sec");

                            }

                            @Override
                            public void onFinish() {
                                binding.Second.setText("Retry");
                            }
                        }.start();
                        phone = "+91" + binding.realOtp.getText().toString().trim();

                        firebaseAuth = FirebaseAuth.getInstance();
                        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                                .setPhoneNumber(phone)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(mobileNumberForVerify.this)                 // Activity (for callback binding)
                                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {




                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        Toast.makeText(mobileNumberForVerify.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(backendOtp, forceResendingToken);
                                        mVerificationId = backendOtp;
                                        binding.getOtp.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(mobileNumberForVerify.this, validateOTP.class);
                                        intent.putExtra("mobileNo", binding.realOtp.getText().toString());
                                        intent.putExtra("backendOtp", mVerificationId);
                                        startActivity(intent);
                                    }
                                })
                                .build();
                        PhoneAuthProvider.verifyPhoneNumber(options);

                    } else {
                        Toast.makeText(mobileNumberForVerify.this, "Enter Correct Number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mobileNumberForVerify.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void bactToRegister(View view) {
        Intent intent = new Intent(mobileNumberForVerify.this, SignUpLogIn.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }
}