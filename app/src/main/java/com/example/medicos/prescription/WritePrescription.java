package com.example.medicos.prescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatSpinner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicos.Model.prescribed_medicine;
import com.example.medicos.Model.prescribed_test;
import com.example.medicos.R;
import com.example.medicos.databinding.ActivityWritePrescriptionBinding;

import java.util.ArrayList;


public class WritePrescription extends AppCompatActivity implements View.OnClickListener {

    private ActivityWritePrescriptionBinding binding;
    ArrayList<prescribed_test> test_list_added1 = new ArrayList<>();
    ArrayList<prescribed_medicine> medicine_list_added1 = new ArrayList<>();
    LinearLayout addMoreAdviceTest,addMoreMedicine;
    Button previewPrescription,addMore1,addMore2;
    private static final String[] Medicine = new String[]{
            "hh", "vv"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWritePrescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        previewPrescription=(Button)findViewById(R.id.preview_prescription);
        addMore1=(Button)findViewById(R.id.add_more1);
        addMore2=(Button)findViewById(R.id.add_more2);
        addMoreAdviceTest = (LinearLayout) findViewById(R.id.addMoreAdviceTest);
        addMoreMedicine = (LinearLayout) findViewById(R.id.addMoreMedicine);

        String patientname = getIntent().getStringExtra("patientName");
        binding.patientnameAtPrescription.setText(patientname);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ArrayAdapter<String> search_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Medicine);
//        binding.search0.setAdapter(search_adapter);
        previewPrescription.setOnClickListener(this);
        addMore1.setOnClickListener(this);
        addMore2.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_more1:
                addview1();
                break;

            case R.id.add_more2:
                addview2();
                break;

            case R.id.preview_prescription:

                if (checkIfValidAndRead()) {
                    Intent intent = new Intent(WritePrescription.this, preview_prescription.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", test_list_added1);
                    bundle.putSerializable("list2", medicine_list_added1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
        }


    }

    private boolean checkIfValidAndRead() {
        test_list_added1.clear();
        boolean result = true;
        for (int i = 0; i < addMoreAdviceTest.getChildCount(); i++) {

            View addMoreTest = addMoreAdviceTest.getChildAt(i);

            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) addMoreTest.findViewById(R.id.search1);
            EditText editText = (EditText) addMoreTest.findViewById(R.id.specification);

            prescribed_test prescribed_test1 = new prescribed_test();

            if (!autoCompleteTextView.getText().toString().isEmpty() & !editText.getText().toString().isEmpty()) {

                prescribed_test1.setTest_name(autoCompleteTextView.getText().toString());
                prescribed_test1.setSpecification(editText.getText().toString());

            } else {

                result = false;
                break;

            }

            test_list_added1.add(prescribed_test1);

        }

        for (int j = 0; j < addMoreMedicine.getChildCount(); j++) {

            View addMoreMed = addMoreMedicine.getChildAt(j);

            AutoCompleteTextView search2 = (AutoCompleteTextView) addMoreMed.findViewById(R.id.search2);
            EditText quantity = (EditText) addMoreMed.findViewById(R.id.quantity1);
            Spinner dose1st = (Spinner) addMoreMed.findViewById(R.id.dose1st);
            Spinner dose2nd = (Spinner) addMoreMed.findViewById(R.id.dose2nd);
            Spinner dose3rd = (Spinner) addMoreMed.findViewById(R.id.dose3rd);
            TextView textView = (TextView) addMoreMed.findViewById(R.id.note1);

            prescribed_medicine prescribed_medicine1 = new prescribed_medicine();

            if (!search2.getText().toString().isEmpty() & !quantity.getText().toString().isEmpty()) {

                prescribed_medicine1.setMedicine_name(search2.getText().toString());
                prescribed_medicine1.setMed_quantity(quantity.getText().toString());
                prescribed_medicine1.setNote(textView.getText().toString());

            } else {

                result = false;
                break;

            }
            if (dose1st.getSelectedItemPosition() != 0 & dose2nd.getSelectedItemPosition() != 0 & dose3rd.getSelectedItemPosition() != 0) {

                prescribed_medicine1.setMed_dose1(dose1st.getSelectedItem().toString());
                prescribed_medicine1.setMed_dose2(dose2nd.getSelectedItem().toString());
                prescribed_medicine1.setMed_dose3(dose2nd.getSelectedItem().toString());

            } else {

                result = false;
                break;

            }

            medicine_list_added1.add(prescribed_medicine1);

        }

        if (test_list_added1.size() == 0 & medicine_list_added1.size() == 0) {

            result = false;
            Toast.makeText(WritePrescription.this, "Enter Details...", Toast.LENGTH_SHORT).show();

        }

        return result;

    }

    private void addview1() {

        final View addMoreTest = getLayoutInflater().inflate(R.layout.advice_test_box, null, false);

        AutoCompleteTextView autoCompleteTextView1 = (AutoCompleteTextView) addMoreTest.findViewById(R.id.search1);
        EditText editText = (EditText) addMoreTest.findViewById(R.id.specification);
        ImageView delete = (ImageView) addMoreTest.findViewById(R.id.delete_test_advice);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(addMoreTest);
            }

        });

        addMoreAdviceTest.addView(addMoreTest);

    }

    private void removeView(View view) {
        addMoreAdviceTest.removeView(view);
    }

    private void addview2() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        final View addMoreMed = inflater.inflate(R.layout.medicine__box, null, false);

        AutoCompleteTextView search2 = (AutoCompleteTextView) addMoreMed.findViewById(R.id.search2);
        EditText quantity1 = (EditText) addMoreMed.findViewById(R.id.quantity1);
        Spinner dose1st = (Spinner) addMoreMed.findViewById(R.id.dose1st);
        Spinner dose2nd = (Spinner) addMoreMed.findViewById(R.id.dose2nd);
        Spinner dose3rd = (Spinner) addMoreMed.findViewById(R.id.dose3rd);
        TextView note1 = (TextView) addMoreMed.findViewById(R.id.note1);
        ImageView delete2 = (ImageView) addMoreMed.findViewById(R.id.delete_medicine);

        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView2(addMoreMed);
            }

        });

        addMoreMedicine.addView(addMoreMed);

    }

    private void removeView2(View view) {
        addMoreMedicine.removeView(view);
    }


}