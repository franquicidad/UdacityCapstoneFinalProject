package com.example.mac.udacitycapstonefinalproject.Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
public class Automoviles implements Parcelable {


    private String objectId,
            marca,
            placa,
            referencia,
            color_vehiculo,
            imagen,sucursal,
            motor;
    private int modelo,
            precio,
            chasis,
            kilometraje;


    private List<Automoviles> lista_de_automoviles=null;

    public Automoviles(){

    }


    public Automoviles(String marca , String placa, String referencia, String color_vehiculo,
                       String imagen, int modelo, int precio, int chasis, int kilometraje, String motor,
                       String sucursal) {

        this.marca = marca;
        this.placa = placa;
        this.referencia = referencia;
        this.color_vehiculo = color_vehiculo;

        this.imagen = imagen;
        this.modelo = modelo;
        this.precio = precio;
        this.chasis = chasis;
        this.kilometraje = kilometraje;
        this.motor = motor;
        this.sucursal=sucursal;

    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor_vehiculo() {
        return color_vehiculo;
    }

    public void setColor_vehiculo(String color_vehiculo) {
        this.color_vehiculo = color_vehiculo;
    }



    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getChasis() {
        return chasis;
    }

    public void setChasis(int chasis) {
        this.chasis = chasis;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }



    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }


    public List<Automoviles> getLista_de_automoviles() {
        return lista_de_automoviles;
    }

    public void setLista_de_automoviles(List<Automoviles> lista_de_automoviles) {
        this.lista_de_automoviles = lista_de_automoviles;
    }
    //
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeDouble(this.quantity);
//        parcel.writeString(this.measure);
//        parcel.writeString(this.ingredient);
//
//    }
//    protected Ingredient(Parcel in){
//        this.quantity= in.readDouble();
//        this.measure=in.readString();
//        this.ingredient=in.readString();
//    }
//
//
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(objectId);
        parcel.writeString(marca);
        parcel.writeString(placa);
        parcel.writeString(referencia);
        parcel.writeString(color_vehiculo);
        parcel.writeString(imagen);
        parcel.writeString(sucursal);
        parcel.writeInt(modelo);
        parcel.writeInt(precio);
        parcel.writeInt(chasis);
        parcel.writeInt(kilometraje);
        parcel.writeString(motor);

    }

    protected Automoviles(Parcel in) {
        this.objectId = in.readString();
        this.marca = in.readString();
        this.placa = in.readString();
        this.referencia = in.readString();
        this.color_vehiculo = in.readString();
        this.imagen = in.readString();
        this.sucursal = in.readString();
        this.modelo = in.readInt();
        this.precio = in.readInt();
        this.chasis = in.readInt();
        this.kilometraje = in.readInt();
        this.motor = in.readString();

    }

    public static final Parcelable.Creator<Automoviles> CREATOR=new Parcelable.Creator<Automoviles>(){

        @Override
        public Automoviles createFromParcel(Parcel source) {
            return new Automoviles(source);
        }

        @Override
        public Automoviles[] newArray(int size) {
            return new Automoviles[size];
        }
    };

//
//    public void setData(DataSnapshot snapshot){
//        snapshot.getValue().
//
//    }




}
