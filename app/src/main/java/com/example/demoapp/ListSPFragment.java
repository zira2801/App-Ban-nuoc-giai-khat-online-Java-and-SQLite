package com.example.demoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import DanhSachSP.SanPham;
import DanhSachSP.SanPhamAdapter;

public class ListSPFragment extends Fragment {
    public ListSPFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private TextView caphe,trasua;
    private RecyclerView rcvCaphe,rcvTraSua,rcv_nuocepsinhto;

    private List<SanPham> sanPhamList1;
    private List<SanPham> sanPhamList2;
    private List<SanPham> sanPhamList3;
    SanPhamDAO sanPhamDAO;
    SanPhamAdapter sanPhamAdapter1,sanPhamAdapter2,sanPhamAdapter3;
    TextView searchView;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_s_p, container, false);

        //Ánh xạ
        caphe = (TextView) view.findViewById(R.id.tv_caphe);
        trasua = (TextView) view.findViewById(R.id.tv_trasua);
        rcvCaphe = view.findViewById(R.id.rcv_caphe);
        rcvTraSua = view.findViewById(R.id.rcv_trasua);
        rcv_nuocepsinhto = view.findViewById(R.id.rcv_nuocep_sinhto);
        sanPhamDAO = new SanPhamDAO(getActivity());
        String caphe = "Cà phê";
        String trasua = "Trà và Trà sữa";
        String nuocep_sinhto = "Nước ép sinh tố";
        sanPhamList1 = sanPhamDAO.getAllByLoai(caphe);
        sanPhamAdapter1 = new SanPhamAdapter(sanPhamList1, getActivity());
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 2);
        rcvCaphe.setLayoutManager(gridLayoutManager1);
        sanPhamAdapter1.setData(sanPhamList1);
        rcvCaphe.setAdapter(sanPhamAdapter1);

        sanPhamList2 = sanPhamDAO.getAllByLoai(trasua);
        sanPhamAdapter2 = new SanPhamAdapter(sanPhamList2, getActivity());
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 2);
        rcvTraSua.setLayoutManager(gridLayoutManager2);
        sanPhamAdapter2.setData(sanPhamList2);
        rcvTraSua.setAdapter(sanPhamAdapter2);


        sanPhamList3 = sanPhamDAO.getAllByLoai(nuocep_sinhto);
        sanPhamAdapter3 = new SanPhamAdapter(sanPhamList3, getActivity());
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getActivity(), 2);
        rcv_nuocepsinhto.setLayoutManager(gridLayoutManager3);
        sanPhamAdapter3.setData(sanPhamList3);
        rcv_nuocepsinhto.setAdapter(sanPhamAdapter3);


        searchView = (TextView) view.findViewById(R.id.searchview);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TimKiemSP.class));
            }
        });
        return view;
    }
}