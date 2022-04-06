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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuestionPaperViewActivity extends AppCompatActivity {

    Toolbar QuestionPaperViewToolbar;
    WebView QuestionPaperWebView;
    ProgressDialog progressDialog;
    String Department,Semester,TEMP;
    //Firebase
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference();
    //Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_paper_view);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting data..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        Bundle extras = getIntent().getExtras();

        TEMP = extras.getString("1");
        Department = extras.getString("Department");
        Semester = extras.getString("Semester");

        CastingViews();

        if(TEMP.equals("DCET QP")){

            Toolbar toolbar = findViewById(R.id.QP_toolbar);
            toolbar.setTitle("D-CET QP");
            toolbar.setNavigationIcon(R.drawable.back_arrow_ic);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed(); // Implemented by activity
                }
            });
            //

            FirebaseDatabase.getInstance().getReference()
                    .child("QUESTION PAPERS")
                    .child(Department)
                    .child("D-CET")
                    .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String URL = dataSnapshot.getValue().toString();
                    QuestionPaperWebView.getSettings().setJavaScriptEnabled(true);
                    QuestionPaperWebView.setWebViewClient(new WebViewClient());
                    QuestionPaperWebView.getSettings().setBuiltInZoomControls(true);
                    QuestionPaperWebView.getSettings().setBuiltInZoomControls(true);
                    QuestionPaperWebView.loadUrl(URL);
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(QuestionPaperViewActivity.this, "Failed to Load", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

            //
        }
        else if(TEMP.equals("QUESTION PAPERS")){

            Toolbar toolbar = findViewById(R.id.QP_toolbar);
            toolbar.setTitle("QUESTION PAPERS");
            toolbar.setNavigationIcon(R.drawable.back_arrow_ic);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed(); // Implemented by activity
                }
            });

            //

            FirebaseDatabase.getInstance().getReference()
                    .child("QUESTION PAPERS")
                    .child(Department)
                    .child(Semester).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String URL = dataSnapshot.getValue().toString();
                    QuestionPaperWebView.getSettings().setJavaScriptEnabled(true);
                    QuestionPaperWebView.setWebViewClient(new WebViewClient());
                    QuestionPaperWebView.getSettings().setBuiltInZoomControls(true);
                    QuestionPaperWebView.getSettings().setBuiltInZoomControls(true);
                    QuestionPaperWebView.loadUrl(URL);
                    progressDialog.dismiss();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(QuestionPaperViewActivity.this, "Failed to Load", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

            //
        }
        else {
            Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
        }

    }

    private void CastingViews() {
        QuestionPaperWebView = findViewById(R.id.QuestionPaperView);
    }

    @Override
    public void onBackPressed() {
        if(QuestionPaperWebView != null && QuestionPaperWebView.canGoBack())
            QuestionPaperWebView.goBack();// if there is previous page open it
        else
            super.onBackPressed();//if there is no previous page, close app
    }

}
