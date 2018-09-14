package com.example.mac.udacitycapstonefinalproject.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String cedula;
    public String telefono;
    public String direccion;

    public User() {
    }

    public User(String cedula, String telefono, String direccion) {
        this.cedula = cedula;
        this.telefono = telefono;
        this.direccion = direccion;
    }
}
