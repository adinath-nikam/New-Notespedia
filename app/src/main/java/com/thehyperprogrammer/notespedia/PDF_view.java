package com.thehyperprogrammer.notespedia;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PDF_view extends AppCompatActivity {

    PDFView pdfViewer;
    String Dept,Sem,Sub_Key;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        // Get Data

        Bundle bundle = getIntent().getExtras();
        Dept = bundle.getString("Dept");
        Sem = bundle.getString("Sem");
        Sub_Key = bundle.getString("SubKey");

        // Get Data

        showAds();

        RetriveURL();

        pdfViewer = findViewById(R.id.pdfviewer_id);

    }

    public class RetrivePDFstream extends AsyncTask<String,Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                if(httpURLConnection.getResponseCode() == 200){
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                }
            }catch (IOException e){
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfViewer.fromStream(inputStream).load();
        }
    }

    // Get URL

    private void RetriveURL() {

        FirebaseDatabase.getInstance().getReference()
                .child("UPLOADS")
                .child(Dept)
                .child(Sem)
                .child(Sub_Key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String URL = dataSnapshot.child("url").getValue().toString();
                        PDF_STREAM(URL);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(PDF_view.this, "Failed to Load", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void PDF_STREAM( String URL) {

        new RetrivePDFstream().execute(URL);

    }
    //Get URL

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
