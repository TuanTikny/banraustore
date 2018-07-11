package com.tuanbapk.banrau.View.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.tuanbapk.banrau.Model.SanPham;
import com.tuanbapk.banrau.Model.SanPhammua;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.MyAnimation.myanimation;

import java.util.ArrayList;

import static com.tuanbapk.banrau.View.Flagment.FragmentGiohang.giohang_tv_slsanpham;
import static com.tuanbapk.banrau.View.Flagment.FragmentGiohang.giohang_tv_tonggiasp;
import static com.tuanbapk.banrau.View.Flagment.FragmentGiohang.giohang_tv_tongsoluong;
import static com.tuanbapk.banrau.View.Mydialog.DialogChonMua.demsp;
import static com.tuanbapk.banrau.View.Mydialog.DialogChonMua.tongtien;
import static com.tuanbapk.banrau.View.Mydialog.DialogChonMua.tongtrongluong;

/**
 * Created by buituan on 2017-10-31.
 */

public class AdapterSanPhamMua extends ArrayAdapter<SanPham> {

    Context c;
    int i;
    ArrayList<SanPham> arr;
    private int lastPosition = -1;

    LayoutInflater inflater;


    public AdapterSanPhamMua(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<SanPham> objects) {
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
        final MyHolderSPMua holderSPMua;
        if (view ==null){
            inflater = LayoutInflater.from(c);
            view = inflater.inflate(i,null);
            holderSPMua = new MyHolderSPMua(view);
            view.setTag(holderSPMua);
            result = view;
        } else {
            holderSPMua = (MyHolderSPMua)view.getTag();
            result = view;
        }

//        Animation animation = AnimationUtils.loadAnimation(c,
//                (position > lastPosition) ? R.anim.scale_down : R.anim.scale_up);
//        result.startAnimation(animation);
//        lastPosition = position;

        // Mượn tạm đối tượng sản phẩm để ánh xạ sản phẩm mua
        SanPham sanPham = arr.get(position);
        holderSPMua.mua_tv_tensp.setText(sanPham.getTenSanPham());
        holderSPMua.mua_tv_soluongsp.setText(sanPham.getSoluong());
        holderSPMua.mua_tv_thanhtiensp.setText(sanPham.getGiaSanPham());
        Glide.with(c).load(sanPham.getHinh()).into(holderSPMua.mua_img_hinhsp);


        holderSPMua.mua_layout_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ShowitemMenuXoa(view,position);
            }
        });

        return view;
    }

    private void ShowitemMenuXoa(View view, final int position){
        final PopupMenu popupMenu = new PopupMenu(c,view);
        popupMenu.getMenuInflater().inflate(R.menu.item_menu_xoaspgiohang,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.giohang_item_menuxoa){
                    // Gọi dialog hỏi xóa
                }
                return false;
            }
        });
        popupMenu.show();
    }

}
