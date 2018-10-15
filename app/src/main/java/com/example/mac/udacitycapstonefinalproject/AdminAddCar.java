package com.example.mac.udacitycapstonefinalproject;

import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminAddCar extends AppCompatActivity {

    @BindView(R.id.image_add_admin_layout)
    ImageView imageAddAdminLayout;
    @BindView(R.id.add_car_photo_admin)
    ImageButton addCarPhotoAdmin;
    @BindView(R.id.ed_marca)
    EditText edMarca;
    @BindView(R.id.ed_chasis)
    EditText edChasis;
    @BindView(R.id.ed_color)
    EditText edColor;
    @BindView(R.id.ed_kilometraje)
    EditText edKilometraje;
    @BindView(R.id.ed_modelo)
    EditText edModelo;
    @BindView(R.id.ed_motor)
    EditText edMotor;
    @BindView(R.id.ed_placa)
    EditText edPlaca;
    @BindView(R.id.ed_precio)
    EditText edPrecio;
    @BindView(R.id.ed_referencia)
    EditText edReferencia;
    @BindView(R.id.ed_sucursal)
    EditText edSucursal;

    String uId;
    String image;
    String marca;
    String chasis;
    String color;
    String kilometraje;
    String modelo;
    String placa;
    String motor;
    String precio;
    String referencia;
    String sucursal;


    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    private static final int RC_PHOTO_PICKER = 2;
    @BindView(R.id.adm_boton_guardar)
    Button admBotonGuardar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_layout);
        ButterKnife.bind(this);

        /**
         * Implement the photo picker
         */
        databaseReference = FirebaseDatabase.getInstance().getReference("Automoviles");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("carUploads");


        addCarPhotoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

        admBotonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarAutomoviles();
                finish();
            }
        });

        TextWatcher adminTextwatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                marca = edMarca.getText().toString().trim();
                chasis = edChasis.getText().toString().trim();
                color = edColor.getText().toString().trim();
                kilometraje = edKilometraje.getText().toString().trim();
                modelo = edModelo.getText().toString().trim();
                placa = edPlaca.getText().toString().trim();
                motor = edMotor.getText().toString().trim();
                precio = edPrecio.getText().toString().trim();
                referencia = edReferencia.getText().toString().trim();
                sucursal = edSucursal.getText().toString().trim();


                if (!chasis.isEmpty()
                        && !color.isEmpty()
                        && !kilometraje.isEmpty()
                        &&!modelo.isEmpty()
                        && !placa.isEmpty()
                        && !motor.isEmpty()
                        &&!precio.isEmpty()
                        && !referencia.isEmpty()
                        && !sucursal.isEmpty()) {
                    admBotonGuardar.setVisibility(View.VISIBLE);
                }else{
                    admBotonGuardar.setVisibility(View.GONE);
                }


            }




            @Override
            public void afterTextChanged(Editable s) {

            }
        };


        edMarca.addTextChangedListener(adminTextwatcher);
        edChasis.addTextChangedListener(adminTextwatcher);
        edColor.addTextChangedListener(adminTextwatcher);
        edKilometraje.addTextChangedListener(adminTextwatcher);
        edModelo.addTextChangedListener(adminTextwatcher);
        edPlaca.addTextChangedListener(adminTextwatcher);
        edMotor.addTextChangedListener(adminTextwatcher);
        edPrecio.addTextChangedListener(adminTextwatcher);
        edReferencia.addTextChangedListener(adminTextwatcher);
        edSucursal.addTextChangedListener(adminTextwatcher);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {

            Uri selectedImageUri = data.getData();
            Log.i("ImageUri", "This is the ImageUri ----------------------->:" + selectedImageUri);
            final StorageReference photoRef = storageReference.child(selectedImageUri.getLastPathSegment());
            photoRef.putFile(selectedImageUri).addOnSuccessListener(
                    this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful()) ;
                            final Uri downloadUrl = urlTask.getResult();
                            image = downloadUrl.toString();
                            Log.i("TAG", "This is the image:---->" + image);

                        }
                    });
        }
    }

    public void GuardarAutomoviles() {

//        if(image!=null) {

        String objMarca = edMarca.getText().toString().trim();
        int objChasis = Integer.parseInt(edChasis.getText().toString().trim());
        String objColor = edColor.getText().toString().trim();
        int objKilometraje = Integer.parseInt(edKilometraje.getText().toString().trim());
        int objModelo = Integer.parseInt(edModelo.getText().toString().trim());
        String objPlaca = edPlaca.getText().toString().trim();
        String objMotor = edMotor.getText().toString().trim();
        int objPrecio = Integer.parseInt(edPrecio.getText().toString().trim());
        String objReferencia = edReferencia.getText().toString().trim();
        String objSucursal = edSucursal.getText().toString().trim();











            Automoviles autosAdmin = new Automoviles(objMarca, objPlaca, objReferencia, objColor, image,
                    objModelo, objPrecio, objChasis, objKilometraje, objMotor, objSucursal);


            databaseReference.push().setValue(autosAdmin);
            String key = databaseReference.getKey();
            Toast.makeText(this, "save ok", Toast.LENGTH_SHORT).show();
        }





    }




