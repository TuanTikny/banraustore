package com.tuanbapk.banrau.View.Flagment;

import android.app.Activity;
import android.content.Intent;
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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.tuanbapk.banrau.Presenter.FirebaseNguoiDung;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.Activity.MainSanPham;
import com.tuanbapk.banrau.View.MyAnimation.CustomToast;
import com.tuanbapk.banrau.View.MyAnimation.myanimation;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.tuanbapk.banrau.Presenter.Checknhap.CheckDN;

/**
 * Created by buituan on 2017-10-28.
 */

public class FragmentDangnhap extends Fragment implements View.OnClickListener {

    private View rootView;
    private OnclickButton1Listener listener;
    private OnclickFinish ketthuc;
    private FirebaseAuth mAuth;
    private FirebaseNguoiDung firebaseNguoiDung;

    public static Button btn_dangnhap, btn_dangky;
    public static EditText edt_user, edt_pass;
    public static TextInputLayout tendangnhapLayout,matkhauLayout;

    private LoginButton btn_loginfacebook;
    private CallbackManager callbackManager;
    CustomToast customToast;

    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 1234;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dangnhap,container,false);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(rootView.getContext());


        mAuth = FirebaseAuth.getInstance();
        // Hàm đăng nhập thẳng vào khi đã có đăng nhập từ trước
        Dangnhapmotlan();

        firebaseNguoiDung = new FirebaseNguoiDung(rootView.getContext());
        customToast = new CustomToast(rootView.getContext());
        //đăng nhập bằng facebook
        Dangnhapfacebook();
        // đăng nhập Google
        DangnhapGoogle();

        mapped();

        return rootView;
    }

    private void DangnhapGoogle(){
        // Yêu cầu cung cấp thông tin cơ bản
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Đăng nhập bằng google
        mGoogleSignInClient = GoogleSignIn.getClient(rootView.getContext(), gso);
        Button signInButton = rootView.findViewById(R.id.btn_loginGoogle);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) rootView.getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            customToast.Customtoastsuccessgoogle(getResources().getString(R.string.fcode_dn_logingoogle_ok));
                            FirebaseUser user = mAuth.getCurrentUser();
                            String id = user.getUid();
                            String name = user.getDisplayName();
                            String urlhinh = String.valueOf(user.getPhotoUrl());
                            firebaseNguoiDung.addTKDangnhap(id,name,urlhinh);

                            Intent intent = new Intent();
                            intent.setClass(rootView.getContext(), MainSanPham.class);
                            intent.putExtra("tennguoidung",id);
                            startActivity(intent);
                            ketthucmaindangnhap();

                        } else {
                            // If sign in fails, display a message to the user.c
                            customToast.Customtoastfail(getResources().getString(R.string.fcode_dn_logingoogle_error));
                        }

                        // ...
                    }
                });
    }



    private void Dangnhapfacebook(){
        callbackManager = CallbackManager.Factory.create();
        btn_loginfacebook =rootView.findViewById(R.id.btn_loginfacebook);

        btn_loginfacebook.setReadPermissions("email", "public_profile");
        btn_loginfacebook.setFragment(this);

        btn_loginfacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                customToast.Customtoastloadface(getResources().getString(R.string.fcode_dn_loginface_waiting));
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                /// Lỗi đăng nhập facebook
                customToast.Customtoastfail(getResources().getString(R.string.fcode_dn_loginface_error));
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                customToast.Customtoastloadfacegoogle(getResources().getString(R.string.fcode_dn_xuli));
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                // ...
            }
        }
    }

    private void handleFacebookAccessToken(AccessToken token){
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener((Activity) rootView.getContext(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    customToast.Customtoastsuccessface(getResources().getString(R.string.fcode_dn_loginface_ok));
                    FirebaseUser user = mAuth.getCurrentUser();
                    String id = user.getUid();
                    String name = user.getDisplayName();
                    String urlhinh = String.valueOf(user.getPhotoUrl());

                    firebaseNguoiDung.addTKDangnhap(id,name,urlhinh);

                    Intent intent = new Intent();
                    intent.setClass(rootView.getContext(), MainSanPham.class);
                    intent.putExtra("tennguoidung",id);
                    startActivity(intent);
                    ketthucmaindangnhap();

                } else {
                    customToast.Customtoastfail(getResources().getString(R.string.fcode_dn_loginface_error));
                }
            }
        });
    }

    private void Dangnhapmotlan(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent();
            intent.setClass(rootView.getContext(), MainSanPham.class);
            // gui UID người dùng vào trong main sản phẩm
            String id =user.getUid();
            intent.putExtra("tennguoidung",id);
            startActivity(intent);
            ketthucmaindangnhap();
        }
    }

    private void mapped() {

        btn_dangnhap = (Button) rootView.findViewById(R.id.btn_dangnhap);
        btn_dangky = (Button) rootView.findViewById(R.id.btn_dangky);

        tendangnhapLayout = (TextInputLayout) rootView.findViewById(R.id.taikhoanLayout);
        matkhauLayout = (TextInputLayout) rootView.findViewById(R.id.matkhauLayout);

        edt_user = (EditText) rootView.findViewById(R.id.edit_taikhoan);
        edt_pass = (EditText) rootView.findViewById(R.id.edit_matkhau);

        btn_dangnhap.setOnClickListener(this);
        btn_dangky.setOnClickListener(this);
        edt_user.setOnClickListener(this);
        edt_pass.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_dangnhap:
                myanimation.makeAnimation(rootView.getContext(),btn_dangnhap,R.anim.anim_scale1);
                dangnhap();
                break;
            case R.id.btn_dangky:
                myanimation.makeAnimation(rootView.getContext(),btn_dangky,R.anim.anim_scale1);
                dangky();
                break;
            case R.id.edit_taikhoan:
                edt_user.setBackgroundResource(R.drawable.trang_80phantram);
                tendangnhapLayout.setError("");
                break;

            case R.id.edit_matkhau:
                edt_pass.setBackgroundResource(R.drawable.trang_80phantram);
                matkhauLayout.setError("");
                break;
            default:
                break;
        }
    }

    private void dangnhap() {
        // Xử lí đăng nhập
        String gettendangnhap = edt_user.getText().toString();
        String getmatkhau = edt_pass.getText().toString();

        if (CheckDN(rootView.getContext(),gettendangnhap, getmatkhau)) {
            // cho phép vào màn hình Main
            customToast.Customtoastload(getResources().getString(R.string.fcode_dn_xuli));
            btn_dangnhap.setEnabled(false);
            Dangnhapthanhcong(gettendangnhap,getmatkhau);

        }
    }

    private void Dangnhapthanhcong(String user, String pass){
        mAuth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener((Activity) rootView.getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            btn_dangnhap.setEnabled(true);


                            Intent intent = new Intent();
                            intent.setClass(rootView.getContext(), MainSanPham.class);
                            // gui tên người dùng vào trong main sản phẩm

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String id = user.getUid();
                            intent.putExtra("tennguoidung",id);
                            startActivity(intent);

                            customToast.Customtoastsuccess(getResources().getString(R.string.fcode_dn_login_ok));
                            ((Activity) rootView.getContext()).finish();
                        } else {
                            btn_dangnhap.setEnabled(true);
                            customToast.Customtoastfail(getResources().getString(R.string.fcode_dn_login_error));
                        }

                        // ...
                    }
                });
    }


    public void setListener(OnclickButton1Listener listener) {
        this.listener = listener;
    }

    public void setKetthuc(OnclickFinish ketthuc) {
        this.ketthuc = ketthuc;
    }

    private void ketthucmaindangnhap(){
        ketthuc.clickfinish();
    }

    private void dangky() {
        // gọi
        listener.clickButton();
    }

    public interface OnclickButton1Listener{
        void clickButton();
    }

    public interface OnclickFinish{
        void clickfinish();
    }
}
