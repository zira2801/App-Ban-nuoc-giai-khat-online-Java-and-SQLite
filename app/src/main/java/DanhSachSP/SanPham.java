package DanhSachSP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class SanPham {
    private  String MaSP;
    private String TenSP;

    private String LoaiSP;
    private String ThongTinSP;
    private float GiaSP;
    private Bitmap imageSP;
    private String FavoriteStatus;
    private String CartStatus;


    public SanPham(String maSP, String tenSP, String loaiSP, String thongTinSP, float giaSP, Bitmap imageSP, String favoriteStatus, String cartStatus) {
        MaSP = maSP;
        TenSP = tenSP;
        LoaiSP = loaiSP;
        ThongTinSP = thongTinSP;
        GiaSP = giaSP;
        this.imageSP = imageSP;
        FavoriteStatus = favoriteStatus;
        CartStatus = cartStatus;
    }

    public SanPham(String maSP, String tenSP, String loaiSP, String thongTinSP, float giaSP, Bitmap imageSP) {
        MaSP = maSP;
        TenSP = tenSP;
        LoaiSP = loaiSP;
        ThongTinSP = thongTinSP;
        GiaSP = giaSP;
        this.imageSP = imageSP;
    }

    public SanPham() {
    }

    public String getCartStatus() {
        return CartStatus;
    }

    public void setCartStatus(String cartStatus) {
        CartStatus = cartStatus;
    }

    public String getFavStatus() {
        return FavoriteStatus;
    }

    public void setFavStatus(String favStatus) {
        this.FavoriteStatus = favStatus;
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

    public String getLoaiSP() {
        return LoaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        LoaiSP = loaiSP;
    }

    public String getThongTinSP() {
        return ThongTinSP;
    }

    public void setThongTinSP(String thongTinSP) {
        ThongTinSP = thongTinSP;
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
