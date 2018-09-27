package com.example.mac.udacitycapstonefinalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminAddCar extends AppCompatActivity{

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

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;

    String uId;



    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    private static final int RC_PHOTO_PICKER = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_layout);
        ButterKnife.bind(this);

        /**
         * Implement the photo picker
         */
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference().child("carUploads");










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
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == RC_PHOTO_PICKER && resultCode==RESULT_OK){
//
//            Uri selectedImageUri=data.getData();
//            Log.i("ImageUri","This is the ImageUri ----------------------->:"+selectedImageUri);
//            final StorageReference photoRef=storageReference.child(selectedImageUri.getLastPathSegment());
//            photoRef.putFile(selectedImageUri).addOnSuccessListener(
//                    this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
//                            while (!urlTask.isSuccessful());
//                            Uri downloadUrl = urlTask.getResult();
//                            Automoviles autos=new Automoviles(null , mUsername , downloadUrl.toString());
//                            mMessagesDatabaseReference.push().setValue(autos);
//
//                        }
//                    });
//        }
    }

    public void Guardar(View view){

        String marca=edMarca.getText().toString().trim();
        int chasis= Integer.parseInt(edChasis.getText().toString().trim());
        String color=edColor.getText().toString().trim();
        int kilometraje=Integer.parseInt(edKilometraje.getText().toString().trim());
        int modelo=Integer.parseInt(edModelo.getText().toString().trim());
        String placa= edPlaca.getText().toString().trim();
        String motor=edMotor.getText().toString().trim();
        int precio=Integer.parseInt(edPrecio.getText().toString().trim());
        String referencia=edReferencia.getText().toString().trim();
        String sucursal= edSucursal.getText().toString().trim();

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        uId=firebaseUser.getUid();



        Automoviles autosAdmin=new Automoviles(uId,marca,placa,referencia,color,imagen,
                modelo,precio,chasis,kilometraje,motor,sucursal);


    }
}


