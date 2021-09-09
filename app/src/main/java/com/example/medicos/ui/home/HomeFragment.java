package com.example.medicos.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
        View view = inflater.inflate(R.layout.fragment_home_,container,false);
        SliderView sliderView = view.findViewById(R.id.imageSlider);


        //image slider.....................>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
       SliderAdapter sliderAdapter = new SliderAdapter(images);
       sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.RED);
        sliderView.setIndicatorUnselectedColor(Color.GREEN);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :sliderView.startAutoCycle();
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
