package com.thehyperprogrammer.notespedia;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultWEBVIEWActivity extends AppCompatActivity {

    WebView webView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_webview);

        Toolbar toolbar = findViewById(R.id.result_toolbar);
        toolbar.setTitle("RESULTS");
        toolbar.setNavigationIcon(R.drawable.back_arrow_ic);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        HideTaskBar();
        CastingViews();
        RetriveURL();


    }

    // User Defined Functions

    private void CastingViews() {
        webView = findViewById(R.id.result_webview);
    }

    private void DiplomaResultWebView(String URL) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(URL);
    }

    private void HideTaskBar() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }

    private void RetriveURL() {

        FirebaseDatabase.getInstance().getReference()
                .child("EXAM RESULT URL")
                .child("URL")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String URL = dataSnapshot.getValue().toString();
                DiplomaResultWebView(URL);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ResultWEBVIEWActivity.this, "Failed to Load", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    // User Defined Functions

}
