package com.tuanbapk.banrau.View.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.MyAnimation.CustomToast;

import java.util.Locale;

/**
 * Created by buituan on 2017-12-26.
 */

public class MainKhoiDong extends AppCompatActivity {
    Spinner spinner;
    Button tieptuc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainkhoidong);
        mapped();
    }

    private void mapped() {
        tieptuc = findViewById(R.id.btn_tieptheo);
        spinner = findViewById(R.id.spinner_choselaguage);

        tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainKhoiDong.this,MainDangNhap.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //B1: Xác định ngôn ngữ hiện tại
                String laghientai = PreferenceManager.getDefaultSharedPreferences(MainKhoiDong.this).getString("LANG","");
                //B2: Xác định ngôn ngữ đã chọn
                String lagchon ="";
                switch (i){
                    case 0:lagchon ="";break;
                    case 1:lagchon ="en";break;
                }
                if (laghientai!=lagchon){
                    chonngonngu(lagchon);
                }
                //B3: Thay đổi ngôn ngữ
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void chonngonngu(String ngonngu){
        PreferenceManager.getDefaultSharedPreferences(MainKhoiDong.this).edit().putString("LANG",ngonngu).commit();
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = new Locale(ngonngu);
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        recreate();
        CustomToast customToast= new CustomToast(this);
        customToast.Customtoastsuccessgoogle(getResources().getString(R.string.languge_name));
    }
}
