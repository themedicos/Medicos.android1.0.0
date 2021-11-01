package com.example.medicos.UserSignIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.medicos.R;
import com.example.medicos.databinding.ActivityMobileNumberForVerifyBinding;
import com.example.medicos.phoneNoClass;
import com.example.medicos.validateOTP;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class mobileNumberForVerify extends AppCompatActivity {

    private ActivityMobileNumberForVerifyBinding binding;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    public String mVerificationId;
    private static final String TAG = "MAIN_TAG";
    private FirebaseAuth firebaseAuth;
    String phone;
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMobileNumberForVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //progressbar.................>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        Sprite doubleBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.GONE);


        phone = "+91" + binding.realOtp.getText().toString().trim();
        binding.getOtp.setOnClickListener(new View.OnClickListener() {
            private FirebaseAuth mAuth;

            @Override
            public void onClick(View v) {
                if (!binding.realOtp.getText().toString().trim().isEmpty()) {
                    if ((binding.realOtp.getText().toString().trim()).length() == 10) {
                        Toast.makeText(mobileNumberForVerify.this, "please wait it may take few seconds...", Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.VISIBLE);
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        phone = "+91" + binding.realOtp.getText().toString().trim();
                        phoneNoClass.setMobileNoOfDoctor(phone);


                        HashMap<String, String> phoneData = new HashMap<>();
                        phoneData.put("mobileNoOfDoctor", phone);
                        DatabaseReference Doctor_data = db.getReference("DoctorData").child(phone);
                        Doctor_data.setValue(phoneData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });


                        SharedPreferences sharedPreferences3 = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit3 = sharedPreferences3.edit();
                        myEdit3.putString("phone", phone);
                        myEdit3.apply();

                        //Timer............>>>>>>>>>>>.
                        new CountDownTimer(180000, 100) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                binding.Timer.setText(MessageFormat.format("{0}", millisUntilFinished / 1000, ""));
                                binding.Second.setText("Sec");
                            }

                            @Override
                            public void onFinish() {
                                binding.Second.setText("Retry");
                                progressBar.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            }
                        }.start();
                        //------------------//

                        //OTP.....................>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
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
                        //------------------------------

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