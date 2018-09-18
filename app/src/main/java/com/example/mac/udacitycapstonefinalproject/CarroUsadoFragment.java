package com.example.mac.udacitycapstonefinalproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;
import com.example.mac.udacitycapstonefinalproject.Service.SvAutomoviles;
import com.example.mac.udacitycapstonefinalproject.Util.AppModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class CarroUsadoFragment extends Fragment {

    RecyclerView carroUsado_rv;
    List<Automoviles> carroUsadosArray;
    GridLayoutManager layoutManager;

    private SvAutomoviles svAutomoviles;
    private static final String TAG = CarroUsadoFragment.class.getName();
    List<Automoviles> mArraylistAutomoviles;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    public CarroUsadoFragment() {
    }

    public static CarroUsadoFragment newInstance2(int page, String title){
        CarroUsadoFragment secondF= new CarroUsadoFragment();
        return  secondF;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        ButterKnife.bind(this, view);

        carroUsado_rv=(RecyclerView)view.findViewById(R.id.recyclerview);

        carroUsadosArray=new ArrayList<Automoviles>();


        mFirebaseDatabase= FirebaseDatabase.getInstance();

        mDatabaseReference=FirebaseDatabase.getInstance().getReference();
        svAutomoviles = new SvAutomoviles();

        GetCarros();

        return view;
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
                carroUsado_rv.setHasFixedSize(true);
                carroUsado_rv.setLayoutManager(layoutManager);
                carroUsado_rv.setAdapter(adapterViewCars);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
