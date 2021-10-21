package com.example.medicos.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicos.R;
import com.example.medicos.Model.prescribed_test;

import java.util.ArrayList;

public class prescribed_testAdapter extends RecyclerView.Adapter<prescribed_testAdapter.prescribed_testView> {

    ArrayList<prescribed_test> test_list_added = new ArrayList<>();

    public prescribed_testAdapter(ArrayList<prescribed_test> test_list_added) {
        this.test_list_added = test_list_added;
    }

    @NonNull
    @Override
    public prescribed_testView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_advice_test, parent, false);

        return new prescribed_testView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull prescribed_testView holder, int position) {
        prescribed_test prescribed_test = test_list_added.get(position);
        holder.newTestName.setText(prescribed_test.getTest_name());
        holder.specification.setText(prescribed_test.getSpecification());

    }

    @Override
    public int getItemCount() {
        return test_list_added.size();
    }

    public class prescribed_testView extends RecyclerView.ViewHolder {

        TextView newTestName,specification;
        public prescribed_testView(@NonNull View itemView) {
            super(itemView);
            newTestName = (TextView) itemView.findViewById(R.id.new_test_name);
            specification = (TextView) itemView.findViewById(R.id.specific);
        }
    }
}
