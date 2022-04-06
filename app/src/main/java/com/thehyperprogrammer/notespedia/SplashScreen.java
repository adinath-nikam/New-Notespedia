package com.thehyperprogrammer.notespedia;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {

    String ProfileSemesterVAR,ProfileDepartmentVAR,ProfileName,ProfileCollege;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){

            FirebaseDatabase.getInstance().getReference()
                    .child("USERS")
                    .child(firebaseUser.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            SendDataDB userProfile = dataSnapshot.getValue(SendDataDB.class);
                            ProfileName = userProfile.getUserName();
                            ProfileDepartmentVAR = userProfile.getUserDepartment();
                            ProfileSemesterVAR = userProfile.getUserSemester();
                            ProfileCollege = userProfile.getUserCollege();

                            Intent intent = new Intent(SplashScreen.this, Home.class);
                            Bundle extras = new Bundle();
                            extras.putString("UserDepartment", ProfileDepartmentVAR);
                            extras.putString("UserSemester", ProfileSemesterVAR);
                            extras.putString("UserName", ProfileName);
                            extras.putString("UserCollege", ProfileCollege);
                            intent.putExtras(extras);
                            startActivity(intent);
                            finish();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(SplashScreen.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();

                        }
                    });//

        }else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);
        }

    }

    public void checkConnection(){
        if(!isOnline()) {
            Dialog dialog = new Dialog(SplashScreen.this);
            dialog.setContentView(R.layout.internet_dialog);
            dialog.show();

            dialog.findViewById(R.id.retryInternet).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkConnection();
                }
            });


        }
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

    @Override
    protected void onStart() {
        super.onStart();
        checkConnection();
    }
}
