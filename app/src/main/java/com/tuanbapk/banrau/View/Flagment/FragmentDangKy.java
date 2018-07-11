package com.tuanbapk.banrau.View.Flagment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tuanbapk.banrau.Presenter.FirebaseNguoiDung;
import com.tuanbapk.banrau.Presenter.FirebaseSanPham;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.MyAnimation.CustomToast;
import com.tuanbapk.banrau.View.MyAnimation.myanimation;


import static com.tuanbapk.banrau.Presenter.Checknhap.CheckDK;
import static com.tuanbapk.banrau.View.Flagment.FragmentDangnhap.edt_pass;
import static com.tuanbapk.banrau.View.Flagment.FragmentDangnhap.edt_user;

/**
 * Created by buituan on 2017-10-28.
 */

public class FragmentDangKy extends Fragment implements View.OnClickListener {

    private View rootView;
    private OnclickButton1Listener listener;

    private EditText taikhoandk, matkhau1, matkhau2;
    public static TextInputLayout tendangkylayout, matkhau1layout, matkhau2layout;
    private Button btndongy, btnhuy;
    public static TextView dangkykhongthanhcong;

    CustomToast customToast;
    FirebaseNguoiDung firebaseNguoiDung;

    FirebaseAuth auth;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_dangky,container,false);
        auth = FirebaseAuth.getInstance();
        customToast = new CustomToast(rootView.getContext());
        firebaseNguoiDung = new FirebaseNguoiDung(rootView.getContext());
        mapped();
        return rootView;
    }

    public void setListener(OnclickButton1Listener listener) {
        this.listener = listener;
    }

    private void mapped() {
        taikhoandk = (EditText) rootView.findViewById(R.id.edit_taikhoandk);
        matkhau1 = (EditText) rootView.findViewById(R.id.edit_matkhau1);
        matkhau2 = (EditText) rootView.findViewById(R.id.edit_matkhau2);

        tendangkylayout = (TextInputLayout) rootView.findViewById(R.id.taikhoandkLayout);
        matkhau1layout = (TextInputLayout) rootView.findViewById(R.id.matkhau1Layout);
        matkhau2layout = (TextInputLayout) rootView.findViewById(R.id.matkhau2Layout);

        dangkykhongthanhcong = (TextView) rootView.findViewById(R.id.dangky_kothanhcong);

        btndongy = (Button) rootView.findViewById(R.id.btn_dangkytk);
        btnhuy = (Button) rootView.findViewById(R.id.btn_thoatdk);

        btndongy.setOnClickListener(this);
        btnhuy.setOnClickListener(this);
        taikhoandk.setOnClickListener(this);
        matkhau1.setOnClickListener(this);
        matkhau2.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_dangkytk:
                myanimation.makeAnimation(rootView.getContext(),btndongy,R.anim.anim_scale1);
                dongydangky();
                break;
            case R.id.btn_thoatdk:
                myanimation.makeAnimation(rootView.getContext(),btnhuy,R.anim.anim_scale1);
                thoatfragmentdangky();
                break;
            case R.id.edit_taikhoandk:
                taikhoandk.setBackgroundResource(R.drawable.trang_80phantram);
                tendangkylayout.setError("");
                dangkykhongthanhcong.setText("");
                break;
            case R.id.edit_matkhau1:
                matkhau1.setBackgroundResource(R.drawable.trang_80phantram);
                matkhau1layout.setError("");
                dangkykhongthanhcong.setText("");
                break;
            case R.id.edit_matkhau2:
                matkhau2.setBackgroundResource(R.drawable.trang_80phantram);
                matkhau2layout.setError("");
                dangkykhongthanhcong.setText("");
                break;
            default:
                break;
        }
    }


    private void dongydangky() {
        String gettendangky = taikhoandk.getText().toString();
        String getmatkhau1 = matkhau1.getText().toString();
        String getmatkhau2 = matkhau2.getText().toString();
        if (CheckDK(rootView.getContext(),gettendangky,getmatkhau1,getmatkhau2)){
                // Xử lí đưa vào authfirebase
            customToast.Customtoastload(getResources().getString(R.string.fcode_dn_xuli));
            Dangkythanhcong(gettendangky,getmatkhau2);


        }
    }

    public void Dangkythanhcong(final String email, final String pass){

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener((Activity) rootView.getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String id = user.getUid();
                            // Sign in success, update UI with the signed-in user's information
                            // Lấy id người dùng
                            // String id =email.substring(0,email.indexOf("@"));
                            // Tạo ra đối tượng sản phẩm yêu thích mặc định
                            // Add một tài khoản người dùng vào Firebase
                            firebaseNguoiDung.addThongtinTK(id,"","","","","");
                            customToast.Customtoastsuccess(getResources().getString(R.string.fcode_dk_register));
                            edt_user.setText(email);
                            edt_pass.setText(pass);
                            listener.clickButton();
                        } else {
                            // If sign in fails, display a message to the user.
                            customToast.Customtoastfail(getResources().getString(R.string.fcode_dk_register_error));
                        }
                    }
                });
    }



    private void thoatfragmentdangky() {
        // hàm xử lí thoát ra khỏi fragmentdangky
        listener.clickButton();
    }

    public interface OnclickButton1Listener{
        void clickButton();
    }
}
