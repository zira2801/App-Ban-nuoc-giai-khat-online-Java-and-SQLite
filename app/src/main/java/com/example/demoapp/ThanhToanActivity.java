package com.example.demoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThanhToanActivity extends AppCompatActivity {
    ImageView trovegiohang;
    SanPhamDAO sanPhamDAO;
    List<Payment> payments;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    PaymentAdapter paymentAdapter;
    private Bitmap bitmapImage;
    private Dialog dialog;
    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        trovegiohang = (ImageView) findViewById(R.id.vetranggiohang);
        recyclerView = (RecyclerView) findViewById(R.id.recyleviewlichsumuahang);
        sanPhamDAO = new SanPhamDAO(ThanhToanActivity.this);

        String name = sharedPreferences.getString(KEY_USERNAME,null);
        if(name == null){
            paymentAdapter = new PaymentAdapter(ThanhToanActivity.this, new ArrayList<>(), new PaymentAdapter.ItemLSClickListener() {
                @Override
                public void onItemClick(Payment payment) {
                }
            });
            recyclerView.setAdapter(paymentAdapter);
        }
        else{
            payments = sanPhamDAO.getPaymentByUsername(name);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(ThanhToanActivity.this,DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThanhToanActivity.this,RecyclerView.VERTICAL,false);
            recyclerView.setLayoutManager(linearLayoutManager);
            AlertDialog.Builder builder = new AlertDialog.Builder(ThanhToanActivity.this);
            paymentAdapter = new PaymentAdapter(ThanhToanActivity.this, payments, new PaymentAdapter.ItemLSClickListener() {
                @Override
                public void onItemClick(Payment payment) {
                    View viewDialogDonHang = getLayoutInflater().inflate(R.layout.item_thongtinthanhtoan,null);
                    TextView tenSp = (TextView) viewDialogDonHang.findViewById(R.id.tv_nameSPdonhang);
                    TextView soluong = (TextView) viewDialogDonHang.findViewById(R.id.tv_sOluongDonHang);
                    TextView tonggia = (TextView) viewDialogDonHang.findViewById(R.id.tv_tonggiaDonHang);
                    TextView tennguoinhan = (TextView) viewDialogDonHang.findViewById(R.id.tv_tennguoinhanDonHang);
                    TextView soDT = (TextView) viewDialogDonHang.findViewById(R.id.tv_SDTdonhang);
                    TextView diaChi = (TextView) viewDialogDonHang.findViewById(R.id.tv_diachiDonHang);
                    TextView ngaydat = (TextView) viewDialogDonHang.findViewById(R.id.tv_NgayDatHang);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    ImageView anhSPDH = viewDialogDonHang.findViewById(R.id.imageSPDonHang);
                    ImageView thoat = (ImageView) viewDialogDonHang.findViewById(R.id.dong_dialog_donhang);
                    tenSp.setText(payment.getTenSP());
                    int SOLUONG = payment.getSoluong();
                    soluong.setText(String.valueOf(SOLUONG));
                    float tongGia = payment.getTongGia();
                    tonggia.setText(String.format(String.format("%.0f",tongGia) + " vnđ"));
                    tennguoinhan.setText(payment.getTenNguoiNhan());
                    soDT.setText(payment.getSoDT());
                    diaChi.setText(payment.getDiaChi());
                    ngaydat.setText(payment.getNgayDatHang());
                    bitmapImage = payment.getImageSP();
                    anhSPDH.setImageBitmap(bitmapImage);
                    builder.setView(viewDialogDonHang);
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
            recyclerView.setAdapter(paymentAdapter);
        }

        trovegiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}