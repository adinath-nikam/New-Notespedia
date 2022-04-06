package com.thehyperprogrammer.notespedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.LENGTH_LONG;

public class Sem_Content extends AppCompatActivity {



    RecyclerView recyclerView;
//    SyllabusAdapter adapter;
    String Dept,Sem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem__content);



        // Get Data

        Bundle bundle = getIntent().getExtras();
        Dept = bundle.getString("Dept");
        Sem = bundle.getString("Sem");

        // Get Data

        recyclerView = findViewById(R.id.sub_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<SyllabusModel> options =
                new FirebaseRecyclerOptions.Builder<SyllabusModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("SYLLABUS")
                                .child(Dept)
                                .child(Sem)
                                , SyllabusModel.class)
                                .build();

        //

        FirebaseRecyclerAdapter<SyllabusModel, Sem_Content.SyllabusViewholder> adapter
                = new FirebaseRecyclerAdapter<SyllabusModel, SyllabusViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SyllabusViewholder holder, final int position, @NonNull SyllabusModel model) {
                holder.Sub_Name.setText(model.getCHAPTER());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Sub_Key = getRef(position).getKey();

                        Toast.makeText(Sem_Content.this, "Please Wait..",LENGTH_LONG).show();
                        Intent intent = new Intent(Sem_Content.this,SyllabusView.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Dept",Dept);
                        bundle.putString("Sem",Sem);
                        bundle.putString("SubKey",Sub_Key);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public Sem_Content.SyllabusViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subjects, parent, false);
                Sem_Content.SyllabusViewholder viewHolder = new Sem_Content.SyllabusViewholder(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        //
    }

    public static class SyllabusViewholder extends RecyclerView.ViewHolder{

        TextView Sub_Name;
        public SyllabusViewholder(@NonNull View itemView) {
            super(itemView);

            Sub_Name = itemView.findViewById(R.id.sub_name);


        }
    }

}
