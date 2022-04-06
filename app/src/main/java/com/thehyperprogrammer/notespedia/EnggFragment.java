package com.thehyperprogrammer.notespedia;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class EnggFragment extends Fragment {

    View view;
    RecyclerView recyclerView;

    public EnggFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_poly, container, false);
        recyclerView = view.findViewById(R.id.college_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RetriveData();
        return view;
    }
    private void RetriveData(){

        FirebaseRecyclerOptions<college_model> options =
                new FirebaseRecyclerOptions.Builder<college_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                        .child("COLLEGES")
                                        .child("ENGINEERING")
                                ,college_model.class)
                        .build();

        //////////

        FirebaseRecyclerAdapter<college_model, CollegeView> adapter
                = new FirebaseRecyclerAdapter<college_model, CollegeView>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CollegeView holder, final int position, @NonNull final college_model model) {

                holder.CLG_NAME.setText(model.getCLG_NAME());
                holder.CLG_DEPT.setText(model.getCLG_DEPT());
                holder.CLG_EMAIL.setText(model.getCLG_EMAIL());
                holder.CLG_PH.setText(model.getCLG_PH());
                holder.CLG_PLACE.setText(model.getCLG_PLACE());
                holder.CLG_RATING.setText(model.getCLG_RATING());
                holder.CLG_URL.setText(model.getCLG_URL());

                Glide.with(getActivity())
                        .load(model.getCLG_IMG_URL())
                        .into(holder.CLG_IMG_URL);

            }

            @NonNull
            @Override
            public CollegeView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.college_item, parent, false);
                CollegeView viewHolder = new CollegeView(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        /////////

    }


    public static class CollegeView extends RecyclerView.ViewHolder{

        TextView CLG_NAME,CLG_URL,CLG_PH,CLG_EMAIL, CLG_PLACE,CLG_DEPT,CLG_RATING;
        ImageView CLG_IMG_URL;
        public CollegeView(@NonNull View itemView) {
            super(itemView);


            CLG_NAME = itemView.findViewById(R.id.clg_name_id);
            CLG_PH = itemView.findViewById(R.id.clg_ph_id);
            CLG_EMAIL = itemView.findViewById(R.id.clg_email_id);
            CLG_PLACE = itemView.findViewById(R.id.clg_place_id);
            CLG_URL = itemView.findViewById(R.id.clg_url_id);
            CLG_RATING = itemView.findViewById(R.id.clg_rating_id);
            CLG_DEPT = itemView.findViewById(R.id.clg_dept_id);
            CLG_IMG_URL = itemView.findViewById(R.id.clg_pic);

        }
    }

}
