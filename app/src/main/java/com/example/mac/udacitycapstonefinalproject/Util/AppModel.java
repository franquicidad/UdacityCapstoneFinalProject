package com.example.mac.udacitycapstonefinalproject.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppModel {
    public  static String Automoviles="Automoviles";
    public  static String Favorites="Favorites";

    public  static  String GetDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  sdf.format(new Date());
    }
}
