package com.tuanbapk.banrau.Presenter;

import android.content.Context;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuanbapk.banrau.Model.MyAplication;
import com.tuanbapk.banrau.Model.SanPham;
import com.tuanbapk.banrau.Model.Sanphamyeuthich;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.Adapter.AdapterSanPham;
import com.tuanbapk.banrau.View.MyAnimation.CustomToast;

import java.util.ArrayList;

/**
 * Created by buituan on 2017-12-03.
 */

public class FirebaseSPYeuThich {

    Context c;
    SanPham sanPham;
    Sanphamyeuthich sanphamyeuthich;

    ListView listView;

    ArrayList<SanPham> arrsanpham = new ArrayList<SanPham>();
    ArrayList<Sanphamyeuthich> arrspyeuthich = new ArrayList<Sanphamyeuthich>();

    AdapterSanPham adapterSanPham;

    CustomToast customToast;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("SanPham");
    DatabaseReference mynguoidung = database.getReference("NguoiDung");

    public FirebaseSPYeuThich(Context context, ListView listView){
        this.c = context;
        this.listView = listView;
    }
    public FirebaseSPYeuThich(Context c){
        this.c = c;
    }

    // THêm sản phẩm yêu thích
    public void Themyeuthich(String idsanpham){
        Sanphamyeuthich sp = new Sanphamyeuthich(idsanpham);
        mynguoidung.child(MyAplication.getInstance().getIdnguoidung()).child("Sanphamyeuthich").child(idsanpham).setValue(sp);
        CustomToast customToast= new CustomToast(c);
        customToast.Customtoastsuccess(c.getResources().getString(R.string.code_dbyeuthich_dathemsp));
    }
    // Xóa sản phẩm yêu thích
    public void Xoayeuthich(String idsanpham){
        mynguoidung.child(MyAplication.getInstance().getIdnguoidung()).child("Sanphamyeuthich").child(idsanpham).removeValue();
    }

    private void LayListsp(final DataSnapshot dtnap){
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                setdataSanpham(dataSnapshot);

                Arrspmoi();

                adapterSanPham = new AdapterSanPham(c, R.layout.item_spyeuthich,Arrspmoi());
                listView.setAdapter(adapterSanPham);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for (SanPham sp : Arrspmoi()){
                    if (sp.getIdSanPham().equals(dtnap.getKey())){
                        Arrspmoi().remove(sp);
                        adapterSanPham = new AdapterSanPham(c, R.layout.item_spyeuthich,Arrspmoi());
                        listView.setAdapter(adapterSanPham);

                        break;
                    }
                }


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // ĐỔ dữ liệu sp yêu thích
    public void LayListspyeuthich(){
           mynguoidung.child(MyAplication.getInstance().getIdnguoidung()).child("Sanphamyeuthich").addChildEventListener(new ChildEventListener() {
               @Override
               public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                   String id = dataSnapshot.getKey();
                   Loadataspyeuthich(dataSnapshot);
                   LayListsp(dataSnapshot);
               }

               @Override
               public void onChildChanged(DataSnapshot dataSnapshot, String s) {


               }

               @Override
               public void onChildRemoved(DataSnapshot dataSnapshot) {
                   LayListsp(dataSnapshot);
                   for (Sanphamyeuthich sp : arrspyeuthich) {
                       if (sp.getIdsanpham().equals(dataSnapshot.getKey())) {
                           arrspyeuthich.remove(sp);
                           CustomToast customToast = new CustomToast(c);
                           customToast.Customtoastfail(" Đã Bỏ Thích");
                           break;
                       }
                   }
               }

               @Override
               public void onChildMoved(DataSnapshot dataSnapshot, String s) {
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });
    }

    private void Loadataspyeuthich(DataSnapshot dataSnapshot){
        sanphamyeuthich = new Sanphamyeuthich();
        sanphamyeuthich.setIdsanpham(dataSnapshot.getValue(Sanphamyeuthich.class).getIdsanpham());
        arrspyeuthich.add(sanphamyeuthich);
    }
    private void setdataSanpham(DataSnapshot dataSnapshot){
        sanPham = new SanPham();
        sanPham.setIdSanPham(dataSnapshot.getValue(SanPham.class).getIdSanPham());
        sanPham.setTenSanPham(dataSnapshot.getValue(SanPham.class).getTenSanPham());
        sanPham.setGiaSanPham(dataSnapshot.getValue(SanPham.class).getGiaSanPham());
        sanPham.setMoTa(dataSnapshot.getValue(SanPham.class).getMoTa());
        sanPham.setSoluong(dataSnapshot.getValue(SanPham.class).getSoluong());
        sanPham.setDonvi(dataSnapshot.getValue(SanPham.class).getDonvi());
        sanPham.setLoaiSanPham(dataSnapshot.getValue(SanPham.class).getLoaiSanPham());
        sanPham.setHinh(dataSnapshot.getValue(SanPham.class).getHinh());
        arrsanpham.add(sanPham);
    }

    private ArrayList<SanPham> Arrspmoi(){
        ArrayList<SanPham> arrmoi = new ArrayList<SanPham>();
        if (arrspyeuthich.size()>0){
            int i = 0;
            while (i <arrspyeuthich.size() ){
                int j = 0;
                while (j<arrsanpham.size()){
                    if (arrspyeuthich.get(i).getIdsanpham().equals(arrsanpham.get(j).getIdSanPham())){
                        arrmoi.add(arrsanpham.get(j));
                        j = arrsanpham.size();
                    } else {
                        j++;
                    }

                }
                i++;
            }
        }

        return arrmoi;
    }
}
