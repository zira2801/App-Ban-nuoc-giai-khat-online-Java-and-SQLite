package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import book.Book;

public class DetailActivity extends AppCompatActivity {

    private ImageView img_nuoc,btn_back;
    private TextView tvGia,tv_namenuoc,tv_mieuta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        //Category
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        else{
            Book book = (Book) bundle.get("object_book");
            tvGia = (TextView) findViewById(R.id.gia_ban);
            img_nuoc = (ImageView) findViewById(R.id.anh_nuoc);
            tv_namenuoc = (TextView) findViewById(R.id.tv_namenuoc);
            tv_mieuta = (TextView) findViewById(R.id.description);
            tvGia.setText(book.getTitle());
            img_nuoc.setImageResource(book.getResourceId());
            tv_namenuoc.setText(book.getTenNuoc());
            tv_mieuta.setText(book.getMieuta());
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
}
