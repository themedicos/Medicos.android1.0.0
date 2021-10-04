package com.example.medicos.prescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.medicos.Model.prescribed_test;
import com.example.medicos.R;
import com.example.medicos.databinding.ActivityWritePrescriptionBinding;

import java.util.ArrayList;


public class WritePrescription extends AppCompatActivity {

    private ActivityWritePrescriptionBinding binding;
    private static final String[] Medicine = new String[]{
            "hh", "vv"
    };

    ArrayList<prescribed_test> test_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWritePrescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String patientname= getIntent().getStringExtra("patientName");

        binding.patientnameAtPrescription.setText(patientname);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ArrayAdapter<String> search_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Medicine);
        binding.search0.setAdapter(search_adapter);

    }

    public void OnAddTest(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View addMoreTest = inflater.inflate(R.layout.advice_test_box, null);
        binding.addMoreAdviceTest.addView(addMoreTest, binding.addMoreAdviceTest.getChildCount() - 1);
    }

    public void OnDeleteTest(View v) {
        binding.addMoreAdviceTest.removeView((View) v.getParent());

    }

    public void OnAddMedicine(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View addMoreTest = inflater.inflate(R.layout.prescription_box, null);
        binding.addMoreMedicine.addView(addMoreTest, binding.addMoreMedicine.getChildCount() - 1);
    }

    public void OnDeleteMedicine(View v) {
        binding.addMoreMedicine.removeView((View) v.getParent());
    }
    public void preview(View v){

        if (checkIfValidAndRead()){
            Intent intent = new Intent(WritePrescription.this, preview_prescription.class);
            Bundle bundle= new Bundle();
            bundle.putSerializable("list",test_list);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    private boolean checkIfValidAndRead() {
        test_list.clear();
        boolean result = true;
        for (int i=0;i<binding.addMoreAdviceTest.getChildCount();i++){
            View addMoreTest = binding.addMoreAdviceTest.getChildAt(i);
            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.search1);
            TextView textView = findViewById(R.id.specification);
            prescribed_test prescribed_test = new prescribed_test();
            if (!autoCompleteTextView.getText().toString().isEmpty()){
                prescribed_test.setTest_name(autoCompleteTextView.getText().toString());
            }else{
                result =false;
                break;
            }
//            if (!textView.getText().toString().equals("")){
//                prescribed_test.setSpecification(textView.getText().toString());
//            }else {
//                result= false;
//                break;
//            }

        }
        return result;
    }


    public void send(View view) {

    }
}