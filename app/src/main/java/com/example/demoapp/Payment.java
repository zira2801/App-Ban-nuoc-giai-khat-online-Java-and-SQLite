package com.example.demoapp;

import android.graphics.Bitmap;

public class Payment {
    private int id_thanhtoan;
    private String MaSP;
    private String TenSP;
    private float TongGia;
    private Bitmap imageSP;
    private String DiaChi;
    private String SoDT;
    private int Soluong;
    private String ThongTinSP;
    private String TenNguoiNhan;
    private String NgayDatHang;
    private String TenKh;

    public Payment(int id_thanhtoan, String maSP, String tenSP, float tongGia, Bitmap imageSP, String diaChi, String soDT, int soluong, String thongTinSP, String tenNguoiNhan, String ngayDatHang, String tenKh) {
        this.id_thanhtoan = id_thanhtoan;
        MaSP = maSP;
        TenSP = tenSP;
        TongGia = tongGia;
        this.imageSP = imageSP;
        DiaChi = diaChi;
        SoDT = soDT;
        Soluong = soluong;
        ThongTinSP = thongTinSP;
        TenNguoiNhan = tenNguoiNhan;
        NgayDatHang = ngayDatHang;
        TenKh = tenKh;
    }

    public Payment() {
    }

    public Payment(String tenSP, float tongGia, Bitmap imageSP, String diaChi, String soDT, int soluong, String thongTinSP, String tenNguoiNhan, String tenKh) {
        TenSP = tenSP;
        TongGia = tongGia;
        this.imageSP = imageSP;
        DiaChi = diaChi;
        SoDT = soDT;
        Soluong = soluong;
        ThongTinSP = thongTinSP;
        TenNguoiNhan = tenNguoiNhan;
        TenKh = tenKh;
    }

    public String getNgayDatHang() {
        return NgayDatHang;
    }

    public void setNgayDatHang(String ngayDatHang) {
        NgayDatHang = ngayDatHang;
    }

    public String getTenNguoiNhan() {
        return TenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        TenNguoiNhan = tenNguoiNhan;
    }

    public int getId_thanhtoan() {
        return id_thanhtoan;
    }

    public void setId_thanhtoan(int id_thanhtoan) {
        this.id_thanhtoan = id_thanhtoan;
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

    public float getTongGia() {
        return TongGia;
    }

    public void setTongGia(float tongGia) {
        TongGia = tongGia;
    }

    public Bitmap getImageSP() {
        return imageSP;
    }

    public void setImageSP(Bitmap imageSP) {
        this.imageSP = imageSP;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String soDT) {
        SoDT = soDT;
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
