package com.example.medicos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class preview_prescription extends AppCompatActivity {

    ArrayList<prescribed_test> test_list_added = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_prescription);
        RecyclerView recyclerView1 = findViewById(R.id.recyclerview1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(preview_prescription.this, RecyclerView.VERTICAL, false);

        recyclerView1.setLayoutManager(linearLayoutManager);

        test_list_added=(ArrayList<prescribed_test>) getIntent().getExtras().getSerializable("list");

        recyclerView1.setAdapter(new prescribed_testAdapter(test_list_added));

    }
}