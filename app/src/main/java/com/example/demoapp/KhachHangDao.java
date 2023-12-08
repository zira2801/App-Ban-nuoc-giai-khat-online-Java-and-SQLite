package com.example.demoapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import DanhSachSP.SanPham;
import Database.DBHelper;

public class KhachHangDao {
    DBHelper dbHelper;

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] byteImage;
    public KhachHangDao(Context context){
        dbHelper = new DBHelper(context);
    }

    // Đăng nhập
    public Boolean insertData(Context context,KhachHang khachHang){
        SQLiteDatabase MyDB = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Bitmap bitmap = khachHang.getAvatar();
        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byteImage = byteArrayOutputStream.toByteArray();
        contentValues.put("TenKH",khachHang.getTenKH());
        contentValues.put("PassKH",khachHang.getPassKH());
        contentValues.put("SoDT",khachHang.getSDT());
        contentValues.put("GioiTinh",khachHang.getGioiTinh());
        contentValues.put("EmailKH",khachHang.getEmailKH());
        contentValues.put("DateKH",khachHang.getNgaySinh());
        contentValues.put("Avatar",byteImage);
        long result = MyDB.insert("KhachHang",null,contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }


    //Đăng ký
    public  Boolean checkNameTK(Context context,String nametk){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase MyDB = helper.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from KhachHang where TenKh = ?",new String[] {nametk});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    public  Boolean checkEmail(Context context,String eamil){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase MyDB = helper.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from KhachHang where EmailKH = ?",new String[] {eamil});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    public  Boolean checkSDT(Context context,String SoDT){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase MyDB = helper.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from KhachHang where SoDT = ?",new String[] {SoDT});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }
    public Boolean checkNametk_password(Context context,String nametk,String password){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase MyDB = helper.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from KhachHang where TenKh = ? and PassKH = ?",new String[] {nametk,password});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    //Thêm thông tin trong profile
    //Update profile
    public boolean update(Context context,KhachHang khachHang){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Bitmap bitmap = khachHang.getAvatar();
        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byteImage = byteArrayOutputStream.toByteArray();
        values.put("SoDT",khachHang.getSDT());
        values.put("GioiTinh",khachHang.getGioiTinh());
        values.put("EmailKH",khachHang.getEmailKH());
        values.put("DateKH",khachHang.getNgaySinh());
        values.put("Avatar",byteImage);
        int row = database.update("KhachHang",values,"TenKh = ?",new String[] {khachHang.getTenKH()});
        return (row>0);
    }

    public boolean updatepassword(Context context,String user,String password){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PassKH",password);
        int row = database.update("KhachHang",values,"TenKh = ?",new String[] {user});
        return (row>0);
    }

    public KhachHang getPersonByUsername(String username) {

        KhachHang khachHang = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM KhachHang WHERE  TenKh = '" + username + "'",null);
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndexOrThrow("TenKh"));
            String soDT = cursor.getString(cursor.getColumnIndexOrThrow("SoDT"));
            String gt = cursor.getString(cursor.getColumnIndexOrThrow("GioiTinh"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("EmailKH"));
            String ngaysinh = cursor.getString(cursor.getColumnIndexOrThrow("DateKH"));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("Avatar"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
            khachHang = new KhachHang(name,null,soDT,gt,email,ngaysinh,bitmap);
        }
        cursor.close();
        db.close();
        return khachHang;
    }

    //Lấy danh sách tài khoản khách hàng
    public List<KhachHang> getAll() {
        List<KhachHang> spList = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] cot = {"TenKh", "PassKH", "SoDT", "GioiTinh","EmailKH","DateKH","Avatar"};
        Cursor cursor = database.query("KhachHang", cot, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            int tenkhIndex = cursor.getColumnIndex("TenKh");
            int passIndex = cursor.getColumnIndex("PassKH");
            int sdtIndex = cursor.getColumnIndex("SoDT");
            int gioitinhIndex = cursor.getColumnIndex("GioiTinh");
            int emailIndex = cursor.getColumnIndex("EmailKH");
            int ngaySinhIndex = cursor.getColumnIndex("DateKH");
            int AvatarIndex = cursor.getColumnIndex("Avatar");
            while (cursor.moveToNext()) {
                KhachHang khachHang = new KhachHang();
                if (tenkhIndex != -1) {
                    khachHang.setTenKH(String.valueOf(cursor.getString(tenkhIndex)));
                }
                if (passIndex != -1) {
                    khachHang.setPassKH(cursor.getString(passIndex));
                }
                if (sdtIndex != -1) {
                    khachHang.setSDT(String.valueOf(cursor.getString(sdtIndex)));
                }
                if (emailIndex != -1) {
                    khachHang.setEmailKH(String.valueOf(cursor.getString(emailIndex)));
                }
                if (gioitinhIndex != -1) {
                    khachHang.setGioiTinh(String.valueOf(cursor.getString(gioitinhIndex)));
                }
                if (ngaySinhIndex != -1) {
                    khachHang.setNgaySinh(String.valueOf(cursor.getString(ngaySinhIndex)));
                }
                if (AvatarIndex!= -1) {
                    byte[] bytes = cursor.getBlob(AvatarIndex);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    khachHang.setAvatar(bitmap);
                }
                spList.add(khachHang);
            }
        }
        return spList;
    }

    //Xóa tài khoản ngươi dùng
    public boolean delete(Context context,KhachHang khachHang){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long row = database.delete("KhachHang","TenKh=?",new String[]{khachHang.getTenKH()});
        if(row == -1){
            return false;
        }
        else{
            return true;
        }
    }
}

