package com.example.demoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import Database.DBHelper;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyAccountActivity extends AppCompatActivity {

    CircleImageView avatar;
    ImageView btn_back;
    RelativeLayout  btn_updateSDT, btn_updateGioiTinh, btn_updateEmail, btn_updateNgaySinh;
    AlertDialog dialog;
    RadioButton radioButton;
    KhachHangDao khachHangDao;
    TextView tv_ten,tv_sodt,tv_gioitinh,tv_email,tv_ngaysinh;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private Uri uri;
    private Bitmap bitmapImage;
    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TEN = "myname";
    private static final String KEY_SDT= "sodt";
    private static final String KEY_GIOITINH = "gioitinh";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NGAYSINH = "ngaysinh";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        //Ánh xạ
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        btn_back = (ImageView) findViewById(R.id.img_back);
        btn_updateSDT = findViewById(R.id.capnhat_sdt);
        btn_updateGioiTinh = findViewById(R.id.capnhat_gioitinh);
        btn_updateEmail = findViewById(R.id.capnhat_email);
        btn_updateNgaySinh = findViewById(R.id.capnhat_date);
        mNgaySinh = (TextView) findViewById(R.id.tv_myngaysinh);
        tv_ten = (TextView) findViewById(R.id.tv_name);
        tv_sodt = (TextView)findViewById(R.id.tv_mysdt);
        tv_gioitinh = (TextView)findViewById(R.id.tv_gt);
        tv_email = (TextView)findViewById(R.id.tv_myemail);
        avatar = (CircleImageView) findViewById(R.id.img_account);
        khachHangDao = new KhachHangDao(MyAccountActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(MyAccountActivity.this);

        //Xự kiện edit tên

        //Hien ten user
        String name = sharedPreferences.getString(KEY_USERNAME,null);

       KhachHang khachHang = khachHangDao.getPersonByUsername(name);
       if(khachHang != null){
           tv_ten.setText(khachHang.getTenKH());
           tv_sodt.setText(khachHang.getSDT());
           tv_gioitinh.setText(khachHang.getGioiTinh());
           tv_email.setText(khachHang.getEmailKH());
           mNgaySinh.setText(khachHang.getNgaySinh());
           bitmapImage = khachHang.getAvatar();
           avatar.setImageBitmap(bitmapImage);
       }
        //Update số đt
        btn_updateSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View viewDialogSDT = getLayoutInflater().inflate(R.layout.edit_sdt,null);
                Button btn_cnsdt = (Button) viewDialogSDT.findViewById(R.id.update_sdt);
                EditText upsodt = (EditText) viewDialogSDT.findViewById(R.id.edt_sdt);
                ImageView thoat = (ImageView) viewDialogSDT.findViewById(R.id.img_thoatSDT);
                builder.setView(viewDialogSDT);
                dialog = builder.create();
                dialog.show();
                btn_cnsdt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String sodt = upsodt.getText().toString();
                        khachHang.setSDT(sodt);
                        if(khachHangDao.update(MyAccountActivity.this,khachHang)){
                            Toast.makeText(MyAccountActivity.this,"Cập nhật số điện thoại thành công",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            tv_sodt.setText(sodt);
                        }
                        else {
                            Toast.makeText(MyAccountActivity.this,"Cập nhật thất bại thất bại.....",Toast.LENGTH_SHORT).show();
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
       //Update giới tính
        btn_updateGioiTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View viewDialogGioiTinh = getLayoutInflater().inflate(R.layout.edit_gioitinh,null);
                Button btn_cngioitinh = (Button) viewDialogGioiTinh.findViewById(R.id.update_gioitinh);
                RadioGroup group = (RadioGroup) viewDialogGioiTinh.findViewById(R.id.radgroup);
                ImageView thoat = (ImageView) viewDialogGioiTinh.findViewById(R.id.img_thoatGioitinh);

                builder.setView(viewDialogGioiTinh);
                dialog = builder.create();
                dialog.show();
                btn_cngioitinh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectGT = group.getCheckedRadioButtonId();
                        radioButton = (RadioButton) viewDialogGioiTinh.findViewById(selectGT);
                        String gt = radioButton.getText().toString();
                        khachHang.setGioiTinh(gt);
                        if(khachHangDao.update(MyAccountActivity.this,khachHang)){
                            Toast.makeText(MyAccountActivity.this,"Cập nhật giới tính thành công",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            tv_gioitinh.setText(gt);
                        }
                        else {
                            Toast.makeText(MyAccountActivity.this,"Cập nhật giới tính thất bại.....",Toast.LENGTH_SHORT).show();
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

        //Update email
        btn_updateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View viewDialogEmail = getLayoutInflater().inflate(R.layout.edit_email,null);
                Button btn_cnemail = (Button) viewDialogEmail.findViewById(R.id.update_email);
                EditText upemail = (EditText) viewDialogEmail.findViewById(R.id.edt_email);
                ImageView thoat = (ImageView) viewDialogEmail.findViewById(R.id.img_thoatEmail);
                builder.setView(viewDialogEmail);
                dialog = builder.create();
                dialog.show();

                btn_cnemail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = upemail.getText().toString();
                        khachHang.setEmailKH(email);
                        if(khachHangDao.update(MyAccountActivity.this,khachHang)){
                            Toast.makeText(MyAccountActivity.this,"Cập nhật Email thành công",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            tv_email.setText(email);
                        }
                        else {
                            Toast.makeText(MyAccountActivity.this,"Cập nhật Email thất bại.....",Toast.LENGTH_SHORT).show();
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
        //Xự kiện edit Ngày sinh
        btn_updateNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MyAccountActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        date_time = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        khachHang.setNgaySinh(date_time);
                        if(khachHangDao.update(MyAccountActivity.this,khachHang)){
                            Toast.makeText(MyAccountActivity.this,"Cập nhật ngày sinh thành công",Toast.LENGTH_SHORT).show();
                            mNgaySinh.setText(date_time);
                        }
                        else {
                            Toast.makeText(MyAccountActivity.this,"Cập nhật ngày sinh thất bại.....",Toast.LENGTH_SHORT).show();
                        }

                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });
        //Xự kiện thoát trang
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
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
                String name = sharedPreferences.getString(KEY_USERNAME,null);
                khachHangDao = new KhachHangDao(MyAccountActivity.this);
                KhachHang khachHang = khachHangDao.getPersonByUsername(name);
                khachHang.setAvatar(bitmapImage);
                if(khachHangDao.update(MyAccountActivity.this,khachHang)){
                    Toast.makeText(MyAccountActivity.this,"Cập nhật Avatar thành công",Toast.LENGTH_SHORT).show();
                    avatar.setImageBitmap(bitmapImage);
                }else {
                    Toast.makeText(MyAccountActivity.this,"Cập nhật Avatar thất bại",Toast.LENGTH_SHORT).show();
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    int mYear;
    int mMonth;
    int mDay;
    String date_time = "";
    TextView mNgaySinh;
}