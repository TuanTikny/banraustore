package com.tuanbapk.banrau.Model;

/**
 * Created by buituan on 2017-11-25.
 */

public class SanPhammua {

    String idsanphammua,idsanpham,soluongmua,tongia;

    public SanPhammua() {
    }

    public SanPhammua(String idsanphammua, String idsanpham, String soluongmua, String tongia) {
        this.idsanphammua = idsanphammua;
        this.idsanpham = idsanpham;
        this.soluongmua = soluongmua;
        this.tongia = tongia;
    }

    public String getIdsanphammua() {
        return idsanphammua;
    }

    public void setIdsanphammua(String idsanphammua) {
        this.idsanphammua = idsanphammua;
    }

    public String getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(String idsanpham) {
        this.idsanpham = idsanpham;
    }

    public String getSoluongmua() {
        return soluongmua;
    }

    public void setSoluongmua(String soluongmua) {
        this.soluongmua = soluongmua;
    }

    public String getTongia() {
        return tongia;
    }

    public void setTongia(String tongia) {
        this.tongia = tongia;
    }
}
