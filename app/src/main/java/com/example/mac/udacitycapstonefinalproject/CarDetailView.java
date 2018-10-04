package com.example.mac.udacitycapstonefinalproject;

import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;
import com.example.mac.udacitycapstonefinalproject.Model.Favorites;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarDetailView extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @BindView(R.id.imgdetalle)
    ImageView imgdetalle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    @BindView(R.id.detalle_precio)
    TextView detallePrecio;
    @BindView(R.id.detalle_referencia_carro)
    TextView detalleReferenciaCarro;
    @BindView(R.id.detalle_modelo)
    TextView detalleModelo;
    @BindView(R.id.detalle_motor)
    TextView detalleMotor;
    @BindView(R.id.lymotor)
    LinearLayout lymotor;
    @BindView(R.id.detalle_kilometraje)
    TextView detalleKilometraje;
    @BindView(R.id.lykilometraje)
    LinearLayout lykilometraje;
    @BindView(R.id.layout_transmicion)
    LinearLayout layoutTransmicion;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.car_description)
    TextView carDescription;
    @BindView(R.id.adView)
    AdView adView;
    @BindView(R.id.edittext_url)
    EditText edittextUrl;
    @BindView(R.id.button_for_pic)
    Button buttonForPic;
    @BindView(R.id.uploaded_pic)
    ImageView uploadedPic;


    private Automoviles automoviles;

    boolean favorite = false;

    public List<Favorites> favoritesList;

    public Favorites favorites;
    FirebaseUser user;
    String urlPicture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_collapsing_toolbar);
        ButterKnife.bind(this);
        user = FirebaseAuth.getInstance().getCurrentUser();

        MobileAds.initialize(this, String.valueOf(R.string.app_id_ads));

        AdView mAdView = (AdView) findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.e
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        //mAdView.setAdSize(AdSize.BANNER);
        //mAdView.setAdUnitId(String.valueOf(R.string.app_id_ads));
        mAdView.loadAd(adRequest);


        automoviles = getIntent().getExtras().getParcelable("ListaVehiculos");
        toolbar.setTitle(automoviles.getMarca());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        detalleModelo.setText(automoviles.getModelo() + "");

        Log.d("Referencias ", automoviles.getReferencia());
        detalleKilometraje.setText(automoviles.getKilometraje() + "");
        detalleMotor.setText(automoviles.getMotor() + "");
        detalleReferenciaCarro.setText(automoviles.getReferencia());
        detalleMotor.setText(automoviles.getMotor() + "");
        detallePrecio.setText("$" + automoviles.getPrecio());

        Glide.with(this).load(automoviles.getImagen()).into(imgdetalle);


        buttonForPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPicture();

            }
        });

//
//        databaseReference = FirebaseDatabase.getInstance().getReference(AppModel.Favorites);
//
//        favoritesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//                final DatabaseReference usersRef = rootRef.child("Favorites");
//                favorites = new Favorites(automoviles.getObjectId(), user.getUid(), AppModel.GetDate());
//                databaseReference.push().setValue(favorites);
//
//
//                usersRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                            String car = ds.child("automovilesId").getValue(String.class);
//                            Favorites fav =ds.getValue(Favorites.class);
//                            String carId =fav.getAutomovilesId();
//                            Log.i("No","Este es el nombre------>:"+carId);
//                            String id=ds.getKey();
//                            AddFavoritesuser();
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//        });


        final Display dwigth = getWindowManager().getDefaultDisplay();

        appbarLayout.post(new Runnable() {
            @Override
            public void run() {
                int heigthPx = dwigth.getWidth() * 1 / 3;
                setAppbarOffset(heigthPx);
            }
        });


    }
//
//    private void GetFavorites() {
//        Query query = databaseReference.orderByChild("automovilesId").equalTo(automoviles.getObjectId());
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getValue() ==null) {
//                        AddFavoritesuser();
//                } else {
//
//                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
//                        Favorites favorites =childDataSnapshot.getValue(Favorites.class);
//                        if(favorites.getUserId()==user.getUid()) {
//                            childDataSnapshot.getRef().removeValue();
//                            Log.d("Datos a volar ", childDataSnapshot.getKey());
//                        }
//
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


//    private void deleteFavoritesUser(String key) {
//        favoritesButton.setImageResource(R.drawable.ic_favorite_border);
//        databaseReference.child(key).getRef().removeValue();
//
//    }
//
//    private void AddFavoritesuser() {
//        favoritesButton.setImageResource(R.drawable.ic_favorite);
//        favorites = new Favorites(automoviles.getObjectId(), user.getUid(), AppModel.GetDate());
//        databaseReference.push().setValue(favorites);
//    }


    private void setAppbarOffset(int heigthPx) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedPreScroll(coordinatorLayout, appbarLayout, null, 0, heigthPx, new int[]{0, 0});

    }




    private void downloadPicture(){

        DownloadImagePicture downloadImagePicture=new DownloadImagePicture();
        urlPicture=edittextUrl.getText().toString().trim();

        downloadImagePicture.execute(urlPicture);

        Log.i("PIC","This is the url"+urlPicture);




    }

    private class DownloadImagePicture extends AsyncTask<String,Void,Bitmap>{

        ProgressDialog progressDialog;


        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                URL pictUrl=new URL(strings[0]);

                HttpURLConnection httpURLConnection=(HttpURLConnection)pictUrl.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                InputStream inputStream=httpURLConnection.getInputStream();

                Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
                Log.i("BIT","This is the image____________:"+bitmap);

                return bitmap;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            progressDialog.dismiss();
            Log.i("BIT","This is the image____________:"+bitmap);


            uploadedPic.setImageBitmap(bitmap);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(CarDetailView.this);
            progressDialog.setMessage("Downloading Picture...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
    }

}

