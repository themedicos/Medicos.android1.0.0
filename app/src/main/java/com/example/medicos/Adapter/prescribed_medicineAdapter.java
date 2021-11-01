package com.example.medicos.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicos.Model.prescribed_medicine;
import com.example.medicos.R;

import java.util.ArrayList;

public class prescribed_medicineAdapter extends RecyclerView.Adapter<prescribed_medicineAdapter.prescribed_medicineView> {
    ArrayList<prescribed_medicine> medicine_list_added = new ArrayList<>();

    public prescribed_medicineAdapter(ArrayList<prescribed_medicine> medicine_list_added) {
        this.medicine_list_added = medicine_list_added;
    }

    @NonNull
    @Override
    public prescribed_medicineView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_prescription,parent,false);
        return new prescribed_medicineView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull prescribed_medicineView holder, int position) {
        prescribed_medicine prescribed_medicine = medicine_list_added.get(position);
        holder.new_medicine_name.setText(prescribed_medicine.getMedicine_name());
        holder.new_note.setText(prescribed_medicine.getNote());
        holder.new_doses_info.setText(prescribed_medicine.getMed_dose1()+","+prescribed_medicine.getMed_dose2()+","+prescribed_medicine.getMed_dose3());
        holder.new_quantity.setText(prescribed_medicine.getMed_quantity());
    }

    @Override
    public int getItemCount() {
        return medicine_list_added.size();
    }

    public class prescribed_medicineView extends RecyclerView.ViewHolder {
        TextView new_medicine_name,new_doses_info,new_quantity,new_note;
        public prescribed_medicineView(@NonNull View itemView) {
            super(itemView);
            new_medicine_name=(TextView) itemView.findViewById(R.id.new_medicine_name);
            new_doses_info=(TextView) itemView.findViewById(R.id.new_doses_info);
            new_quantity=(TextView) itemView.findViewById(R.id.new_quantity);
            new_note=(TextView) itemView.findViewById(R.id.new_note);

        }
    }
}
