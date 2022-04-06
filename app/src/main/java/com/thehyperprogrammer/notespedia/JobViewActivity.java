package com.thehyperprogrammer.notespedia;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobViewActivity extends AppCompatActivity {

    String Department,Semester,Username;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    private  int CALL_PERMISSION_CODE = 1;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_view);


        Toolbar toolbar = findViewById(R.id.jobToolbar);
        toolbar.setTitle("FIND JOBS");
        toolbar.setNavigationIcon(R.drawable.back_arrow_ic);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        showAds();

        progressDialog = new ProgressDialog(JobViewActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        Bundle extras = getIntent().getExtras();
        Department = extras.getString("Department");
        Semester = extras.getString("Semester");
        Username = extras.getString("Username");

        recyclerView=findViewById(R.id.JobRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        FirebaseRecyclerOptions<JobModel> options =
                new FirebaseRecyclerOptions.Builder<JobModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                        .child("JOBS")
                                        .child(Department)
                        ,JobModel.class)
                        .build();

        FirebaseRecyclerAdapter<JobModel, JobViewHolder> adapter
                = new FirebaseRecyclerAdapter<JobModel, JobViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull JobViewHolder holder, final int position, @NonNull final JobModel model) {

                Glide.with(JobViewActivity.this)
                        .load(model.getCMP_IMG_URL())
                        .into(holder.cmp_img);

                holder.cmp_name.setText(model.getCMP_NAME());
                holder.cmp_cat.setText(model.getCMP_CAT());

                holder.cmp_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String key = getRef(position).getKey();

                        FirebaseDatabase.getInstance().getReference()
                                .child("JOBS")
                                .child(Department)
                                .child(key)
                                .child("CMP_CALL")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        String phone = dataSnapshot.getValue().toString();
                                        //request permission
                                        if(ContextCompat.checkSelfPermission(JobViewActivity.this,
                                                Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {

                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                                            startActivity(intent);

                                        }else {
                                            requestStoragePermission();
                                        }
                                        //request permission
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                    }
                });


                //mail
                holder.cmp_mail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String key = getRef(position).getKey();

                        FirebaseDatabase.getInstance().getReference()
                                .child("JOBS")
                                .child(Department)
                                .child(key)
                                .child("CMP_MAIL")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String mail = dataSnapshot.getValue().toString();
                                        Toast.makeText(JobViewActivity.this,"Please Wait..", Toast.LENGTH_SHORT).show();

                                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                                "mailto", mail, null));
                                        startActivity(Intent.createChooser(emailIntent, null));

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(JobViewActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                });
                //mail

                //cmp url

                holder.cmp_url.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String key = getRef(position).getKey();

                        FirebaseDatabase.getInstance().getReference()
                                .child("JOBS")
                                .child(Department)
                                .child(key)
                                .child("CMP_URL")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String url = dataSnapshot.getValue().toString();
                                        Toast.makeText(JobViewActivity.this,"Please Wait..", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(JobViewActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                });

                //cmp url

                progressDialog.dismiss();


            }

            @NonNull
            @Override
            public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false);
                JobViewHolder viewHolder = new JobViewHolder(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }






    public static class JobViewHolder extends RecyclerView.ViewHolder{

        TextView cmp_name,cmp_cat;
        CardView cmp_call,cmp_mail;
        ImageView cmp_img;
        CardView cmp_url;
        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

            cmp_name = itemView.findViewById(R.id.cmp_name);
            cmp_mail = itemView.findViewById(R.id.mail_ic);
            cmp_cat = itemView.findViewById(R.id.cmp_cat);
            cmp_img = itemView.findViewById(R.id.cmp_img);
            cmp_url = itemView.findViewById(R.id.cmp_detail_ic);
            cmp_call = itemView.findViewById(R.id.call_ic);

        }
    }




    private void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(JobViewActivity.this, Manifest.permission.CALL_PHONE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed for Calling")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(JobViewActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CALL_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
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
