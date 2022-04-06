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

public class InternshipsView extends AppCompatActivity {

    String Department,Semester,Username;
    RecyclerView recyclerView;
    TextView Intern_TV;
    String url;
    ProgressDialog progressDialog;
    private  int CALL_PERMISSION_CODE = 1;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internships_view);

        Toolbar toolbar = findViewById(R.id.internshipToolbar);
        toolbar.setTitle("FIND INTERNSHIPS");
        toolbar.setNavigationIcon(R.drawable.back_arrow_ic);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        showAds();

        progressDialog = new ProgressDialog(InternshipsView.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Bundle extras = getIntent().getExtras();
        Department = extras.getString("Department");
        Semester = extras.getString("Semester");
        Username = extras.getString("Username");

        recyclerView = findViewById(R.id.internships_recycler_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intern_TV = findViewById(R.id.intern_view_header_id);
        Intern_TV.setText("Hey,\t"+Username+"\nsome of best Internships availabel only for you.");


        FirebaseRecyclerOptions<Internships_model> options =
                new FirebaseRecyclerOptions.Builder<Internships_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                        .child("INTERNSHIPS")
                                        .child(Department)
                                ,Internships_model.class)
                        .build();


        FirebaseRecyclerAdapter<Internships_model, InternshipsView.InternshipsViewHolder> adapter
                = new FirebaseRecyclerAdapter<Internships_model, InternshipsView.InternshipsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull InternshipsView.InternshipsViewHolder holder, final int position, @NonNull final Internships_model model) {

                Glide.with(InternshipsView.this)
                        .load(model.getIMG_URL())
                        .into(holder.INTERN_IMG_URL);


                holder.INTERN_ADDRESS.setText("-\t"+model.getINTERN_ADDRESS());
                holder.INTERN_EMAIL.setText("-\t"+model.getINTERN_EMAIL());
                holder.INTERN_HRS.setText("-\t"+model.getINTERN_HRS());
                holder.INTERN_SKILLS.setText("-\t"+model.getINTERN_SKILLS());
                holder.INTERN_NAME.setText(model.getINTERN_COMPANY());
                holder.INTERN_PHONE.setText("-\t"+model.getINTERN_PH());
                holder.INTERN_URL.setText("-\t"+model.getINTERN_URL());

                progressDialog.dismiss();


                //intern url

                holder.INTERN_URL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String key = getRef(position).getKey();

                        FirebaseDatabase.getInstance().getReference()
                                .child("INTERNSHIPS")
                                .child(Department)
                                .child(key)
                                .child("INTERN_URL")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        url = dataSnapshot.getValue().toString();
                                        Toast.makeText(InternshipsView.this,"Please Wait..", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(InternshipsView.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                });

                //intern url
                //intern mail
                holder.INTERN_EMAIL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String key = getRef(position).getKey();

                        FirebaseDatabase.getInstance().getReference()
                                .child("INTERNSHIPS")
                                .child(Department)
                                .child(key)
                                .child("INTERN_EMAIL")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String mail = dataSnapshot.getValue().toString();
                                        Toast.makeText(InternshipsView.this,"Please Wait..", Toast.LENGTH_SHORT).show();

                                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                                "mailto", mail, null));
                                        startActivity(Intent.createChooser(emailIntent, null));

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(InternshipsView.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                });
                //intern mail
                //intern call
                holder.INTERN_PHONE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String key = getRef(position).getKey();




                        FirebaseDatabase.getInstance().getReference()
                                .child("INTERNSHIPS")
                                .child(Department)
                                .child(key)
                                .child("INTERN_PH")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String phone = dataSnapshot.getValue().toString();
                                        Toast.makeText(InternshipsView.this,"Please Wait..", Toast.LENGTH_SHORT).show();


                                        //request permission
                                        if(ContextCompat.checkSelfPermission(InternshipsView.this,
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
                                        Toast.makeText(InternshipsView.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                });
                //intern call


            }

            @NonNull
            @Override
            public InternshipsView.InternshipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.internship_item, parent, false);
                InternshipsView.InternshipsViewHolder viewHolder = new InternshipsView.InternshipsViewHolder(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        /////////




















    }



    public static class InternshipsViewHolder extends RecyclerView.ViewHolder{

        TextView INTERN_NAME,INTERN_URL,INTERN_PHONE,INTERN_EMAIL,INTERN_HRS, INTERN_ADDRESS,INTERN_SKILLS;
        ImageView INTERN_IMG_URL;
        public InternshipsViewHolder(@NonNull View itemView) {
            super(itemView);

            INTERN_EMAIL = itemView.findViewById(R.id.tv_mail_id);
            INTERN_PHONE = itemView.findViewById(R.id.tv_phone_id);
            INTERN_HRS = itemView.findViewById(R.id.tv_hrs_id);
            INTERN_ADDRESS = itemView.findViewById(R.id.tv_place_id);
            INTERN_SKILLS = itemView.findViewById(R.id.tv_skills_id);
            INTERN_URL = itemView.findViewById(R.id.tv_url_id);
            INTERN_NAME = itemView.findViewById(R.id.intern_name_id);
            INTERN_IMG_URL = itemView.findViewById(R.id.intern_IMG_id);


        }
    }


    private void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(InternshipsView.this, Manifest.permission.CALL_PHONE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed for Calling")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(InternshipsView.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_CODE);
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
