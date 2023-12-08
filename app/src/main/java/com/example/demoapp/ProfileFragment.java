package com.example.demoapp;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private ConstraintLayout btn_TTKH;
    private Button btnlogout;

    CircleImageView circleImageView;
    TextView tv_nameuser;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    KhachHangDao khachHangDao;
    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    public ProfileFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnlogout = (Button) view.findViewById(R.id.btn_dn);
        btn_TTKH = (ConstraintLayout) view.findViewById(R.id.TTKH);
        tv_nameuser = (TextView) view.findViewById(R.id.tv_username);
        circleImageView = (CircleImageView) view.findViewById(R.id.imageAccount);
        khachHangDao = new KhachHangDao(getActivity());

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.apply();
                Intent i = new Intent(getActivity(),MainActivity.class);
                startActivity(i);
            }
        });
        btn_TTKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),MyAccountActivity.class));
            }
        });

        //Hien ten user
        String name = sharedPreferences.getString(KEY_USERNAME,null);
        String pass = sharedPreferences.getString(KEY_PASSWORD,null);
        KhachHang khachHang = khachHangDao.getPersonByUsername(name);
        if(name != null || pass != null){
            //So set data on textView
            tv_nameuser.setText(khachHang.getTenKH());
            Bitmap byteImage = khachHang.getAvatar();
            circleImageView.setImageBitmap(byteImage);
        }
        return view;
    }
}