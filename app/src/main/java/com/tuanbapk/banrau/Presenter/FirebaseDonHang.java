package com.tuanbapk.banrau.Presenter;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuanbapk.banrau.Model.Donhang;
import com.tuanbapk.banrau.Model.MyAplication;
import com.tuanbapk.banrau.Model.SanPhammua;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.Activity.MainSanPham;
import com.tuanbapk.banrau.View.Adapter.AdapterDonHang;
import com.tuanbapk.banrau.View.Flagment.FragmentgvSanPham;
import com.tuanbapk.banrau.View.MyAnimation.CustomToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by buituan on 2017-12-03.
 */

public class FirebaseDonHang {
    Context c;
    Donhang donhang;
    ArrayList<Donhang> arrdonhang = new ArrayList<Donhang>();
    ListView listView;
    AdapterDonHang adapterDonHang;

    NotificationCompat.Builder notification;
    private static final int uniqueID = 123;

    CustomToast customToast;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myDonHang = database.getReference("Donhang");


    public FirebaseDonHang(Context c){
        this.c = c;
    }
    public FirebaseDonHang(Context context, ListView listView){
        this.c = context;
        this.listView = listView;
    }

    // CÁC HÀM XỬ LÍ DATA Cho Đơn Hàng
//----------------------------------------------------------------------------------------------------------------------------
    private String id,idnguoimua, ngaymua;

