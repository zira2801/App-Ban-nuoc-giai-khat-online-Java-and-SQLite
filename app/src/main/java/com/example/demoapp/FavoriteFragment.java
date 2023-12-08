package com.example.demoapp;

import static android.accounts.AccountManager.KEY_PASSWORD;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import Database.DBHelper;

public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    RecyclerView recyclerView;
    private DBHelper dbHelper;
    private SanPhamDAO sanPhamDAO;
    private List<FavoriteItem> favoriteItemList;
    private FavoriteAdapter favoriteAdapter;
    KhachHangDao khachHangDao;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getActivity().getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dbHelper = new DBHelper(getActivity());
        sanPhamDAO = new SanPhamDAO(getActivity());
        recyclerView = view.findViewById(R.id.recyleviewFavorite);
        String name = sharedPreferences.getString(KEY_USERNAME,null);
        if(name == null){
            favoriteAdapter = new FavoriteAdapter(getActivity(), new ArrayList<>());
            recyclerView.setAdapter(favoriteAdapter);
        }
        else{
            favoriteItemList = sanPhamDAO.getFavoriteByUsername(name);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
            recyclerView.setLayoutManager(linearLayoutManager);
            favoriteAdapter = new FavoriteAdapter(getActivity(),favoriteItemList);
            recyclerView.setAdapter(favoriteAdapter);
        }
        return view;
    }


}