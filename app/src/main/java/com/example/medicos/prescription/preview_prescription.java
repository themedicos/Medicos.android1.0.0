package com.example.medicos.prescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.medicos.Adapter.prescribed_medicineAdapter;
import com.example.medicos.Adapter.prescribed_testAdapter;
import com.example.medicos.Model.patientPhNo;
import com.example.medicos.Model.prescribed_medicine;
import com.example.medicos.Model.prescribed_test;
import com.example.medicos.R;
import com.example.medicos.databinding.ActivityPreviewPrescriptionBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class preview_prescription extends AppCompatActivity {

    private ActivityPreviewPrescriptionBinding binding;
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    RecyclerView recyclerView1, recyclerView2;
    ArrayList<prescribed_test> test_list_added2 = new ArrayList<>();
    ArrayList<prescribed_medicine> medicine_list_added2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreviewPrescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView1 = findViewById(R.id.recyclerview1);
        recyclerView2 = findViewById(R.id.recyclerview2);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);

        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView2.setLayoutManager(linearLayoutManager2);

        test_list_added2 = (ArrayList<prescribed_test>) getIntent().getExtras().getSerializable("list");
        medicine_list_added2 = (ArrayList<prescribed_medicine>) getIntent().getExtras().getSerializable("list2");

        recyclerView1.setAdapter(new prescribed_testAdapter(test_list_added2));
        recyclerView2.setAdapter(new prescribed_medicineAdapter(medicine_list_added2));

        String patientPhNumber = patientPhNo.getPhNoOfPatient();
        SharedPreferences sharedPreferences3 = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String DoctorPhNo = sharedPreferences3.getString("phone", "");
        binding.DoctorPhNOPp.setText(DoctorPhNo);
        DatabaseReference roat = db.getReference("DoctorData").child(DoctorPhNo);
        roat.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    String doctorName = "Dr."+ task.getResult().child("name").getValue();
                    String DoctorClinicName = String.valueOf(task.getResult().child("clinicName").getValue());
                    String DoctorClinicLocation = String.valueOf(task.getResult().child("clinicLocation").getValue());
                    binding.ClinicNamePp.setText(DoctorClinicName);
                    binding.DoctorNamePp.setText(doctorName);
                    binding.clinicLocationPp.setText(DoctorClinicLocation);
                } else {

                }
            }
        });
        roat.child(patientPhNumber).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    String patientName = String.valueOf(task.getResult().child("nameOfPatient").getValue());
                    String patientAge = String.valueOf(task.getResult().child("ageOfPatient").getValue());
                    String PatientGender = String.valueOf(task.getResult().child("genderOfPatient").getValue());
                    binding.patientAgePp.setText(patientAge);
                    binding.PatientNamePp.setText(patientName);
                    binding.PatientGenderPp.setText(PatientGender);
                } else {

                }
            }
        });

    }

}