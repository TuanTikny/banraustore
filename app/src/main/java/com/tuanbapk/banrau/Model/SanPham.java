package com.tuanbapk.banrau.Model;

/**
 * Created by buituan on 2017-10-27.
 */

public class SanPham {

    String idSanPham;
    String TenSanPham;
    String GiaSanPham;
    String MoTa;
    String Soluong;
    String Donvi;
    String LoaiSanPham;
    String Hinh;

    public SanPham() {

    }

    public SanPham(String idSanPham, String tenSanPham, String giaSanPham, String moTa, String soluong, String donvi, String loaiSanPham, String hinh) {
        this.idSanPham = idSanPham;
        TenSanPham = tenSanPham;
        GiaSanPham = giaSanPham;
        MoTa = moTa;
        Soluong = soluong;
        Donvi = donvi;
        LoaiSanPham = loaiSanPham;
        Hinh = hinh;
    }

    public String getDonvi() {
        return Donvi;
    }

    public void setDonvi(String donvi) {
        Donvi = donvi;
    }

    public String getLoaiSanPham() {
        return LoaiSanPham;
    }

    public void setLoaiSanPham(String loaiSanPham) {
        LoaiSanPham = loaiSanPham;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public String getGiaSanPham() {
        return GiaSanPham;
    }

    public void setGiaSanPham(String giaSanPham) {
        GiaSanPham = giaSanPham;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public String getSoluong() {
        return Soluong;
    }

    public void setSoluong(String soluong) {
        Soluong = soluong;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String hinh) {
        Hinh = hinh;
    }
}
