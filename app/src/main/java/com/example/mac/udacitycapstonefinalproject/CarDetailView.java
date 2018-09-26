package com.example.mac.udacitycapstonefinalproject;

import android.graphics.PorterDuff;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private Automoviles automoviles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_collapsing_toolbar);
        ButterKnife.bind(this);

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

        final Display dwigth = getWindowManager().getDefaultDisplay();

        appbarLayout.post(new Runnable() {
            @Override
            public void run() {
                int heigthPx = dwigth.getWidth() * 1 / 3;
                setAppbarOffset(heigthPx);
            }
        });
    }

    private void setAppbarOffset(int heigthPx) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedPreScroll(coordinatorLayout, appbarLayout, null, 0, heigthPx, new int[]{0, 0});

    }

}
