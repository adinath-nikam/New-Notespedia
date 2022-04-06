package com.thehyperprogrammer.notespedia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class NoticeActivity extends AppCompatActivity {

    String ProfileCollege;
    RecyclerView recyclerView;
    TextView college_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        Bundle extras = getIntent().getExtras();
        ProfileCollege = extras.getString("UserCollege");

        recyclerView = findViewById(R.id.notice_recyclerView);
        college_name = findViewById(R.id.college_name);
        college_name.setText(ProfileCollege);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        //
        FirebaseRecyclerOptions<notice_data> options =
                new FirebaseRecyclerOptions.Builder<notice_data>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                        .child("NOTICE")
                                        .child(ProfileCollege)
                                ,notice_data.class)
                        .build();


        FirebaseRecyclerAdapter<notice_data, NoticeViewHolder> adapter
                = new FirebaseRecyclerAdapter<notice_data, NoticeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull NoticeViewHolder holder, final int position, @NonNull final notice_data model) {

                holder.notice_content.setText(model.getNotice_content());
                holder.notice_date.setText(model.getNotice_date());
                holder.notice_sender.setText(model.getNotice_sender());

            }

            @NonNull
            @Override
            public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
                NoticeViewHolder viewHolder = new NoticeViewHolder(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


        //




    }




    //viewholder
    public static class NoticeViewHolder extends RecyclerView.ViewHolder{

        TextView notice_sender,notice_content,notice_date;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            notice_sender = itemView.findViewById(R.id.notice_sender_id);
            notice_content = itemView.findViewById(R.id.notice_content);
            notice_date = itemView.findViewById(R.id.notice_date);
        }
    }
    //viewholder


}
