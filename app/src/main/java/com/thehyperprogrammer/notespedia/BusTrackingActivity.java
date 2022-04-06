package com.thehyperprogrammer.notespedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class BusTrackingActivity extends AppCompatActivity {


    private String ProfileCollege;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_tracking);

        recyclerView = findViewById(R.id.busRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        ProfileCollege = extras.getString("UserCollege");




        //
        FirebaseRecyclerOptions<BusData> options =
                new FirebaseRecyclerOptions.Builder<BusData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                        .child("BUS LOCATIONS")
                                .child(ProfileCollege)
                                ,BusData.class)
                        .build();

        FirebaseRecyclerAdapter<BusData, BusViewHolder> adapter
                = new FirebaseRecyclerAdapter<BusData, BusViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BusViewHolder holder, final int position, @NonNull final BusData model) {

                holder.BusNumber.setText(model.getBus_number());







                //

                holder.busCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String key = getRef(position).getKey();

                        Bundle bundle = new Bundle();
                        bundle.putString("college",ProfileCollege);
                        bundle.putString("busKey",key);
                        Intent intent = new Intent(BusTrackingActivity.this,BusMapView.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                //




            }

            @NonNull
            @Override
            public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_item, parent, false);
                BusViewHolder viewHolder = new BusViewHolder(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


        //



    }


    //viewholder
    public static class BusViewHolder extends RecyclerView.ViewHolder{

        TextView BusNumber;
        CardView busCardView;

        public BusViewHolder(@NonNull View itemView) {
            super(itemView);
            BusNumber = itemView.findViewById(R.id.bus_no);
            busCardView = itemView.findViewById(R.id.bus_cardView);
        }
    }
    //viewholder





}
