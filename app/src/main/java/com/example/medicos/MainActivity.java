package com.example.medicos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.medicos.databinding.ActivityMainBinding;
import com.example.medicos.ui.home.HomeFragment;
import com.example.medicos.ui.prescription.Prescription;
import com.example.medicos.ui.userprofile.Userprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_prescription, R.id.navigation_userprofile).build();


        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new HomeFragment());
        transaction.commit();


                binding.navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                        int id = item.getItemId();
                        switch(id){
                            case R.id.navigation_home:
                            transaction.replace(R.id.container,new HomeFragment());
                            break;
                            case R.id.navigation_prescription:
                            transaction.replace(R.id.container,new Prescription());
                            break;
                            case R.id.navigation_userprofile:
                            transaction.replace(R.id.container,new Userprofile());
                            break;
                        }
                        transaction.commit();
                        return true;
                    }


                });


    }
@Override
public void onBackPressed() {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder.setTitle("Exit Application?");
    alertDialogBuilder
            .setMessage("Click yes to exit!")
            .setCancelable(false)
            .setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    })

            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    dialog.cancel();
                }
            });

    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
}

}