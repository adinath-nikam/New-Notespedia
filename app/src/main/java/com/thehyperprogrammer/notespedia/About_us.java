package com.thehyperprogrammer.notespedia;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class About_us extends AppCompatActivity {

    private AdView mAdView;
    private  int CALL_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = findViewById(R.id.aboutToolbar);
        toolbar.setTitle("ABOUT US");
        toolbar.setNavigationIcon(R.drawable.back_arrow_ic);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        showAds();

        final TextView aboutText = findViewById(R.id.abt_text);

        FirebaseDatabase.getInstance().getReference()
                .child("ABOUT")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        aboutText.setText(dataSnapshot.child("Text").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        findViewById(R.id.dev_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View dialogView = getLayoutInflater().inflate(R.layout.developers, null);
                BottomSheetDialog dialog = new BottomSheetDialog(About_us.this);
                dialog.setContentView(dialogView);
                dialog.show();

                //Adinath Instagram Redirect

                dialogView.findViewById(R.id.adi_msg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseDatabase.getInstance().getReference().child("DEVELOPERS").child("ADINATH").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String URL = dataSnapshot.getValue().toString();
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                    }
                });

                //Adinath Instagram Redirect

                //Omkar Instagram Redirect

                dialogView.findViewById(R.id.om_msg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseDatabase.getInstance().getReference().child("DEVELOPERS").child("OMKAR").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String URL = dataSnapshot.getValue().toString();
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                    }
                });

                //Omkar Instagram Redirect

                //Deepak Instagram Redirect

                dialogView.findViewById(R.id.deep_msg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseDatabase.getInstance().getReference().child("DEVELOPERS").child("DEEPAK").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String URL = dataSnapshot.getValue().toString();
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });

                //Deepak Instagram Redirect

                //Ashitosh Instagram Redirect

                dialogView.findViewById(R.id.ashi_msg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseDatabase.getInstance().getReference().child("DEVELOPERS").child("ASHITOSH").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String URL = dataSnapshot.getValue().toString();
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });

                //Ashitosh Instagram Redirect

            }
        });



        findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //request permission
                if(ContextCompat.checkSelfPermission(About_us.this,
                        Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {

                    FirebaseDatabase.getInstance().getReference()
                            .child("ABOUT")
                            .child("Phone")
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String phone = dataSnapshot.getValue().toString();
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                }else {
                    requestStoragePermission();
                }
                //request permission
            }
        });


        findViewById(R.id.mail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("ABOUT")
                        .child("Mail")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String mail = dataSnapshot.getValue().toString();
                                Toast.makeText(About_us.this,"Please Wait..", Toast.LENGTH_SHORT).show();

                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                        "mailto", mail, null));
                                startActivity(Intent.createChooser(emailIntent, null));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
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


    private void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(About_us.this, Manifest.permission.CALL_PHONE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed for Calling")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(About_us.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_CODE);
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
}
