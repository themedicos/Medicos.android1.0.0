package com.example.medicos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class preview_prescription extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_prescription);
        RecyclerView recyclerView1 = findViewById(R.id.recyclerview1);
        ArrayList<prescribed_test> test_list_added;


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(preview_prescription.this, RecyclerView.VERTICAL, false);

        recyclerView1.setLayoutManager(linearLayoutManager);

        test_list_added=(ArrayList<prescribed_test>) getIntent().getExtras().getSerializable("list");

        prescribed_testAdapter adapter = new prescribed_testAdapter(this,test_list_added);

        recyclerView1.setAdapter(adapter);


    }

    public void backTowritePrescription(View view) {
        Intent intent = new Intent(preview_prescription.this,WritePrescription.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
    }
}