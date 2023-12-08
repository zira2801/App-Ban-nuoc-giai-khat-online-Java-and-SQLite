package com.example.demoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.KhachHangViewHolder>{

    List<KhachHang> khachHangs;
    Context mContext;
    private ItemKHClickListener mClickListener;

    KhachHangDao khachHangDao;

    public KhachHangAdapter(Context context, List<KhachHang> khachHangList, ItemKHClickListener itemClickListener){
        khachHangs = khachHangList;
        this.mContext = context;
        mClickListener = itemClickListener;
    }
    public interface ItemKHClickListener{
        void onItemClick(KhachHang khachHang);
    }
    @NonNull
    @Override
    public KhachHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_khachhang,parent,false);
        khachHangDao = new KhachHangDao(mContext);
        return new KhachHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangViewHolder holder, int position) {
        KhachHang khachHang = khachHangs.get(position);
        if(khachHangs == null){
            return;
        }
            Bitmap bitmap = khachHang.getAvatar();
            holder.tenKH.setText(khachHang.getTenKH());
            holder.EmailKH.setText(khachHang.getEmailKH());
            holder.imageKH.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(view -> {
            mClickListener.onItemClick(khachHang); // nó sẽ hiện lên vị trí item trong recyleview
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
                        khachHangDao.delete(mContext,khachHang);
                        khachHangs.remove(khachHang);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "Xóa tài khoản " + khachHang.getTenKH() + " thành công", Toast.LENGTH_SHORT).show();
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
        return khachHangs.size();
    }

    public class KhachHangViewHolder extends RecyclerView.ViewHolder{
        TextView tenKH,EmailKH;
        ImageView imageKH;
        public KhachHangViewHolder(@NonNull View itemView) {
            super(itemView);

            tenKH = itemView.findViewById(R.id.tv_tenKH);
            EmailKH = itemView.findViewById(R.id.tv_KHemail);
            imageKH = itemView.findViewById(R.id.AvatarKH);
        }
    }
}
