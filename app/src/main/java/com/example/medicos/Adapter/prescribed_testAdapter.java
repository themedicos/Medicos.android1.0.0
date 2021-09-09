package com.example.medicos.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicos.R;
import com.example.medicos.Model.prescribed_test;
import com.example.medicos.prescription.preview_prescription;

import java.util.ArrayList;

public class prescribed_testAdapter extends RecyclerView.Adapter<prescribed_testAdapter.prescribed_testView> {
    ArrayList<prescribed_test> test_list_added = new ArrayList<>();

    public prescribed_testAdapter(preview_prescription preview_prescription, ArrayList<prescribed_test> test_list) {
        this.test_list_added = test_list;
    }

    @NonNull
    @Override
    public prescribed_testView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advice_test_box,parent,false);
        return new prescribed_testView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull prescribed_testView holder, int position) {
        prescribed_test prescribed_test = test_list_added.get(position);
        holder.newTestName.setText(prescribed_test.getTest_name());

    }

    @Override
    public int getItemCount() {
        return test_list_added.size();
    }

    public class prescribed_testView extends  RecyclerView.ViewHolder{

        TextView newTestName;
        public prescribed_testView(@NonNull View itemView) {
            super(itemView);
            newTestName =(TextView)itemView.findViewById(R.id.new_test_name);
        }
    }
}
