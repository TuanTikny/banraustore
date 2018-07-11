package com.tuanbapk.banrau.View.Mydialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.tuanbapk.banrau.Model.Donhang;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.Adapter.AdapterDonHang;

/**
 * Created by buituan on 2017-12-06.
 */

public class DialogThongTinDonHang {

    private TextView ngay,sosanpham,trongluong,tien,trangthai;

    AdapterDonHang a;

    public DialogThongTinDonHang(AdapterDonHang a){
        this.a = a;
    }

    private void Anhxa(Dialog d){
        ngay = d.findViewById(R.id.infordh_ngaydat);
        sosanpham = d.findViewById(R.id.infordh_sosanpham);
        trongluong = d.findViewById(R.id.infordh_trongluong);
        tien = d.findViewById(R.id.infordh_tongtien);
        trangthai = d.findViewById(R.id.infordh_trangthai);
    }

    public void PopUpThongTinDonHang(Context c, Donhang donhang){
        Dialog d = new Dialog(c);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_thongtindonhang);

        Anhxa(d);

        ngay.setText(donhang.getNgaymua());
        sosanpham.setText(donhang.getSosanphammua()+" "+c.getResources().getString(R.string.fcode_dialogorder_sp));
        trongluong.setText(donhang.getSoluongkg()+" Kg");
        tien.setText(donhang.getTongtien()+" VNĐ");

        if (donhang.getTinhtrangdonhang().equals("0")){
            trangthai.setText(c.getResources().getString(R.string.fcode_dialogorder_choxacnhan));
        } else if (donhang.getTinhtrangdonhang().equals("1")){
            trangthai.setText(c.getResources().getString(R.string.fcode_dialogorder_danghchuyen));
        } else if (donhang.getTinhtrangdonhang().equals("2")){
            trangthai.setText(c.getResources().getString(R.string.fcode_dialogorder_danhan));
        } else if (donhang.getTinhtrangdonhang().equals("3")){
            trangthai.setText(c.getResources().getString(R.string.fcode_dialogorder_dahuy));
        }

        d.show();
    }
}
