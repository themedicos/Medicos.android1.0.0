package com.example.medicos.UserSignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.medicos.Adapter.SliderAdapter;
import com.example.medicos.MainActivity;
import com.example.medicos.R;
import com.example.medicos.databinding.ActivityFirstSliderBinding;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class FirstSlider extends AppCompatActivity {

    private ActivityFirstSliderBinding binding;
    int autoSave;

    SharedPreferences sharedpreferences;

    SliderView sliderView;
    int[] images = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstSliderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        sharedpreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        int j = sharedpreferences.getInt("key", 0);

        //Default is 0 so autologin is disabled
        if (j > 0) {
            Intent activity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(activity);
        }


        SliderView sliderView = findViewById(R.id.imageSlider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.RED);
        sliderView.setIndicatorUnselectedColor(Color.GREEN);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

    }

    public void skip(View view) {
        Intent intent = new Intent(FirstSlider.this, MainActivity.class);
        startActivity(intent);
    }

    public void next(View view) {
        //Once you click login, it will add 1 to shredPreference which will allow autologin in onCreate
        autoSave = 1;
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putInt("key", autoSave);
//        editor.apply();
        Intent intent = new Intent(FirstSlider.this, SignUpLogIn.class);
        startActivity(intent);
    }
}
