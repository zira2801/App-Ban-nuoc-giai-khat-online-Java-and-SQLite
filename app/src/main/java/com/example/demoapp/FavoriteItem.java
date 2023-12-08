package com.example.demoapp;

import android.graphics.Bitmap;

public class FavoriteItem {
    private int id_fav;
    private String MaSP;
    private String TenSP;
    private float GiaSP;
    private Bitmap imageSP;
    private String FavoriteStatus;
    private String ThongTinSP;
    private String TenKh;

    public FavoriteItem(int id_fav, String maSP, String tenSP, float giaSP, Bitmap imageSP, String favoriteStatus, String thongTinSP, String tenKh) {
        this.id_fav = id_fav;
        MaSP = maSP;
        TenSP = tenSP;
        GiaSP = giaSP;
        this.imageSP = imageSP;
        FavoriteStatus = favoriteStatus;
        ThongTinSP = thongTinSP;
        TenKh = tenKh;
    }

    public String getTenKh() {
        return TenKh;
    }

    public String getThongTinSP() {
        return ThongTinSP;
    }

    public void setThongTinSP(String thongTinSP) {
        ThongTinSP = thongTinSP;
    }

    public void setTenKh(String tenKh) {
        TenKh = tenKh;
    }

    public FavoriteItem() {
    }

    public String getFavoriteStatus() {
        return FavoriteStatus;
    }

    public void setFavoriteStatus(String favoriteStatus) {
        FavoriteStatus = favoriteStatus;
    }

    public String getMasp() {
        return MaSP;
    }

    public void setMasp(String masp) {
        this.MaSP = masp;
    }

    public int getId_fav() {
        return id_fav;
    }

    public void setId_fav(int id_fav) {
        this.id_fav = id_fav;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public float getGiaSP() {
        return GiaSP;
    }

    public void setGiaSP(float giaSP) {
        GiaSP = giaSP;
    }

    public Bitmap getImageSP() {
        return imageSP;
    }

    public void setImageSP(Bitmap imageSP) {
        this.imageSP = imageSP;
    }
}
