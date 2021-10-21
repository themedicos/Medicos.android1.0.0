package com.example.medicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.medicos.UserSignIn.SignUpLogIn;
import com.example.medicos.databinding.ActivityUserInputBinding;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class UserInputActivity extends AppCompatActivity {

    private ActivityUserInputBinding binding;

    private DatePickerDialog picker;

    FirebaseDatabase db = FirebaseDatabase.getInstance();


    private static final String[] loginas = new String[]{
            "Doctor"
    };

    private static final String[] gender = new String[]{
            "Male", "Female", "Others"
    };
    public String phone;
    int autoSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //progressbar.......................>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.GONE);


        ArrayAdapter<String> search_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, loginas);
        binding.loginas.setAdapter(search_adapter);

        ArrayAdapter<String> search_adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, gender);
        binding.Gender.setAdapter(search_adapter1);

        String x = phoneNoClass.getMobileNoOfDoctor();
        SharedPreferences sharedPreferences3 =getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String y= sharedPreferences3.getString("phone","");

        binding.usreInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.name.getText().toString().trim().isEmpty() & !binding.yearOfBirth.getText().toString().trim().isEmpty() & !binding.clinic.getText().toString().isEmpty() & !binding.clinicLocation.getText().toString().isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    String loginas_ = binding.loginas.getText().toString();
                    String name_ = binding.name.getText().toString();
                    String yearofbirth_ = binding.yearOfBirth.getText().toString();
                    String gender_ = binding.Gender.getText().toString();
                    String clinic_=binding.clinic.getText().toString();
                    String clinicLocation_ = binding.clinicLocation.getText().toString();

                    //sharedpreference to save user details
                    SharedPreferences sharedPreferences1 = getSharedPreferences("userDataInSharedPref",MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences1.edit();
                    editor.putString("loginas", loginas_);
                    editor.putString("name", name_);
                    editor.putString("yearofbirth", yearofbirth_);
                    editor.putString("gender", gender_);
                    editor.putString("clinicName", clinic_);
                    editor.putString("clinicLocation", clinicLocation_);
                    editor.apply();

                    //save data in database
                    HashMap<String, String> userinput = new HashMap<>();
                    userinput.put("loginas", loginas_);
                    userinput.put("name", name_);
                    userinput.put("yearofbirth", yearofbirth_);
                    userinput.put("gender", gender_);
                    userinput.put("clinicName", clinic_);
                    userinput.put("clinicLocation", clinicLocation_);
                    DatabaseReference root = db.getReference("DoctorData").child(y);
                    root.setValue(userinput).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            autoSave=1;
                            SharedPreferences sharedpreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("key", autoSave);
                            editor.apply();
                            progressBar.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Intent intent = new Intent(UserInputActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(UserInputActivity.this, "Fill out all the blank", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void backToRegister(View view) {
        Intent intent = new Intent(UserInputActivity.this, SignUpLogIn.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }

    public void yearOfBirth(View view) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        picker = new DatePickerDialog(UserInputActivity.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                binding.yearOfBirth.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

            }
        }, year, month, day);
        picker.show();
    }
}