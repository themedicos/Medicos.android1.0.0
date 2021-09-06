package com.example.medicos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicos.databinding.ActivityUserInputBinding;
import com.example.medicos.ui.userprofile.Userprofile;

import java.util.jar.Attributes;

public class UserInputActivity extends AppCompatActivity {

    private ActivityUserInputBinding binding;
    private static final String[] loginas = new String[]{
            "Doctor", "Individual"
    };
    private  static  final String[] gender = new String[]{
            "Male","Female","Others"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> search_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, loginas);
        binding.autoCompleteTextView.setAdapter(search_adapter);

        ArrayAdapter<String> search_adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, gender);
        binding.autoCompleteTextView2.setAdapter(search_adapter1);



        binding.usreInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.Name.getText().toString().trim().isEmpty() & !binding.dateOfBirth.getText().toString().trim().isEmpty()) {
                    Intent intent = new Intent(UserInputActivity.this, MainActivity.class);
//                    intent.putExtra("name",Name.getText().toString());
//                    intent.putExtra("dateOfbirth",dateOfBirth.getText().toString());
//                    intent.putExtra("loginas",spinner1.toString());
//                    intent.putExtra("gender",spinner2.toString());

                    Bundle bundle = new Bundle();
                    bundle.putString("name", binding.Name.getText().toString());
                    bundle.putString("dateOfbirth", binding.dateOfBirth.getText().toString());
//                    bundle.putString("loginas", String.valueOf(binding.LogInAs.getSelectedItem().toString()));
//                    bundle.putString("gender", String.valueOf(binding.Gender.getSelectedItem().toString()));

                    //PASS OVER THE BUNDLE TO OUR FRAGMENT
                    Userprofile userprofile = new Userprofile();
                    userprofile.setArguments(bundle);

                    startActivity(intent);

                } else {
                    Toast.makeText(UserInputActivity.this, "Fill out all the blanck", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void backToRegister(View view) {
        Intent intent = new Intent(UserInputActivity.this,SignUpLogIn.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
    }
}