    /// Hàm trả về ngày giờ hiện tại
    public static String ngaygiohientai(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String datenow = simpleDateFormat.format(calendar.getTime());
        return datenow;
    }
    private void Thongtinnguoidungcoban(){
        id = myDonHang.push().getKey();
        idnguoimua = MyAplication.getInstance().getIdnguoidung();
        ngaymua = ngaygiohientai();
    }
    private void XuatThongBao(String sticker, String tieude, String noidung){
        notification = new NotificationCompat.Builder(c);
        notification.setAutoCancel(true);
        Intent intent = new Intent(c, FragmentgvSanPham.class);
        MediaPlayer mp = MediaPlayer.create(c, R.raw.sound_eff);
        Vibrator vibrator= (Vibrator) c.getSystemService(VIBRATOR_SERVICE);

        notification.setSmallIcon(R.drawable.icon_app);
        notification.setTicker(sticker);
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(tieude);
        notification.setContentText(noidung);
        mp.start();
        vibrator.vibrate(500);

        PendingIntent pendingIntent = PendingIntent.getActivity(c, 0 , intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) c.getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());
    }

    // Thêm Đơn Hàng Mới
    public void Themdonhang(String sosanphammua, String soluongkg, String tongtien, SanPhammua sanPhammua){
        Thongtinnguoidungcoban();
        Donhang donhang = new Donhang(id,idnguoimua,ngaymua,"0",sosanphammua,soluongkg,tongtien,sanPhammua);
        myDonHang.child(id).setValue(donhang);
    }

    public void Themsanphammuavaodonhang(String idsanpham, String soluongmua, String tonggia){
        String idspmua = myDonHang.child(id).push().getKey();
        SanPhammua spmua = new SanPhammua(idspmua, idsanpham,soluongmua,tonggia);
        myDonHang.child(id).child("sanphammua").child(idspmua).setValue(spmua);
    }



    /// Hàm Lấy Dữ liệu đơn hàng
    public void LaydulieuDonhangcho(){
        myDonHang.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                setdataDonhang(dataSnapshot);
                loadDonhangcho();
                adapterDonHang = new AdapterDonHang(c,R.layout.item_hoadon,loadDonhangcho());
                listView.setAdapter(adapterDonHang);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (!dataSnapshot.child("tinhtrangdonhang").getValue().equals("0")){
                    Donhang dh = Donhangsua(dataSnapshot);
                    int vitrisua = -1;
                    for (int i = 0; i < arrdonhang.size(); i++) {
                        if (arrdonhang.get(i).getIddonhang().equals(dataSnapshot.getKey())) {
                            vitrisua = i;
                            break;
                        }
                    }
                    arrdonhang.remove(vitrisua);
                    adapterDonHang = new AdapterDonHang(c,R.layout.item_hoadon,loadDonhangcho());
                    listView.setAdapter(adapterDonHang);
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void LaydulieuDonhanggui(){
        myDonHang.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                setdataDonhang(dataSnapshot);
                loadDonhangdanggui();
                adapterDonHang = new AdapterDonHang(c,R.layout.item_hoadon,loadDonhangdanggui());
                listView.setAdapter(adapterDonHang);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.child("tinhtrangdonhang").getValue().equals("3")){
                    Donhang dh = Donhangsua(dataSnapshot);
                    int vitrisua = -1;
                    for (int i = 0; i < arrdonhang.size(); i++) {
                        if (arrdonhang.get(i).getIddonhang().equals(dataSnapshot.getKey())) {
                            vitrisua = i;
                            break;
                        }
                    }
                    arrdonhang.remove(vitrisua);
                    adapterDonHang = new AdapterDonHang(c,R.layout.item_hoadon,loadDonhangdanggui());
                    listView.setAdapter(adapterDonHang);
                }
                if (dataSnapshot.child("tinhtrangdonhang").getValue().equals("1")){
                    String donhang = dataSnapshot.child("ngaymua").getValue().toString();
                    XuatThongBao(c.getResources().getString(R.string.code_dbdonhang_order)+" " +donhang+
                            c.getResources().getString(R.string.code_dbdonhang_daxacnhan),
                            c.getResources().getString(R.string.code_dbdonhang_daxacnhan1),
                            c.getResources().getString(R.string.code_dbdonhang_order)+" " +donhang+
                                    "\n "+c.getResources().getString(R.string.code_dbdonhang_daxacnhan3));
                    Donhang dh = Donhangsua(dataSnapshot);
                    int vitrisua = -1;
                    for (int i = 0; i < arrdonhang.size(); i++) {
                        if (arrdonhang.get(i).getIddonhang().equals(dataSnapshot.getKey())) {
                            vitrisua = i;
                            break;
                        }
                    }
                    arrdonhang.add(vitrisua,dh);
                    adapterDonHang = new AdapterDonHang(c,R.layout.item_hoadon,loadDonhangdanggui());
                    listView.setAdapter(adapterDonHang);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void LaydulieuDonhangnhan(){
        myDonHang.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                setdataDonhang(dataSnapshot);
                loadDonhangdanhan();
                adapterDonHang = new AdapterDonHang(c,R.layout.item_hoadon,loadDonhangdanhan());
                listView.setAdapter(adapterDonHang);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.child("tinhtrangdonhang").getValue().equals("2")){
                    Donhang dh = Donhangsua(dataSnapshot);
                    int vitrisua = -1;
                    for (int i = 0; i < arrdonhang.size(); i++) {
                        if (arrdonhang.get(i).getIddonhang().equals(dataSnapshot.getKey())) {
                            vitrisua = i;
                            break;
                        }
                    }
                    arrdonhang.add(vitrisua,dh);
                    adapterDonHang = new AdapterDonHang(c,R.layout.item_hoadon,loadDonhangdanhan());
                    listView.setAdapter(adapterDonHang);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void LaydulieuDonhanghuy(){
        myDonHang.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                setdataDonhang(dataSnapshot);
                loadDonhanghuy();
                adapterDonHang = new AdapterDonHang(c,R.layout.item_hoadon,loadDonhanghuy());
                listView.setAdapter(adapterDonHang);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.child("tinhtrangdonhang").getValue().equals("3")){
                    String donhang = dataSnapshot.child("ngaymua").getValue().toString();
                    XuatThongBao(c.getResources().getString(R.string.code_dbdonhang_order)+" " +donhang +
                            " "+c.getResources().getString(R.string.code_dbdonhang_dahuy),c.getResources().getString(R.string.code_dbdonhang_dahuy2),
                            c.getResources().getString(R.string.code_dbdonhang_order)+" " +donhang+"\n"+c.getResources().getString(R.string.code_dbdonhang_dahuy));
                    Donhang dh = Donhangsua(dataSnapshot);
                    int vitrisua = -1;
                    for (int i = 0; i < arrdonhang.size(); i++) {
                        if (arrdonhang.get(i).getIddonhang().equals(dataSnapshot.getKey())) {
                            vitrisua = i;
                            break;
                        }
                    }
                    arrdonhang.add(vitrisua,dh);
                    adapterDonHang = new AdapterDonHang(c,R.layout.item_hoadon,loadDonhanghuy());
                    listView.setAdapter(adapterDonHang);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private  void setdataDonhang(DataSnapshot dataSnapshot){
        donhang = new Donhang();
        //iddonhang, idnguoimua,ngaymua,tinhtrangdonhang,sosanphammua,soluongkg,tongtien;
        donhang.setIddonhang(dataSnapshot.getValue(Donhang.class).getIddonhang());
        donhang.setIdnguoimua(dataSnapshot.getValue(Donhang.class).getIdnguoimua());
        donhang.setNgaymua(dataSnapshot.getValue(Donhang.class).getNgaymua());
        donhang.setSosanphammua(dataSnapshot.getValue(Donhang.class).getSosanphammua());
        donhang.setSoluongkg(dataSnapshot.getValue(Donhang.class).getSoluongkg());
        donhang.setTongtien(dataSnapshot.getValue(Donhang.class).getTongtien());
        donhang.setTinhtrangdonhang(dataSnapshot.getValue(Donhang.class).getTinhtrangdonhang());
        donhang.setSanphammua(null);

        arrdonhang.add(donhang);
    }
    private Donhang Donhangsua(DataSnapshot dataSnapshot){
        donhang = new Donhang();
        //iddonhang, idnguoimua,ngaymua,tinhtrangdonhang,sosanphammua,soluongkg,tongtien;
        donhang.setIddonhang(dataSnapshot.getValue(Donhang.class).getIddonhang());
        donhang.setIdnguoimua(dataSnapshot.getValue(Donhang.class).getIdnguoimua());
        donhang.setNgaymua(dataSnapshot.getValue(Donhang.class).getNgaymua());
        donhang.setSosanphammua(dataSnapshot.getValue(Donhang.class).getSosanphammua());
        donhang.setSoluongkg(dataSnapshot.getValue(Donhang.class).getSoluongkg());
        donhang.setTongtien(dataSnapshot.getValue(Donhang.class).getTongtien());
        donhang.setTinhtrangdonhang(dataSnapshot.getValue(Donhang.class).getTinhtrangdonhang());
        donhang.setSanphammua(null);

        return donhang;
    }


    private void loadDonhang(){
        if (arrdonhang.size()>0){
            int i =0;
            String nguoidunghientai = MyAplication.getInstance().getIdnguoidung();
            ArrayList<Donhang> arr = new ArrayList<Donhang>();
            while (i< arrdonhang.size()){
                if (nguoidunghientai.equals(arrdonhang.get(i).getIdnguoimua())){
                    arr.add(arrdonhang.get(i));

                    adapterDonHang = new AdapterDonHang(c, R.layout.item_hoadon,arr);
                    listView.setAdapter(adapterDonHang);
                    adapterDonHang.notifyDataSetChanged();
                }
                i++;
            }
        } else {
            // Không có dữ liệu trả về
            customToast  = new CustomToast(c);
            customToast.Customtoastfail(c.getResources().getString(R.string.code_db_resulterror));
        }
    }

    private ArrayList<Donhang> loadDonhangcho(){
        ArrayList<Donhang> arr = new ArrayList<Donhang>();
        if (arrdonhang.size()>0){
            int i =0;
            String nguoidunghientai = MyAplication.getInstance().getIdnguoidung();
            while (i< arrdonhang.size()){
                if (nguoidunghientai.equals(arrdonhang.get(i).getIdnguoimua()) && arrdonhang.get(i).getTinhtrangdonhang().equals("0")){
                    arr.add(arrdonhang.get(i));
                }
                i++;
            }
        } else {
            // Không có dữ liệu trả về
            customToast  = new CustomToast(c);
            customToast.Customtoastfail(c.getResources().getString(R.string.code_db_resulterror));
        }
        return arr;
    }

    private ArrayList<Donhang> loadDonhangdanggui(){
        ArrayList<Donhang> arr = new ArrayList<Donhang>();
        if (arrdonhang.size()>0){
            int i =0;
            String nguoidunghientai = MyAplication.getInstance().getIdnguoidung();
            while (i< arrdonhang.size()){
                if (nguoidunghientai.equals(arrdonhang.get(i).getIdnguoimua()) && arrdonhang.get(i).getTinhtrangdonhang().equals("1")){
                    arr.add(arrdonhang.get(i));
                }
                i++;
            }
        } else {
            // Không có dữ liệu trả về
            customToast  = new CustomToast(c);
            customToast.Customtoastfail(c.getResources().getString(R.string.code_db_resulterror));
        }
        return arr;
    }
    private ArrayList<Donhang> loadDonhangdanhan(){
        ArrayList<Donhang> arr = new ArrayList<Donhang>();
        if (arrdonhang.size()>0){
            int i =0;
            String nguoidunghientai = MyAplication.getInstance().getIdnguoidung();
            while (i< arrdonhang.size()){
                if (nguoidunghientai.equals(arrdonhang.get(i).getIdnguoimua()) && arrdonhang.get(i).getTinhtrangdonhang().equals("2")){
                    arr.add(arrdonhang.get(i));
                }
                i++;
            }
        } else {
            // Không có dữ liệu trả về
            customToast  = new CustomToast(c);
            customToast.Customtoastfail(c.getResources().getString(R.string.code_db_resulterror));
        }
        return arr;
    }
    private ArrayList<Donhang> loadDonhanghuy(){
        ArrayList<Donhang> arr = new ArrayList<Donhang>();
        if (arrdonhang.size()>0){
            int i =0;
            String nguoidunghientai = MyAplication.getInstance().getIdnguoidung();

            while (i< arrdonhang.size()){
                if (nguoidunghientai.equals(arrdonhang.get(i).getIdnguoimua()) && arrdonhang.get(i).getTinhtrangdonhang().equals("3")){
                    arr.add(arrdonhang.get(i));
                }
                i++;
            }
        } else {
            // Không có dữ liệu trả về
            customToast  = new CustomToast(c);
            customToast.Customtoastfail(c.getResources().getString(R.string.code_db_resulterror));
        }
        return arr;
    }

}

