package com.tuanbapk.banrau.View.Flagment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.tuanbapk.banrau.Model.Donhang;
import com.tuanbapk.banrau.Model.SanPham;
import com.tuanbapk.banrau.Presenter.FirebaseDonHang;
import com.tuanbapk.banrau.Presenter.FirebaseSanPham;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.Adapter.AdapterSanPhamMua;
import com.tuanbapk.banrau.View.MyAnimation.CustomToast;

import java.util.ArrayList;

import ru.katso.livebutton.LiveButton;

import static com.tuanbapk.banrau.View.Mydialog.DialogChonMua.adapterspmua;
import static com.tuanbapk.banrau.View.Mydialog.DialogChonMua.arrsanphammua;
import static com.tuanbapk.banrau.View.Mydialog.DialogChonMua.demsp;
import static com.tuanbapk.banrau.View.Mydialog.DialogChonMua.tongtien;
import static com.tuanbapk.banrau.View.Mydialog.DialogChonMua.tongtrongluong;

/**
 * Created by buituan on 2017-10-31.
 */

public class FragmentGiohang extends Fragment {

    private View rootview;
    private Onclickbuttonlistener listener;
    CustomToast customToast;
    FirebaseDonHang firebase;

    Donhang donhang;

    public static SwipeMenuListView listspmua;
    public static TextView giohang_tv_slsanpham,giohang_tv_tongsoluong,giohang_tv_tonggiasp;
    public static LiveButton giohang_btn_muathem,giohang_btn_dathang,giohang_btn_huy;

//    private AdRequest adRequest1;
//    private  AdRequest adRequest2;
//    private InterstitialAd fullAd;
//    private InterstitialAd fullAdhai;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_giohang,container,false);
        customToast = new CustomToast(rootview.getContext());
        firebase = new FirebaseDonHang(rootview.getContext());

        mapped();

//        adRequest1 = new AdRequest.Builder().build();
//        adRequest2 = new AdRequest.Builder().build();
//
//
//        // show quang cao full screen
//        fullAd = new InterstitialAd(rootview.getContext());
//        fullAd.setAdListener(new AdListener(){
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//                fullAd.loadAd(adRequest1);
//            }
//        });
//
//        fullAd.setAdUnitId(getResources().getString(R.string.full_hai));
//        fullAd.loadAd(adRequest1);
//
//        // show quang cao full screen 2
//        fullAdhai = new InterstitialAd(rootview.getContext());
//        fullAdhai.setAdListener(new AdListener(){
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//                fullAdhai.loadAd(adRequest2);
//            }
//        });
//
//        fullAdhai.setAdUnitId(getResources().getString(R.string.full_ba));
//        fullAdhai.loadAd(adRequest2);


        return rootview;
    }

    private void mapped() {
        listspmua = (SwipeMenuListView) rootview.findViewById(R.id.giohang_lv_sanpham);

        giohang_tv_slsanpham = (TextView) rootview.findViewById(R.id.giohang_tv_slsanpham);
        giohang_tv_tongsoluong =(TextView) rootview.findViewById(R.id.giohang_tv_tongsoluong);
        giohang_tv_tonggiasp = (TextView)rootview.findViewById(R.id.giohang_tv_tonggiasp);

        giohang_btn_dathang = (LiveButton) rootview.findViewById(R.id.giohang_btn_dathang);
        giohang_btn_dathang.setText("  "+getResources().getString(R.string.fcode_cart_btndathang));

        giohang_btn_muathem =(LiveButton) rootview.findViewById(R.id.giohang_btn_muathem);
        giohang_btn_muathem.setText("  "+getResources().getString(R.string.fcode_cart_btnmuatiep));

        giohang_btn_huy = (LiveButton) rootview.findViewById(R.id.giohang_btn_huy);
        giohang_btn_huy.setText("   "+getResources().getString(R.string.fcode_cart_btnhuy));

        Dieuhuongitemlist();


        // Sự kiện đặt đơn hàng
        giohang_btn_dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  //----------------------------------------------------------------------------------------------------
            if (arrsanphammua.size()==0){
                Snackbar.make(view, getResources().getString(R.string.fcode_cart_snackcontent), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                    //xử lí sau 3 giây hiện nút
                    PopupdialogDathangOk(rootview.getContext());

            }

//--------------------------------------------------------------------------------------------------------
            }
        });


        giohang_btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fullAdhai.show();
                if (arrsanphammua.size()==0){
                    Snackbar.make(view, getResources().getString(R.string.fcode_cart_snackcontent), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    // Sự kiện hủy giỏ hàng
                    PopupdialogXoa(rootview.getContext());
                }
            }
        });

        // Sự kiện hủy thoát ra khỏi giỏ hàng và mua thêm
        giohang_btn_muathem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.ClickButton();
                // show quang cao full khi dung
