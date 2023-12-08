package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThanhToanThanhCong extends AppCompatActivity {

    Button vetrangchu;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan_thanh_cong);
        vetrangchu = (Button) findViewById(R.id.btn_vetrangchu);

        vetrangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ThanhToanThanhCong.this,MainActivity.class));
                finish();
            }
        });
    }
}