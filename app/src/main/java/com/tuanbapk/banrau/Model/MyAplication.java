package com.tuanbapk.banrau.Model;

import android.app.Activity;
import android.app.Application;

/**
 * Created by buituan on 2017-10-30.
 */

public class MyAplication extends Application {

    private static MyAplication instance;
    private String idnguoidung;

    @Override
    public void onCreate() {
        super.onCreate();
        instance =this;
    }

    public static MyAplication getInstance(){
        return instance;
    }


    public String getIdnguoidung() {
        return idnguoidung;
    }

    public void setIdnguoidung(String idnguoidung) {
        this.idnguoidung = idnguoidung;
    }
}
