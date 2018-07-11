package com.tuanbapk.banrau.View.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.tuanbapk.banrau.Model.MyAplication;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.Flagment.FragmentAbouts;
import com.tuanbapk.banrau.View.Flagment.FragmentDonHang;
import com.tuanbapk.banrau.View.Flagment.FragmentGiohang;
import com.tuanbapk.banrau.View.Flagment.FragmentNguoidung;
import com.tuanbapk.banrau.View.Flagment.FragmentTygia;
import com.tuanbapk.banrau.View.Flagment.FragmentYeuThich;
import com.tuanbapk.banrau.View.Flagment.FragmentgvSanPham;
import com.tuanbapk.banrau.View.Flagment.FragmentlvSanPham;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainSanPham extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentlvSanPham fragmentlvSanPham;
    private FragmentgvSanPham fragmentgvSanPham;
    private FragmentNguoidung fragmentNguoidung;
    private FragmentGiohang fragmentGiohang;
    private FragmentDonHang fragmentDonHang;
    private FragmentYeuThich fragmentYeuThich;
    private FragmentTygia fragmentTygia;
//    private FragmentAbouts fragmentAbouts;

    public static String idnguoidung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_san_pham);
        setTitle(getResources().getString(R.string.mcode_mainsp_title));



        initidnguoidung();
        initDrawer();
        inintFragment();
        getSupportFragmentManager().beginTransaction()
                .show(fragmentgvSanPham)
                .hide(fragmentlvSanPham)
                .hide(fragmentNguoidung)
                .hide(fragmentGiohang)
                .hide(fragmentDonHang)
                .hide(fragmentYeuThich)
//                .hide(fragmentAbouts)
                .hide(fragmentTygia)
                .commit();

    }

    private void initidnguoidung() {
        Intent intent = MainSanPham.this.getIntent();
        if (intent == null){
            return;
        }
        idnguoidung = intent.getStringExtra("tennguoidung");
        MyAplication.getInstance().setIdnguoidung(idnguoidung);


    }

    private void inintFragment() {
        // Fragment lvSanpham
        fraglvSanpham();
        //Fragment gvSanPham
        fraggvSanpham();
        //Fragment Nguoidung
        fragNguoidung();
        //Fragment Giohang
        fragGiohang();
        // Fragment DonHang
        fragDonhang();

        fragyeuthichsp();
//        fragmentAbouts = new FragmentAbouts();
        fragmentTygia = new FragmentTygia();


        getSupportFragmentManager().beginTransaction()
                .add(R.id.ln_mainsanpham,fragmentlvSanPham,FragmentlvSanPham.class.getName())
                .add(R.id.ln_mainsanpham,fragmentgvSanPham,FragmentgvSanPham.class.getName())
                .add(R.id.ln_mainsanpham,fragmentNguoidung,FragmentNguoidung.class.getName())
                .add(R.id.ln_mainsanpham,fragmentGiohang,FragmentGiohang.class.getName())
                .add(R.id.ln_mainsanpham,fragmentDonHang,FragmentDonHang.class.getName())
                .add(R.id.ln_mainsanpham,fragmentYeuThich, FragmentYeuThich.class.getName())
//                .add(R.id.ln_mainsanpham,fragmentAbouts, FragmentAbouts.class.getName())
                .add(R.id.ln_mainsanpham,fragmentTygia,FragmentTygia.class.getName())
                .commit();


    }





    private void initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_san_pham, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // xử lí sự kiện cài đặt cho ứng dụng

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_thongtinuser) {
            // Sự kiện chuyển vào màn hình thông tin tài khoản
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentlvSanPham)
                        .hide(fragmentgvSanPham)
                        .hide(fragmentGiohang)
                        .hide(fragmentDonHang)
                        .hide(fragmentYeuThich)
                    .hide(fragmentTygia)
