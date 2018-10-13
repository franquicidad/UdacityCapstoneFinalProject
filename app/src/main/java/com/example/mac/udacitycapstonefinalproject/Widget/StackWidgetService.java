package com.example.mac.udacitycapstonefinalproject.Widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.target.ViewTarget;
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
    static List<Automoviles> mArraylistAutomoviles;


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetService(this.getApplicationContext()));
    }


 class WidgetService implements RemoteViewsService.RemoteViewsFactory {
    SvAutomoviles svAutomoviles;

     FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    Context mContext;
    private Context context;
    private int appWidgetId;
    String marca;
    String imagen;
    Intent intent;
    Automoviles automoviles;


    public WidgetService(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
//        handleActionUpdateCarWidgets();

            mArraylistAutomoviles = CarWidgetProvider.automovilesArrayList;



    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if(mArraylistAutomoviles !=null){
            return mArraylistAutomoviles.size();
        }else {
            return 0;
        }
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Automoviles autoService=mArraylistAutomoviles.get(position);
        String imagen=autoService.getImagen();
        String marca =autoService.getMarca();


//        Bitmap imageBitmap=Glide.with(context).asBitmap().load(imagen).get();

//        int imagenInt= Integer.parseInt(imagen);

        // Update the plant image
        views.setImageViewResource(R.id.widget_image_item, R.drawable.mazda2azul);

        views.setTextViewText(R.id.textView,marca);


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

    }

}

