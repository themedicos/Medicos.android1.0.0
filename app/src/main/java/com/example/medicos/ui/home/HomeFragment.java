package com.example.medicos.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.medicos.R;
import com.example.medicos.Adapter.SliderAdapter;
import com.example.medicos.databinding.FragmentHomeBinding;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class HomeFragment extends Fragment {

    //private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    SliderView sliderView;
    int[] images = {
            R.drawable.homesliderone,
            R.drawable.homesliderthree,
            R.drawable.homeslidertwo,
            R.drawable.homesliderfour
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_home_, container, false);
        SliderView sliderView = view.findViewById(R.id.imageSlider);
        TextView card1=view.findViewById(R.id.card1);
        TextView card2=view.findViewById(R.id.card2);
        TextView card3=view.findViewById(R.id.card3);

        //image slider.....................>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.RED);
        sliderView.setIndicatorUnselectedColor(Color.GREEN);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :sliderView.startAutoCycle();

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                final View customLayout1 = getLayoutInflater().inflate(R.layout.alert_card_one, null);
                builder1.setView(customLayout1)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                final View customLayout2 = getLayoutInflater().inflate(R.layout.alert_card_two, null);
                builder2.setView(customLayout2)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
                final View customLayout3 = getLayoutInflater().inflate(R.layout.alert_card_three, null);
                builder3.setView(customLayout3)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog3 = builder3.create();
                dialog3.show();
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
