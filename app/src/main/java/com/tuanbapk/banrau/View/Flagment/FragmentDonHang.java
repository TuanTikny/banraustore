package com.tuanbapk.banrau.View.Flagment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;


import com.tuanbapk.banrau.Presenter.FirebaseDonHang;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.MyAnimation.myanimation;

/**
 * Created by buituan on 2017-11-25.
 */

public class FragmentDonHang extends Fragment implements View.OnClickListener {

    private View rootview;
    private OnclickButtonListenerDonhang listenerdonhang;
    private ImageView btncho,btngui,btnnhan,btnhuy;
    private LinearLayout tabcustomlayout;
    private TextView tab1_title,tab2_title,tab3_title,tab4_title;


    private ListView listcho, listgui, listnhan, listhuy;
    TabHost tabHost;
    myanimation animationcustom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_donhang,container, false);

        mapped();
        Goitab();

        FirebaseDonHang fcho = new FirebaseDonHang(rootview.getContext(),listcho);
        fcho.LaydulieuDonhangcho();
        FirebaseDonHang fgui = new FirebaseDonHang(rootview.getContext(),listgui);
        fgui.LaydulieuDonhanggui();
        FirebaseDonHang fnhan = new FirebaseDonHang(rootview.getContext(),listnhan);
        fnhan.LaydulieuDonhangnhan();
        FirebaseDonHang fhuy = new FirebaseDonHang(rootview.getContext(),listhuy);
        fhuy.LaydulieuDonhanghuy();

        sukienchuyentabhost();

//        AdView adView = (AdView) rootview.findViewById(R.id.adView);
//        adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);



        return rootview;
    }

    private void mapped() {
        listcho = (ListView) rootview.findViewById(R.id.lv_donhangcho);
        listgui = (ListView) rootview.findViewById(R.id.lv_donhanggui);
        listnhan = (ListView) rootview.findViewById(R.id.lv_donhangnhan);
        listhuy = (ListView) rootview.findViewById(R.id.lv_donhanghuy);
        tabHost = (TabHost) rootview.findViewById(R.id.donhang_tabhost);

        tab1_title = rootview.findViewById(R.id.tab1_title);
        tab2_title = rootview.findViewById(R.id.tab2_title);
        tab3_title = rootview.findViewById(R.id.tab3_title);
        tab4_title = rootview.findViewById(R.id.tab4_title);

        tabcustomlayout = (LinearLayout) rootview.findViewById(R.id.tabcustom_layout);
        btncho = rootview.findViewById(R.id.img_tabcho);
        btngui = rootview.findViewById(R.id.img_tabgui);
        btnnhan = rootview.findViewById(R.id.img_tabdanhan);
        btnhuy = rootview.findViewById(R.id.img_tabhuy);
        btncho.setBackgroundResource(R.drawable.bg_itemtabdonhang);

    }

    private void sukienchuyentabhost(){
        btncho.setOnClickListener(this);
        btngui.setOnClickListener(this);
        btnnhan.setOnClickListener(this);
        btnhuy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        animationcustom = new myanimation();
        switch (view.getId()){
            case R.id.img_tabcho:
                tabHost.setCurrentTab(0);
                animationcustom.makeAnimation(rootview.getContext(),view,R.anim.anim_scale1);
                btncho.setBackgroundResource(R.drawable.bg_itemtabdonhang);
                btnnhan.setBackgroundResource(R.drawable.bg_tabhostmacdinh);
                btngui.setBackgroundResource(R.drawable.bg_tabhostmacdinh);
                btnhuy.setBackgroundResource(R.drawable.bg_tabhostmacdinh);

                break;
            case R.id.img_tabgui:
                tabHost.setCurrentTab(1);
                animationcustom.makeAnimation(rootview.getContext(),view,R.anim.anim_scale1);
                btngui.setBackgroundResource(R.drawable.bg_itemtabdonhang);
                btnnhan.setBackgroundResource(R.drawable.bg_tabhostmacdinh);
                btncho.setBackgroundResource(R.drawable.bg_tabhostmacdinh);
                btnhuy.setBackgroundResource(R.drawable.bg_tabhostmacdinh);
                break;
            case R.id.img_tabdanhan:
                tabHost.setCurrentTab(2);
                animationcustom.makeAnimation(rootview.getContext(),view,R.anim.anim_scale1);
                btnnhan.setBackgroundResource(R.drawable.bg_itemtabdonhang);
                btncho.setBackgroundResource(R.drawable.bg_tabhostmacdinh);
                btngui.setBackgroundResource(R.drawable.bg_tabhostmacdinh);
                btnhuy.setBackgroundResource(R.drawable.bg_tabhostmacdinh);
                break;
            case R.id.img_tabhuy:
                tabHost.setCurrentTab(3);
                animationcustom.makeAnimation(rootview.getContext(),view,R.anim.anim_scale1);
                btnhuy.setBackgroundResource(R.drawable.bg_itemtabdonhang);
                btnnhan.setBackgroundResource(R.drawable.bg_tabhostmacdinh);
                btngui.setBackgroundResource(R.drawable.bg_tabhostmacdinh);
                btncho.setBackgroundResource(R.drawable.bg_tabhostmacdinh);
                break;
            default:
                break;
        }
    }

    private void Goitab() {
        tabHost.setup();
      //  tabHost.getTabWidget().setAnimation(AnimationUtils.loadAnimation(rootview.getContext(),R.anim.anim_rotate30));

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
       tab1.setIndicator("",getResources().getDrawable(R.drawable.ic_boxguidi));
        tabHost.addTab(tab1);


        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("",getResources().getDrawable(R.drawable.ic_hopgiaohang));
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
       tab3.setIndicator("",getResources().getDrawable(R.drawable.ic_hopdagiao));
        tabHost.addTab(tab3);

        TabHost.TabSpec tab4 = tabHost.newTabSpec("t4");
        tab4.setContent(R.id.tab4);
        tab4.setIndicator("",getResources().getDrawable(R.drawable.ic_hophuy));
        tabHost.addTab(tab4);

    }


    public void setListenerdonhang(OnclickButtonListenerDonhang listenerdonhang) {
        this.listenerdonhang = listenerdonhang;
    }

    public interface OnclickButtonListenerDonhang{
        void clickButton();
    }

}
