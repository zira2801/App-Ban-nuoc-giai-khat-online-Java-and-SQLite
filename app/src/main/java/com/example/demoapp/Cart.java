package com.example.demoapp;

import android.graphics.Bitmap;

public class Cart {
    private int id_cart;
    private String MaSP;
    private  String TenSP;
    private float GiaSP;
    private  Bitmap imageSP;
    private int Soluong;
    private String ThongTinSP;
    private String CartStatus;
    private String TenKh;


    public Cart(int id_cart, String maSP, String tenSP, float giaSP, Bitmap imageSP, int soluong, String thongTinSP, String cartStatus, String tenKh) {
        this.id_cart = id_cart;
        MaSP = maSP;
        TenSP = tenSP;
        GiaSP = giaSP;
        this.imageSP = imageSP;
        Soluong = soluong;
        ThongTinSP = thongTinSP;
        CartStatus = cartStatus;
        TenKh = tenKh;
    }

    public Cart(int id_cart, String maSP, String tenSP, float giaSP, Bitmap imageSP, int soluong, String thongTinSP, String cartStatus) {
        this.id_cart = id_cart;
        MaSP = maSP;
        TenSP = tenSP;
        GiaSP = giaSP;
        this.imageSP = imageSP;
        Soluong = soluong;
        ThongTinSP = thongTinSP;
        CartStatus = cartStatus;
    }

    public Cart() {
    }

    public String getCartStatus() {
        return CartStatus;
    }

    public void setCartStatus(String cartStatus) {
        CartStatus = cartStatus;
    }

    public int getId_cart() {
        return id_cart;
    }

    public void setId_cart(int id_cart) {
        this.id_cart = id_cart;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
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

    public  Bitmap getImageSP() {
        return imageSP;
    }

    public void setImageSP(Bitmap imageSP) {
        this.imageSP = imageSP;
    }

    public int getSoluong() {
        return Soluong;
    }

    public void setSoluong(int soluong) {
        Soluong = soluong;
    }

    public String getThongTinSP() {
        return ThongTinSP;
    }

    public void setThongTinSP(String thongTinSP) {
        ThongTinSP = thongTinSP;
    }

    public String getTenKh() {
        return TenKh;
    }

    public void setTenKh(String tenKh) {
        TenKh = tenKh;
    }

    }

