package com.example.demoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegisterActivity extends AppCompatActivity {

    String[] cameraPermission;
    String[] storagePermission;

    EditText username, password,repassword,ten,sdt,gioitinh,ngaysinh,email;
    KhachHangDao DBDangKy;
    CircleImageView avatar;
    KhachHangDao khachHangDao;
    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_SDT= "sodt";
    private static final String KEY_GIOITINH = "gioitinh";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NGAYSINH = "ngaysinh";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private Uri uri;
    private Bitmap bitmapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        Anhxa();
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();


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
                avatar.setImageBitmap(bitmapImage);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void Anhxa(){
        username =(EditText) findViewById(R.id.edt_username);
        password = (EditText) findViewById(R.id.edt_password);
        repassword = (EditText) findViewById(R.id.edt_repassword);
        ten = (EditText) findViewById(R.id.edt_ten);
        sdt = (EditText) findViewById(R.id.edt_sdt);
        gioitinh = (EditText)findViewById(R.id.edt_gioitinh);
        ngaysinh = (EditText) findViewById(R.id.edt_date);
        email = (EditText) findViewById(R.id.edt_email);
        avatar = (CircleImageView) findViewById(R.id.profile_image);
        DBDangKy = new KhachHangDao(this);

    }
    public void btn_register(View view) {
        String user = username.getText().toString();
        String pass = password.getText().toString();
        String repass = repassword.getText().toString();
        String mysdt = sdt.getText().toString();
        String mygioitinh = gioitinh.getText().toString();
        String myemail = email.getText().toString();
        String mydate = ngaysinh.getText().toString();
        KhachHang khachHang = new KhachHang(user,pass,mysdt,mygioitinh,myemail,mydate,bitmapImage);
        if(user.equals("") ||pass.equals("")|| repass.equals("")  || mysdt.equals("") || mygioitinh.equals("") || myemail.equals("") || mydate.equals("") || bitmapImage == null){
            Toast.makeText(RegisterActivity.this,"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
        }
        else{
            if(username.length() >0 || password.length() >0 || repassword.length() >0 || sdt.length() >0 || email.length() >0 || gioitinh.length() >0 || ngaysinh.length() >0){
                if(user.length() <= 20){
                    if(user.matches(".*\\d.*")) { // Điều kiện phải có 1 ký tự là số
                        if (pass.length() >= 8) {
                            if (pass.equals(repass)) {
                                Boolean checkuser = DBDangKy.checkNameTK(RegisterActivity.this, user);
                                if (checkuser == false) {
                                    if (myemail.contains("@gmail.com")) {
                                        Boolean checkEmail = DBDangKy.checkEmail(RegisterActivity.this, myemail);
                                        if (checkEmail == false) {
                                            if (mysdt.matches("[0-9]+") && mysdt.length() == 10) {  // Số điện thoại phải là ký tự số
                                                Boolean checkSODT = DBDangKy.checkSDT(RegisterActivity.this, mysdt);
                                                if (checkSODT == false) {
                                                    Boolean insert = DBDangKy.insertData(RegisterActivity.this, khachHang);
                                                    if (insert == true) {
                                                        if(mydate.matches("\\d{2}/\\d{2}/\\d{4}")){
                                                            editor.putString(KEY_USERNAME, user);
                                                            editor.putString(KEY_PASSWORD, pass);
                                                            editor.putString(KEY_SDT, mysdt);
                                                            editor.putString(KEY_GIOITINH, mygioitinh);
                                                            editor.putString(KEY_EMAIL, myemail);
                                                            editor.putString(KEY_NGAYSINH, mydate);
                                                            editor.apply();
                                                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                                        } else {
                                                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                                        }
                                                        }else{
                                                        Toast.makeText(RegisterActivity.this, "Ngày tháng năm sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                                                    }

                                                } else {
                                                    Toast.makeText(RegisterActivity.this, "Số điện thoại đã được đăng ký", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Số điện thoại bạn nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Email đã được đăng ký", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Email đã nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Toast.makeText(RegisterActivity.this, "Mật khẩu phải nhập đủ 8 ký tự", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Tên tài khoản phải có ít nhất một ký tự số", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(RegisterActivity.this,"Tên mật khẩu không được vượt quá 20 ký tự",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void btn_back_dk(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finish();
    }
}