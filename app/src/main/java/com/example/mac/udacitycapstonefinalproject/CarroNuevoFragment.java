package com.example.mac.udacitycapstonefinalproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

 public class CarroNuevoFragment extends Fragment {


     private LinearLayoutManager layoutManager;
     List<Automoviles> mArraylistAutomoviles;
     FirebaseDatabase mFirebaseDatabase;
     DatabaseReference mDatabaseReference;
     Automoviles automoviles;

     @BindView(R.id.recyclerview)
     RecyclerView recyclerView;
     public static CarroNuevoFragment newInstance(int page, String title){
         CarroNuevoFragment firstFragment= new CarroNuevoFragment();
         return  firstFragment;
     }

     public CarroNuevoFragment() {
         // Required empty public constructor
     }


     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.recycler_view, container, false);
         ButterKnife.bind(this, view);

         recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);

         mArraylistAutomoviles=new ArrayList<Automoviles>();

         AdapterViewCars adapterViewCars=new AdapterViewCars(getContext(),mArraylistAutomoviles);
         layoutManager = new GridLayoutManager(getContext(), 2);
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(layoutManager);
         recyclerView.setAdapter(adapterViewCars);

         /**
          * Traer datos de Firebase
          */
//here
         mDatabaseReference=FirebaseDatabase.getInstance().getReference();

         mDatabaseReference.child("results").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 mArraylistAutomoviles.clear();
                 if(dataSnapshot.exists()){
                     for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                         automoviles=new Automoviles();
                         mArraylistAutomoviles.add(snapshot.getValue(Automoviles.class));


                     }
                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });



         return view;


     }



}
