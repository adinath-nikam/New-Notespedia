package com.thehyperprogrammer.notespedia;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExamScheduleActivity extends AppCompatActivity {

    TextView PDFTextTV;
    WebView ExamwebView;
    ProgressDialog progressDialog;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_schedule);

        Toolbar toolbar = findViewById(R.id.exam_schedule_toolbar);
        toolbar.setTitle("EXAM DETAILS");
        toolbar.setNavigationIcon(R.drawable.back_arrow_ic);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        showAds();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting Exam Details..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        CastingViews();

        RetriveURL();


    }

    private void CastingViews() {
        PDFTextTV = findViewById(R.id.PDFTextTV);
        ExamwebView = findViewById(R.id.ExamdetailesView);
    }


    private void GetExamDetails(String URL) {
        progressDialog.show();
        ExamwebView.getSettings().setJavaScriptEnabled(true);
        ExamwebView.setWebViewClient(new WebViewClient());
        ExamwebView.getSettings().setBuiltInZoomControls(true);
        ExamwebView.getSettings().setBuiltInZoomControls(true);
        ExamwebView.loadUrl(URL);
        progressDialog.dismiss();
    }


    private void RetriveURL() {

        FirebaseDatabase.getInstance().getReference().child("EXAM SCHEDULES").child("url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String URL = dataSnapshot.getValue().toString();
                GetExamDetails(URL);
                Toast.makeText(ExamScheduleActivity.this, "Please wait while it load...", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ExamScheduleActivity.this, "Failed to Load", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    //ads
    private void showAds() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    //ads


}
