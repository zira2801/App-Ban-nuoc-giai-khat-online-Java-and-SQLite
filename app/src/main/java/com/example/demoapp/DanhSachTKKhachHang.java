package com.example.demoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DanhSachTKKhachHang extends AppCompatActivity {

    ImageView back_admin;
    KhachHangDao khachHangDao;
    List<KhachHang> khachHangList;
    RecyclerView recyclerView;
    KhachHangAdapter khachHangAdapter;
    AlertDialog dialog;
    private Bitmap bitmapImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_tkkhach_hang);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyleviewKhachHang);
        back_admin = (ImageView) findViewById(R.id.back_Admin);
        khachHangDao = new KhachHangDao(DanhSachTKKhachHang.this);
        khachHangList = khachHangDao.getAll();
        AlertDialog.Builder builder = new AlertDialog.Builder(DanhSachTKKhachHang.this);
        khachHangAdapter = new KhachHangAdapter(DanhSachTKKhachHang.this, khachHangList, new KhachHangAdapter.ItemKHClickListener() {
            @Override
            public void onItemClick(KhachHang khachHang) {
                View viewDialogTTKH = getLayoutInflater().inflate(R.layout.thongtin_kh,null);
                TextView tentk = (TextView) viewDialogTTKH.findViewById(R.id.tv_nameKH);
                TextView passtk = (TextView) viewDialogTTKH.findViewById(R.id.tv_matkhauKH);
                TextView sdttk = (TextView) viewDialogTTKH.findViewById(R.id.tv_SDTKH);
                TextView gttk = (TextView) viewDialogTTKH.findViewById(R.id.tv_GioiTinhKH);
                TextView emailtk = (TextView) viewDialogTTKH.findViewById(R.id.tv_emailKH);
                TextView ngaysinhtk = (TextView) viewDialogTTKH.findViewById(R.id.tv_ngaysinhKH);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                CircleImageView avatar = viewDialogTTKH.findViewById(R.id.anhdaidienKH);
                ImageView thoat = (ImageView) viewDialogTTKH.findViewById(R.id.closeThongtinkh);
                tentk.setText(khachHang.getTenKH());
                passtk.setText(khachHang.getPassKH());
                sdttk.setText(khachHang.getSDT());
                gttk.setText(khachHang.getGioiTinh());
                emailtk.setText(khachHang.getEmailKH());
                ngaysinhtk.setText(khachHang.getNgaySinh());
                bitmapImage = khachHang.getAvatar();
                avatar.setImageBitmap(bitmapImage);
                builder.setView(viewDialogTTKH);
                dialog = builder.create();
                dialog.show();
                thoat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhSachTKKhachHang.this,RecyclerView.VERTICAL,false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(DanhSachTKKhachHang.this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(khachHangAdapter);
        back_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DanhSachTKKhachHang.this,AdminActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
            }
        });
    }
}