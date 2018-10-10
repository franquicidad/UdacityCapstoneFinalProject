package com.example.mac.udacitycapstonefinalproject.Widget;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.mac.udacitycapstonefinalproject.CarroNuevoFragment;
import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;

import java.util.ArrayList;
import java.util.List;

public class ListService extends IntentService {

    ArrayList<Automoviles> serviceArrayAutos=new ArrayList<>();

    public String LIST_SERVICE_MAIN=CarroNuevoFragment.LIST_SERVICE;

    public ListService() {
        super("LIST_SERVICE_MAIN");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent !=null){
            serviceArrayAutos=intent.getParcelableArrayListExtra(LIST_SERVICE_MAIN);
            updateList(serviceArrayAutos);
        }
    }
    public void updateList( List<Automoviles> serviceArrayAutos){


        Intent intent=new Intent();
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putParcelableArrayListExtra(LIST_SERVICE_MAIN,(ArrayList<Automoviles>)serviceArrayAutos);
        sendBroadcast(intent);


    }

}
