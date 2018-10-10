package com.example.mac.udacitycapstonefinalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;
import com.example.mac.udacitycapstonefinalproject.Model.Favorites;
import com.example.mac.udacitycapstonefinalproject.Model.Users;
import com.example.mac.udacitycapstonefinalproject.Service.SvAutomoviles;
import com.example.mac.udacitycapstonefinalproject.Util.AppModel;
import com.example.mac.udacitycapstonefinalproject.Widget.ListService;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mac.udacitycapstonefinalproject.CarroNuevoFragment.LIST_SERVICE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Firebase related Global variables
     */
    String mUsername;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private ChildEventListener mChildEventListener;
    private static final int RC_SIGN_IN = 123;
    public static final String ANONYMOUS = "anonymous";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;

    String UID;
    boolean isAdm;

    List<Automoviles> automovilesArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /**
         * Googles User Authentication Process
         */

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mStorageReference= FirebaseStorage.getInstance().getReference("CarImages");



        //TODO: Mirar como hacer para subir a la base de datos desde aca obtener el UID y subir con getDisplayName y el resto de cosas.



        ViewPager pager1=(ViewPager)findViewById(R.id.pager) ;

        TabPagerAdapter adapter=new TabPagerAdapter(getSupportFragmentManager());
        pager1.setAdapter(adapter);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    UID=user.getUid();
                    onSignedInInitialize(user.getDisplayName());
                    ValidateUser();
                } else {
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Arrays.asList(

                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.FacebookBuilder().build()

                                    ))
                                    .build(),
                            RC_SIGN_IN);
                }

            }
        };


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView dwUsuario = (TextView) headerView.findViewById(R.id.drawer_usuario);
        TextView dwCorreo = (TextView) headerView.findViewById(R.id.drawer_correo);
        ImageView imgdwavatar = (ImageView) headerView.findViewById(R.id.imgdwavatar);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            dwUsuario.setText(name);


            Glide.with(this).load(photoUrl).into(imgdwavatar);
            dwCorreo.setText(email);


        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddCar=new Intent(getApplicationContext(),AdminAddCar.class);
                startActivity(intentAddCar);


            }
        });


//        databaseTest();


//        Intent serviceIntentWidget=new Intent(this, ListService.class);
//        serviceIntentWidget.putParcelableArrayListExtra(LIST_SERVICE,(ArrayList<Automoviles>) automovilesArrayList);
//        this.startService(serviceIntentWidget);

    }

    private void ValidateUser() {
        Query query = mDatabaseReference.orderByKey().equalTo(mFirebaseAuth.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() ==null) {
                    AddUserProfile();
                }else {
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {


                        boolean isAdm = ds.child("isAdmin").getValue(Boolean.class);
                        if (isAdm) {
                            fab.show();
                        } else {
                            fab.hide();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void AddUserProfile() {

    mDatabaseReference.child(mFirebaseAuth.getUid()).child("name").setValue(mFirebaseAuth.getCurrentUser().getDisplayName());

    mDatabaseReference.child(mFirebaseAuth.getUid()).child("isAdmin").setValue(false);

    }


    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
    }

    private void onSignedInInitialize(String displayName) {
        mUsername = displayName;
        attachDatabaseReadListener();
    }

    private void attachDatabaseReadListener() {

        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            };
            //mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_out) {
            AuthUI.getInstance().signOut(this);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (requestCode == RESULT_OK) {
                Toast.makeText(this, "Signed In", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in cancellled", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseReadListener();
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            //mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }




    }

    public void databaseTest(){
        mFirebaseDatabase=FirebaseDatabase.getInstance();

        mDatabaseReference=FirebaseDatabase.getInstance().getReference();
        automovilesArrayList=new ArrayList<>();

        Query query = mDatabaseReference.child(AppModel.Automoviles);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Automoviles automoviles = new Automoviles();

                SvAutomoviles svAutomoviles=new SvAutomoviles();

                /**
                 * Here you Create another Automoviles object to setLista_de_automoviles
                 *
                 */
                automoviles.setLista_de_automoviles(svAutomoviles.GetAutomoviles(dataSnapshot));


                automovilesArrayList= automoviles.getLista_de_automoviles();

                /**
                 * Then you get the arraylist in the adapters constructor which receives as parameters
                 * AdapterViewCars(context,List<Automoviles)
                 */

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}