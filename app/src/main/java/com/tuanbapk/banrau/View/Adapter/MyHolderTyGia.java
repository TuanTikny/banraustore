package com.tuanbapk.banrau.View.Adapter;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuanbapk.banrau.R;

/**
 * Created by buituan on 2017-12-16.
 */

public class MyHolderTyGia {

    ImageView imgHinh;
    TextView txtCo,txtMuaTM,txtMuaCK,txtBanTM,txtBanCK;

    public MyHolderTyGia(View v){
        imgHinh = v.findViewById(R.id.imgHinhCo);
        txtCo = v.findViewById(R.id.txtCo);
        txtMuaTM = v.findViewById(R.id.txtMuaTM);
        txtBanTM = v.findViewById(R.id.txtBanTM);
        txtMuaCK = v.findViewById(R.id.txtMuaCK);
        txtBanCK = v.findViewById(R.id.txtBanCK);
    }

}
