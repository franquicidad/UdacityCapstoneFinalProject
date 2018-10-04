package com.example.mac.udacitycapstonefinalproject.Model;

import java.util.Date;

public class Favorites {
    private String id;
   private  String automovilesId;
   private  String userId;
   private String fecha;

    public Favorites() {
    }

    public Favorites(String automovilesId, String userId, String fecha) {
        this.automovilesId = automovilesId;
        this.userId = userId;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutomovilesId() {
        return automovilesId;
    }

    public void setAutomovilesId(String automovilesId) {
        this.automovilesId = automovilesId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}


