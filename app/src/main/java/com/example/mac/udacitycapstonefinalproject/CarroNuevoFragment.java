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

     @BindView(R.id.recyclerview)
     RecyclerView recyclerView;

     public CarroNuevoFragment() {
         // Required empty public constructor
     }


     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.recycler_view, container, false);
         ButterKnife.bind(this, view);

         mArraylistAutomoviles=new ArrayList<>();


         layoutManager = new GridLayoutManager(getContext(), 2);
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(layoutManager);



         /**
          * Traer datos de Firebase
          */

         mDatabaseReference=FirebaseDatabase.getInstance().getReference();

         mDatabaseReference.child("results").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 mArraylistAutomoviles.clear();
                 if(dataSnapshot.exists()){
                     for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                         Automoviles automoviles=snapshot.getValue(Automoviles.class);
                         Log.d("Objeto","Estos serian los valores:"+automoviles.getMarca());
                         mArraylistAutomoviles.add(automoviles.getMarca());

                         
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
