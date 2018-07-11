package com.tuanbapk.banrau.View.Flagment;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.tuanbapk.banrau.Presenter.FirebaseSanPham;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.MyAnimation.CustomToast;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_diachi;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_hoten;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_sdt;
import static com.tuanbapk.banrau.View.Mydialog.DialogChonMua.arrsanphammua;

/**
 * Created by buituan on 2017-10-28.
 */

public class FragmentlvSanPham extends Fragment {

    private View rootview;
    private OnclickButton1Listener listener;
    private OnclickgiohangListener giohanglistener;
    private OnclickThongTinTKListener tklistenner;
    FirebaseSanPham firebaseconnect;
    CustomToast customToast;

    private ImageView imgiconsapxep;
    private ListView listView;

    private android.support.v7.widget.SearchView timkiem;
    private ImageView voice_timkiem;
    private final int REQ_CODE_SPEECH_OUT = 143;
    private String kqvoice ="";
    private boolean nutvoice = true;
//    private AdRequest adRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_sanphamlv,container,false);


//        AdView adView = (AdView) rootview.findViewById(R.id.adView);
//        adRequest = new AdRequest.Builder().build();
//
//        adView.loadAd(adRequest);

        // Ánh xạ
        mapped();
        customToast = new CustomToast(rootview.getContext());
        // Firebase đổ dữ liệu
        firebaseconnect = new FirebaseSanPham(rootview.getContext(),listView);
        firebaseconnect.Laydulieudolist();

        return rootview;
    }
// Hàm Ánh Xạ
    private void mapped() {
        imgiconsapxep = (ImageView) rootview.findViewById(R.id.img_sapxepsp);
        timkiem = rootview.findViewById(R.id.search_timkiemsp);
        listView = (ListView) rootview.findViewById(R.id.lv_sanpham);
        voice_timkiem = rootview.findViewById(R.id.voice_timkiemlv);
        voice_timkiem.setEnabled(true);
        voice_timkiem.setImageResource(R.drawable.bg_trangtaivoice);

        // sự kiện cho icon xắp xếp
        imgiconsapxep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set level cho icon gridview
                listener.clickButton();

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
                    firebaseconnect.Laydulieudolist();
                    nutvoice = true;
                }
            }
        });

        timkiem.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseconnect.Timkiemonlist(listView,newText.trim());
                return false;
            }
        });

        timkiem.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                firebaseconnect.xoaadapter();
                firebaseconnect.Laydulieudolist();
                return false;
            }
        });


        // Floatgiohangkhichon

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
                                    tklistenner.clickThongtinTK();
                                }
                            }).show();
                } else if (arrsanphammua.size()==0){
                    Snackbar.make(view, getResources().getString(R.string.fcode_snack_content1), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    giohanglistener.clickGiohang();
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
                if (null != data){
                    ArrayList<String> voiceArr = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    kqvoice = voiceArr.get(0);
                    customToast.Customtoastsuccessvoicesearch(kqvoice);
                    firebaseconnect.Timkiemonlist(listView,kqvoice);
                }
                break;
        }
    }

    public void setTklistenner(OnclickThongTinTKListener tklistenner) {
        this.tklistenner = tklistenner;
    }

    public void setGiohanglistener(OnclickgiohangListener giohanglistener) {
        this.giohanglistener = giohanglistener;
    }

    // Lắng nghe để ẩn fragment này
    public void setListener(OnclickButton1Listener listener) {
        this.listener = listener;
    }

    public interface OnclickButton1Listener{
        void clickButton();
    }

    public interface OnclickgiohangListener{
        void clickGiohang();
    }

    public interface OnclickThongTinTKListener{
        void clickThongtinTK();
    }
}
