package com.tuanbapk.banrau.Presenter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.tuanbapk.banrau.View.Activity.MainDangNhap;
import com.tuanbapk.banrau.View.MyAnimation.CustomToast;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import static com.tuanbapk.banrau.View.Flagment.FragmentDangnhap.btn_dangnhap;

/**
 * Created by buituan on 2017-11-01.
 */

public class Broadcast extends BroadcastReceiver {
    CustomToast customToast;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        // đặt biến kiểm tra tình trạng kết nối mạng
        boolean wifiinfor = networkInfo.isConnected();

        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConect = networkInfo.isConnected();

        customToast = new CustomToast(context);

        // Check cho wifi
     //   customToast.Customtoastload("Đang kiểm tra mạng...");
        if (btn_dangnhap!=null){
            if (wifiinfor){
                //   customToast.Customtoastsuccess("Có Kết Nối mạng");
                btn_dangnhap.setEnabled(true);
            } else {
                //      customToast.Customtoastfail("Không Có Kết Nối Mạng Hiện Tại");
                btn_dangnhap.setEnabled(false);
            }
        }
        // hàm viết cho mạng di động

    }
}
