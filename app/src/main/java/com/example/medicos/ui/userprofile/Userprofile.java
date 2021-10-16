package com.example.medicos.ui.userprofile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.medicos.R;
import com.example.medicos.UserSignIn.FirstSlider;
import com.example.medicos.databinding.FragmentUserprofileBinding;
import com.example.medicos.phoneNoClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Userprofile extends Fragment {

    private FragmentUserprofileBinding binding;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase db = FirebaseDatabase.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserprofileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        View view = inflater.inflate(R.layout.fragment_userprofile, container, false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

//        TextView UserName = (TextView) view.findViewById(R.id.UserName);
//        TextView Birth_date = (TextView) view.findViewById(R.id.Birth_date);
//        TextView Usergender = (TextView) view.findViewById(R.id.Usergender);
//        TextView Specification = (TextView) view.findViewById(R.id.Specification);
//        Button logout = (Button) view.findViewById(R.id.logout);

        TextView UserName = (TextView) view.findViewById(R.id.UserName);
        TextView Fullname = (TextView)view.findViewById(R.id.Fullname);
        TextView ClinicName = (TextView)view.findViewById(R.id.ClinicName);
        TextView SpecicationOfUser = (TextView) view.findViewById(R.id.SpecicationOfUser);
        TextView logout = (TextView) view.findViewById(R.id.logout);
        String x = phoneNoClass.getMobileNoOfDoctor();

        SharedPreferences sharedPreferences1 = getContext().getApplicationContext().getSharedPreferences("userDataInSharedPref", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences3 = getContext().getApplicationContext().getSharedPreferences("userDataInSharedPref", Context.MODE_PRIVATE);
        String uname = "Dr."+sharedPreferences1.getString("name", "");
        String udob = sharedPreferences1.getString("yearofbirth", "");
        String ugender = sharedPreferences1.getString("gender", "");
        String uspeci = sharedPreferences1.getString("loginas", "");
        String cname = sharedPreferences1.getString("clinicName", "");
        UserName.setText(uname);
        Fullname.setText(uname);
        ClinicName.setText(cname);
        SpecicationOfUser.setText(uspeci);
//        Birth_date.setText(udob);
//        Usergender.setText(ugender);
//        Specification.setText(uspeci);

        logout.setOnClickListener(v -> {
            Toast.makeText(getContext(), "logging out...", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getActivity().getApplicationContext().getApplicationContext().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences.edit();
            editor1.putInt("key", 0);
            editor1.apply();
            Intent intent = new Intent(getContext(), FirstSlider.class);
            startActivity(intent);
            sharedPreferences1.edit().clear().apply();
            sharedPreferences3.edit().clear().apply();
        });

        //use this if you want to fetch data from realtime database
//        DatabaseReference roat = db.getReference("DoctorData").child(x);
//        roat.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()) {
//                    String uname = String.valueOf(task.getResult().child("name").getValue());
//                    String udob = String.valueOf(task.getResult().child("yearofbirth").getValue());
//                    String ugender = String.valueOf(task.getResult().child("gender").getValue());
//                    String uspeci = String.valueOf(task.getResult().child("loginas").getValue());
//                    UserName.setText(uname);
//                    Birth_date.setText(udob);
//                    Usergender.setText(ugender);
//                    Specification.setText(uspeci);
//                } else {
//                    Toast.makeText(getContext(), "unable" + task.getException(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });


        return view;
    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}