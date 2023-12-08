package com.example.demoapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import DanhSachSP.SanPham;
import Database.DBHelper;

public class SanPhamDAO {

    DBHelper dbHelper;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] byteImage;

    SanPham sanPham;

    FavoriteItem favoriteItem;
    public SanPhamDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public Boolean insertSP(Context context,SanPham sanPham){
        SQLiteDatabase MyDB = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Bitmap bitmap = sanPham.getImageSP();
        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byteImage = byteArrayOutputStream.toByteArray();
        contentValues.put("MaSP",sanPham.getMaSP());
        contentValues.put("TenSP",sanPham.getTenSP());
        contentValues.put("LoaiSP",sanPham.getLoaiSP());
        contentValues.put("ThongTinSP",sanPham.getThongTinSP());
        contentValues.put("GiaSP",sanPham.getGiaSP());
        contentValues.put("FavoriteStatus",sanPham.getFavStatus());
        contentValues.put("imageSP",byteImage);
        contentValues.put("CartStatus",sanPham.getCartStatus());
        long result = MyDB.insert("SanPham",null,contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    //Kiểm tra mã sản phẩm đã có hay chưa
    public boolean checkMaSP(String masp){
        SQLiteDatabase MyDB = dbHelper.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from SanPham where MaSP = ?",new String[] {masp});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    //Kiểm tra tên đã tồn tại hay chưa trong cart
    public boolean checktensp(String tensp){
        SQLiteDatabase MyDB = dbHelper.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from GIOHANG where TenSP = ?",new String[] {tensp});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }
    //Kiểm tra tên sản phẩm đã tồn tại hay chưa
    public boolean checkTenSP(String tenSP){
        SQLiteDatabase MyDB = dbHelper.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from SanPham where TenSP = ?",new String[] {tenSP});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }
    public List<SanPham> getAll() {
        List<SanPham> spList = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] cot = {"MaSP", "TenSP", "LoaiSP", "ThongTinSP","GiaSP","imageSP","CartStatus"};
        Cursor cursor = database.query("SanPham", cot, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            int maspIndex = cursor.getColumnIndex("MaSP");
            int tenspIndex = cursor.getColumnIndex("TenSP");
            int loaiIndex = cursor.getColumnIndex("LoaiSP");
            int ThongTinIndex = cursor.getColumnIndex("ThongTinSP");
            int giaIndex = cursor.getColumnIndex("GiaSP");
            int ImageIndex = cursor.getColumnIndex("imageSP");
            int StatusIndex = cursor.getColumnIndex("CartStatus");
            while (cursor.moveToNext()) {
                SanPham sanPham = new SanPham();
                if (maspIndex != -1) {
                    sanPham.setMaSP(String.valueOf(cursor.getString(maspIndex)));
                }
                if (tenspIndex != -1) {
                    sanPham.setTenSP(cursor.getString(tenspIndex));
                }
                if (loaiIndex != -1) {
                    sanPham.setLoaiSP(String.valueOf(cursor.getString(loaiIndex)));
                }
                if (ThongTinIndex != -1) {
                    sanPham.setThongTinSP(String.valueOf(cursor.getString(ThongTinIndex)));
                }
                if (giaIndex != -1) {
                    sanPham.setGiaSP(Float.parseFloat(cursor.getString(giaIndex)));
                }
                if (ImageIndex != -1) {
                    byte[] bytes = cursor.getBlob(ImageIndex);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    sanPham.setImageSP(bitmap);
                }
                if (StatusIndex != -1) {
                    sanPham.setCartStatus(String.valueOf( cursor.getString(StatusIndex)));
                }
                spList.add(sanPham);
            }
        }
        cursor.close();
        return spList;
    }

    //Update san pham
    public boolean update(Context context,SanPham sanPham){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Bitmap bitmap = sanPham.getImageSP();
        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byteImage = byteArrayOutputStream.toByteArray();
        values.put("MaSP",sanPham.getMaSP());
        values.put("TenSP",sanPham.getTenSP());
        values.put("LoaiSP",sanPham.getLoaiSP());
        values.put("ThongTinSP",sanPham.getThongTinSP());
        values.put("GiaSP",sanPham.getGiaSP());
        values.put("imageSP",byteImage);
        int row = database.update("SanPham",values,"MaSP = ?",new String[] {sanPham.getMaSP()});
        return (row>0);
    }
//Update so luong
public boolean updateSL(Context context,String masp,String tenkh,int sl){
    SQLiteDatabase database = dbHelper.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("Soluong",sl);
    int row = database.update("GIOHANG",values," MaSP= ? AND TenKh=?",new String[] {masp,tenkh});
    return (row>0);
}

    public boolean updateGia(Context context,String masp,String tenkh,float gia){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("GiaSP",gia);
        int row = database.update("GIOHANG",values," MaSP= ? AND TenKh=?",new String[] {masp,tenkh});
        return (row>0);
    }


    public boolean updateCartStatus(Context context,String masp,String cartstatus){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CartStatus",cartstatus);
        int row = database.update("SanPham",values," MaSP= ?",new String[] {masp});
        return (row>0);
    }
    //Delete san pham

    public boolean delete(Context context,SanPham sanPham){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long row = database.delete("SanPham","MaSP=?",new String[]{sanPham.getMaSP()});
        if(row == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deletemaSP(Context context,String masp){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long row = database.delete("SanPham","MaSP=?",new String[]{masp});
        if(row == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public List<SanPham> getAllByLoai(String loai) {
        List<SanPham> spList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham WHERE  LoaiSP = '" + loai + "'",null);
        if(cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                SanPham sanPham = new SanPham();
                String masp = cursor.getString(cursor.getColumnIndexOrThrow("MaSP"));
                String tensp = cursor.getString(cursor.getColumnIndexOrThrow("TenSP"));
                String loaisp = cursor.getString(cursor.getColumnIndexOrThrow("LoaiSP"));
                String ttsp = cursor.getString(cursor.getColumnIndexOrThrow("ThongTinSP"));
                float gia = cursor.getFloat(cursor.getColumnIndexOrThrow("GiaSP"));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("imageSP"));
                Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
                sanPham.setMaSP(masp);
                sanPham.setTenSP(tensp);
                sanPham.setLoaiSP(loaisp);
                sanPham.setThongTinSP(ttsp);
                sanPham.setGiaSP(gia);
                sanPham.setImageSP(bitmap);
                spList.add(sanPham);
            }
        }
        cursor.close();
        db.close();
        return spList;
    }


    public SanPham getAllByMaSP(String masp) {
        SanPham spList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham WHERE  MaSP = '" + masp + "'",null);
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            String maSP = cursor.getString(cursor.getColumnIndexOrThrow("MaSP"));
            String tensp = cursor.getString(cursor.getColumnIndexOrThrow("TenSP"));
            String loaisp = cursor.getString(cursor.getColumnIndexOrThrow("LoaiSP"));
            String ttsp = cursor.getString(cursor.getColumnIndexOrThrow("ThongTinSP"));
            String cartstatus = cursor.getString(cursor.getColumnIndexOrThrow("CartStatus"));
            String fav_status = cursor.getString(cursor.getColumnIndexOrThrow("FavoriteStatus"));
            float gia = cursor.getFloat(cursor.getColumnIndexOrThrow("GiaSP"));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("imageSP"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
            spList = new SanPham(maSP,tensp,loaisp,ttsp,gia,bitmap,fav_status,cartstatus);
        }
        cursor.close();
        db.close();
        return spList;
    }

    //Lấy dữ liệu từ cart
    public Cart getAllByMasp(String masp) {
        Cart spList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM GIOHANG WHERE  MaSP = '" + masp + "'",null);
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            int idcart = cursor.getInt(cursor.getColumnIndexOrThrow("id_cart"));
            String maSP = cursor.getString(cursor.getColumnIndexOrThrow("MaSP"));
            String tensp = cursor.getString(cursor.getColumnIndexOrThrow("TenSP"));
            String ttsp = cursor.getString(cursor.getColumnIndexOrThrow("ThongTinSP"));
            float gia = cursor.getFloat(cursor.getColumnIndexOrThrow("GiaSP"));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("imageSP"));
            String cartStatus = cursor.getString(cursor.getColumnIndexOrThrow("CartStatus"));
            int soluong = cursor.getInt(cursor.getColumnIndexOrThrow("Soluong"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
            spList = new Cart(idcart,maSP,tensp,gia,bitmap,soluong,ttsp,cartStatus);
        }
        cursor.close();
        db.close();
        return spList;
    }
    public boolean insertDataToFavorite(Context context,String maSp,String tenSP, float giaSP, byte[] imageSP,String fav_status,String ttSP ,String tenKh){
        SQLiteDatabase MyDB = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MaSP",maSp);
        contentValues.put("TenSP", tenSP);
        contentValues.put("GiaSP", giaSP);
        contentValues.put("imageSP", imageSP);
        contentValues.put("ThongTinSP",ttSP);
        contentValues.put("FavoriteStatus",fav_status);
        contentValues.put("TenKh", tenKh);
        long result = MyDB.insert("SanPhamYeuThich", null, contentValues);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }
    public boolean removeFavorite(Context context,String masp,String tenkh){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FavoriteStatus","0");
        long row = database.update("SanPhamYeuThich",values,"MaSP=? AND TenKh=?" ,new String[]{masp,tenkh});
        if(row == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deleteFavorite(Context context,String masp,String tenkh){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long row = database.delete("SanPhamYeuThich","MaSP=? AND TenKh=? AND FavoriteStatus =?" ,new String[]{masp,tenkh, String.valueOf(0)});
        if(row == -1){
            return false;
        }
        else{
            return true;
        }
    }


    //XỰ kiện thêm vào giỏ hàng
    public boolean insertDataToCart(Context context,String maSp,String tenSP, float giaSP, byte[] imageSP,int soluong,String ttSP,String cartStatus ,String tenKh){
        SQLiteDatabase MyDB = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MaSP",maSp);
        contentValues.put("TenSP", tenSP);
        contentValues.put("GiaSP", giaSP);
        contentValues.put("imageSP", imageSP);
        contentValues.put("ThongTinSP",ttSP);
        contentValues.put("Soluong",soluong);
        contentValues.put("TenKh", tenKh);
        contentValues.put("CartStatus",cartStatus);
        long result = MyDB.insert("GIOHANG", null, contentValues);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteCart(Context context,String masp,String tenkh){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long row = database.delete("GIOHANG","MaSP=? AND TenKh=?" ,new String[]{masp,tenkh});
        if(row == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public List<Cart> getCartByUsername(String username) {
        List<Cart> spList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM GIOHANG WHERE  TenKh = ?",new String[]{username});
        if(cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Cart cart = new Cart();
                int id_cart = cursor.getInt(cursor.getColumnIndexOrThrow("id_cart"));
                String MaSp = cursor.getString(cursor.getColumnIndexOrThrow("MaSP"));
                String tenSP = cursor.getString(cursor.getColumnIndexOrThrow("TenSP"));
                float giaSp = cursor.getFloat(cursor.getColumnIndexOrThrow("GiaSP"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("TenKh"));
                int SoLuong = cursor.getInt(cursor.getColumnIndexOrThrow("Soluong"));
                String ttsp = cursor.getString(cursor.getColumnIndexOrThrow("ThongTinSP"));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("imageSP"));
                Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
                cart.setId_cart(id_cart);
                cart.setMaSP(MaSp);
                cart.setTenSP(tenSP);
                cart.setGiaSP(giaSp);
                cart.setThongTinSP(ttsp);
                cart.setTenKh(name);
                cart.setSoluong(SoLuong);
                cart.setImageSP(bitmap);
                spList.add(cart);
            }
        }
        cursor.close();
        db.close();
        return spList;
    }

    public List<Cart> getAllCart() {
        List<Cart> spList = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] cot = {"id_cart","MaSP", "TenSP","ThongTinSP","GiaSP","imageSP","TenKh"};
        Cursor cursor = database.query("GIOHANG", cot, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            int id_cart = cursor.getColumnIndex("id_cart");
            int maspIndex = cursor.getColumnIndex("MaSP");
            int tenspIndex = cursor.getColumnIndex("TenSP");
            int ThongTinIndex = cursor.getColumnIndex("ThongTinSP");
            int giaIndex = cursor.getColumnIndex("GiaSP");
            int ImageIndex = cursor.getColumnIndex("imageSP");
            int teKhIndex = cursor.getColumnIndex("TenKh");
            while (cursor.moveToNext()) {
                Cart cart = new Cart();
                if (id_cart != -1) {
                    cart.setMaSP(String.valueOf(cursor.getString(id_cart)));
                }
                if (maspIndex != -1) {
                    cart.setMaSP(String.valueOf(cursor.getString(maspIndex)));
                }
                if (tenspIndex != -1) {
                    cart.setTenSP(cursor.getString(tenspIndex));
                }
                if (ThongTinIndex != -1) {
                    cart.setThongTinSP(String.valueOf(cursor.getString(ThongTinIndex)));
                }
                if (giaIndex != -1) {
                    cart.setGiaSP(Float.parseFloat(cursor.getString(giaIndex)));
                }
                if (ImageIndex != -1) {
                    byte[] bytes = cursor.getBlob(ImageIndex);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    cart.setImageSP(bitmap);
                }
                if (teKhIndex != -1) {
                    cart.setTenKh(String.valueOf( cursor.getString(teKhIndex)));
                }
                spList.add(cart);
            }
        }
        cursor.close();
        return spList;
    }
    public Cart getAllCart(String username) {
        Cart spList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM GIOHANG WHERE  TenKh = ? AND MaSP=?",new String[]{username,spList.getMaSP()});
        if(cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int id_cart = cursor.getInt(cursor.getColumnIndexOrThrow("id_cart"));
                String MaSp = cursor.getString(cursor.getColumnIndexOrThrow("MaSP"));
                String tenSP = cursor.getString(cursor.getColumnIndexOrThrow("TenSP"));
                float giaSp = cursor.getFloat(cursor.getColumnIndexOrThrow("GiaSP"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("TenKh"));
                int SoLuong = cursor.getInt(cursor.getColumnIndexOrThrow("Soluong"));
                String ttsp = cursor.getString(cursor.getColumnIndexOrThrow("ThongTinSP"));
                String cartStatus = cursor.getString(cursor.getColumnIndexOrThrow("CartStatus"));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("imageSP"));
                Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
                spList = new Cart(id_cart,MaSp,tenSP,giaSp,bitmap,SoLuong,ttsp,cartStatus,name);
            }
        }
        cursor.close();
        db.close();
        return spList;
    }

    //Đọc dữ liệu bảng favorit
    public List<FavoriteItem> getFavoriteByUsername(String username) {
        List<FavoriteItem> spList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SanPhamYeuThich WHERE  TenKh = ? AND FavoriteStatus = ? ",new String[]{username, String.valueOf(1)});
        if(cursor != null && cursor.getCount() > 0){
        while (cursor.moveToNext()){
            FavoriteItem favoriteItem1 = new FavoriteItem();
            int id_fav = cursor.getInt(cursor.getColumnIndexOrThrow("id_fav"));
            String MaSp = cursor.getString(cursor.getColumnIndexOrThrow("MaSP"));
            String tenSP = cursor.getString(cursor.getColumnIndexOrThrow("TenSP"));
            float giaSp = cursor.getFloat(cursor.getColumnIndexOrThrow("GiaSP"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("TenKh"));
            String fav_status = cursor.getString(cursor.getColumnIndexOrThrow("FavoriteStatus"));
            String ttsp = cursor.getString(cursor.getColumnIndexOrThrow("ThongTinSP"));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("imageSP"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
            favoriteItem1.setId_fav(id_fav);
            favoriteItem1.setMasp(MaSp);
            favoriteItem1.setTenSP(tenSP);
            favoriteItem1.setGiaSP(giaSp);
            favoriteItem1.setThongTinSP(ttsp);
            favoriteItem1.setTenKh(name);
            favoriteItem1.setFavoriteStatus(fav_status);
            favoriteItem1.setImageSP(bitmap);
            spList.add(favoriteItem1);
        }
        }
        cursor.close();
        db.close();
        return spList;
    }

    public List<SanPham> searchProducts(String query) {
        List<SanPham> products = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] cot = {"MaSP", "TenSP", "GiaSP","LoaiSP","imageSP","ThongTinSP","FavoriteStatus","CartStatus"};
        String selection = "TenSP LIKE ?";
        String[] selectionArgs = { "%" + query + "%" };
        Cursor cursor = db.query("SanPham",cot, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            String masp = cursor.getString(cursor.getColumnIndexOrThrow("MaSP"));
            String tensp = cursor.getString(cursor.getColumnIndexOrThrow("TenSP"));
            float giasp = cursor.getFloat(cursor.getColumnIndexOrThrow("GiaSP"));
            String loaip = cursor.getString(cursor.getColumnIndexOrThrow("LoaiSP"));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("imageSP"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
            String thongtinsp = cursor.getString(cursor.getColumnIndexOrThrow("ThongTinSP"));
            String favsp = cursor.getString(cursor.getColumnIndexOrThrow("FavoriteStatus"));
            String cartsp = cursor.getString(cursor.getColumnIndexOrThrow("CartStatus"));
            SanPham product = new SanPham(masp,tensp,loaip,thongtinsp,giasp,bitmap,favsp,cartsp);
            products.add(product);
        }
        cursor.close();
        db.close();
        return products;
    }

    //Thêm vào thanh toán
    public Boolean insertThanhToan(Context context,String masp,String tensp,float gia,String thongtin,String diachi,String sdt,byte[] imageSP,int soluong,String tennguoinhan,String ngaydat,String tenkh){
        SQLiteDatabase MyDB = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MaSP",masp);
        contentValues.put("TenSP",tensp);
        contentValues.put("ThongTinSP",thongtin);
        contentValues.put("TongGia",gia);
        contentValues.put("DiaChi",diachi);
        contentValues.put("imageSP",imageSP);
        contentValues.put("SoDT",sdt);
        contentValues.put("Soluong",soluong);
        contentValues.put("TenNguoiNhan",tennguoinhan);
        contentValues.put("NgayDatHang",ngaydat);
        contentValues.put("TenKh",tenkh);
        long result = MyDB.insert("THANHTOAN",null,contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

//Lấy dữ liệu trong bảng sản phẩm

    public SanPham getAllByTenSP(String tenSP) {
        SanPham spList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham WHERE  TenSP = '" + tenSP + "'",null);
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            String maSP = cursor.getString(cursor.getColumnIndexOrThrow("MaSP"));
            String tensp = cursor.getString(cursor.getColumnIndexOrThrow("TenSP"));
            String loaisp = cursor.getString(cursor.getColumnIndexOrThrow("LoaiSP"));
            String ttsp = cursor.getString(cursor.getColumnIndexOrThrow("ThongTinSP"));
            String cartstatus = cursor.getString(cursor.getColumnIndexOrThrow("CartStatus"));
            String favstatus = cursor.getString(cursor.getColumnIndexOrThrow("FavoriteStatus"));
            float gia = cursor.getFloat(cursor.getColumnIndexOrThrow("GiaSP"));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("imageSP"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
            spList = new SanPham(maSP,tensp,loaisp,ttsp,gia,bitmap,favstatus,cartstatus);
        }
        cursor.close();
        db.close();
        return spList;
    }

    //Lấy dữ liệu từ bảng thanh toán
    public List<Payment> getPaymentByUsername(String username) {
        List<Payment> spList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THANHTOAN WHERE  TenKh = ?",new String[]{username});
        if(cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Payment payment = new Payment();
                int id_thanhtoan = cursor.getInt(cursor.getColumnIndexOrThrow("id_thanhtoan"));
                String MaSp = cursor.getString(cursor.getColumnIndexOrThrow("MaSP"));
                String tenSP = cursor.getString(cursor.getColumnIndexOrThrow("TenSP"));
                float tonggiaSp = cursor.getFloat(cursor.getColumnIndexOrThrow("TongGia"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("TenKh"));
                String diaChi = cursor.getString(cursor.getColumnIndexOrThrow("DiaChi"));
                String sdt = cursor.getString(cursor.getColumnIndexOrThrow("SoDT"));
                int sl = cursor.getInt(cursor.getColumnIndexOrThrow("Soluong"));
                String tennguoinhan = cursor.getString(cursor.getColumnIndexOrThrow("TenNguoiNhan"));
                String ngaydathang = cursor.getString(cursor.getColumnIndexOrThrow("NgayDatHang"));
                String ttsp = cursor.getString(cursor.getColumnIndexOrThrow("ThongTinSP"));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("imageSP"));
                Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
                payment.setId_thanhtoan(id_thanhtoan);
                payment.setMaSP(MaSp);
                payment.setTenSP(tenSP);
                payment.setTongGia(tonggiaSp);
                payment.setTenKh(name);
                payment.setDiaChi(diaChi);
                payment.setSoDT(sdt);
                payment.setSoluong(sl);
                payment.setTenNguoiNhan(tennguoinhan);
                payment.setNgayDatHang(ngaydathang);
                payment.setThongTinSP(ttsp);
                payment.setImageSP(bitmap);
                spList.add(payment);
            }
        }
        cursor.close();
        db.close();
        return spList;
    }

    //Xóa lịch sử mua hàng
    public boolean deletePayment(Context context,int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long row = database.delete("THANHTOAN","id_thanhtoan=?" ,new String[]{String.valueOf(id)});
        if(row == -1){
            return false;
        }
        else{
            return true;
        }
    }
}
