package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    Button btn_thoatadmin, btn_quanlysanpham , btn_quanllytaikhoankhachhang;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        btn_thoatadmin = (Button) findViewById(R.id.thoatAdmin);
        btn_quanlysanpham = (Button) findViewById(R.id.quanlysanpham);
        btn_quanllytaikhoankhachhang = (Button) findViewById(R.id.quanlystaikhoankhachhang);
        String name = sharedPreferences.getString(KEY_USERNAME,null);
        String pass = sharedPreferences.getString(KEY_PASSWORD,null);
        btn_thoatadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.apply();
                Intent i = new Intent(AdminActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn_quanlysanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,QuanLySanPham.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });

        btn_quanllytaikhoankhachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,DanhSachTKKhachHang.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });
    }
}