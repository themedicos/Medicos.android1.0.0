package com.example.medicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.example.medicos.UserSignIn.mobileNumberForVerify;
import com.example.medicos.databinding.ActivityValidateOtpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

public class validateOTP extends AppCompatActivity {

    private ActivityValidateOtpBinding binding;

    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityValidateOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();

        binding.mobileNo.setText(String.format(
                "+91-%s", getIntent().getStringExtra("mobileNo")
        ));
        String otpCode = getIntent().getStringExtra("backendOtp");

        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.TimerOtp.setText(MessageFormat.format("{0}", millisUntilFinished / 1000,""));
                binding.TimerSecond.setText("Sec");
            }

            @Override
            public void onFinish() {
                binding.resendOtp.setText("Resend Otp");
            }
        }.start();

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.realOtp.getText().toString().trim().isEmpty()) {
                    if ((binding.realOtp.getText().toString().trim()).length() == 6) {

                        if (otpCode != null) {

                            String enteredOtp = binding.realOtp.getText().toString();

                            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpCode, enteredOtp);
                            signInWithPhoneAuthCredential(credential);
                        }

                    } else {
                        Toast.makeText(validateOTP.this, "OTP Must Be 6 Digit", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(validateOTP.this, "OTP Must Be Non-empty and 6 Digit", Toast.LENGTH_SHORT).show();
                }
            }
            private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
                firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = task.getResult().getUser();
                                    Intent intent = new Intent(validateOTP.this, UserInputActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    startActivity(intent);

                                } else {
                                    Toast.makeText(validateOTP.this, "unable to connect", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        binding.resendOtp.setOnClickListener(new View.OnClickListener() {
            private FirebaseAuth mAuth;

            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();

                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber(getIntent().getStringExtra("mobileNo"))       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(validateOTP.this)                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);

                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(validateOTP.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String newbackendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(newbackendOtp, forceResendingToken);
                        newbackendOtp = otpCode;
                        Toast.makeText(validateOTP.this, "successfully send", Toast.LENGTH_SHORT).show();
                    }
                };
            }
        });
    }

    public void backtomobileNumber(View view) {
        Intent intent = new Intent(validateOTP.this, mobileNumberForVerify.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }
}