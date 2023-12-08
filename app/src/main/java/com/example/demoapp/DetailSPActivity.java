package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;

import DanhSachSP.SanPham;
import Database.DBHelper;
import book.Book;

public class DetailSPActivity extends AppCompatActivity implements Serializable {
    private ImageView img_nuoc,btn_back,plus,remove;
    private TextView tvGia,tv_namenuoc,tv_mieuta;

    private int soluong;
    private TextView btn_addCart;
    private TextView edt_soluong;
    DBHelper dbHelper;
    SanPhamDAO sanPhamDAO;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    private  boolean isAddtoCart = false;
    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spactivity);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        SanPham sanPham = new SanPham();
                tvGia = (TextView) findViewById(R.id.tv_gia);
                img_nuoc = (ImageView) findViewById(R.id.img_nuoc);
                tv_namenuoc = (TextView) findViewById(R.id.tv_tennuoc);
                tv_mieuta = (TextView) findViewById(R.id.description_sp);
                sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
                editor = sharedPreferences.edit();



                String name = sharedPreferences.getString(KEY_USERNAME,null);
                edt_soluong = (TextView) findViewById(R.id.edt_SL);
                btn_addCart = (TextView) findViewById(R.id.addcart);
                plus = (ImageView) findViewById(R.id.plus);
                remove = (ImageView) findViewById(R.id.remove);
                btn_addCart = (TextView) findViewById(R.id.addcart);
                sanPhamDAO = new SanPhamDAO(DetailSPActivity.this);
                tv_namenuoc.setText(getIntent().getStringExtra("tensp"));
                tv_mieuta.setText(getIntent().getStringExtra("ttsp"));
                byte[] imageData = getIntent().getByteArrayExtra("imagesp");
                final Bitmap[] bitmap = {BitmapFactory.decodeStream(new ByteArrayInputStream(imageData))};
                Glide.with(DetailSPActivity.this).load(bitmap[0]).into(img_nuoc);
                float gia = getIntent().getFloatExtra("giasp", sanPham.getGiaSP());
                tvGia.setText(String.format("%.0f",gia) + " vnđ");

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soluong+=1;
                edt_soluong.setText(String.valueOf(soluong));
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(soluong == 0){
                    return;
                }
                else {
                    soluong-=1;
                }
                edt_soluong.setText(String.valueOf(soluong));
            }
        });
            btn_addCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(name == null){
                        startActivity(new Intent(DetailSPActivity.this,LoginActivity.class));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        finish();
                        return;
                    }
                        String tenNuoc = tv_namenuoc.getText().toString();
                        SanPham sanPham1 = sanPhamDAO.getAllByTenSP(tenNuoc);
                        String masp = sanPham1.getMaSP();
                        float giaSP = sanPham1.getGiaSP();// Lấy giá sản phẩm
                    float tonggia = giaSP*soluong;
                        String tenSP = sanPham1.getTenSP();
                        String ttSP = sanPham1.getThongTinSP();
                        String cartstatus = sanPham1.getCartStatus();
                        Bitmap bitmap = sanPham1.getImageSP();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] imageSP = byteArrayOutputStream.toByteArray();
                        if(cartstatus.equals("0")){
                            sanPham1.setCartStatus("1");
                            if(sanPhamDAO.insertDataToCart(DetailSPActivity.this,masp,tenSP,tonggia,imageSP,soluong,ttSP,sanPham1.getCartStatus(),name)){
                                sanPhamDAO.updateCartStatus(DetailSPActivity.this, sanPham1.getMaSP(),"1");
                                Toast.makeText(DetailSPActivity.this,"Thêm sản phẩm vào giỏ hàng thành công",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(DetailSPActivity.this, "Sản phẩm đã tồn tại trong giỏ hàng", Toast.LENGTH_SHORT).show();
                        }
                }
            });
            btn_back = (ImageView) findViewById(R.id.trolai);
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                }
            });
        }
    }