package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import DanhSachSP.SanPham;

public class ThongTinThanhToanActivity extends AppCompatActivity {

    TextView naemSP,TongGia,SoLuong;
    EditText diachi,SoDT,tennguoinhan;
    Button btnTroVe,btn_thanhtoan;
    ImageView img_SP;
    SanPhamDAO sanPhamDAO;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private ByteArrayOutputStream byteArrayOutputStream;
    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_thanh_toan);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        Cart cart = new Cart();
        naemSP = (TextView) findViewById(R.id.tv_nameProduct);
        img_SP = (ImageView) findViewById(R.id.imageSPcart);
        TongGia = (TextView) findViewById(R.id.tv_tonggia);
        diachi = (EditText) findViewById(R.id.tv_diachi);
        SoDT = (EditText) findViewById(R.id.tv_SDTThanhtoan);
        tennguoinhan = (EditText) findViewById(R.id.tv_tennguoinhan);
        btnTroVe = (Button) findViewById(R.id.trovegiohang);
        btn_thanhtoan = (Button) findViewById(R.id.btn_thanhtoan);
        SoLuong = (TextView) findViewById(R.id.tv_sOluong);
        sanPhamDAO = new SanPhamDAO(ThongTinThanhToanActivity.this);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String name = sharedPreferences.getString(KEY_USERNAME,null);
        naemSP.setText(getIntent().getStringExtra("Tensp"));
        int soluong = getIntent().getIntExtra("Soluong",cart.getSoluong());
        SoLuong.setText(String.valueOf(soluong));
        float giaSp = getIntent().getFloatExtra("GiaSP",cart.getGiaSP());
        TongGia.setText(String.format("%.0f",giaSp) + " vnđ");
        byte[] imageData = getIntent().getByteArrayExtra("Imagesp");
        final Bitmap[] bitmap = {BitmapFactory.decodeStream(new ByteArrayInputStream(imageData))};
        Glide.with(ThongTinThanhToanActivity.this).load(bitmap[0]).into(img_SP);

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
            }
        });

        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Cart> cartList = sanPhamDAO.getAllCart();
                for(Cart cart1 : cartList){

                    String namesp = naemSP.getText().toString();
                    String nguoinhan = tennguoinhan.getText().toString();
                    String sodt = SoDT.getText().toString();
                    String DiaChi = diachi.getText().toString();
                    Bitmap bitmap = cart1.getImageSP();
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byte[] imageSP = byteArrayOutputStream.toByteArray();
                    //Lấy ngày hiện tại
                    Calendar calendar = Calendar.getInstance();
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String currentDate = sdf.format(calendar.getTime());

                    if(tennguoinhan.equals("") || SoDT.equals("") || DiaChi.equals("")){
                        Toast.makeText(ThongTinThanhToanActivity.this,"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(sanPhamDAO.insertThanhToan(ThongTinThanhToanActivity.this,cart1.getMaSP(),namesp,giaSp,cart1.getThongTinSP(),DiaChi,sodt,imageSP,soluong,nguoinhan,currentDate,name)){
                            sanPhamDAO.deleteCart(ThongTinThanhToanActivity.this,cart1.getMaSP(),cart1.getTenKh());
                            sanPhamDAO.updateCartStatus(ThongTinThanhToanActivity.this,cart1.getMaSP(),"0");
                            startActivity(new Intent(ThongTinThanhToanActivity.this,ThanhToanThanhCong.class));
                            finish();
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                        }
                        else {
                            Toast.makeText(ThongTinThanhToanActivity.this,"Thanh toán thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}