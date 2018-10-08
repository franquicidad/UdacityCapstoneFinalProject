package com.example.mac.udacitycapstonefinalproject.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mac.udacitycapstonefinalproject.MainActivity;
import com.example.mac.udacitycapstonefinalproject.R;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link CarWidgetConfigureActivity CarWidgetConfigureActivity}
 */
public class CarWidget extends AppWidgetProvider {

    String image="";



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = CarWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.car_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);


        image = intent.getStringExtra("widget_image");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; ++i) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.car_widget);

            // set intent for widget service that will create the views
            Intent serviceIntent = new Intent(context, WidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))); // embed extras so they don't get ignored
            remoteViews.setRemoteAdapter(appWidgetIds[i], R.id.widget_stack_view, serviceIntent);

            // set intent for item click (opens main activity)
            Intent viewIntent = new Intent(context, MainActivity.class);
            viewIntent.setAction(MainActivity.ACTIVITY_SERVICE);
            viewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            viewIntent.setData(Uri.parse(viewIntent.toUri(Intent.URI_INTENT_SCHEME)));

            PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent, 0);
            remoteViews.setPendingIntentTemplate(R.id.widget_stack_view
                    , viewPendingIntent);

            // update widget
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);

            AppWidgetTarget awt = new AppWidgetTarget(context, R.id.widget_image_item, remoteViews, appWidgetIds) {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    super.onResourceReady(resource, transition);
                }
            };

            RequestOptions options = new RequestOptions().
                    override(300, 300).placeholder(R.drawable.carapp_logo).error(R.drawable.carapp_logo);

            Glide.with(context.getApplicationContext())
                    .asBitmap()
                    .load(image)
                    .apply(options)
                    .into(awt);

        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
//        for (int appWidgetId : appWidgetIds) {
//            CarWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
//        }
        super.onDeleted(context,appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        super.onDisabled(context);
    }

//    @Override
//    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
//        RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.car_widget);
//
//        int minWidth=newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
//        int maxWidth=newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
//        int minHeight=newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
//        int maxHeight=newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
//
//        if(maxHeight>100){
//            remoteViews.setViewVisibility(R.id.widget_text, View.VISIBLE);
//        }else{
//            remoteViews.setViewVisibility(R.id.widget_text,View.GONE);
//        }
//
//        appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
//    }
//    private void resizeWidget(Bundle appWidgetOptions, RemoteViews views) {
//        int minWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
//        int maxWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
//        int minHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
//        int maxHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
//
//        if (maxHeight > 100) {
//            views.setViewVisibility(R.id.widget_text, View.VISIBLE);
//        } else {
//            views.setViewVisibility(R.id.widget_text, View.GONE);
//        }
//    }
}

