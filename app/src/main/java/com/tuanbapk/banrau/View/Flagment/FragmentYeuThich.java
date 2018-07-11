package com.tuanbapk.banrau.View.Flagment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tuanbapk.banrau.Presenter.FirebaseSPYeuThich;
import com.tuanbapk.banrau.Presenter.FirebaseSanPham;
import com.tuanbapk.banrau.R;

import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_diachi;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_hoten;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_sdt;
import static com.tuanbapk.banrau.View.Mydialog.DialogChonMua.arrsanphammua;

/**
 * Created by buituan on 2017-12-01.
 */

public class FragmentYeuThich extends Fragment {
    private View rootview;

    private ListView listyeuthich;
    FirebaseSPYeuThich firebaseSPYeuThich;

    private OnclickcartListener onclickcartListener;
    private Onclicthongtinuser onclicthongtinuser;

//    private AdRequest adRequest;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_yeuthich,container,false);

        // show quang cao banner
//        AdView adView = (AdView) rootview.findViewById(R.id.adView);
//        adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);


        mapped();

          firebaseSPYeuThich = new FirebaseSPYeuThich(rootview.getContext(),listyeuthich);
          firebaseSPYeuThich.LayListspyeuthich();

        return rootview;
    }

    private void mapped() {
        listyeuthich = rootview.findViewById(R.id.lv_yeuthich);

        FloatingActionButton fabgiohang = (FloatingActionButton) rootview.findViewById(R.id.fab);
        fabgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử Lí Đặt Đơn hàng Hiện Tại
                if (tk_edt_hoten.getText().toString().equals("") || tk_edt_sdt.getText().toString().equals("") || tk_edt_diachi.getText().equals("")){
                    Snackbar.make(view, getResources().getString(R.string.fcode_snack_content), Snackbar.LENGTH_LONG)
                            .setAction(getResources().getString(R.string.fcode_snack_updateinfor), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    onclicthongtinuser.clickuser();
                                }
                            }).show();
                } else if (arrsanphammua.size()==0){
                    Snackbar.make(view, getResources().getString(R.string.fcode_snack_content1), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    // Mở fagment giỏ hàng
                } else {
                    onclickcartListener.clickcart();
                }
            }
        });
    }

    public void setOnclicthongtinuser(Onclicthongtinuser onclicthongtinuser) {
        this.onclicthongtinuser = onclicthongtinuser;
    }

    public void setOnclickcartListener(OnclickcartListener onclickcartListener) {
        this.onclickcartListener = onclickcartListener;
    }

    public interface OnclickcartListener{
        void clickcart();
    }

    public interface Onclicthongtinuser{
        void clickuser();
    }
}
