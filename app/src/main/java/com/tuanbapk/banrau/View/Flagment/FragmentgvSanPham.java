package com.tuanbapk.banrau.View.Flagment;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;


import com.tuanbapk.banrau.Presenter.FirebaseSanPham;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.MyAnimation.CustomToast;

import java.util.ArrayList;
import java.util.Locale;

import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_diachi;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_hoten;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_sdt;
import static com.tuanbapk.banrau.View.Mydialog.DialogChonMua.arrsanphammua;

/**
 * Created by buituan on 2017-10-28.
 */

public class FragmentgvSanPham extends Fragment {

    private View rootview;
    private OnclickButton1Listener listener;
    private OnclickgiohangListener giohanglistener;
    private OnClickThongTinCaNhan thongtintklistener;

    FirebaseSanPham firebaseconnect;
    CustomToast customToast;

    private ImageView imgiconsapxep;
    private GridView gridView;

    private android.support.v7.widget.SearchView searchView;
    private ImageView voice_timkiem;
    private final int REQ_CODE_SPEECH_OUT = 143;
    private String kqvoice ="";
    private boolean nutvoice = true;
//    private AdRequest adRequest;
//    private  AdRequest adRequest1;
//
//    private InterstitialAd fullAd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_sanphamgv,container,false);
        customToast = new CustomToast(rootview.getContext());

//        // show quang cao banner
//        AdView adView = (AdView) rootview.findViewById(R.id.adView);
//        adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//
//        adRequest1 = new AdRequest.Builder().build();
//        // show quang cao full screen
//        fullAd = new InterstitialAd(rootview.getContext());
//        fullAd.setAdListener(new AdListener(){
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//                fullAd.loadAd(adRequest1);
//            }
//        });

//        fullAd.setAdUnitId(getResources().getString(R.string.full_ad_unit_id));
//        fullAd.loadAd(adRequest1);


        // Ánh xạ
        mapped();
        // Firebase đổ dữ liệu
        firebaseconnect = new FirebaseSanPham(rootview.getContext(),gridView);
        firebaseconnect.Laydulieudogriv();


        return rootview;

    }

    private void mapped() {
        imgiconsapxep = (ImageView) rootview.findViewById(R.id.img_sapxepsp);
        searchView = rootview.findViewById(R.id.search_timkiemspgv);
        gridView = (GridView) rootview.findViewById(R.id.gv_sanpham);
        voice_timkiem = rootview.findViewById(R.id.voice_timkiemgv);
        voice_timkiem.setEnabled(true);
        voice_timkiem.setImageResource(R.drawable.bg_trangtaivoice);


        // sự kiện chuyển listview button
        imgiconsapxep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chuyển list view
                listener.clickButton();

                // show quang cao full khi dung
//                fullAd.show();
            }
        });

        voice_timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nutvoice){
                    Openvoicelen();
                    nutvoice =false;
                } else {
                    firebaseconnect.xoaadapter();
                    firebaseconnect.Laydulieudogriv();
                    nutvoice = true;
                }
            }
        });


        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseconnect.TimkiemonGrid(gridView,newText);
                return false;
            }
        });


        searchView.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                firebaseconnect.xoaadapter();
                firebaseconnect.Laydulieudogriv();
                return false;
            }
        });

        // Float giỏ hàng
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
                                    thongtintklistener.clickThongTin();
                                }
                            }).show();
                } else if (arrsanphammua.size()==0){
                    Snackbar.make(view, getResources().getString(R.string.fcode_snack_content1), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    // Mở fagment giỏ hàng
                } else {
                    giohanglistener.clickgiohang();
                }


            }
        });
    }

    private void Openvoicelen(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getResources().getString(R.string.fcode_void_talknamesp));
        try {
            startActivityForResult(intent,REQ_CODE_SPEECH_OUT);
        }catch (Exception e){

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_CODE_SPEECH_OUT:
                if (data!=null){
                    ArrayList<String> voiceArr = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    kqvoice = voiceArr.get(0);
                    customToast.Customtoastsuccessvoicesearch(kqvoice);
                    firebaseconnect.TimkiemonGrid(gridView,kqvoice);
                }
                break;
        }
    }

    // Gắn sự kiện khi click vào giỏ tại main này hàng
    public void setGiohanglistener(OnclickgiohangListener giohanglistener) {
        this.giohanglistener = giohanglistener;
    }

    public void setThongtintklistener(OnClickThongTinCaNhan thongtintklistener) {
        this.thongtintklistener = thongtintklistener;
    }

    public void setListener(OnclickButton1Listener listener) {
        this.listener = listener;
    }

    public interface OnclickButton1Listener{
        void clickButton();
    }

    public interface OnclickgiohangListener{
        void clickgiohang();
    }

    public interface OnClickThongTinCaNhan{
        void  clickThongTin();
    }

}
