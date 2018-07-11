package com.tuanbapk.banrau.View.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;

import com.tuanbapk.banrau.Model.Donhang;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.Mydialog.DialogThongTinDonHang;

import java.util.ArrayList;


/**
 * Created by buituan on 2017-11-27.
 */

public class AdapterDonHang extends ArrayAdapter<Donhang> {

    Context c;
    int i;
    ArrayList<Donhang> arrdonhang;
    private int lastPosition = -1;

    LayoutInflater inflater;
    MyHoderDonHang holderview;

    DialogThongTinDonHang d;


    public AdapterDonHang(@NonNull Context context, int resource, @NonNull ArrayList<Donhang> objects) {
        super(context, resource, objects);

        this.c = context;
        this.i = resource;
        this.arrdonhang = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        final  View result;

        if (view == null){
            inflater = LayoutInflater.from(c);
            view =inflater.inflate(i, null);
            holderview = new MyHoderDonHang(view);
            view.setTag(holderview);
            result = view;
        }else {
            holderview = (MyHoderDonHang) view.getTag();
            result = view;
        }

        d = new DialogThongTinDonHang(this);

        Animation animation = AnimationUtils.loadAnimation(c,
                (position > lastPosition) ? R.anim.scale_down : R.anim.scale_up);
        result.startAnimation(animation);
        lastPosition = position;


        final Donhang donhang = arrdonhang.get(position);
        holderview.itemdh_tvngaydat.setText(donhang.getNgaymua());
        holderview.itemdh_tvtongtien.setText(donhang.getTongtien()+" VNĐ");
        holderview.itemdh_tvsoluong.setText(donhang.getSoluongkg()+" Kg");

        if (donhang.getTinhtrangdonhang().equals("0")){
            settrangthai(c.getResources().getString(R.string.fcode_dialogorder_choxacnhan),R.drawable.ic_boxguidi,36,215,255);
        } else if (donhang.getTinhtrangdonhang().equals("1")){
            settrangthai(c.getResources().getString(R.string.fcode_dialogorder_danghchuyen),R.drawable.ic_hopgiaohang,82,255,59);
        } else if (donhang.getTinhtrangdonhang().equals("2")){
            settrangthai(c.getResources().getString(R.string.fcode_dialogorder_danhan),R.drawable.ic_hopdagiao,196,185,1);
        } else if (donhang.getTinhtrangdonhang().equals("3")){
            settrangthai(c.getResources().getString(R.string.fcode_dialogorder_dahuy),R.drawable.ic_hophuy,249,47,47);
        }


        holderview.itemdh_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.PopUpThongTinDonHang(c,donhang);
            }
        });


        return view;
    }

    private void settrangthai(String text, int Rid, int r, int g, int b){
        holderview.itemdh_imgtrangthai.setImageResource(Rid);
        holderview.itemdh_tvsoluong.setTextColor(Color.rgb(r,g,b));
    }
}
