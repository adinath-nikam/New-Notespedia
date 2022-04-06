package com.thehyperprogrammer.notespedia;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {


    //Global Variables
    FirebaseAuth mAuth;
    ImageView User_Branch_Icon;
    FirebaseDatabase database;
    DatabaseReference mRef;
    CardView about,CS_CardView,logout,ResultViewCardView, StudenPortalCardView, ExamSchedule, QuestionPaperCardView, ScholarshipCardView;
    CircleImageView HomeProfileImage;
    String ProfileSemesterVAR,ProfileDepartmentVAR,ProfileName,ProfileCollege;
    private FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    TextView User_Branch_Text;
    boolean doubleBackToExitPressedOnce = false;
    private AdView mAdView;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        checkConnection();

        CastingViews();

        showAds();



        Bundle extras = getIntent().getExtras();
        ProfileDepartmentVAR = extras.getString("UserDepartment");
        ProfileSemesterVAR = extras.getString("UserSemester");
        ProfileName = extras.getString("UserName");
        ProfileCollege = extras.getString("UserCollege");

        User_Branch_Icon = findViewById(R.id.User_Branch_Icon_ID);
        User_Branch_Text = findViewById(R.id.User_Branch_Text_ID);

        if(ProfileDepartmentVAR.equals("COMPUTER SCIENCE AND ENGINEERING")) {
            User_Branch_Text.setText(ProfileDepartmentVAR);
            User_Branch_Icon.setBackgroundResource(R.drawable.computer);
            findViewById(R.id.GB).setBackgroundResource(R.drawable.cs_b);
        }
        else if(ProfileDepartmentVAR.equals("MECHANICAL ENGINEERING")) {
            User_Branch_Text.setText(ProfileDepartmentVAR);
            User_Branch_Icon.setBackgroundResource(R.drawable.mechanical);
            findViewById(R.id.GB).setBackgroundResource(R.drawable.me_b);
        }
        else if(ProfileDepartmentVAR.equals("AUTOMOBILE ENGINEERING")) {
            User_Branch_Text.setText(ProfileDepartmentVAR);
            User_Branch_Icon.setBackgroundResource(R.drawable.automobile);
            findViewById(R.id.GB).setBackgroundResource(R.drawable.at_b);
        }
        else if(ProfileDepartmentVAR.equals("MECHATRONICS AND ENGINEERING")) {
            User_Branch_Text.setText(ProfileDepartmentVAR);
            User_Branch_Icon.setBackgroundResource(R.drawable.mechatronics);
            findViewById(R.id.GB).setBackgroundResource(R.drawable.mc_b);
        }
        else if(ProfileDepartmentVAR.equals("ELECTRICAL AND ELECTRONICS ENGINEERING")) {
            User_Branch_Text.setText(ProfileDepartmentVAR);
            User_Branch_Icon.setBackgroundResource(R.drawable.electrical);
            findViewById(R.id.GB).setBackgroundResource(R.drawable.ee_b);
        }
        else if(ProfileDepartmentVAR.equals("ELECTRONICS AND COMMUNICATION ENGINEERING")) {
            User_Branch_Text.setText("ELECTRONICS AND COMM. ENGINEERING");
            User_Branch_Icon.setBackgroundResource(R.drawable.electronics);
            findViewById(R.id.GB).setBackgroundResource(R.drawable.ec_b);
        }
        else if(ProfileDepartmentVAR.equals("CIVIL ENGINEERING")) {
            User_Branch_Text.setText(ProfileDepartmentVAR);
            User_Branch_Icon.setBackgroundResource(R.drawable.civil);
            findViewById(R.id.GB).setBackgroundResource(R.drawable.ce_b);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel =
                    new NotificationChannel("MyNotifications",
                            "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successful";
                        if (!task.isSuccessful()) {
                            startActivity(new Intent(Home.this,ExamScheduleActivity.class));
                        }
                    }
                });

        HideTaskBar();

        //

        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("USERS").child(mAuth.getUid());

        //

        //Set Home Profile Image
        HomeProfileImage();
        //Set Home Profile Image

        share();

        gotoProfileActivity();

        gotoScholarshipsActivity();

        gotoAboutActivity();

        gotoStudentPortal();

        gotoSemestersActivity();

        gotoExamScheduleActivity();

        logoutActivity();

        gotoDiplomaResultView();

        gotoQuestionPapersActivity();

        gotoDcetQP();

//        gotoBusTracking();

        gotoUploads();


        gotoNotice();

    }

    private void gotoNotice() {

        findViewById(R.id.notice_cardView_ID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, NoticeActivity.class);
                Bundle extras = new Bundle();
                extras.putString("UserCollege", ProfileCollege);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

    }

