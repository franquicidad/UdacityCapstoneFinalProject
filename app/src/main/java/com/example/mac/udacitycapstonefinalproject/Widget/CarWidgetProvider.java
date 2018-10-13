package com.example.mac.udacitycapstonefinalproject.Widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mac.udacitycapstonefinalproject.CarDetailView;
import com.example.mac.udacitycapstonefinalproject.CarroNuevoFragment;
import com.example.mac.udacitycapstonefinalproject.MainActivity;
import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;
import com.example.mac.udacitycapstonefinalproject.R;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link CarWidgetConfigureActivity CarWidgetConfigureActivity}
 */
public class CarWidgetProvider extends AppWidgetProvider {

    static ArrayList<Automoviles> automovilesArrayList;
    String image;
    String marca;
    Automoviles auto;

    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, CarWidgetProvider.class));


        if(intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            ArrayList carArrayList = intent.getParcelableArrayListExtra(CarroNuevoFragment.LIST_SERVICE);
            if(carArrayList != null) {
                automovilesArrayList= carArrayList;
            }

            if(automovilesArrayList!= null) {
                updateCarWidgets(context, appWidgetManager, appWidgetIds);
            }
        }

        super.onReceive(context, intent);
    }

     void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {

        RemoteViews rv;

            rv = getCarGridRemoteView(context);
        appWidgetManager.updateAppWidget(appWidgetId,rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Start the intent service update widget action, the service takes care of updating the widgets UI
        if(automovilesArrayList !=null) {
            updateCarWidgets(context, appWidgetManager, appWidgetIds);
        }
    }


    public void updateCarWidgets(Context context, AppWidgetManager appWidgetManager,
                                          int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_stack_view);
            updateAppWidget(context, appWidgetManager,appWidgetId);
        }
    }


    /**
     * Creates and returns the RemoteViews to be displayed in the GridView mode widget
     *
     * @param context The context
     * @return The RemoteViews for the GridView mode widget
     */
    private  RemoteViews getCarGridRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.car_widget);
        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, StackWidgetService.class);
        views.setRemoteAdapter(R.id.widget_stack_view, intent);
        // Set the PlantDetailActivity intent to launch when clicked
        Intent appIntent = new Intent(context, CarDetailView.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_stack_view, appPendingIntent);
        // Handle empty gardens
        return views;
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action when one or more AppWidget instances have been deleted
    }

    @Override
    public void onEnabled(Context context) {
        // Perform any action when an AppWidget for this provider is instantiated
    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }

}

