package com.thehyperprogrammer.notespedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentPortal extends AppCompatActivity {

    String Department,Semester,Username;
    TextView HeaderTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_portal);

        Bundle extras = getIntent().getExtras();
        Department = extras.getString("Department");
        Semester = extras.getString("Semester");
        Username = extras.getString("Username");

        HeaderTV = findViewById(R.id.SP_header_id);
        HeaderTV.setText("Hey,\t"+Username+"\nwe got something special for you.");

        findViewById(R.id.internships_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentPortal.this, InternshipsView.class);
                Bundle extras = new Bundle();
                extras.putString("Username",Username);
                extras.putString("Department", Department);
                extras.putString("Semester", Semester);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        findViewById(R.id.college_card_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentPortal.this,CollegeViewActivity.class));
            }
        });

        findViewById(R.id.startup_card_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentPortal.this,StartupViewActivity.class));
            }
        });

        findViewById(R.id.job_card_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentPortal.this, JobViewActivity.class);
                Bundle extras = new Bundle();
                extras.putString("Username",Username);
                extras.putString("Department", Department);
                extras.putString("Semester", Semester);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

    }
}
