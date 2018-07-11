package com.tuanbapk.banrau.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tuanbapk.banrau.Model.Tygia;

import java.util.ArrayList;

/**
 * Created by buituan on 2017-12-16.
 */

public class AdapterTygia extends ArrayAdapter<Tygia>{
    Context context;
    ArrayList<Tygia> arr;
    int i;
    LayoutInflater inflater;

    public AdapterTygia(@NonNull Context context, int resource, @NonNull ArrayList<Tygia> objects) {
        super(context, resource, objects);

        this.context = context;
        this.arr = objects;
        this.i = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        MyHolderTyGia myHolderTyGia;
        if (view == null){
            inflater = LayoutInflater.from(context);
            view = inflater.inflate(i,null);
            myHolderTyGia = new MyHolderTyGia(view);
            view.setTag(myHolderTyGia);
        }else {
            myHolderTyGia = (MyHolderTyGia)view.getTag();
        }

        Tygia tygia = arr.get(position);

        myHolderTyGia.imgHinh.setImageBitmap(tygia.getBitmap());
        myHolderTyGia.txtCo.setText(tygia.getType());
        myHolderTyGia.txtBanCK.setText(tygia.getBanck());
        myHolderTyGia.txtMuaCK.setText(tygia.getMuack());
        myHolderTyGia.txtBanTM.setText(tygia.getBantienmat());
        myHolderTyGia.txtMuaTM.setText(tygia.getMuatienmat());

       return  view;
    }
}
