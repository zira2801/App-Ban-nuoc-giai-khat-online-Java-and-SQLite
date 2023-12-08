package com.example.demoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import DanhSachSP.SanPham;

public class SanPhamListAdapter extends RecyclerView.Adapter<SanPhamListAdapter.SanPhamListVIewHolder>{
    private List<SanPham> sanPhamList;
    private Context mContext;
    private ItemClickListener mClickListener;

    private SanPhamDAO sanPhamDAO;


    public SanPhamListAdapter(List<SanPham> sanPhamList, Context mContext,ItemClickListener itemClickListener) {
        this.sanPhamList = sanPhamList;
        this.mContext = mContext;
        mClickListener = itemClickListener;
        sanPhamDAO = new SanPhamDAO(mContext);
    }

    public void setData(List<SanPham> list){
        this.sanPhamList = list;
        notifyDataSetChanged();
    }

    public interface ItemClickListener{
        void onItemClick(SanPham sanPham);
    }

    @NonNull
    @Override
    public SanPhamListVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sanpham,parent,false);
        return new SanPhamListVIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamListVIewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        if(sanPham == null){
            return;
        }
        float giaSp = sanPham.getGiaSP();
        Bitmap bitmap = sanPham.getImageSP();
        holder.imgSp.setImageBitmap(bitmap);
        holder.tvmasp.setText(sanPham.getMaSP());
        holder.tvTenSP.setText(sanPham.getTenSP());
        holder.tvLoai.setText(sanPham.getLoaiSP());
        holder.tvGia.setText(String.format("%.0f",giaSp) + " vnđ");

        holder.itemView.setOnClickListener(view -> {
            mClickListener.onItemClick(sanPham); // nó sẽ hiện lên vị trí item trong recyleview
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Bạn chắc chắn muốn xóa?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xóa dữ liệu tương ứng từ DAO và cập nhật lại RecyclerView
                        sanPhamDAO.delete(mContext,sanPham);
                        sanPhamList.remove(sanPham);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "Xóa sản phẩm " + sanPham.getMaSP() + " thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Không", null);
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(sanPhamList!=null){
            return sanPhamList.size();
        }
        return 0;
    }

    public void onClick(){

    }
    public class SanPhamListVIewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSp;
        private TextView tvmasp;
        private TextView tvTenSP;
        private TextView tvLoai;
        private TextView tvGia;
        private CardView layout_item;
        public SanPhamListVIewHolder(@NonNull View itemView) {
            super(itemView);
            imgSp = itemView.findViewById(R.id.imageSP);
            tvmasp = itemView.findViewById(R.id.tv_masp);
            tvTenSP = itemView.findViewById(R.id.tv_tensp);
            tvGia = itemView.findViewById(R.id.tv_giasp);
            tvLoai = itemView.findViewById(R.id.tv_loaisp);
            layout_item = itemView.findViewById(R.id.layout_dssp);
        }
    }
}
