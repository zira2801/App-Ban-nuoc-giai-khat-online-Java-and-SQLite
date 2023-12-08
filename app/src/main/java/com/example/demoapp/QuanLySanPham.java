package com.example.demoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import DanhSachSP.SanPham;

public class QuanLySanPham extends AppCompatActivity {

    EditText edt_masp,edt_tensp,edt_ttsp,edt_giasp,edt_loai;
    Button btn_them,btn_hienthi;
    ImageView btn_troveAdmin,imageViewSP;
    SanPhamDAO sanPhamDAO;

    private Uri uri;
    private Bitmap bitmapImage;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        btn_troveAdmin = (ImageView) findViewById(R.id.back_adminpage);
        btn_them = (Button) findViewById(R.id.them_sp);
        btn_hienthi = (Button) findViewById(R.id.btn_hienthidssp);
        edt_masp = (EditText) findViewById(R.id.edt_masp);
        edt_tensp = (EditText) findViewById(R.id.edt_tensp);
        edt_ttsp = (EditText) findViewById(R.id.edt_ttsp);
        edt_giasp = (EditText) findViewById(R.id.edt_giasp);
        edt_loai = (EditText) findViewById(R.id.edt_loaisp);
        imageViewSP = (ImageView) findViewById(R.id.image_sp);
        sanPhamDAO = new SanPhamDAO(QuanLySanPham.this);

        btn_hienthi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuanLySanPham.this,DanhSachQLSP.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String masp = edt_masp.getText().toString();
                String tensp = edt_tensp.getText().toString();
                String ttsp = edt_ttsp.getText().toString();
                float gia = Float.parseFloat(edt_giasp.getText().toString());
                String loai = edt_loai.getText().toString();
                String favstatus = "0";
                String cartstatus = "0";
                SanPham sanPham = new SanPham(masp,tensp,loai,ttsp,gia,bitmapImage,favstatus,cartstatus);
                if(masp.equals("") || tensp.equals("") || loai.equals("") || ttsp.equals("") || String.valueOf(gia).equals("") || bitmapImage == null){
                    Toast.makeText(QuanLySanPham.this, "Bạn phải điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkMASP = sanPhamDAO.checkMaSP(masp);
                    if(checkMASP == false){
                        Boolean checkTensp = sanPhamDAO.checkTenSP(tensp);
                        if(checkTensp == false){
                            if(edt_masp.length() > 0 && edt_tensp.length() > 0 && edt_ttsp.length() > 0 && edt_giasp.length() > 0 && edt_loai.length() > 0){
                                if(sanPhamDAO.insertSP(QuanLySanPham.this,sanPham)){
                                    Toast.makeText(QuanLySanPham.this,"Thêm sản phẩm thành công",Toast.LENGTH_SHORT).show();
                                    edt_masp.setText("");
                                    edt_tensp.setText("");
                                    edt_ttsp.setText("");
                                    edt_giasp.setText("");
                                    edt_loai.setText("");
                                    imageViewSP.setImageBitmap(null);
                                    imageViewSP.setBackgroundResource(R.drawable.baseline_add_photo_alternate_24_2);
                                }
                                else {
                                    Toast.makeText(QuanLySanPham.this,"Thêm sản phẩm thất bại",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else {
                            Toast.makeText(QuanLySanPham.this,"Tên sản phẩm đã tồn tại",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(QuanLySanPham.this,"Mã sản phẩm đã tồn tại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btn_troveAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuanLySanPham.this,AdminActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
            }
        });

        imageViewSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,100);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK && data!=null){
            uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmapImage = BitmapFactory.decodeStream(inputStream);
                // Chuyển đổi ảnh thành một mảng byte
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                imageViewSP.setImageBitmap(bitmapImage);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
}