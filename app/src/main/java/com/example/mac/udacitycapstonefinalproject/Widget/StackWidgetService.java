package com.example.mac.udacitycapstonefinalproject.Widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mac.udacitycapstonefinalproject.AdapterViewCars;
import com.example.mac.udacitycapstonefinalproject.CarroNuevoFragment;
import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;
import com.example.mac.udacitycapstonefinalproject.R;
import com.example.mac.udacitycapstonefinalproject.Service.SvAutomoviles;
import com.example.mac.udacitycapstonefinalproject.Util.AppModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StackWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetService(this.getApplicationContext(),intent));
    }


 class WidgetService implements RemoteViewsService.RemoteViewsFactory {
    SvAutomoviles svAutomoviles;
     List<Automoviles> mArraylistAutomoviles;

     FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    Context mContext;
    private Context context;
    private int appWidgetId;
    String marca;
    String imagen;
    Intent intent;
    Automoviles automoviles;


    public WidgetService(Context applicationContext,Intent intent) {
        this.intent=intent;

        mContext = applicationContext;

    }

    @Override
    public void onCreate() {

        mArraylistAutomoviles=intent.getParcelableArrayListExtra(CarroNuevoFragment.LIST_SERVICE);

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
//        handleActionUpdateCarWidgets();

        if(intent !=null) {
            mArraylistAutomoviles = intent.getParcelableArrayListExtra(CarroNuevoFragment.LIST_SERVICE);
        }


    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return mArraylistAutomoviles.size();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.car_widget);
        Automoviles autoService=mArraylistAutomoviles.get(position);
        String imagen=autoService.getImagen();
        String marca =autoService.getMarca();

        int imagenInt= Integer.parseInt(imagen);

        // Update the plant image
        views.setImageViewResource(R.id.widget_image_item, imagenInt);

        views.setTextViewText(R.id.textView,marca);

//        // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
//        Bundle extras = new Bundle();
//        extras.putLong(PlantDetailActivity.EXTRA_PLANT_ID , plantId);
//        Intent fillInIntent = new Intent();
//        fillInIntent.putExtras(extras);
//        views.setOnClickFillInIntent(R.id.widget_plant_image , fillInIntent);

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public  void handleActionUpdateCarWidgets(){
        mFirebaseDatabase=FirebaseDatabase.getInstance();

        mDatabaseReference=FirebaseDatabase.getInstance().getReference();

        mArraylistAutomoviles=new ArrayList<>();


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


                mArraylistAutomoviles=automoviles.getLista_de_automoviles();
                marca=automoviles.getMarca();
                imagen=automoviles.getImagen();

                /**
                 * Then you get the arraylist in the adapters constructor which receives as parameters
                 * AdapterViewCars(context,List<Automoviles)
                 */

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
        int[] appWidgetIds=appWidgetManager.getAppWidgetIds(new ComponentName(context,CarWidgetProvider.class));
//        CarWidgetProvider carWidgetProvider=new CarWidgetProvider();
//        carWidgetProvider.updateCarWidgets(context,appWidgetManager,appWidgetIds);

    }
}
}

