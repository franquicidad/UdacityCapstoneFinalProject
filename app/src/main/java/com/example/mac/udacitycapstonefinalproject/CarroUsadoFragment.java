package com.example.mac.udacitycapstonefinalproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class CarroUsadoFragment extends Fragment {

    RecyclerView carroUsado_rv;
    List<Automoviles> carroUsadosArray;
    GridLayoutManager layoutManager;

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


        AdapterViewCars adapterViewCars=new AdapterViewCars(getContext(),carroUsadosArray);
        layoutManager = new GridLayoutManager(getContext(), 2);
        carroUsado_rv.setHasFixedSize(true);
        carroUsado_rv.setLayoutManager(layoutManager);
        carroUsado_rv.setAdapter(adapterViewCars);

        return view;
    }
}
