package com.example.medicos.Model;

import android.app.Application;

public class patientPhNo extends Application {
    public static String phNoOfPatient;

    public patientPhNo() {

    }

    public static String getPhNoOfPatient() {
        return phNoOfPatient;
    }

    public static String setPhNoOfPatient(String phNoOfPatient) {
        patientPhNo.phNoOfPatient = phNoOfPatient;
        return phNoOfPatient;
    }
}
