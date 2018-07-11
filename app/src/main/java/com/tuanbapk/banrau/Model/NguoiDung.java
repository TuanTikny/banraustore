package com.tuanbapk.banrau.Model;

/**
 * Created by buituan on 2017-10-29.
 */

public class NguoiDung {

    String idTK;
    String hoten;
    String sodienthoai;
    String diachi;
    String motakhac;
    String hinhdaidien;
    Sanphamyeuthich sanphamyeuthich;

    public NguoiDung() {
    }

    public NguoiDung(String idTK, String hoten, String sodienthoai, String diachi, String motakhac, String hinhdaidien) {
        this.idTK = idTK;
        this.hoten = hoten;
        this.sodienthoai = sodienthoai;
        this.diachi = diachi;
        this.motakhac = motakhac;
        this.hinhdaidien = hinhdaidien;
    }

    public NguoiDung(Sanphamyeuthich sanphamyeuthich) {
        this.sanphamyeuthich = sanphamyeuthich;
    }

    public Sanphamyeuthich getSanphamyeuthich() {
        return sanphamyeuthich;
    }

    public void setSanphamyeuthich(Sanphamyeuthich sanphamyeuthich) {
        this.sanphamyeuthich = sanphamyeuthich;
    }

    public String getIdTK() {
        return idTK;
    }

    public void setIdTK(String idTK) {
        this.idTK = idTK;
    }

    public String getHinhdaidien() {
        return hinhdaidien;
    }

    public void setHinhdaidien(String hinhdaidien) {
        this.hinhdaidien = hinhdaidien;
    }


    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getMotakhac() {
        return motakhac;
    }

    public void setMotakhac(String motakhac) {
        this.motakhac = motakhac;
    }

}
