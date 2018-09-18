package com.example.mac.udacitycapstonefinalproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;
import com.example.mac.udacitycapstonefinalproject.Service.SvAutomoviles;
import com.example.mac.udacitycapstonefinalproject.Util.AppModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

 public class CarroNuevoFragment extends Fragment {

    private SvAutomoviles svAutomoviles;
     private static final String TAG = CarroUsadoFragment.class.getName();
     private LinearLayoutManager layoutManager;
     List<Automoviles> mArraylistAutomoviles;
     FirebaseDatabase mFirebaseDatabase;
     DatabaseReference mDatabaseReference;
     FirebaseAuth mAuth;
     Automoviles automoviles;
     private String userID;
     FirebaseUser user;

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
         mFirebaseDatabase=FirebaseDatabase.getInstance();

         mDatabaseReference=FirebaseDatabase.getInstance().getReference();
         svAutomoviles = new SvAutomoviles();
/*
         Automoviles automoviles=new Automoviles("Mazda",
                 "JEQ675", "Mazda2", "Red", true,
                 "https://firebasestorage.googleapis.com/v0/b/udacitycapstonefinal.appspot." +
                         "com/o/CarImages%2Fmazda2blanco.jpeg?alt=media&token=e1e58174-0f96-4c32-8fcf-650436217981",
                 2014, 50000000, 2432342, 0, "FGRE243234",
                 "AutoBlitz",true);

         mDatabaseReference.child("Automoviles").child("01").setValue(automoviles);
*/

         GetCarros();


         /**
          * Traer datos de Firebase
          */


//         user=mAuth.getCurrentUser();
//         userID=user.getUid();



         return view;

     }

     private void GetCarro(String key){
         Query query = mDatabaseReference.child(AppModel.Automoviles).child(key);
         query.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 Automoviles automoviles = svAutomoviles.GetAutomovil(dataSnapshot);

             }
             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
     }

     private void GetCarros(){
         Query query = mDatabaseReference.child(AppModel.Automoviles);
         query.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 Automoviles automoviles = new Automoviles();

                 automoviles.setLista_de_automoviles(svAutomoviles.GetAutomoviles(dataSnapshot));


                 AdapterViewCars adapterViewCars=new AdapterViewCars(getContext(), automoviles.getLista_de_automoviles());
                 layoutManager = new GridLayoutManager(getContext(), 2);
                 recyclerView.setHasFixedSize(true);
                 recyclerView.setLayoutManager(layoutManager);
                 recyclerView.setAdapter(adapterViewCars);

             }
             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
     }
 }
