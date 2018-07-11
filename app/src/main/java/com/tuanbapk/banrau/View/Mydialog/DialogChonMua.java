package com.tuanbapk.banrau.View.Mydialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tuanbapk.banrau.Model.SanPham;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.Adapter.AdapterSanPham;
import com.tuanbapk.banrau.View.Adapter.AdapterSanPhamMua;
import com.tuanbapk.banrau.View.MyAnimation.CustomToast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import ru.katso.livebutton.LiveButton;

import static com.tuanbapk.banrau.View.Flagment.FragmentGiohang.giohang_tv_slsanpham;
import static com.tuanbapk.banrau.View.Flagment.FragmentGiohang.giohang_tv_tonggiasp;
import static com.tuanbapk.banrau.View.Flagment.FragmentGiohang.giohang_tv_tongsoluong;
import static com.tuanbapk.banrau.View.Flagment.FragmentGiohang.listspmua;


/**
 * Created by buituan on 2017-10-31.
 */

public class DialogChonMua {
    TextView chonmua_tv_tensp,chonmua_tv_giasp,chonmua_tv_donvisp,chonmua_tv_titlethanhtien,chonmua_tv_thanhtiensp;
    LiveButton chonmua_btn_themgiohang,chonmua_btn_huy;
    ImageView chonmua_img_hinhsp;
    EditText chonmua_edtsoluong;
    Button chonmua_btntru,chonmua_btncong;



    // Mượn đối tượng sản phẩm để đổ dữ liệu
    AdapterSanPham a;
    CustomToast customToast;

    /// Biến dữ liệu mua
    String thanhtien;

    public static int demsp =0;
    public static double tongtrongluong=0;
    public static double tongtien=0;


    public static ArrayList<SanPham> arrsanphammua = new ArrayList<SanPham>();
    public static AdapterSanPhamMua adapterspmua;


    public DialogChonMua (AdapterSanPham a){
        this.a =a;
    }

    private void Mapped(Dialog d, Context c){
        chonmua_tv_tensp = (TextView) d.findViewById(R.id.chonmua_tv_tensp);
        chonmua_tv_giasp = (TextView) d.findViewById(R.id.chonmua_tv_giasp);
        chonmua_tv_donvisp = (TextView)d.findViewById(R.id.chonmua_tv_donvisp);
        chonmua_tv_titlethanhtien = (TextView)d.findViewById(R.id.chonmua_tv_titlethanhtien);
        chonmua_tv_thanhtiensp =(TextView) d.findViewById(R.id.chonmua_tv_thanhtiensp);

        chonmua_btn_themgiohang = (LiveButton) d.findViewById(R.id.chonmua_btn_themgiohang);
        chonmua_btn_huy = (LiveButton)d.findViewById(R.id.chonmua_btn_huy);
        chonmua_btn_themgiohang.setText("   "+c.getResources().getString(R.string.fcode_dialogchosebuy_btnaddsp));
        chonmua_btn_huy.setText("   "+c.getResources().getString(R.string.fcode_dialogchosebuy_cancel));

        chonmua_img_hinhsp = (ImageView) d.findViewById(R.id.chonmua_img_hinhsp);

        chonmua_edtsoluong = (EditText) d.findViewById(R.id.chonmua_edtsoluong);
        chonmua_btntru = (Button) d.findViewById(R.id.chonmua_btntru);
        chonmua_btncong = (Button) d.findViewById(R.id.chonmua_btncong);


    }

    public  void  PopDialogChonMua(final Context c, final SanPham sanPham){
        final Dialog d = new Dialog(c);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_chonmuasp);

        // Gọi ánh xạ
        Mapped(d,c);
        // Gắn thông tin

        chonmua_tv_tensp.setText(sanPham.getTenSanPham());
        chonmua_tv_giasp.setText(sanPham.getGiaSanPham());
        chonmua_tv_thanhtiensp.setText(sanPham.getGiaSanPham());
        chonmua_tv_titlethanhtien.setText(c.getResources().getString(R.string.fcode_dialogchosebuy_money));
        chonmua_tv_donvisp.setText("/"+sanPham.getDonvi());
        Glide.with(c).load(sanPham.getHinh()).into(chonmua_img_hinhsp);

        // Gắn giá trị mua
        Gangiatrimua(sanPham);


