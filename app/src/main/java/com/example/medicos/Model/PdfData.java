package com.example.medicos.Model;


import java.util.ArrayList;

public class PdfData {
    static ArrayList<prescribed_test> testArrayList;
    static ArrayList<prescribed_medicine> medicineArrayList;

    public ArrayList<prescribed_test> getTestArrayList() {
        return testArrayList;
    }

    public ArrayList<prescribed_medicine> getMedicineArrayList() {
        return medicineArrayList;
    }

    public void setTestArrayList(ArrayList<prescribed_test> list) {
        testArrayList = list;
    }

    public void setMedicineArrayList(ArrayList<prescribed_medicine> list) {
        medicineArrayList = list;
    }
}