//                fullAd.show();
            }
        });
    }

    private void PopupdialogXoa(Context c){

        final AlertDialog d = new AlertDialog.Builder(c).create();
        d.setTitle(getResources().getString(R.string.fcode_cart_delcart_title));
        d.setMessage(getResources().getString(R.string.fcode_cart_delcart_content));
        // dùng Cancel khi chạm vào vùng ngoài dialog cũng ko làm tắt dialog
        d.setCanceledOnTouchOutside(false);
        // set icon cho dialog
        d.setIcon(R.drawable.deleteicon);

        // sự kiện lắng nghe khi nhấp vào nút Ok
        d.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.fcode_dialogdelete_favoritesp_btndelete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                xoagiohang();
                customToast.Customtoastfail(getResources().getString(R.string.fcode_cart_delcart));
            }
        });

        // sự kiện lắng nghe khi nhấp vào nút No
        d.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.fcode_dialogdelete_favoritesp_btnno), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                d.dismiss();
            }
        });

        d.show();
    }

    private void PopupdialogDathangOk(Context c){
        final  AlertDialog d = new AlertDialog.Builder(c).create();
        d.setTitle(getResources().getString(R.string.fcode_cart_dialogsuccess_title));
        d.setMessage(getResources().getString(R.string.fcode_cart_dialogsuccess_content)+" : "+"\n"+"\n"+
                        getResources().getString(R.string.frag_dialoginforsp_count)+" "+String.valueOf(demsp)+" "+getResources().getString(R.string.mcode_menu_production)+"\n"+
                        getResources().getString(R.string.frag_cart_tongtrongluong)+" "+String.format("%,.1f",tongtrongluong)+" Kg"+"\n"+
                        getResources().getString(R.string.frag_cart_tongtien)+" "+String.format("%,.0f",tongtien)+" VNĐ"+"\n"+"\n"+
                        getResources().getString(R.string.fcode_cart_kiemtra));
        // dùng Cancel khi chạm vào vùng ngoài dialog cũng ko làm tắt dialog
        d.setCanceledOnTouchOutside(false);
        // set icon cho dialog
        d.setIcon(R.drawable.ic_datthanhcong);

        // sự kiện lắng nghe khi nhấp vào nút Ok
        d.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.fcode_cart_xacnhan), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                xukiendathang();
                customToast.Customtoastsuccess(getResources().getString(R.string.fcode_cart_dathangthanhcong));
                xoagiohang();
                listener.ClickButton();
                d.dismiss();
            }
        });
        d.show();
    }

    private void xoagiohang(){
        adapterspmua.clear();
        tongtrongluong=0;
        demsp =0;
        tongtien =0;
        giohang_tv_slsanpham.setText("0.0");
        giohang_tv_tongsoluong.setText("0.0");
        giohang_tv_tonggiasp.setText("0.0");
    }

    private void xukiendathang(){
        // Mượn tạm lớp Arrsanphammua
        if (arrsanphammua.size()>=1){
            firebase.Themdonhang(String.valueOf(demsp),String.format("%,.1f",tongtrongluong),String.format("%,.0f",tongtien),null);
            int i = 0;
            do {
                String idsanpham = arrsanphammua.get(i).getIdSanPham();
                String soluong = arrsanphammua.get(i).getSoluong();
                String tonggia = arrsanphammua.get(i).getGiaSanPham();
                firebase.Themsanphammuavaodonhang(idsanpham,soluong,tonggia);
                i++;
            }while (i<arrsanphammua.size());

        }
    }


    public void setListener(Onclickbuttonlistener listener) {
        this.listener = listener;
    }

    public interface Onclickbuttonlistener{
        void ClickButton();
    }

    private void Dieuhuongitemlist(){
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(rootview.getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(180);
                // set a icon
                deleteItem.setIcon(R.drawable.removeicontrang);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listspmua.setMenuCreator(creator);
        listspmua.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);


        listspmua.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        PopupdialogXoa(rootview.getContext(),index);
                        break;
                }
                return false;
            }
        });

    }

    public void PopupdialogXoa(Context c, final int positon){
        final AlertDialog d = new AlertDialog.Builder(c).create();
        d.setTitle(getResources().getString(R.string.fcode_cart_delsp_title));
        d.setMessage(getResources().getString(R.string.fcode_cart_delsp_content));
        // dùng Cancel khi chạm vào vùng ngoài dialog cũng ko làm tắt dialog
        d.setCanceledOnTouchOutside(false);
        // set icon cho dialog
        d.setIcon(R.drawable.removeicon);
        d.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.fcode_dialogdelete_favoritesp_btndelete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // Cập nhật lại số lượng, trọng lượng và tổng tiền
//-------------------------------------------------------------------------------------------------------
                // Trích giá trị trongluong Kg
                String laygiatrisl =arrsanphammua.get(positon).getSoluong();
                String giatritrongluongspxoa = laygiatrisl.substring(0,laygiatrisl.indexOf("/"));

                // Trích giá trị tiền của sp đó
                String laygiatritien = arrsanphammua.get(positon).getGiaSanPham();
                String giatritienspxoa = laygiatritien;

                try {
                    tongtrongluong = tongtrongluong-Double.parseDouble(giatritrongluongspxoa);
                    tongtien = tongtien-Double.parseDouble(giatritienspxoa);
                    demsp    = demsp-1;

                    giohang_tv_slsanpham.setText(demsp+"");
                    giohang_tv_tongsoluong.setText(String.format("%.1f",tongtrongluong));
                    giohang_tv_tonggiasp.setText(String.format("%.0f",tongtien));

                } catch (Exception e){}
//--------------------------------------------------------------------------------------------------------
                // tính toán lại xong mới xóa sản phẩm đó
                arrsanphammua.remove(positon);
                adapterspmua.notifyDataSetChanged();
            }
        });

        // sự kiện lắng nghe khi nhấp vào nút No
        d.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.fcode_dialogdelete_favoritesp_btnno), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                d.dismiss();
            }
        });

        d.show();
    }
}
