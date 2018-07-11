package com.tuanbapk.banrau.View.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.tuanbapk.banrau.Model.SanPham;
import com.tuanbapk.banrau.Presenter.FirebaseSPYeuThich;
import com.tuanbapk.banrau.Presenter.FirebaseSanPham;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.MyAnimation.myanimation;
import com.tuanbapk.banrau.View.Mydialog.DialogChonMua;
import com.tuanbapk.banrau.View.Mydialog.DialogThongtinSanPham;

import java.util.ArrayList;

/**
 * Created by buituan on 2017-10-28.
 */

public class AdapterSanPham extends ArrayAdapter<SanPham> {

    Context c;
    int i;
    ArrayList<SanPham> arr;
    private int lastPosition = -1;

    LayoutInflater inflater;

    myanimation hieuung;
    FirebaseSPYeuThich firebaze;

    DialogThongtinSanPham dialogthongtinsp = new DialogThongtinSanPham(this);
    DialogChonMua dialogchonmua = new DialogChonMua(this);


    public AdapterSanPham(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<SanPham> objects) {
        super(context, resource, objects);

        this.c = context;
        this.i = resource;
        this.arr = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        final  View result;

        MyHolderSanpham holorview;
        if (view == null){
            inflater = LayoutInflater.from(c);
            view = inflater.inflate(i,null);
            holorview = new MyHolderSanpham(view);
            view.setTag(holorview);
            result = view;
        } else {
            holorview = (MyHolderSanpham)view.getTag();
            result = view;
        }

        final SanPham sanpham = arr.get(position);


        holorview.tvtensanpham.setText(sanpham.getTenSanPham());
        holorview.tvgiasanpham.setText(sanpham.getGiaSanPham()+"đ");
        holorview.tvdonvisanpham.setText("/"+sanpham.getDonvi());
        holorview.btnchonmua.setText("    "+c.getResources().getString(R.string.item_choosebuy));
        Glide.with(c).load(sanpham.getHinh()).into(holorview.hinhsanpham);

        Animation animation = AnimationUtils.loadAnimation(c,
                (position > lastPosition) ? R.anim.scale_down : R.anim.scale_up);
        result.startAnimation(animation);
        lastPosition = position;

        holorview.btnchonmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogchonmua.PopDialogChonMua(c,arr.get(position));
            }
        });


        holorview.layoutsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogthongtinsp.PopDialogthongtinSP(c,arr.get(position));
            }
        });


        firebaze = new FirebaseSPYeuThich(c);

        // sự kiện Thêm Yêu thích
        holorview.imgthemyeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hieuung = new myanimation();
                hieuung.makeAnimation(c,view,R.anim.anim_scale1);
                firebaze.Themyeuthich(sanpham.getIdSanPham());
            }
        });

        // Sự kiện Xóa yêu thích
        holorview.imgxoayeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hieuung = new myanimation();
                hieuung.makeAnimation(c,view,R.anim.anim_scale1);
                PopupdialogXoa(c, position,sanpham);
            }
        });


        return view;
    }


    private void PopupdialogXoa(final Context c, final int positon, final SanPham sanPham){
        final AlertDialog d = new AlertDialog.Builder(c).create();
        d.setTitle(c.getResources().getString(R.string.fcode_dialogdelete_favoritesp_title));
        d.setMessage(c.getResources().getString(R.string.fcode_dialogdelete_favoritesp_content));
        // dùng Cancel khi chạm vào vùng ngoài dialog cũng ko làm tắt dialog
        d.setCanceledOnTouchOutside(false);
        // set icon cho dialog
        d.setIcon(R.drawable.removeicon);
        d.setButton(AlertDialog.BUTTON_POSITIVE, c.getResources().getString(R.string.fcode_dialogdelete_favoritesp_btndelete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaze.Xoayeuthich(arr.get(positon).getIdSanPham());
            }
        });

        // sự kiện lắng nghe khi nhấp vào nút No
        d.setButton(AlertDialog.BUTTON_NEGATIVE, c.getResources().getString(R.string.fcode_dialogdelete_favoritesp_btnno), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                d.dismiss();
            }
        });

        d.show();
    }
}
