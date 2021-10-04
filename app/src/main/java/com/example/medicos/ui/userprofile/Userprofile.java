package com.example.medicos.ui.userprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.medicos.R;
import com.example.medicos.databinding.FragmentUserprofileBinding;
import com.example.medicos.phoneNoClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Userprofile extends Fragment {

    private FragmentUserprofileBinding binding;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase db= FirebaseDatabase.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserprofileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        View view = inflater.inflate(R.layout.fragment_userprofile, container, false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        String x= phoneNoClass.getMobileNoOfDoctor();

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
            }
        });
        DatabaseReference roat=db.getReference("DoctorData").child("+919937336406");
        roat.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){

                    Toast.makeText(getContext(), "updating....."+task.getException(), Toast.LENGTH_SHORT).show();
                    String uname=String.valueOf(task.getResult().child("name").getValue());
                    binding.UserName.setText(uname);

                }else {
                    Toast.makeText(getContext(), "unable"+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}