package com.tuanbapk.banrau.Presenter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuanbapk.banrau.Model.MyAplication;
import com.tuanbapk.banrau.Model.NguoiDung;
import com.tuanbapk.banrau.View.MyAnimation.CustomToast;

import java.util.ArrayList;

import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_diachi;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_hinh;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_hoten;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_motakhac;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_sdt;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_img_nguoidung;

/**
 * Created by buituan on 2017-12-03.
 */

public class FirebaseNguoiDung {
    Context c;
    NguoiDung nguoiDung;
    ArrayList<NguoiDung> arrnguoidung = new ArrayList<NguoiDung>();

    CustomToast customToast;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mynguoidung = database.getReference("NguoiDung");

    public FirebaseNguoiDung (Context c){
        this.c = c;
    }

    // Thêm và sửa 1 thông tin người dùng mới
    public void addThongtinTK(String idTk, String Ten, String sdt, String diachi, String motakhac, String hinh){
        //  NguoiDung nguoiDung = new NguoiDung(idTk,Ten,sdt,diachi,motakhac,hinh);
        mynguoidung.child(idTk).child("idTK").setValue(idTk);
        mynguoidung.child(idTk).child("hoten").setValue(Ten);
        mynguoidung.child(idTk).child("sodienthoai").setValue(sdt);
        mynguoidung.child(idTk).child("diachi").setValue(diachi);
        mynguoidung.child(idTk).child("motakhac").setValue(motakhac);
        mynguoidung.child(idTk).child("hinhdaidien").setValue(hinh);
    }
    // Thêm thông tin khi đăng nhập
    public void addTKDangnhap(String idTk,String Ten,String hinh){
        mynguoidung.child(idTk).child("idTK").setValue(idTk);
        mynguoidung.child(idTk).child("hoten").setValue(Ten);
        mynguoidung.child(idTk).child("hinhdaidien").setValue(hinh);
    }

    // Sửa thông tin người dùng

    // hàm Lấy dữ liệu người dùng
    public void LaydataTK(){
        mynguoidung.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                setdatanguoidung(dataSnapshot);
                LayNguoidungHT();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                setdatanguoidung(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void setdatanguoidung(DataSnapshot dataSnapshot){
        nguoiDung = new NguoiDung();
        nguoiDung.setIdTK(dataSnapshot.getValue(NguoiDung.class).getIdTK());
        nguoiDung.setHoten(dataSnapshot.getValue(NguoiDung.class).getHoten());
        nguoiDung.setSodienthoai(dataSnapshot.getValue(NguoiDung.class).getSodienthoai());
        nguoiDung.setDiachi(dataSnapshot.getValue(NguoiDung.class).getDiachi());
        nguoiDung.setMotakhac(dataSnapshot.getValue(NguoiDung.class).getMotakhac());
        nguoiDung.setHinhdaidien(dataSnapshot.getValue(NguoiDung.class).getHinhdaidien());

        arrnguoidung.add(nguoiDung);
    }

    public void LayNguoidungHT(){
        if (arrnguoidung.size()>0){
            int i = 0;
            String hientai = MyAplication.getInstance().getIdnguoidung();
            while (i < arrnguoidung.size()) {
                String nd = arrnguoidung.get(i).getIdTK();
                if (nd.equals(hientai) == true) {
                    NguoiDung ht = new NguoiDung();
                    ht = arrnguoidung.get(i);
                    // Lấy thông tin người dùng đó
                    String hoten = ht.getHoten();
                    String sdt = ht.getSodienthoai();
                    String diachi = ht.getDiachi();
                    String motakhac = ht.getMotakhac();
                    String hinh = ht.getHinhdaidien();
                    // Đổ về fragment thông tin người dùng
                    tk_edt_hoten.setText(hoten);

                    tk_edt_sdt.setText(sdt);
                    tk_edt_diachi.setText(diachi);
                    tk_edt_motakhac.setText(motakhac);
                    tk_edt_hinh.setText(hinh);
                    if (hinh.equals("")==false) {
                        try {
                            Glide.with(c).load(hinh).into(tk_img_nguoidung);
                        }catch (Exception e){

                        }

                    }
                    i = arrnguoidung.size();
                } else {
                    i++;}}}}
}
