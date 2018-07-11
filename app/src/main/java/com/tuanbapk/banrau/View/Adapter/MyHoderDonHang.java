package com.tuanbapk.banrau.View.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuanbapk.banrau.R;

/**
 * Created by buituan on 2017-11-28.
 */

public class MyHoderDonHang {

    LinearLayout itemdh_layout;
    TextView itemdh_tvsoluong,itemdh_tvtongtien,itemdh_tvngaydat;
    ImageView itemdh_imgtrangthai;

    public MyHoderDonHang(View view){
        itemdh_layout = (LinearLayout) view.findViewById(R.id.itemdh_layout);
        itemdh_imgtrangthai = (ImageView) view.findViewById(R.id.itemdh_imgtrangthai);
        itemdh_tvngaydat = (TextView) view.findViewById(R.id.itemdh_tvngaydat);
        itemdh_tvtongtien = (TextView) view.findViewById(R.id.itemdh_tvtongtien);
        itemdh_tvsoluong = (TextView) view.findViewById(R.id.itemdh_tvsoluongsp);
    }
}
