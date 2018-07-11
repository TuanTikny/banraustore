package com.tuanbapk.banrau.Presenter;

import android.content.Context;
import android.widget.GridView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuanbapk.banrau.Model.Donhang;
import com.tuanbapk.banrau.Model.MyAplication;
import com.tuanbapk.banrau.Model.NguoiDung;
import com.tuanbapk.banrau.Model.SanPham;
import com.tuanbapk.banrau.Model.SanPhammua;
import com.tuanbapk.banrau.Model.Sanphamyeuthich;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.Adapter.AdapterDonHang;
import com.tuanbapk.banrau.View.Adapter.AdapterSanPham;
import com.tuanbapk.banrau.View.MyAnimation.CustomToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_diachi;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_hinh;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_hoten;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_motakhac;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_edt_sdt;
import static com.tuanbapk.banrau.View.Flagment.FragmentNguoidung.tk_img_nguoidung;

/**
 * Created by buituan on 2017-10-28.
 */

public class FirebaseSanPham {

    Context c;
    SanPham sanPham;

    ListView listView;
    GridView gridView;

    ArrayList<SanPham> arrsanpham = new ArrayList<SanPham>();
    AdapterSanPham adapterSanPham;

    CustomToast customToast;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("SanPham");

    public FirebaseSanPham(Context c) {
        this.c = c;
    }

    public FirebaseSanPham(Context context, GridView gridView) {
        this.c = context;
        this.gridView = gridView;
    }

    public FirebaseSanPham(Context context, ListView listView) {
        this.c = context;
        this.listView = listView;
    }


    // Các hàm xủ lí cho data Sản Phẩm
//-----------------------------------------------------------------------------------------------------------------------------
    public void Laydulieudolist() {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Chú ý chỉ load một lần chỗ này còn lại xử lí bên lớp Adapter
                setdataSanpham(dataSnapshot);
                loadlistview();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                SanPham sp = dataSanPham(dataSnapshot);

                int vitrisua = -1;
                for (int i = 0; i < arrsanpham.size(); i++) {
                    if (arrsanpham.get(i).getIdSanPham().equals(dataSnapshot.getKey())) {
                        vitrisua = i;
                        break;
                    }
                }
                arrsanpham.set(vitrisua, sp);
                adapterSanPham = new AdapterSanPham(c, R.layout.item_sanpham_lv, arrsanpham);
                listView.setAdapter(adapterSanPham);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for (SanPham sp : arrsanpham) {
                    if (sp.getIdSanPham().equals(dataSnapshot.getKey())) {
                        arrsanpham.remove(sp);
                        adapterSanPham = new AdapterSanPham(c, R.layout.item_sanpham_lv, arrsanpham);
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

    public void Laydulieudogriv() {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Chú ý chỉ load một lần chỗ này còn lại xử lí bên lớp Adapter
                setdataSanpham(dataSnapshot);
                loadgridview();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                SanPham sp = dataSanPham(dataSnapshot);

                int vitrisua = -1;
                for (int i = 0; i < arrsanpham.size(); i++) {
                    if (arrsanpham.get(i).getIdSanPham().equals(dataSnapshot.getKey())) {
                        vitrisua = i;
                        break;
                    }
                }
                arrsanpham.set(vitrisua, sp);
                adapterSanPham = new AdapterSanPham(c, R.layout.item_sanpham_gv, arrsanpham);
                gridView.setAdapter(adapterSanPham);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for (SanPham sp : arrsanpham) {
                    if (sp.getIdSanPham().equals(dataSnapshot.getKey())) {
                        arrsanpham.remove(sp);
                        adapterSanPham = new AdapterSanPham(c, R.layout.item_sanpham_gv, arrsanpham);
                        gridView.setAdapter(adapterSanPham);
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


    private void setdataSanpham(DataSnapshot dataSnapshot) {
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

    private SanPham dataSanPham(DataSnapshot dataSnapshot) {
        sanPham = new SanPham();
        sanPham.setIdSanPham(dataSnapshot.getValue(SanPham.class).getIdSanPham());
        sanPham.setTenSanPham(dataSnapshot.getValue(SanPham.class).getTenSanPham());
        sanPham.setGiaSanPham(dataSnapshot.getValue(SanPham.class).getGiaSanPham());
        sanPham.setMoTa(dataSnapshot.getValue(SanPham.class).getMoTa());
        sanPham.setSoluong(dataSnapshot.getValue(SanPham.class).getSoluong());
        sanPham.setDonvi(dataSnapshot.getValue(SanPham.class).getDonvi());
        sanPham.setLoaiSanPham(dataSnapshot.getValue(SanPham.class).getLoaiSanPham());
        sanPham.setHinh(dataSnapshot.getValue(SanPham.class).getHinh());

        return sanPham;
    }


    private void loadgridview() {
        if (arrsanpham.size() > 0) {
            adapterSanPham = new AdapterSanPham(c, R.layout.item_sanpham_gv, arrsanpham);
            gridView.setAdapter(adapterSanPham);
        } else {
            customToast = new CustomToast(c);
            customToast.Customtoastfail(c.getResources().getString(R.string.code_db_resulterror));
        }
    }

    private void loadlistview() {
        if (arrsanpham.size() > 0) {
            adapterSanPham = new AdapterSanPham(c, R.layout.item_sanpham_lv, arrsanpham);
            listView.setAdapter(adapterSanPham);
        } else {
            // Không có dữ liệu trả về
            customToast = new CustomToast(c);
            customToast.Customtoastfail(c.getResources().getString(R.string.code_db_resulterror));
        }

    }

    //     hàm tìm kiếm
    public void TimkiemonGrid(GridView grid, String tensptim) {
        tensptim = tensptim.toLowerCase(Locale.getDefault());
        ArrayList<SanPham> arrsanphamtim = new ArrayList<>();


        if (tensptim.length() == 0) {
            adapterSanPham.clear();
        } else {
            int i = 0;
            while (i < arrsanpham.size()) {
                String tensp = arrsanpham.get(i).getTenSanPham();
                if (tensp.toLowerCase(Locale.getDefault()).contains(tensptim)) {
                    SanPham sp = arrsanpham.get(i);
                    arrsanphamtim.add(sp);
                }
                i++;
            }
            if (arrsanphamtim.size() > 0) {
                adapterSanPham = new AdapterSanPham(c, R.layout.item_sanpham_gv, arrsanphamtim);
                grid.setAdapter(adapterSanPham);
                adapterSanPham.notifyDataSetChanged();
            }
        }
    }

    // hàm tìm kiếm lv
    public void Timkiemonlist(ListView list, String tensptim) {
        tensptim = tensptim.toLowerCase(Locale.getDefault());
        ArrayList<SanPham> arrsanphamtim = new ArrayList<>();

        if (tensptim.length() == 0) {
            adapterSanPham.clear();
        } else {
            int i = 0;
            while (i < arrsanpham.size()) {
                String tensp = arrsanpham.get(i).getTenSanPham();
                if (tensp.toLowerCase(Locale.getDefault()).contains(tensptim)) {
                    SanPham sp = arrsanpham.get(i);
                    arrsanphamtim.add(sp);
                }
                i++;
            }
            if (arrsanphamtim.size() > 0) {
                adapterSanPham = new AdapterSanPham(c, R.layout.item_sanpham_lv, arrsanphamtim);
                list.setAdapter(adapterSanPham);
                adapterSanPham.notifyDataSetChanged();
            }
        }
    }

    public void xoaadapter() {
        arrsanpham.clear();
        adapterSanPham.clear();
    }

}

