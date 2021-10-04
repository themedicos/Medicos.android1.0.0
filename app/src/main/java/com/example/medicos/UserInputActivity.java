package com.example.medicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.medicos.UserSignIn.SignUpLogIn;
import com.example.medicos.databinding.ActivityUserInputBinding;
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
    public  String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ArrayAdapter<String> search_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, loginas);
        binding.loginas.setAdapter(search_adapter);

        ArrayAdapter<String> search_adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, gender);
        binding.Gender.setAdapter(search_adapter1);

        String x= phoneNoClass.getMobileNoOfDoctor();



        binding.usreInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.name.getText().toString().trim().isEmpty() & !binding.yearOfBirth.getText().toString().trim().isEmpty()) {
                    String loginas_ = binding.loginas.getText().toString();
                    String name_ = binding.name.getText().toString();
                    String yearofbirth_ = binding.yearOfBirth.getText().toString();
                    String gender_ = binding.Gender.getText().toString();
                    HashMap<String, String> userinput = new HashMap<>();

                    userinput.put("loginas", loginas_);
                    userinput.put("name", name_);
                    userinput.put("yearofbirth", yearofbirth_);
                    userinput.put("gender", gender_);
                    DatabaseReference root = db.getReference("DoctorData").child(x);
                    root.setValue(userinput).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
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
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                binding.yearOfBirth.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

            }
        },year, month, day);
        picker.show();
    }
}