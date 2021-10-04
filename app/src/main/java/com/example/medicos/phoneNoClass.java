package com.example.medicos;

import android.app.Application;

public class phoneNoClass extends Application {
    public static String mobileNoOfDoctor;

    public phoneNoClass() {
    }

    public static String getMobileNoOfDoctor() {
        return mobileNoOfDoctor;
    }

    public static void setMobileNoOfDoctor(String mobileNoOfDoctor) {
        phoneNoClass.mobileNoOfDoctor = mobileNoOfDoctor;
    }
}
