package com.example.demoapp;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import DanhSachSP.SanPham;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>{

    private Context mContext;
    private List<FavoriteItem> favoriteItems;
    SanPhamDAO sanPhamDAO;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    KhachHangDao khachHangDao;
    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";

   public FavoriteAdapter(Context context,List<FavoriteItem> favoriteItems){
        mContext = context;
        this.favoriteItems = favoriteItems;
    }
    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_favorite,parent,false);
        sanPhamDAO = new SanPhamDAO(mContext);
        khachHangDao = new KhachHangDao(mContext);
        sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        FavoriteItem favoriteItem = favoriteItems.get(position);
        if(favoriteItem == null){
            return;
        }

        float giaSp = favoriteItem.getGiaSP();
        Bitmap bitmap = favoriteItem.getImageSP();
        holder.imageSP.setImageBitmap(bitmap);
        holder.tenSP.setText(favoriteItem.getTenSP());
        holder.giaSP.setText(String.format("%.0f",giaSp) + " vnđ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailSPActivity.class);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap =favoriteItem.getImageSP();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("tensp",favoriteItem.getTenSP());
                intent.putExtra("ttsp",favoriteItem.getThongTinSP());
                intent.putExtra("giasp",favoriteItem.getGiaSP());
                intent.putExtra("imagesp",byteArray);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteItems.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder{
        TextView tenSP,giaSP;
        ImageView imageSP,imageFavorited;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            tenSP = itemView.findViewById(R.id.tv_tenspfv);
            giaSP = itemView.findViewById(R.id.tv_giaspfav);
            imageSP = itemView.findViewById(R.id.imageSPfav);
            imageFavorited = itemView.findViewById(R.id.img_favorited);
            imageFavorited.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    khachHangDao = new KhachHangDao(mContext);
                    String name = sharedPreferences.getString(KEY_USERNAME,null);
                    final FavoriteItem favoriteItem = favoriteItems.get(position);
                    if(sanPhamDAO.removeFavorite(mContext,favoriteItem.getMasp(),favoriteItem.getTenKh())){
                        Toast.makeText(mContext,"Xóa khỏi danh sách yêu thích thành công",Toast.LENGTH_SHORT).show();
                        sanPhamDAO.deleteFavorite(mContext,favoriteItem.getMasp(),favoriteItem.getTenKh());
                        removeItem(position);
                        notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(mContext,"Xóa khỏi danh sách yêu thích thất bại",Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }
    private void removeItem(int position) {
        favoriteItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,favoriteItems.size());
    }
}