        // Sự kiện + giỏ hàng
        chonmua_btn_themgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customToast= new CustomToast(c);
                // cộng thêm sản phẩm trên 1 lần click
                    if (chonmua_tv_thanhtiensp.getText().toString().equals("0")){
                        customToast.Customtoastfail(c.getResources().getString(R.string.fcode_dialogchosebuy_pleaseinputcount));
                    }else {
                        demsp++;
                        // Xử lí thêm sp vào giỏ hàn tại đây
//------------------------------------------------------------------------------
                        thanhtien = chonmua_tv_thanhtiensp.getText().toString();
                        String idSanPham = sanPham.getIdSanPham();
                        String TenSanPham = sanPham.getTenSanPham();
                        String Giasanphammua = thanhtien;
                        String MoTa = sanPham.getMoTa();
                        String Soluong = String.valueOf(chonmua_edtsoluong.getText().toString()) + "/Kg";
                        String Donvi = sanPham.getDonvi();
                        String LoaiSanPham = sanPham.getLoaiSanPham();
                        String Hinh = sanPham.getHinh();

                        if (arrsanphammua.size() <= 100) {
                            SanPham sp = new SanPham(idSanPham, TenSanPham, Giasanphammua, MoTa, Soluong, Donvi, LoaiSanPham, Hinh);
                            arrsanphammua.add(sp);

                            // TỔng sản phẩm đã chọn mua
                            giohang_tv_slsanpham.setText(String.valueOf(demsp));
                            try {
                                // Tính tổng trọng lượng
                                tongtrongluong = tongtrongluong + Double.parseDouble(chonmua_edtsoluong.getText().toString());
                                // Tính tổng tiền
                                giohang_tv_tongsoluong.setText(String.format("%.1f", tongtrongluong));

                                tongtien = tongtien + Double.parseDouble(thanhtien);

                                giohang_tv_tonggiasp.setText(String.format("%.0f", tongtien));
                            } catch (Exception e) {
                            }

                        } else {
                            customToast.Customtoastfail(c.getResources().getString(R.string.fcode_dialohchosebuy_100production));
                        }


                        if (arrsanphammua.size() > 0) {
                            adapterspmua = new AdapterSanPhamMua(c, R.layout.item_spmua, arrsanphammua);
                            listspmua.setAdapter(adapterspmua);
                            customToast.Customtoastsuccess(c.getResources().getString(R.string.fcode_dialogchosebuy_addok));
                        }

                        d.dismiss();
                        //------------------------------------------------------------------------------
                    }

            }
        });



        // Sự kiện hủy
        chonmua_btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });


        d.show();
    }

    // Hàm gắn giá trị cứng cho spin
    private void Gangiatrimua(final SanPham sanPham){

       chonmua_edtsoluong.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               int maxsoluong = 0;
               try {
                   maxsoluong = Integer.parseInt(charSequence.toString());
               }catch (Exception e){}

               if (charSequence.toString().equals("")){
                   charSequence = "0";
                  tinhtoannhap(sanPham,charSequence.toString());
               }else if (charSequence.toString().equals(".")) {
                   charSequence = "1.0";
                   chonmua_edtsoluong.setText(charSequence);
                   tinhtoannhap(sanPham,charSequence.toString());
               }else if (maxsoluong>=101){
                   charSequence = "100.0";
                   chonmua_edtsoluong.setText(charSequence);
                   tinhtoannhap(sanPham,charSequence.toString());
                }else {
                    tinhtoannhap(sanPham,charSequence.toString());
                }
           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       });

        chonmua_btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double thamso = Double.parseDouble(chonmua_edtsoluong.getText().toString());
                thamso+=0.1;
                if (thamso>=100){
                    thamso = 100;
                    chonmua_edtsoluong.setText("100.0");
                }else {
                    tinhtoanspin(sanPham, thamso);

                }
            }
        });

        chonmua_btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double thamso = Double.parseDouble(chonmua_edtsoluong.getText().toString());
                thamso-=0.1;
                if (thamso<=0.1){
                    thamso = 0.1;
                    chonmua_edtsoluong.setText("0.1");
                } else {
                    tinhtoanspin(sanPham, thamso);
                }

            }
        });
    }


    private void tinhtoanspin(SanPham sanPham, double thamso){
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.0", decimalFormatSymbols);
        chonmua_edtsoluong.setText(decimalFormat.format(thamso));
        try {
            Double sl = thamso;
            Double gia = Double.parseDouble(sanPham.getGiaSanPham());
            Double kqtongtien = sl*gia;
            thanhtien = String.format("%.0f",kqtongtien);
            chonmua_tv_thanhtiensp.setText(thanhtien);
        }catch (Exception e){}
    }

    private void tinhtoannhap(SanPham sanPham, String a){
        double thamso = Double.parseDouble(a);
        try {
            Double sl = thamso;
            Double gia = Double.parseDouble(sanPham.getGiaSanPham());
            Double kqtongtien = sl * gia;
            thanhtien = String.format("%.0f", kqtongtien);
            chonmua_tv_thanhtiensp.setText(thanhtien);
        } catch (Exception e) {
        }
    }

}