//                        .hide(fragmentAbouts)
                    .show(fragmentNguoidung)
                    .commit();
        } else if (id == R.id.nav_sanpham) {
            // Sự kiện chuyển về main sản phẩm
            // Chuyển về flagment sản phẩm
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentNguoidung)
                        .hide(fragmentGiohang)
                        .hide(fragmentDonHang)
                        .hide(fragmentYeuThich)
                        .hide(fragmentlvSanPham)
                    .hide(fragmentTygia)
//                        .hide(fragmentAbouts)
                    .show(fragmentgvSanPham)
                    .commit();
        } else if (id == R.id.nav_Spyeuthich) {
            // Sự kiện sang main sản phẩm yêu thích
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentDonHang)
                        .hide(fragmentgvSanPham)
                        .hide(fragmentlvSanPham)
                        .hide(fragmentNguoidung)
                        .hide(fragmentGiohang)
                    .hide(fragmentTygia)
//                        .hide(fragmentAbouts)
                    .show(fragmentYeuThich)
                    .commit();

        } else if (id == R.id.nav_lsdonhang) {
            // Sự kiện vào main đơn hàng
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out)
                    .hide(fragmentgvSanPham)
                    .hide(fragmentlvSanPham)
                    .hide(fragmentNguoidung)
                    .hide(fragmentGiohang)
                    .hide(fragmentYeuThich)
                    .hide(fragmentTygia)
//                        .hide(fragmentAbouts)
                    .show(fragmentDonHang)
                    .commit();
        }else if (id == R.id.nav_lstygia){
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out)
                    .hide(fragmentgvSanPham)
                    .hide(fragmentlvSanPham)
                    .hide(fragmentNguoidung)
                    .hide(fragmentGiohang)
                    .hide(fragmentYeuThich)
