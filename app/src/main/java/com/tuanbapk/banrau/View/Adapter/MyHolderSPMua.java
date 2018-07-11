package com.tuanbapk.banrau.View.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuanbapk.banrau.R;

/**
 * Created by buituan on 2017-10-31.
 */

public class MyHolderSPMua {
    LinearLayout mua_layout_sanpham;
    TextView mua_tv_tensp,mua_tv_soluongsp,mua_tv_thanhtiensp;
    ImageView mua_img_hinhsp,mua_img_deletespmua;

    public MyHolderSPMua(View v){
        mua_layout_sanpham = (LinearLayout) v.findViewById(R.id.mua_layout_sanpham);
        mua_tv_tensp = (TextView) v.findViewById(R.id.mua_tv_tensp);
        mua_tv_soluongsp = (TextView) v.findViewById(R.id.mua_tv_soluongsp);
        mua_tv_thanhtiensp = (TextView) v.findViewById(R.id.mua_tv_thanhtiensp);
        mua_img_hinhsp = (ImageView) v.findViewById(R.id.mua_img_hinhsp);
        mua_img_deletespmua = (ImageView) v.findViewById(R.id.mua_img_deletespmua);
    }
}
