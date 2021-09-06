package com.example.medicos;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicos.databinding.ActivityMobileNumberForVerifyBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class mobileNumberForVerify extends AppCompatActivity {

    private ActivityMobileNumberForVerifyBinding binding;

    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private static final String TAG = "MAIN_TAG";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog pd;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMobileNumberForVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        phone = binding.realOtp.getText().toString().trim();

        binding.getOtp.setOnClickListener(new View.OnClickListener() {
            private FirebaseAuth mAuth;

            @Override
            public void onClick(View v) {
                if (!binding.realOtp.getText().toString().trim().isEmpty()) {
                    if ((binding.realOtp.getText().toString().trim()).length() == 10) {

//
//                        progressBar.setVisibility(View.VISIBLE);
//                        getOtp.setVisibility(View.INVISIBLE);
                        phone = "+91" + binding.realOtp.getText().toString().trim();

                        firebaseAuth = FirebaseAuth.getInstance();
                        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                                .setPhoneNumber(phone)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(mobileNumberForVerify.this)                 // Activity (for callback binding)
                                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                                        binding.progressBar.setVisibility(View.VISIBLE);
                                        binding.getOtp.setVisibility(View.GONE);


                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        Toast.makeText(mobileNumberForVerify.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(backendOtp, forceResendingToken);
                                        binding.progressBar.setVisibility(View.GONE);
                                        binding.getOtp.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(mobileNumberForVerify.this, validateOTP.class);
                                        intent.putExtra("mobileNo", binding.realOtp.getText().toString());
                                        intent.putExtra("backendOtp", backendOtp);
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
        Intent intent = new Intent(mobileNumberForVerify.this,SignUpLogIn.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
    }
}