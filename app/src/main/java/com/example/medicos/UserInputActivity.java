package com.example.medicos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.medicos.Model.UserInfo;
import com.example.medicos.UserSignIn.SignUpLogIn;
import com.example.medicos.databinding.ActivityUserInputBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInputActivity extends AppCompatActivity {

    private ActivityUserInputBinding binding;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    com.example.medicos.Model.UserInfo userInfo;

    private static final String[] loginas = new String[]{
            "Doctor", "Individual"
    };
    private static final String[] gender = new String[]{
            "Male", "Female", "Others"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayAdapter<String> search_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, loginas);
        binding.loginas.setAdapter(search_adapter);

        ArrayAdapter<String> search_adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, gender);
        binding.Gender.setAdapter(search_adapter1);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");
        userInfo = new UserInfo();


        binding.usreInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.name.getText().toString().trim().isEmpty() & !binding.yearOfBirth.getText().toString().trim().isEmpty()) {
                    String loginas_ = binding.loginas.getText().toString();
                    String name_ = binding.name.getText().toString();
                    String yearofbirth_ = binding.yearOfBirth.getText().toString();
                    String gender_ = binding.Gender.getText().toString();
                    addDataToDatabase(loginas_, name_, yearofbirth_, gender_);
                } else {
                    Toast.makeText(UserInputActivity.this, "Fill out all the blanck", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addDataToDatabase(String loginas_, String name_, String yearofbirth_, String gender_) {
        userInfo.setLog_in_as(loginas_);
        userInfo.setUsername(name_);
        userInfo.setYear_of_birth(yearofbirth_);
        userInfo.setUser_gender(gender_);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(userInfo);
                Intent intent = new Intent(UserInputActivity.this, MainActivity.class);
                startActivity(intent);

                Toast.makeText(UserInputActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UserInputActivity.this, "Unable to Connect", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void backToRegister(View view) {
        Intent intent = new Intent(UserInputActivity.this, SignUpLogIn.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }
}