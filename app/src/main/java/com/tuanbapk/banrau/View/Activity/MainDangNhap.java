package com.tuanbapk.banrau.View.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.tuanbapk.banrau.Presenter.Broadcast;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.Flagment.FragmentDangKy;
import com.tuanbapk.banrau.View.Flagment.FragmentDangnhap;
import com.tuanbapk.banrau.View.MyAnimation.myanimation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by buituan on 2017-10-27.
 */

public class MainDangNhap extends AppCompatActivity  {
    Intent intent;
    private FragmentDangKy fragmentDangKy;
    private FragmentDangnhap fragmentDangnhap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dangnhap);
        setTitle("");

        // Lấy keyhash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.tuanbapk.banrau",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        intent = new Intent(MainDangNhap.this,Broadcast.class);
        sendBroadcast(intent);




        initFragment();

        // fragment đăng nhập mặc định sẽ mở đăng ký thì đóng
        getSupportFragmentManager().beginTransaction().show(fragmentDangnhap).hide(fragmentDangKy).commit();
    }

    private void initFragment() {
        fragmentDangKy = new FragmentDangKy();

        fragmentDangKy.setListener(new FragmentDangKy.OnclickButton1Listener() {
            @Override
            public void clickButton() {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.right_out,R.anim.right_in,R.anim.left_in,R.anim.left_out)
                        .hide(fragmentDangKy)
                        .show(fragmentDangnhap).commit();
            }
        });

        fragmentDangnhap = new FragmentDangnhap();

        fragmentDangnhap.setListener(new FragmentDangnhap.OnclickButton1Listener() {
            @Override
            public void clickButton() {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.left_out2,R.anim.right_out,R.anim.right_in)
                        .show(fragmentDangKy)
                        .hide(fragmentDangnhap)
                        .commit();
            }
        });

        fragmentDangnhap.setKetthuc(new FragmentDangnhap.OnclickFinish() {
            @Override
            public void clickfinish() {
                finish();
            }
        });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.ln_dangnhap,fragmentDangKy,FragmentDangKy.class.getName())
                .add(R.id.ln_dangnhap,fragmentDangnhap,FragmentDangnhap.class.getName())
                .commit();

    }
}
