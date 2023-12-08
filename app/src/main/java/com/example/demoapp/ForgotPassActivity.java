package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ForgotPassActivity extends AppCompatActivity {

    EditText editText;
    Button btn_resetpass;
    KhachHangDao khachHangDao;
    ImageView img_back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        editText = (EditText) findViewById(R.id.edt_nhapusername);
        btn_resetpass = (Button) findViewById(R.id.btn_forgotpass);
        img_back = (ImageView) findViewById(R.id.btn_trovelogin);
        khachHangDao = new KhachHangDao(ForgotPassActivity.this);
        btn_resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editText.getText().toString();
                if(username.equals("")){
                    Toast.makeText(ForgotPassActivity.this,"Bạn phải nhập tên tài khoản",Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkusername = khachHangDao.checkNameTK(ForgotPassActivity.this,username);
                    if(checkusername == true){
                        Intent intent = new Intent(ForgotPassActivity.this,UpdatePassActivity.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                    }
                    else{
                        Toast.makeText(ForgotPassActivity.this,"Tài khoản không tồn tại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassActivity.this,LoginActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
            }
        });
    }
}