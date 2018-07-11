package com.tuanbapk.banrau.View.Flagment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tuanbapk.banrau.Presenter.FirebaseNguoiDung;
import com.tuanbapk.banrau.Presenter.FirebaseSanPham;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.Activity.MainSanPham;
import com.tuanbapk.banrau.View.MyAnimation.CustomToast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.katso.livebutton.LiveButton;

import static android.app.Activity.RESULT_OK;

/**
 * Created by buituan on 2017-10-29.
 */

@SuppressWarnings("VisibleForTests")
public class FragmentNguoidung extends Fragment {

    private View rootview;
    private Onclickbuttonlistener listener;

    public static CircleImageView tk_img_nguoidung;
    public static EditText tk_edt_hoten, tk_edt_sdt, tk_edt_diachi, tk_edt_motakhac, tk_edt_hinh;
    LiveButton tk_btn_capnhat;

    FirebaseNguoiDung firebaseNguoiDung;
    CustomToast customToast;
    boolean flag;

    final int RQ_TAKE_PHOTO = 1;
    final int RQ_CHOSE_PHOTO = 2;

//    private AdRequest adRequest1;
//
//    private InterstitialAd fullAd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_thongtintk,container,false);

        mapped();
        firebaseNguoiDung = new FirebaseNguoiDung(rootview.getContext());
        firebaseNguoiDung.LaydataTK();
        disabledt();

//        adRequest1 = new AdRequest.Builder().build();
//
//        // show quang cao full screen
//        fullAd = new InterstitialAd(rootview.getContext());
//        fullAd.setAdListener(new AdListener(){
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//                fullAd.loadAd(adRequest1);
//            }
//        });
//
//        fullAd.setAdUnitId(getResources().getString(R.string.full_bon));
//        fullAd.loadAd(adRequest1);


        return rootview;
    }

    private void mapped() {
        // Ánh xạ vào FragmentNguoidung
        tk_img_nguoidung = (CircleImageView) rootview.findViewById(R.id.tk_img_nguoidung);
        tk_edt_hoten = (EditText) rootview.findViewById(R.id.tk_edit_hoten);
        tk_edt_sdt = (EditText) rootview.findViewById(R.id.tk_edit_sdt);
        tk_edt_diachi = (EditText) rootview.findViewById(R.id.tk_edit_diachi);
        tk_edt_motakhac = (EditText) rootview.findViewById(R.id.tk_edit_motakhac);
        tk_edt_hinh = (EditText) rootview.findViewById(R.id.tk_edt_hinh);

        tk_btn_capnhat = (LiveButton) rootview.findViewById(R.id.tk_btn_capnhat);

        // Sự kiện khi chọn vào hình
        tk_img_nguoidung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xin quyền đối với android trên API 21
                ActivityCompat.requestPermissions((Activity) rootview.getContext(), new String[]{Manifest.permission.CAMERA}, RQ_TAKE_PHOTO);
                ActivityCompat.requestPermissions((Activity) rootview.getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RQ_CHOSE_PHOTO);
                Showmenuchohinh(view);
            }
        });

        // Sự kiện cập nhật thông tin người dùng
        tk_btn_capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show quang cao full khi dung
//                fullAd.show();
                // Lấy các thông tin hiện thời
                if (flag){
                    Capnhatthongtin();
                    disabledt();
                    customToast = new CustomToast(rootview.getContext());
                    customToast.Customtoastsuccess1(getResources().getString(R.string.fcode_account_saveinfor));
                    tk_btn_capnhat.setText("   "+getResources().getString(R.string.fcode_account_btnchange));
                } else {
                    enableedt();
                    tk_btn_capnhat.setText("   "+getResources().getString(R.string.fcode_account_btnsave));
                }

            }
        });


    }
    private void disabledt(){
        tk_edt_hoten.setEnabled(false);
        tk_edt_sdt.setEnabled(false);
        tk_edt_diachi.setEnabled(false);
        tk_edt_motakhac.setEnabled(false);
        tk_img_nguoidung.setEnabled(false);
        flag = false;
    }
    private void enableedt(){
        tk_edt_hoten.setEnabled(true);
        tk_edt_sdt.setEnabled(true);
        tk_edt_diachi.setEnabled(true);
        tk_edt_motakhac.setEnabled(true);
        tk_img_nguoidung.setEnabled(true);
        flag = true;
    }

    private void Capnhatthongtin(){
        String id,ten,sdt,diachi,motakhac,hinh;
        id = MainSanPham.idnguoidung;
        ten = tk_edt_hoten.getText().toString();
        sdt = tk_edt_sdt.getText().toString();
        diachi = tk_edt_diachi.getText().toString();
        motakhac = tk_edt_motakhac.getText().toString();
        hinh = tk_edt_hinh.getText().toString();

        firebaseNguoiDung.addThongtinTK(id,ten,sdt,diachi,motakhac,hinh);
    }


    /// Các Hàm xử lí úp sửa hình

    private void Showmenuchohinh(View view){
        final PopupMenu popupMenu = new PopupMenu(rootview.getContext(),view);
        popupMenu.getMenuInflater().inflate(R.menu.chose_take_photo,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.itemmenu_chonhinh){
                    // Gọi hàm chọn hình
                    Chonhinh();
                } else if (menuItem.getItemId()==R.id.itemmenu_chuphinh){
                    // Gọi hàm chụp hình
                    Chuphinh();
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void Chuphinh(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,RQ_TAKE_PHOTO);
    }
    private void Chonhinh(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,RQ_CHOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        if (resultCode ==RESULT_OK){
            if (requestCode ==RQ_CHOSE_PHOTO){
                try {
                    Uri imgUri = data.getData();
                    InputStream is = rootview.getContext().getContentResolver().openInputStream(imgUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    tk_img_nguoidung.setImageBitmap(bitmap);

                    Calendar calendar = Calendar.getInstance();
                    StorageReference hinhchon = storageRef.child("Hinhnguoidung"+calendar.getTimeInMillis()+".png");

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data1 = baos.toByteArray();

                    UploadTask uploadTask = hinhchon.putBytes(data1);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            customToast.Customtoastfail(getResources().getString(R.string.fcode_account_pleaseretry));
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            String UrlHinh = downloadUrl.toString();
                            tk_edt_hinh.setText(UrlHinh);
                        }
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode ==RQ_TAKE_PHOTO){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    tk_img_nguoidung.setImageBitmap(bitmap);

                Calendar calendar = Calendar.getInstance();
                StorageReference hinhchup = storageRef.child("Hinhnguoidung"+calendar.getTimeInMillis()+".png");

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data1 = baos.toByteArray();

                UploadTask uploadTask = hinhchup.putBytes(data1);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        customToast.Customtoastfail(getResources().getString(R.string.fcode_account_pleaseretry));
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        String UrlHinh = downloadUrl.toString();
                        tk_edt_hinh.setText(UrlHinh);
                    }
                });

            }
        }
    }


    public void setListener(Onclickbuttonlistener listener) {
        this.listener = listener;
    }

    public interface Onclickbuttonlistener{
        void clickFragnguoidung();
    }
}
