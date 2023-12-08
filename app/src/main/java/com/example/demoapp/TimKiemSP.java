package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import DanhSachSP.SanPham;
import DanhSachSP.SanPhamAdapter;

public class TimKiemSP extends AppCompatActivity {

    List<SanPham> sanPhamList;
    SanPhamDAO sanPhamDAO;
    RecyclerView recyclerView;
    ImageView back_timkiem;

    SearchView searchView;
    SanPhamAdapter sanPhamAdapter;
    Button btn_search;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_sp);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        back_timkiem = (ImageView) findViewById(R.id.back_timkiem);
        recyclerView = (RecyclerView) findViewById(R.id.rcv_timkiemsp);
        searchView = findViewById(R.id.searchviewSP);
        btn_search = (Button) findViewById(R.id.btn_timkiem);
        sanPhamDAO = new SanPhamDAO(TimKiemSP.this);
       /* sanPhamList = (List<SanPham>) sanPhamDAO.getAll();*/
      /*  sanPhamAdapter = new SanPhamAdapter(sanPhamList,TimKiemSP.this);*/
        sanPhamAdapter = new SanPhamAdapter(new ArrayList<>());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(TimKiemSP.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        /* sanPhamAdapter.setData(sanPhamList);*/
        recyclerView.setAdapter(sanPhamAdapter);
        back_timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = searchView.getQuery().toString();
                search(keyword);
                showSearchResults();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.length()>0){
                    search(s);
                    showSearchResults();
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if (s.trim().isEmpty()) {
                    hideSearchResults();
                }
                return false;
            }
        });
    }

    //hideSearchResults() là một hàm tùy chỉnh để ẩn danh sách kết quả tìm kiếm khi
    // không có từ khóa tìm kiếm hoặc người dùng đã xóa tất cả nội dung trong SearchView
    private void hideSearchResults() {
        recyclerView.setVisibility(View.GONE);
    }

    private void showSearchResults() {
        recyclerView.setVisibility(View.VISIBLE); // Hiển thị recyclerView
    }
    private void search(String s) {
        List<SanPham> productList = sanPhamDAO.searchProducts(s);
        sanPhamAdapter = new SanPhamAdapter(productList,TimKiemSP.this);
        recyclerView.setAdapter(sanPhamAdapter);
    }
}