//    private void gotoBusTracking() {
//        findViewById(R.id.Bus_tracking_ID).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Home.this, BusTrackingActivity.class);
//                Bundle extras = new Bundle();
//                extras.putString("UserCollege", ProfileCollege);
//                intent.putExtras(extras);
//                startActivity(intent);
//            }
//        });
//
//    }


    // User Defined Functions

    //get Images
    private void HomeProfileImage() {
        storageReference.child(mAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(HomeProfileImage);
            }
        });
    }
    // get images

    private void gotoDiplomaResultView() {

        ResultViewCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,ResultWEBVIEWActivity.class));
            }
        });

    }

    private void HideTaskBar() {
        // Task Bar Hide
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Task Bar Hide
    }


    private void CastingViews() {
        // Casting Variables
        HomeProfileImage = findViewById(R.id.home_profile_image);
        logout = findViewById(R.id.logout_card);
        mAuth = FirebaseAuth.getInstance();
        ResultViewCardView = findViewById(R.id.ResultViewCardView);
        about = findViewById(R.id.aboutopt);
        CS_CardView = findViewById(R.id.CS_CardView_ID);
        StudenPortalCardView = findViewById(R.id.students_portal_id);
        ExamSchedule = findViewById(R.id.ExamScheduleId);
        QuestionPaperCardView = findViewById(R.id.QuestionPaperCard_ID);
        ScholarshipCardView = findViewById(R.id.ScholarshipCardViewID);
    }

    private void gotoProfileActivity() {
        HomeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Profile.class);
                startActivity(intent);
            }
        });
    }

    private void gotoAboutActivity() {
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,About_us.class);
                startActivity(intent);
            }
        });
    }

    private void logoutActivity() {
        // Logout Button
        findViewById(R.id.logout_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Home.this);
                dialog.setContentView(R.layout.logout_layout);
                dialog.show();

                dialog.findViewById(R.id.logout_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.logout_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(Home.this,MainActivity.class));
                    }
                });
            }
        });



        // Logout Button Code Ends
    }


    private void gotoExamScheduleActivity() {
        ExamSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ExamScheduleActivity.class));
            }
        });
    }

    private void gotoQuestionPapersActivity() {
        QuestionPaperCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, QuestionPaperViewActivity.class);
                Bundle extras = new Bundle();
                extras.putString("1", "QUESTION PAPERS");
                extras.putString("Department", ProfileDepartmentVAR);
                extras.putString("Semester", ProfileSemesterVAR);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    private void gotoScholarshipsActivity() {
        ScholarshipCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,ScholarshipViewActivity.class));
            }
        });
    }


    private void gotoSemestersActivity() {
        CS_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Sem_Content.class);
                Bundle bundle = new Bundle();
                bundle.putString("Dept",ProfileDepartmentVAR);
                bundle.putString("Sem",ProfileSemesterVAR);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void gotoDcetQP() {
        findViewById(R.id.Dcet_ID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, QuestionPaperViewActivity.class);
                Bundle extras = new Bundle();
                extras.putString("1", "DCET QP");
                extras.putString("Department", ProfileDepartmentVAR);
                extras.putString("Semester", ProfileSemesterVAR);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }


    public void checkConnection(){
        if(!isOnline()) {
            Toast.makeText(Home.this, "Check Your Internet Connection!", Toast.LENGTH_LONG).show();
        }
    }

    private void gotoUploads(){

        findViewById(R.id.Uploads_ID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, UploadsActivity.class);
                Bundle extras = new Bundle();
                extras.putString("Username",ProfileName);
                extras.putString("Department", ProfileDepartmentVAR);
                extras.putString("Semester", ProfileSemesterVAR);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

    }


    private void share() {

        findViewById(R.id.shareBTN_ID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference()
                        .child("SHARE")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String ShareSUB = dataSnapshot.child("SHARE SUB").getValue().toString();
                                String ShareBODY = dataSnapshot.child("SHARE BODY").getValue().toString();
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setType("text/type");
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT,ShareSUB);
                                shareIntent.putExtra(Intent.EXTRA_TEXT,ShareBODY);
                                startActivity(Intent.createChooser(shareIntent,"Share using.."));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


            }
        });

    }

    private void gotoStudentPortal() {

        findViewById(R.id.students_portal_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, StudentPortal.class);
                Bundle extras = new Bundle();
                extras.putString("Username",ProfileName);
                extras.putString("Department", ProfileDepartmentVAR);
                extras.putString("Semester", ProfileSemesterVAR);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "click, Again to Exit", Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

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
    //User Defined Functions Ends
}
