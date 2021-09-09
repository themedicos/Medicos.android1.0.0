package com.example.medicos.UserSignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.medicos.Adapter.SliderAdapter;
import com.example.medicos.MainActivity;
import com.example.medicos.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class FirstSlider extends AppCompatActivity {

    SliderView sliderView;
    int[] images={
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four
    };
//    String prevStarted = "yes";
//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
//        if (!sharedpreferences.getBoolean(prevStarted, false)) {
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.putBoolean(prevStarted, Boolean.TRUE);
//            editor.apply();
//        } else {
//            moveToSecondary();
//        }
//    }
//
//    private void moveToSecondary() {
//        Intent intent = new Intent(FirstSlider.this,MainActivity.class);
//        startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_slider);
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
        Intent intent = new Intent(FirstSlider.this, SignUpLogIn.class);
        startActivity(intent);
    }
}