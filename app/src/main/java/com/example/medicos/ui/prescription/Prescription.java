package com.example.medicos.ui.prescription;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.medicos.Model.patientPhNo;
import com.example.medicos.R;
import com.example.medicos.phoneNoClass;
import com.example.medicos.prescription.WritePrescription;
import com.example.medicos.databinding.FragmentPrescriptionBinding;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Prescription extends Fragment {
    private FragmentPrescriptionBinding binding;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    ProgressBar progressBar;

    TextInputLayout textInputLayout;
    EditText gender;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPrescriptionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        progressBar = (ProgressBar)view.findViewById(R.id.spin_kit);
        Sprite doubleBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.GONE);

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
        final View customLayout = getLayoutInflater().inflate(R.layout.alert_patient_details,null);
        builder.setView(customLayout)
                .setCancelable(false)
                .setPositiveButton(R.string.Submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        progressBar.setVisibility(View.VISIBLE);
                        EditText user_name = (EditText) customLayout.findViewById(R.id.PatientName);
                        EditText age = (EditText) customLayout.findViewById(R.id.age);
                        textInputLayout = customLayout.findViewById(R.id.menu);
                        gender = (EditText) customLayout.findViewById(R.id.PatientGenderInput);

                        String[] gender_Array = {"Male","male","Female","female","Others","others"};
                        String x = phoneNoClass.getMobileNoOfDoctor();


                        SharedPreferences sharedPreferences3 = getActivity().getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        String y = sharedPreferences3.getString("phone", "");

                        Float i = Float.parseFloat(age.getText().toString());

                        if (!user_name.getText().toString().isEmpty() || !age.getText().toString().isEmpty() || !gender.getText().toString().isEmpty()){
                            if(!user_name.getText().toString().isEmpty()){
                                if(i<=120&&!age.getText().toString().isEmpty()){
                                    if((gender.getText().toString().contains("Male")||gender.getText().toString().contains("male")||gender.getText().toString().contains("Female")||
                                            gender.getText().toString().contains("female")||gender.getText().toString().contains("Others")||gender.getText().toString().contains("others"))&&!gender.getText().toString().isEmpty()){
                                        String patient_mobileNumber = binding.realOtp.getText().toString();
                                        String phNoOfpatient= patientPhNo.setPhNoOfPatient(patient_mobileNumber);
                                        HashMap<String, String> patientData = new HashMap<>();
                                        patientData.put("nameOfPatient", user_name.getText().toString());
                                        patientData.put("ageOfPatient", age.getText().toString());
                                        patientData.put("genderOfPatient", gender.getText().toString());
                                        patientData.put("mobileNoOfPatient", patient_mobileNumber);
                                        DatabaseReference Patient_data = db.getReference("DoctorData").child(y)
                                                .child(patient_mobileNumber);
                                        Patient_data.setValue(patientData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Intent intent = new Intent(getContext(), WritePrescription.class);
                                                intent.putExtra("patientName", user_name.getText().toString());
                                                startActivity(intent);
                                            }
                                        });

                                    }else{
                                        Toast.makeText(getContext(), "Please Enter Male,Female,Others", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(getContext(), "Please Enter Correct age", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getContext(), "Please Enter Name ", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
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