package com.tuanbapk.banrau.Presenter;


import android.content.Context;

import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.Activity.MainDangNhap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tuanbapk.banrau.View.Flagment.FragmentDangKy.matkhau1layout;
import static com.tuanbapk.banrau.View.Flagment.FragmentDangKy.matkhau2layout;
import static com.tuanbapk.banrau.View.Flagment.FragmentDangKy.tendangkylayout;
import static com.tuanbapk.banrau.View.Flagment.FragmentDangKy.dangkykhongthanhcong;
import static com.tuanbapk.banrau.View.Flagment.FragmentDangnhap.matkhauLayout;
import static com.tuanbapk.banrau.View.Flagment.FragmentDangnhap.tendangnhapLayout;

/**
 * Created by buituan on 2017-10-27.
 */

public class Checknhap {

    public static boolean CheckDN(Context c,String tendangnhap, String matkhau){

        boolean flag = false;

        // kiểm tra xem tài khoản nhập vào chưa

        if (tendangnhap.trim().equals("")) {
            tendangnhapLayout.setError("         "+c.getResources().getString(R.string.check_dn_taikhoan));
            return false;
        } else{
            tendangnhapLayout.setError("");
            flag = true;
        }

        // kiểm tra mật khẩu có nhập vào không

        if (matkhau.trim().equals("")) {
            matkhauLayout.setError("         "+c.getResources().getString(R.string.check_dn_matkhau));
            return false;
        } else{
            matkhauLayout.setError("");
            flag = true;
        }


//        if (flag == true){
//            Boolean Loginsucess = mydb.DangnhapData(tendangnhap,matkhau);
//            if (Loginsucess==true){
//                matkhauLayout.setError("");
//                return true;
//            } else {
//                matkhauLayout.setError("Tên đăng nhập hoặc mật khẩu chưa chính xác !");
//                return false;
//            }
//        }

        return true;
    }

    // Hàm xử lí chuối nhập vào cho đăng ký
    public static boolean CheckDK(Context c,String tendangnhap, String matkhau1, String matkhau2){

        Pattern patternTendangnhap = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcherTendangnhap = patternTendangnhap.matcher(tendangnhap);

        if (matcherTendangnhap.matches()==false) {
            tendangkylayout.setError("         "+c.getResources().getString(R.string.check_dk_email));
            dangkykhongthanhcong.setText(c.getResources().getString(R.string.check_dk_noteemail));
            return false;
        } else{
            tendangkylayout.setError("");
            dangkykhongthanhcong.setText("");
        }

        // CHECK MẬT KHẨU NHẬP VÀO TRÊN 6 Ký Tự yêu cầu nhập giống tên đăn nhập tối thiểu 1 ký tự in hoa 1 ký tự in thường và 1 số
        Pattern patternMatKhau = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$");
        Matcher matcherMatKhau = patternMatKhau.matcher(matkhau1);

        if (matcherMatKhau.matches()==false) {
            matkhau1layout.setError("         "+c.getResources().getString(R.string.check_dk_matkhau));
            dangkykhongthanhcong.setText(c.getResources().getString(R.string.check_dk_notematkhau));
            return false;
        } else{
            matkhau1layout.setError("");
            dangkykhongthanhcong.setText("");
        }

        if (matkhau2.equals(matkhau1)==false){
            matkhau2layout.setError("         "+c.getResources().getString(R.string.check_dk_matkhaulan2));
            return false;
        } else {
            matkhau2layout.setError("");
        }

        return true;
    }
}
