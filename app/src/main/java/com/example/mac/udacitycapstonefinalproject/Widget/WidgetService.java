package com.example.mac.udacitycapstonefinalproject.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mac.udacitycapstonefinalproject.AdapterViewCars;
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

public class WidgetService extends RemoteViewsService {
    SvAutomoviles svAutomoviles;
    List<Automoviles> mArraylistAutomoviles;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;



    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetItemFactory(getApplicationContext(),intent);
    }

    class WidgetItemFactory implements RemoteViewsFactory{
        private Context context;
        private int appWidgetId;
        String marca;
        String imagen;
        List<Automoviles> mAutos;
        Automoviles automoviles;

        WidgetItemFactory(Context context,Intent intent){
            this.context=context;
            this.appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {

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
                    AdapterViewCars adapterViewCars=new AdapterViewCars(getApplicationContext(), automoviles.getLista_de_automoviles());


                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mArraylistAutomoviles.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views=new RemoteViews(context.getPackageName(), R.layout.widget_item);
            views.setTextViewText(R.id.textView, (CharSequence) mArraylistAutomoviles.get(position));

            mAutos=new ArrayList<>();

           Automoviles autos= mArraylistAutomoviles.get(position);

           Intent intent=new Intent();
           automoviles=intent.getExtras().getParcelable("widget_image");
           String autoImagen=automoviles.getImagen();
           Log.i("Image","This is the image in the widget---->: "+autoImagen);


           String image=autos.getImagen();

           Intent imageIntent=new Intent();
           imageIntent.putExtra("widget_image",image);


           return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
