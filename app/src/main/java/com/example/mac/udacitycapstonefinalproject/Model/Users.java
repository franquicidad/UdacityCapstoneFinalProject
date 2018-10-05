package com.example.mac.udacitycapstonefinalproject.Model;

import java.util.List;

public class Users {

    private String name;
    private boolean isAdmin;

    private List<Users> lista_de_usuarios=null;

    public Users() {
    }

    public Users(String name, boolean isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setisAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<Users> getLista_de_usuarios() {
        return lista_de_usuarios;
    }

    public void setLista_de_usuarios(List<Users> lista_de_usuarios) {
        this.lista_de_usuarios = lista_de_usuarios;
    }
}
