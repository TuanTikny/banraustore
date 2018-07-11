package com.tuanbapk.banrau.View.Mydialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tuanbapk.banrau.Model.SanPham;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.Adapter.AdapterSanPham;

/**
 * Created by buituan on 2017-10-31.
 */

public class DialogThongtinSanPham {
    TextView thongtin_tv_tensp,thongtin_tv_giasp,thongtin_tv_donvisp,thongtin_tv_donvisp1,thongtin_tv_loaisp,thongtin_tv_motasp,thongtin_tv_soluongsp;
    ImageView thongtin_img_hinhsp;

    AdapterSanPham adapterSanPham;

    public DialogThongtinSanPham (AdapterSanPham adapterSanPham){
        this.adapterSanPham = adapterSanPham;
    }

    private void Mapped(Dialog d){
        thongtin_tv_tensp = (TextView) d.findViewById(R.id.thongtin_tv_tensp);
        thongtin_tv_giasp =(TextView)d.findViewById(R.id.thongtin_tv_giasp);
        thongtin_tv_donvisp =(TextView) d.findViewById(R.id.thongtin_tv_donvisp);
        thongtin_tv_donvisp1 =(TextView) d.findViewById(R.id.thongtin_tv_donvisp1);
        thongtin_tv_loaisp =(TextView) d.findViewById(R.id.thongtin_tv_loaisp);
        thongtin_tv_motasp =(TextView) d.findViewById(R.id.thongtin_tv_motasp);
        thongtin_tv_soluongsp =(TextView) d.findViewById(R.id.thongtin_tv_soluongsp);

        thongtin_img_hinhsp = (ImageView) d.findViewById(R.id.thongtin_img_hinhsp);
    }

    public void PopDialogthongtinSP(Context c, SanPham sanPham){
        Dialog d = new Dialog(c);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_thongtinsanpham);
        // Gọi ánh xạ
        Mapped(d);
        // Gắn thông tin
        thongtin_tv_tensp.setText(sanPham.getTenSanPham());
        thongtin_tv_giasp.setText(sanPham.getGiaSanPham()+"đ");
        thongtin_tv_donvisp.setText(" /"+sanPham.getDonvi());
        thongtin_tv_donvisp1.setText(sanPham.getDonvi());
        thongtin_tv_loaisp.setText(sanPham.getLoaiSanPham());
        thongtin_tv_soluongsp.setText(sanPham.getSoluong());
        thongtin_tv_motasp.setText(sanPham.getMoTa());

        Glide.with(c).load(sanPham.getHinh()).into(thongtin_img_hinhsp);

        d.show();
    }
}
