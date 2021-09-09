package com.example.medicos.ui.userprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.medicos.R;
import com.example.medicos.UserSignIn.SignUpLogIn;
import com.example.medicos.databinding.FragmentUserprofileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Userprofile extends Fragment {

    private FragmentUserprofileBinding binding;
    FirebaseAuth firebaseAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserprofileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        View view = inflater.inflate(R.layout.fragment_userprofile, container, false);

        firebaseAuth=FirebaseAuth.getInstance();

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), SignUpLogIn.class);
                startActivity(intent);
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