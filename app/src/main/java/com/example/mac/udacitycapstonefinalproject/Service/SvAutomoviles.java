package com.example.mac.udacitycapstonefinalproject.Service;

import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SvAutomoviles {
    public Automoviles GetAutomovil(DataSnapshot dataSnapshot) {
        Automoviles automoviles =  dataSnapshot.getValue(Automoviles.class);
        return automoviles;
    }
    public List<Automoviles> GetAutomoviles(DataSnapshot dataSnapshot) {
        List<Automoviles> automovilesList = new ArrayList<>();
        for(DataSnapshot ds:dataSnapshot.getChildren()) {
            Automoviles automoviles = ds.getValue(Automoviles.class);
            automovilesList.add(automoviles);
        }
        return automovilesList;
    }
}
