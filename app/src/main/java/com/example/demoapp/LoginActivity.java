package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

import Database.DBHelper;


public class LoginActivity extends AppCompatActivity {
    private ImageView btnback;
    EditText username, password;
    TextView forgotpass;
    Button btn_dangnhap;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    KhachHangDao DBDangNhap;
    DBHelper dbHelper;
    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        Anhxa();
        btnback = (ImageView) findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
            }
        });

        btn_dangnhap = (Button) findViewById(R.id.btn_dn);
        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    btn_login(view);
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgotPassActivity.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });

    }

    //Anh xa
    public void Anhxa(){
        username = (EditText) findViewById(R.id.edt_username);
        password = (EditText) findViewById(R.id.edt_password);
        dbHelper = new DBHelper(LoginActivity.this);
        DBDangNhap = new KhachHangDao(this);
        forgotpass = (TextView) findViewById(R.id.quenmatkhau);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void btn_login(View view) {
        String user = username.getText().toString();
        String pass = password.getText().toString();
        Boolean checkuserpass = DBDangNhap.checkNametk_password(this,user,pass);
        if(user.equals("") || pass.equals("")){
            Toast.makeText(LoginActivity.this,"Bạn phải nhập đủ tên tài khoản và mật khẩu",Toast.LENGTH_SHORT).show();
        }
        else{
            if(username.length()>0 && password.length()>0){
                if(password.length() >= 8){

                    if(user.equals("admin") && pass.equals("admin@123")){
                        Toast.makeText(LoginActivity.this,"Đăng nhập Admin thành công",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,AdminActivity.class));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                    }
                    else {
                        if(checkuserpass == true){
                            editor.putString(KEY_USERNAME, user);
                            editor.putString(KEY_PASSWORD,pass);
                            editor.apply();
                            Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Bạn đã nhập sai tên tài khoản hoặc mật khẩu",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this,"Mật khẩu phải đủ 8 ký tự trở lên",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }

    public void btn_dangky(View view) {
        Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        finish();
        }
    }