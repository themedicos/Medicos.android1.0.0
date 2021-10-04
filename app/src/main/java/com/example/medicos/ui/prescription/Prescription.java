package com.example.medicos.ui.prescription;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.medicos.R;
import com.example.medicos.phoneNoClass;
import com.example.medicos.prescription.WritePrescription;
import com.example.medicos.databinding.FragmentPrescriptionBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Prescription extends Fragment {
    private FragmentPrescriptionBinding binding;

    FirebaseDatabase db = FirebaseDatabase.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPrescriptionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        String x = phoneNoClass.getMobileNoOfDoctor();

        String patient_mobileNumber = binding.realOtp.getText().toString();
        binding.getOtp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if (!binding.realOtp.getText().toString().trim().isEmpty()) {
                    if ((binding.realOtp.getText().toString().trim()).length() == 10) {

                        Patient_detail_show();

                    } else {
                        Toast.makeText(getActivity(), "Enter Correct Number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }


    private void Patient_detail_show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View customLayout = getLayoutInflater().inflate(R.layout.alert_patient_details, null);
        builder.setView(customLayout)
                .setPositiveButton(R.string.Submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText user_name = (EditText) customLayout.findViewById(R.id.PatientName);
                        EditText age = (EditText) customLayout.findViewById(R.id.age);
                        EditText gender = (EditText) customLayout.findViewById(R.id.PatientGender);


                        if (!user_name.getText().toString().isEmpty() & (age.getText().toString().trim()).length() <= 3) {
                            String patient_mobileNumber = binding.realOtp.getText().toString();
                            HashMap<String, String> patientData = new HashMap<>();
                            patientData.put("nameOfPatient", user_name.getText().toString());
                            patientData.put("ageOfPatient", age.getText().toString());
                            patientData.put("genderOfPatient", gender.getText().toString());
                            patientData.put("mobileNoOfPatient", patient_mobileNumber);
                            DatabaseReference Patient_data = db.getReference("DoctorData").child("+919937336406")
                                    .child(patient_mobileNumber);
                            Patient_data.setValue(patientData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), WritePrescription.class);
                                    intent.putExtra("patientName", user_name.getText().toString());
                                    startActivity(intent);
                                }
                            });

                        } else {

                            Toast.makeText(getContext(), "You have not entered all the datas correctly", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}