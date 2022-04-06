package com.thehyperprogrammer.notespedia;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class StartupViewActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    CircleImageView editor_iv;
    TextView editor_name,edit_date,post_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back_arrow_ic);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        progressDialog = new ProgressDialog(StartupViewActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        editor_iv = findViewById(R.id.editor_pic);
        editor_name = findViewById(R.id.editor_name_id);
        edit_date = findViewById(R.id.edit_date_id);
        post_header = findViewById(R.id.startup_header_id);
        recyclerView = findViewById(R.id.sp_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase.getInstance().getReference()
                .child("START UPS")
                .child("START UP HEADER")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        post_header.setText(dataSnapshot.child("POST_HEADER").getValue().toString());
                        editor_name.setText(dataSnapshot.child("POST_EDITOR_NAME").getValue().toString());
                        edit_date.setText(dataSnapshot.child("POST_EDIT_DATE").getValue().toString());

                        Glide.with(StartupViewActivity.this)
                                .load(dataSnapshot.child("POST_EDITOR_IMG").getValue())
                                .into(editor_iv);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        FirebaseRecyclerOptions<StartUpModel> options =
                new FirebaseRecyclerOptions.Builder<StartUpModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                        .child("START UPS")
                                        .child("STARTUP STORIES")
                                ,StartUpModel.class)
                        .build();




        FirebaseRecyclerAdapter<StartUpModel, StartupViewHolder> adapter
                = new FirebaseRecyclerAdapter<StartUpModel, StartupViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull StartupViewHolder holder, final int position, @NonNull final StartUpModel model) {

                Glide.with(StartupViewActivity.this)
                        .load(model.getSTARTUP_IMG_URL())
                        .into(holder.STARTUP_IMG_URL);

                holder.STARTUP_TITLE.setText(model.getSTARTUP_TITLE());
                holder.STARTUP_DESC.setText(model.getSTARTUP_DESC());
                progressDialog.dismiss();


            }

            @NonNull
            @Override
            public StartupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.startup_item, parent, false);
                StartupViewHolder viewHolder = new StartupViewHolder(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        /////////



    }

    public static class StartupViewHolder extends RecyclerView.ViewHolder{

        TextView STARTUP_TITLE,STARTUP_DESC;
        ImageView STARTUP_IMG_URL;
        public StartupViewHolder(@NonNull View itemView) {
            super(itemView);

            STARTUP_TITLE = itemView.findViewById(R.id.startup_title_id);
            STARTUP_DESC = itemView.findViewById(R.id.startup_desc_id);
            STARTUP_IMG_URL = itemView.findViewById(R.id.startup_pic);

        }
    }

}
