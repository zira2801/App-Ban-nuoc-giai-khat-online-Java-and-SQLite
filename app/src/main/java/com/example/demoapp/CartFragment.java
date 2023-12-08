package com.example.demoapp;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import DanhSachSP.SanPham;
import Database.DBHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.x
 */
public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    private DBHelper dbHelper;
    private SanPhamDAO sanPhamDAO;
    private List<Cart> CartItemList;
    private CartAdapter cartAdapter;

    TextView lichsumuahang;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";

    private TextView btn_muahang;
    private ByteArrayOutputStream byteArrayOutputStream;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getActivity().getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String name = sharedPreferences.getString(KEY_USERNAME,null);
        dbHelper = new DBHelper(getActivity());
        sanPhamDAO = new SanPhamDAO(getActivity());
        lichsumuahang = view.findViewById(R.id.btn_lichsumuahang);
        List<Integer> checkedPositions = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyleviewCart);
        List<Cart> cartList = sanPhamDAO.getCartByUsername(name);
        if(name == null){
            cartAdapter = new CartAdapter(getActivity(), new ArrayList<>());
            recyclerView.setAdapter(cartAdapter);
        }
        else{
            CartItemList = sanPhamDAO.getCartByUsername(name);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
            recyclerView.setLayoutManager(linearLayoutManager);
            cartAdapter = new CartAdapter(getActivity(), CartItemList);
            recyclerView.setAdapter(cartAdapter);
        }


/*
        btn_muahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ThongTinThanhToanActivity.class);
                    int position = cartList.size();
                    Cart cart = new Cart();
                    Bitmap bitmap = cart.getImageSP();
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byte[] imageSP = byteArrayOutputStream.toByteArray();
                    intent.putExtra("Tensp",cart.getTenSP());
                    intent.putExtra("Imagesp",imageSP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    (getActivity()).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
        });*/

        lichsumuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ThanhToanActivity.class));
                (getActivity()).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
        return view;
    }
}