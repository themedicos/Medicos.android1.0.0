package com.example.medicos.Model;

import java.io.Serializable;

public class prescribed_medicine implements Serializable {
    public String medicine_name;
    public String med_quantity;
    public String med_dose1;
    public String med_dose2;
    public String med_dose3;

    public prescribed_medicine(){

    }

    public prescribed_medicine(String medicine_name, String med_quantity, String med_dose1, String med_dose2, String med_dose3, String note) {
        this.medicine_name = medicine_name;
        this.med_quantity = med_quantity;
        this.med_dose1 = med_dose1;
        this.med_dose2 = med_dose2;
        this.med_dose3 = med_dose3;
        this.note = note;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getMed_quantity() {
        return med_quantity;
    }

    public void setMed_quantity(String med_quantity) {
        this.med_quantity = med_quantity;
    }

    public String getMed_dose1() {
        return med_dose1;
    }

    public void setMed_dose1(String med_dose1) {
        this.med_dose1 = med_dose1;
    }

    public String getMed_dose2() {
        return med_dose2;
    }

    public void setMed_dose2(String med_dose2) {
        this.med_dose2 = med_dose2;
    }

    public String getMed_dose3() {
        return med_dose3;
    }

    public void setMed_dose3(String med_dose3) {
        this.med_dose3 = med_dose3;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String note;
}
