package com.example.medicos.prescription;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicos.Adapter.prescribed_medicineAdapter;
import com.example.medicos.Adapter.prescribed_testAdapter;
import com.example.medicos.MainActivity;
import com.example.medicos.Model.PdfData;
import com.example.medicos.Model.patientPhNo;
import com.example.medicos.Model.prescribed_medicine;
import com.example.medicos.Model.prescribed_test;
import com.example.medicos.R;
import com.example.medicos.databinding.ActivityPreviewPrescriptionBinding;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class preview_prescription extends AppCompatActivity {

    private ActivityPreviewPrescriptionBinding binding;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseAuth firebaseAuth;
    String DoctorClinicName, doctorName, DoctorClinicLocation;
    String patientName, patientAge, PatientGender,PatientNumber;
    ImageView share;
    private LinearLayout view;
    String currentPdfName = "Pdf1";
    int xd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreviewPrescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String patientPhNumber = patientPhNo.getPhNoOfPatient();
        SharedPreferences sharedPreferences3 = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String DoctorPhNo = sharedPreferences3.getString("phone", "");
//        binding.DoctorPhNOPp.setText(DoctorPhNo);
        DatabaseReference roat = db.getReference("DoctorData").child(DoctorPhNo);
        roat.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    doctorName = "Dr." + task.getResult().child("name").getValue();
                    DoctorClinicName = String.valueOf(task.getResult().child("clinicName").getValue());
                    DoctorClinicLocation = String.valueOf(task.getResult().child("clinicLocation").getValue());
                    random();

                } else {

                }
            }


        });


        roat.child(patientPhNumber).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    patientName = String.valueOf(task.getResult().child("nameOfPatient").getValue());
                    patientAge = String.valueOf(task.getResult().child("ageOfPatient").getValue());
                    PatientGender = String.valueOf(task.getResult().child("genderOfPatient").getValue());
                    PatientNumber = String.valueOf(task.getResult().child("mobileNoOfPatient").getValue());
                    random();
                } else {

                }
            }
        });


        try {
            share = findViewById(R.id.share);
            Toolbar toolbar = findViewById(R.id.readToolBar);
            setSupportActionBar(toolbar);
            LayoutInflater infla = LayoutInflater.from(this);
            view = (LinearLayout) infla.inflate(R.layout.pdf_item, null, false);

            share.setOnClickListener(view -> share());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", e.getMessage().toString());
        }

    }

    private void random() {
        xd++;
        if (xd==2) {
            informationEntry();
            assignValues();
            generatePdf();
        }

    }

    @SuppressLint("SetTextI18n")
    private void informationEntry() {
        TextView name =(TextView) view.findViewById(R.id.name_preview);
        TextView age_preview = (TextView) view.findViewById(R.id.age_preview);
        TextView sex_preview = (TextView) view.findViewById(R.id.sex_preview);
        TextView header = (TextView) view.findViewById(R.id.header);
        TextView doctorName_preview = (TextView) view.findViewById(R.id.doctorName_preview);
        TextView contactNo = (TextView) view.findViewById(R.id.contactNo);
        TextView date = (TextView) view.findViewById(R.id.date);
        contactNo.setText("CONTACT NO: "+PatientNumber);
        header.setText(DoctorClinicName);
        doctorName_preview.setText("Name of the doctor:\n"+ doctorName);
        name.setText("NAME OF THE PATIENT:"+ patientName);
        age_preview.setText("AGE:"+ patientAge);
        sex_preview.setText("SEX:"+ PatientGender);
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        date.setText("DATE:" + mDay + "-" + mMonth + "-" + mYear);

    }

    @Override
    protected void onDestroy() {
        view = null;
        super.onDestroy();
    }

    public void share() {
        File file = new File(getExternalFilesDir(null), currentPdfName + ".pdf");
        Uri uri = FileProvider.getUriForFile(this,
                getApplicationContext().getPackageName() + ".provider",
                file);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("application/pdf");
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(sendIntent, "Share using ..."));
    }

    public void assignValues() {
        PdfData obj = new PdfData();
        LinearLayout testLayout = view.findViewById(R.id.layoutTable1);
        LinearLayout medicineLayout = view.findViewById(R.id.layoutTable2);
        int counter = 0;
        for (prescribed_test test : obj.getTestArrayList()) {
            LinearLayout testItem = getItem(counter);
            TextView sno = testItem.findViewById(R.id.sno);
            sno.setText(Integer.toString(counter + 1));
            TextView testText = testItem.findViewById(R.id.test);
            testText.setText(test.test_name);
            TextView spec = testItem.findViewById(R.id.spec);
            spec.setText(test.specification);
            testLayout.addView(testItem);
            ++counter;
        }
        counter = 0;

        for (prescribed_medicine med : obj.getMedicineArrayList()) {
            Log.d(counter + "", med.toString());
            LinearLayout medItem = getMedicine(counter);
            TextView sno = medItem.findViewById(R.id.sno);
            sno.setText(Integer.toString(counter + 1));
            TextView medName = medItem.findViewById(R.id.medName);
            medName.setText(med.medicine_name);
            TextView unitDose = medItem.findViewById(R.id.unitDose);
            unitDose.setText(med.med_quantity);
            TextView freq = medItem.findViewById(R.id.freq);
            freq.setText(med.med_dose1 + "/" + med.med_dose2 + "/" + med.getMed_dose3());
            medicineLayout.addView(medItem);
            ++counter;
        }
    }

    public LinearLayout getItem(int a) {
        LayoutInflater inflater = LayoutInflater.from(this);
        if (a % 2 == 0) {
            return (LinearLayout) inflater.inflate(R.layout.table_item, null, false);
        } else {
            return (LinearLayout) inflater.inflate(R.layout.table_item_2, null, false);
        }
    }

    public LinearLayout getMedicine(int a) {
        LayoutInflater inflater = LayoutInflater.from(this);
        if (a % 2 == 0) {
            return (LinearLayout) inflater.inflate(R.layout.rx_table_1, null, false);
        } else {
            return (LinearLayout) inflater.inflate(R.layout.rx_table_2, null, false);
        }
    }

    private void generatePdf() {
        currentPdfName = "Pdf" + (new Date().getTime());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getDisplay().getRealMetrics(displayMetrics);
        } else {
            this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }
        view.measure(
                View.MeasureSpec.makeMeasureSpec(
                        displayMetrics.widthPixels, View.MeasureSpec.EXACTLY
                ),
                View.MeasureSpec.makeMeasureSpec(
                        displayMetrics.heightPixels, View.MeasureSpec.EXACTLY
                )
        );
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        ConstraintLayout con = view.findViewById(R.id.ha);
        Bitmap bitmap = Bitmap.createBitmap(con.getMeasuredWidth(), con.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        con.draw(canvas);
        Bitmap.createScaledBitmap(bitmap, 452, 800, true);
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(con.getMeasuredWidth(), con.getHeight() + 300, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        page.getCanvas().drawBitmap(bitmap, 0F, 0F, null);
        pdfDocument.finishPage(page);
        File filePath = new File(getExternalFilesDir(null), currentPdfName + ".pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            pdfDocument.close();
        }
        pdfDocument.close();
        PDFView pdfView = findViewById(R.id.pdfView);
        pdfView.fromFile(filePath)
                .load();
    }
}

