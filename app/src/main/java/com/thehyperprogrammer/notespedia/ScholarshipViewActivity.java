package com.thehyperprogrammer.notespedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScholarshipViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_view);

        progressDialog = new ProgressDialog(ScholarshipViewActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        recyclerView = findViewById(R.id.Scholarship_RV_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Scholarship_Model> options =
                new FirebaseRecyclerOptions.Builder<Scholarship_Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                        .child("SCHOLARSHIPS")
                                ,Scholarship_Model.class)
                        .build();

        FirebaseRecyclerAdapter<Scholarship_Model, ScholarshipViewActivity.ScholarshipViewholder> adapter
                = new FirebaseRecyclerAdapter<Scholarship_Model, ScholarshipViewActivity.ScholarshipViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ScholarshipViewActivity.ScholarshipViewholder holder, final int position, @NonNull final Scholarship_Model model) {
                holder.SC_NAME.setText(model.getSC_NAME());
                holder.SC_DESC.setText(model.getSC_DESC());
                holder.SC_CATEGORY.setText(model.getSC_CATEGORY());
                holder.SC_AMOUNT.setText(model.getSC_AMOUNT());
                holder.SC_EXPIRY.setText(model.getSC_EXPIRY());



                Glide.with(ScholarshipViewActivity.this)
                        .load(model.getSC_IMG_URL())
                        .into(holder.SC_IMG_URL);

                progressDialog.dismiss();

                holder.SC_BTN_ID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String key = getRef(position).getKey();

                        FirebaseDatabase.getInstance().getReference()
                                .child("SCHOLARSHIPS")
                                .child(key)
                                .child("SC_URL")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        String url = dataSnapshot.getValue().toString();
                                        Toast.makeText(ScholarshipViewActivity.this,"Please Wait..", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                        startActivity(intent);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(ScholarshipViewActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                                    }
                                });



                    }
                });


            }

            @NonNull
            @Override
            public ScholarshipViewActivity.ScholarshipViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scholarship_view_layout, parent, false);
                ScholarshipViewholder viewHolder = new ScholarshipViewActivity.ScholarshipViewholder(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    public static class ScholarshipViewholder extends RecyclerView.ViewHolder{

        TextView SC_NAME,SC_DESC,SC_AMOUNT,SC_EXPIRY,SC_CATEGORY;
        ImageView SC_IMG_URL;
        Button SC_BTN_ID;
        public ScholarshipViewholder(@NonNull View itemView) {
            super(itemView);

            SC_NAME = itemView.findViewById(R.id.SC_name_id);
            SC_DESC = itemView.findViewById(R.id.SC_desc_id);
            SC_AMOUNT = itemView.findViewById(R.id.SC_amt_id);
            SC_EXPIRY = itemView.findViewById(R.id.SC_expiry_id);
            SC_CATEGORY = itemView.findViewById(R.id.SC_category_id);
            SC_IMG_URL = itemView.findViewById(R.id.sc_image_id);
            SC_BTN_ID = itemView.findViewById(R.id.sc_btn_id);

        }
    }

}
