package com.example.mac.udacitycapstonefinalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.mac.udacitycapstonefinalproject.Service.SvAutomoviles;
import com.example.mac.udacitycapstonefinalproject.Util.AppModel;
import com.example.mac.udacitycapstonefinalproject.Widget.ListService;
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
     public static final String LIST_SERVICE ="listService";
     Context context;



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

     public void GetCarros(){
         Query query = mDatabaseReference.child(AppModel.Automoviles);
         query.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 Automoviles automoviles = new Automoviles();

                 /**
                  * Here you Create another Automoviles object to setLista_de_automoviles
                  */



                 automoviles.setLista_de_automoviles(svAutomoviles.GetAutomoviles(dataSnapshot));

                 mArraylistAutomoviles=automoviles.getLista_de_automoviles();
                 String marca=automoviles.getMarca();
                 String imagen=automoviles.getImagen();

                 Automoviles xx=svAutomoviles.GetAutomovil(dataSnapshot);
                 Log.i("MArca","This is -----------------> MArca:"+marca);



                 /**
                  * Then you get the arraylist in the adapters constructor which receives as parameters
                  * AdapterViewCars(context,List<Automoviles)
                  */
                 AdapterViewCars adapterViewCars=new AdapterViewCars(getContext(), automoviles.getLista_de_automoviles());
                 layoutManager = new GridLayoutManager(getContext(), 2);
                 recyclerView.setHasFixedSize(true);
                 recyclerView.setLayoutManager(layoutManager);
                 recyclerView.setAdapter(adapterViewCars);

                 /**Here i pass the object into the Widget
                  *
                  */

                 Intent serviceIntentWidget=new Intent(getContext(), ListService.class);
                 serviceIntentWidget.putParcelableArrayListExtra(LIST_SERVICE,(ArrayList<Automoviles>) mArraylistAutomoviles);
                 getActivity().startService(serviceIntentWidget);



             }
             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
     }
 }
