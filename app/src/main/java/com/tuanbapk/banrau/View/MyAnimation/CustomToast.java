package com.tuanbapk.banrau.View.MyAnimation;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.tuanbapk.banrau.R;

/**
 * Created by buituan on 2017-10-29.
 */

public class CustomToast {

    Context c;
     public CustomToast(Context c){
        this.c = c;
    }

    public void Customtoastload(String str){
        StyleableToast toatload = new StyleableToast(c,str, Toast.LENGTH_LONG);
        toatload.setBackgroundColor(Color.parseColor("#1CBA00"));
        toatload.setTextColor(Color.WHITE);
        toatload.setIcon(R.drawable.icon_load);
        toatload.spinIcon();
        toatload.show();
    }
    public void Customtoastsuccess(String str){
        StyleableToast toatload = new StyleableToast(c,str,Toast.LENGTH_SHORT);
        toatload.setBackgroundColor(Color.parseColor("#1CBA00"));
        toatload.setTextColor(Color.WHITE);
        toatload.setIcon(R.drawable.icon_succses);
        toatload.show();
    }
    public void Customtoastfail(String str){
        StyleableToast toatload = new StyleableToast(c,str,Toast.LENGTH_SHORT);
        toatload.setBackgroundColor(Color.parseColor("#fc7859"));
        toatload.setTextColor(Color.WHITE);
        toatload.setIcon(R.drawable.icon_failled);
        toatload.show();
    }
    public void Customtoastsuccess1(String s){
        StyleableToast toatload = new StyleableToast(c,s,Toast.LENGTH_SHORT);
        toatload.setBackgroundColor(Color.parseColor("#ffac3f"));
        toatload.setTextColor(Color.WHITE);
        toatload.setIcon(R.drawable.icon_succses);
        toatload.show();
    }
    public void Customtoastloadfacegoogle(String str){
        StyleableToast toatload = new StyleableToast(c,str, Toast.LENGTH_LONG);
        toatload.setBackgroundColor(Color.parseColor("#FC941D"));
        toatload.setTextColor(Color.WHITE);
        toatload.setIcon(R.drawable.icon_load);
        toatload.spinIcon();
        toatload.show();
    }
    public void Customtoastsuccessgoogle(String s){
        StyleableToast toatload = new StyleableToast(c,s,Toast.LENGTH_SHORT);
        toatload.setBackgroundColor(Color.parseColor("#FC941D"));
        toatload.setTextColor(Color.WHITE);
        toatload.setIcon(R.drawable.icon_succses);
        toatload.show();
    }

    public void Customtoastloadface(String str){
        StyleableToast toatload = new StyleableToast(c,str, Toast.LENGTH_LONG);
        toatload.setBackgroundColor(Color.parseColor("#4579FC"));
        toatload.setTextColor(Color.WHITE);
        toatload.setIcon(R.drawable.icon_load);
        toatload.spinIcon();
        toatload.show();
    }
    public void Customtoastsuccessface(String s){
        StyleableToast toatload = new StyleableToast(c,s,Toast.LENGTH_SHORT);
        toatload.setBackgroundColor(Color.parseColor("#4579FC"));
        toatload.setTextColor(Color.WHITE);
        toatload.setIcon(R.drawable.icon_succses);
        toatload.show();
    }

    public void Customtoastsuccessvoicesearch(String s){
        StyleableToast toatload = new StyleableToast(c,s,Toast.LENGTH_SHORT);
        toatload.setBackgroundColor(Color.parseColor("#00C638"));
        toatload.setTextColor(Color.WHITE);
        toatload.setIcon(R.drawable.ic_timkiem);
        toatload.show();
    }
}
