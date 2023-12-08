package com.example.demoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

import DanhSachSP.SanPham;

public class DanhSachQLSP extends AppCompatActivity {

    List<SanPham> sanPhamList;
    SanPhamDAO sanPhamDAO;
    ImageView btn_trove;

    AlertDialog dialog;
    RecyclerView recyclerView;
    private Bitmap bitmapImage;
    ImageView upAnhSP;
    Uri uri;
    SanPhamListAdapter sanPhamListAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_qlsp);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        btn_trove = (ImageView) findViewById(R.id.back_dssp);
        recyclerView = findViewById(R.id.recyleviewSP);
        sanPhamDAO = new SanPhamDAO(DanhSachQLSP.this);
        sanPhamList = (List<SanPham>) sanPhamDAO.getAll();
        AlertDialog.Builder builder = new AlertDialog.Builder(DanhSachQLSP.this);
        sanPhamListAdapter = new SanPhamListAdapter(sanPhamList, DanhSachQLSP.this, new SanPhamListAdapter.ItemClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onItemClick(SanPham sanPham) {
                View viewDialogUpSP = getLayoutInflater().inflate(R.layout.update_listsp,null);
                Button btn_capnhatsp = (Button) viewDialogUpSP.findViewById(R.id.update_sp);
                EditText upMasp = (EditText) viewDialogUpSP.findViewById(R.id.edt_updatemasp);
                EditText upTensp = (EditText) viewDialogUpSP.findViewById(R.id.edt_updatetensp);
                EditText upTTsp = (EditText) viewDialogUpSP.findViewById(R.id.edt_updatettsp);
                EditText upGiasp = (EditText) viewDialogUpSP.findViewById(R.id.edt_updategiasp);
                EditText upLoaisp = (EditText) viewDialogUpSP.findViewById(R.id.edt_updateloaisp);
                upAnhSP = (ImageView) viewDialogUpSP.findViewById(R.id.updateimage_sp);
                ImageView thoat = (ImageView) viewDialogUpSP.findViewById(R.id.thoat_updatesp);
                upAnhSP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                        i.setType("image/*");
                        startActivityForResult(i,100);
                    }
                });
                builder.setView(viewDialogUpSP);
                dialog = builder.create();
                dialog.show();
                upMasp.setText(sanPham.getMaSP());
                upTensp.setText(sanPham.getTenSP());
                upTTsp.setText(sanPham.getThongTinSP());
                upGiasp.setText(String.format("%.0f",sanPham.getGiaSP()));
                upLoaisp.setText(sanPham.getLoaiSP());
                bitmapImage = sanPham.getImageSP();
                upAnhSP.setImageBitmap(bitmapImage);
                btn_capnhatsp.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View view) {
                        String masp = upMasp.getText().toString();
                        String tensp = upTensp.getText().toString();
                        String loaisp = upLoaisp.getText().toString();
                        String thongtin = upTTsp.getText().toString();
                        String gia = upGiasp.getText().toString();
                        sanPham.setMaSP(masp);
                        sanPham.setTenSP(tensp);
                        sanPham.setLoaiSP(loaisp);
                        sanPham.setThongTinSP(thongtin);
                        sanPham.setGiaSP(Float.parseFloat(gia));
                        sanPham.setImageSP(bitmapImage);
                        if(sanPhamDAO.update(DanhSachQLSP.this,sanPham)){
                            Toast.makeText(DanhSachQLSP.this,"Cập nhật sản phẩm " + sanPham.getMaSP() + " thành công",Toast.LENGTH_SHORT).show();
                            int position = sanPhamList.indexOf(sanPham); // Lấy vị trí của sản phẩm trong danh sách
                            sanPhamList.set(position, sanPham); // Cập nhật thông tin sản phẩm trong danh sách
                            sanPhamListAdapter.notifyItemChanged(position);
                            dialog.dismiss();
                        }
                    }
                });
                thoat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhSachQLSP.this,RecyclerView.VERTICAL,false);
       RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(DanhSachQLSP.this,DividerItemDecoration.VERTICAL);
       recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(sanPhamListAdapter);


        btn_trove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DanhSachQLSP.this,QuanLySanPham.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
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
                upAnhSP.setImageBitmap(bitmapImage);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
}