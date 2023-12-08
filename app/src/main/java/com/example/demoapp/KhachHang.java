package com.example.demoapp;

import android.graphics.Bitmap;

public class KhachHang {

    private String PassKH;
    private String TenKH;
    private String SDT;
    private String GioiTinh;
    private String EmailKH;
    private String NgaySinh;
    private Bitmap Avatar;

    public KhachHang() {
    }


    public KhachHang(String tenKH, String passKH, String SDT, String gioiTinh, String emailKH, String ngaySinh,Bitmap avatar) {
        TenKH = tenKH;
        PassKH = passKH;
        this.SDT = SDT;
        GioiTinh = gioiTinh;
        EmailKH = emailKH;
        NgaySinh = ngaySinh;
        Avatar = avatar;
    }

    public Bitmap getAvatar() {
        return Avatar;
    }

    public void setAvatar(Bitmap avatar) {
        Avatar = avatar;
    }
    public String getPassKH() {
        return PassKH;
    }

    public void setPassKH(String passKH) {
        PassKH = passKH;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        TenKH = tenKH;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getEmailKH() {
        return EmailKH;
    }

    public void setEmailKH(String emailKH) {
        EmailKH = emailKH;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }
}
