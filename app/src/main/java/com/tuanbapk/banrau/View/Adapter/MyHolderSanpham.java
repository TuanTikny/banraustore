package com.tuanbapk.banrau.View.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuanbapk.banrau.R;
import ru.katso.livebutton.LiveButton;

/**
 * Created by buituan on 2017-10-28.
 */

public class MyHolderSanpham {
    LinearLayout layoutsanpham;
    ImageView hinhsanpham, imgthemyeuthich, imgxoayeuthich;
    TextView tvtensanpham, tvgiasanpham, tvdonvisanpham;
    LiveButton btnchonmua;

    public MyHolderSanpham(View view){
        layoutsanpham = (LinearLayout) view.findViewById(R.id.layout_sanpham);
        hinhsanpham = (ImageView) view.findViewById(R.id.img_hinhsp);
        tvtensanpham = (TextView) view.findViewById(R.id.tv_tensp);
        tvgiasanpham = (TextView) view.findViewById(R.id.tv_giasp);
        tvdonvisanpham = (TextView)view.findViewById(R.id.tv_donvisp);
        btnchonmua = (LiveButton)view.findViewById(R.id.btn_chonmua);
        imgthemyeuthich = (ImageView) view.findViewById(R.id.img_themyeuthich);
        imgxoayeuthich = (ImageView) view.findViewById(R.id.img_xoayeuthich);
    }

}
