package com.example.medicos.ui.prescription;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.medicos.R;
import com.example.medicos.prescription.WritePrescription;
import com.example.medicos.databinding.FragmentPrescriptionBinding;

public class Prescription extends Fragment {
    private FragmentPrescriptionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPrescriptionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

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
                        EditText user_name = (EditText) customLayout.findViewById(R.id.username);
                        EditText age = (EditText) customLayout.findViewById(R.id.age);
//                        Spinner gender = customLayout.findViewById(R.id.Gender);
                        if (!user_name.getText().toString().isEmpty() & !age.getText().toString().isEmpty()) {
                            Intent intent = new Intent(getContext(), WritePrescription.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Enter all Details", Toast.LENGTH_SHORT).show();
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