//                        .hide(fragmentAbouts)
                    .hide(fragmentDonHang)
                    .show(fragmentTygia)
                    .commit();
        } else if (id == R.id.nav_thongtinstore) {
            // Sự kiện vào main thông tin cửa hàng
//            getSupportFragmentManager().beginTransaction()
//            .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
//                    .hide(fragmentlvSanPham)
//                    .hide(fragmentgvSanPham)
//                    .hide(fragmentGiohang)
//                    .hide(fragmentDonHang)
//                    .hide(fragmentYeuThich)
//                    .hide(fragmentNguoidung)
////                    .show(fragmentAbouts)
//                    .commit();
            Intent intent = new Intent();
            intent.setClass(this, MainAboutus.class);startActivity(intent);

        } else if (id == R.id.nav_dangxuat) {
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent();
                intent.setClass(MainSanPham.this, MainDangNhap.class);
                startActivity(intent);
                finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private  void fraglvSanpham(){
        //Phần tạo mới của Fragment ListviewSanpham
        fragmentlvSanPham = new FragmentlvSanPham();

        fragmentlvSanPham.setListener(new FragmentlvSanPham.OnclickButton1Listener() {
            @Override
            public void clickButton() {
                // ẩn listview hiện gridview
                getSupportFragmentManager().beginTransaction()
                        .hide(fragmentlvSanPham)
                        .show(fragmentgvSanPham)
                        .commit();
            }
        });
        fragmentlvSanPham.setGiohanglistener(new FragmentlvSanPham.OnclickgiohangListener() {
            @Override
            public void clickGiohang() {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentlvSanPham)
                        .show(fragmentGiohang)
                        .commit();
            }
        });
        fragmentlvSanPham.setTklistenner(new FragmentlvSanPham.OnclickThongTinTKListener() {
            @Override
            public void clickThongtinTK() {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentlvSanPham)
                        .show(fragmentNguoidung)
                        .commit();
            }
        });
    }

    private void fragyeuthichsp(){
        fragmentYeuThich = new FragmentYeuThich();
        fragmentYeuThich.setOnclickcartListener(new FragmentYeuThich.OnclickcartListener() {
            @Override
            public void clickcart() {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentYeuThich)
                        .show(fragmentGiohang)
                        .commit();
            }
        });

        fragmentYeuThich.setOnclicthongtinuser(new FragmentYeuThich.Onclicthongtinuser() {
            @Override
            public void clickuser() {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentYeuThich)
                        .show(fragmentNguoidung)
                        .commit();
            }
        });
    }

    private  void fraggvSanpham(){
        //Phần tạo mới của Fragment GridviewSanpham
        fragmentgvSanPham = new FragmentgvSanPham();

        fragmentgvSanPham.setListener(new FragmentgvSanPham.OnclickButton1Listener() {
            @Override
            public void clickButton() {
                //ẩn gridview hiện listview
                getSupportFragmentManager().beginTransaction()
                        .hide(fragmentgvSanPham)
                        .show(fragmentlvSanPham)
                        .commit();
            }
        });
        fragmentgvSanPham.setGiohanglistener(new FragmentgvSanPham.OnclickgiohangListener() {
            @Override
            public void clickgiohang() {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentgvSanPham)
                        .show(fragmentGiohang)
                        .commit();
            }
        });

        fragmentgvSanPham.setThongtintklistener(new FragmentgvSanPham.OnClickThongTinCaNhan() {
            @Override
            public void clickThongTin() {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentgvSanPham)
                        .show(fragmentNguoidung)
                        .commit();
            }
        });
    }

    private void fragNguoidung(){
        //Fragment của người dùng
        fragmentNguoidung = new FragmentNguoidung();

        fragmentNguoidung.setListener(new FragmentNguoidung.Onclickbuttonlistener() {
            @Override
            public void clickFragnguoidung() {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentNguoidung)
                        .show(fragmentgvSanPham)
                        .commit();
            }
        });
    }

    private void fragGiohang(){
        // Fragment của Giỏ Hàng
        fragmentGiohang = new FragmentGiohang();
        fragmentGiohang.setListener(new FragmentGiohang.Onclickbuttonlistener() {
            @Override
            public void ClickButton() {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentGiohang)
                        .show(fragmentgvSanPham)
                        .commit();
            }
        });
    }

    private void fragDonhang(){
        fragmentDonHang = new FragmentDonHang();
        fragmentDonHang.setListenerdonhang(new FragmentDonHang.OnclickButtonListenerDonhang() {
            @Override
            public void clickButton() {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentDonHang)
                        .show(fragmentgvSanPham)
                        .commit();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (fragmentNguoidung.isVisible()){
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentNguoidung)
                        .show(fragmentgvSanPham)
                        .commit();
            } else if (fragmentYeuThich.isVisible()){
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentYeuThich)
                        .show(fragmentgvSanPham)
                        .commit();
            } else if (fragmentDonHang.isVisible()){
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentDonHang)
                        .show(fragmentgvSanPham)
                        .commit();
            }else if (fragmentGiohang.isVisible()){
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentGiohang)
                        .show(fragmentgvSanPham)
                        .commit();
            }else if (fragmentTygia.isVisible()){
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
                        .hide(fragmentTygia)
                        .show(fragmentgvSanPham)
                        .commit();
            }else if (fragmentgvSanPham.isVisible() || fragmentlvSanPham.isVisible()){
               PopupdialogDongApp(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void PopupdialogDongApp(Context c){
        final AlertDialog d = new AlertDialog.Builder(c).create();
        d.setTitle(getResources().getString(R.string.mcode_mainsp_logout));

        d.setMessage(getResources().getString(R.string.mcode_mainsp_logout_content));
        // dùng Cancel khi chạm vào vùng ngoài dialog cũng ko làm tắt dialog
        d.setCanceledOnTouchOutside(false);
        // set icon cho dialog
        d.setIcon(R.drawable.icon_offf);
        d.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.mcode_button_logout_btnexit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                d.dismiss();
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
