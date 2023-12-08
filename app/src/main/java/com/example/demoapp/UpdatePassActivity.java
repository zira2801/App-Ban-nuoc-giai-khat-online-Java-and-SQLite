package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class UpdatePassActivity extends AppCompatActivity {

    EditText edt_newpass,edt_repass;
    Button xacnhanrepass;
    TextView tentk;
    KhachHangDao khachHangDao;
    ImageView img_back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        edt_newpass = (EditText) findViewById(R.id.edt_nhappass);
        edt_repass = (EditText) findViewById(R.id.edt_nhaprepass);
        tentk = (TextView) findViewById(R.id.tv_tentaikhoan);
        img_back = (ImageView) findViewById(R.id.btn_troveforgotpass);
        khachHangDao = new KhachHangDao(UpdatePassActivity.this);
        xacnhanrepass = (Button) findViewById(R.id.btn_xacnhanresetpass);

        Intent intent = getIntent();
        tentk.setText(intent.getStringExtra("username"));
        xacnhanrepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = tentk.getText().toString();
                String password = edt_newpass.getText().toString();
                String repass = edt_repass.getText().toString();
                if(password.equals(repass)){
                    Boolean check_pass_update = khachHangDao.updatepassword(UpdatePassActivity.this,user,password);
                    if(check_pass_update == true){
                        Toast.makeText(UpdatePassActivity.this, "Đặt lại mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(UpdatePassActivity.this,LoginActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                    }
                    else{
                        Toast.makeText(UpdatePassActivity.this, "Đặt lại mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(UpdatePassActivity.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdatePassActivity.this,ForgotPassActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
            }
        });
    }
}