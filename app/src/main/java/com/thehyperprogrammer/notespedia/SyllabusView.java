package com.thehyperprogrammer.notespedia;

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

public class SyllabusView extends AppCompatActivity {
    String Dept,Sem,Sub_Key;
    WebView Syllabus_WV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_view);


        // Get Data
        Bundle bundle = getIntent().getExtras();
        Dept = bundle.getString("Dept");
        Sem = bundle.getString("Sem");
        Sub_Key = bundle.getString("SubKey");

        Toolbar toolbar = findViewById(R.id.syllabusToolbar);
        toolbar.setTitle(Sem);
        toolbar.setNavigationIcon(R.drawable.back_arrow_ic);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        // Get Data
        RetriveURL();
    }

    private void RetriveURL() {

        FirebaseDatabase.getInstance().getReference()
                .child("SYLLABUS")
                .child(Dept)
                .child(Sem)
                .child(Sub_Key)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String URL = dataSnapshot.child("LINK").getValue().toString();
                GetSylPapers(URL);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SyllabusView.this, "Failed to Load", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void GetSylPapers(String URL) {

        Syllabus_WV = findViewById(R.id.SyllabusView);

        Syllabus_WV.getSettings().setJavaScriptEnabled(true);
        Syllabus_WV.setWebViewClient(new WebViewClient());
        Syllabus_WV.getSettings().setBuiltInZoomControls(true);
        Syllabus_WV.getSettings().setBuiltInZoomControls(true);
        Syllabus_WV.loadUrl(URL);
    }

}
