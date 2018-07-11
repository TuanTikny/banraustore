package com.tuanbapk.banrau.Model;

/**
 * Created by buituan on 2017-10-29.
 */

public class Donhang {
    String iddonhang, idnguoimua,ngaymua,tinhtrangdonhang,sosanphammua,soluongkg,tongtien;
    SanPhammua sanphammua;

    public Donhang() {
    }

    public Donhang(String iddonhang, String idnguoimua, String ngaymua, String tinhtrangdonhang, String sosanphammua, String soluongkg, String tongtien, SanPhammua sanphammua) {
        this.iddonhang = iddonhang;
        this.idnguoimua = idnguoimua;
        this.ngaymua = ngaymua;
        this.tinhtrangdonhang = tinhtrangdonhang;
        this.sosanphammua = sosanphammua;
        this.soluongkg = soluongkg;
        this.tongtien = tongtien;
        this.sanphammua = sanphammua;
    }

    public String getIddonhang() {
        return iddonhang;
    }

    public void setIddonhang(String iddonhang) {
        this.iddonhang = iddonhang;
    }

    public String getIdnguoimua() {
        return idnguoimua;
    }

    public void setIdnguoimua(String idnguoimua) {
        this.idnguoimua = idnguoimua;
    }

    public String getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(String ngaymua) {
        this.ngaymua = ngaymua;
    }

    public String getTinhtrangdonhang() {
        return tinhtrangdonhang;
    }

    public void setTinhtrangdonhang(String tinhtrangdonhang) {
        this.tinhtrangdonhang = tinhtrangdonhang;
    }

    public String getSosanphammua() {
        return sosanphammua;
    }

    public void setSosanphammua(String sosanphammua) {
        this.sosanphammua = sosanphammua;
    }

    public String getSoluongkg() {
        return soluongkg;
    }

    public void setSoluongkg(String soluongkg) {
        this.soluongkg = soluongkg;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public SanPhammua getSanphammua() {
        return sanphammua;
    }

    public void setSanphammua(SanPhammua sanphammua) {
        this.sanphammua = sanphammua;
    }
}
