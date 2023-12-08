package com.example.demoapp;

import static android.content.Context.MODE_PRIVATE;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.util.List;

import DanhSachSP.SanPham;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CarViewHolder> {
    private Context mContext;
    private List<Cart> cartList;
    SanPhamDAO sanPhamDAO;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    KhachHangDao khachHangDao;

    private ByteArrayOutputStream byteArrayOutputStream;
    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";

    public CartAdapter(Context context,List<Cart> cartList){
        mContext = context;
        this.cartList = cartList;
    }


    @NonNull
    @Override
    public CartAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_cart,parent,false);
        sanPhamDAO = new SanPhamDAO(mContext);
        khachHangDao = new KhachHangDao(mContext);
        sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return new CarViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CarViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        SanPham sanPham = sanPhamDAO.getAllByMaSP(cart.getMaSP());
        if(cartList == null){
            return;
        }
        float giaSp = cart.getGiaSP();
        Bitmap bitmap = cart.getImageSP();
        holder.imageSP.setImageBitmap(bitmap);
        holder.tenSP.setText(cart.getTenSP());
        holder.giaSP.setText(String.format("%.0f",giaSp) + " vnđ");
        holder.soluong.setText(String.valueOf(cart.getSoluong()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailSPActivity.class);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap =cart.getImageSP();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("tensp",cart.getTenSP());
                intent.putExtra("ttsp",cart.getThongTinSP());
                intent.putExtra("giasp",sanPham.getGiaSP());
                intent.putExtra("imagesp",byteArray);
                intent.putExtra("soluong",cart.getSoluong());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
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
                        sanPhamDAO.deleteCart(mContext,cart.getMaSP(), cart.getTenKh());
                        cartList.remove(cart);
                        sanPhamDAO.updateCartStatus(mContext, cart.getMaSP(), "0");
                        Toast.makeText(mContext, "Xóa sản phẩm khỏi giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
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
        if(cartList != null){
            return cartList.size();
        }
        return 0;
    }

  /*  public interface OnCheckedChangeListener {
        void onCheckedChange(int position, boolean isChecked);
    }*/
    public class CarViewHolder extends RecyclerView.ViewHolder{
        TextView tenSP,giaSP,soluong,btn_thanhtoan;
        ImageView imageSP,plus,remove;

      /*  private CheckBox myCheckBox;
        private OnCheckedChangeListener listener;*/
        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSP = itemView.findViewById(R.id.tv_tenspcart);
            soluong = itemView.findViewById(R.id.tv_soluong_cart);
            imageSP = itemView.findViewById(R.id.imageSPcart);
            plus = itemView.findViewById(R.id.plusitem);
            remove=itemView.findViewById(R.id.removeitem);
            giaSP = itemView.findViewById(R.id.tv_giaspcart);
            btn_thanhtoan = itemView.findViewById(R.id.btn_muahang);

            btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ThongTinThanhToanActivity.class);
                    int position = getAdapterPosition();
                    Cart cart = cartList.get(position);
                    Bitmap bitmap = cart.getImageSP();
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byte[] imageSP = byteArrayOutputStream.toByteArray();
                    intent.putExtra("Tensp",cart.getTenSP());
                    intent.putExtra("Imagesp",imageSP);
                    intent.putExtra("GiaSP",cart.getGiaSP());
                    intent.putExtra("Soluong",cart.getSoluong());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            });
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Cart cart = cartList.get(position);
                    SanPham sanPham = sanPhamDAO.getAllByMaSP(cart.getMaSP());
                    int so_luong = cart.getSoluong();
                    so_luong+=1;
                    sanPhamDAO.updateSL(mContext,cart.getMaSP(),cart.getTenKh(), so_luong);
                    float giasptheoSL = so_luong * sanPham.getGiaSP();
                    cart.setGiaSP(Math.round(giasptheoSL));
                    cart.setSoluong(so_luong);
                    sanPhamDAO.updateGia(mContext,cart.getMaSP(),cart.getTenKh(),giasptheoSL);
                    soluong.setText(String.valueOf(so_luong));
                    DecimalFormat decimalFormat = new DecimalFormat("###.##");
                    String formattedValue = decimalFormat.format(giasptheoSL);
                    giaSP.setText(formattedValue + " vnđ");
                }
            });
            remove.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Cart cart = cartList.get(position);
                    SanPham sanPham = sanPhamDAO.getAllByMaSP(cart.getMaSP());
                    int so_luong = cart.getSoluong();
                    if(so_luong == 0){
                        return;
                    }
                    else {
                        so_luong -=1;
                    }
                    sanPhamDAO.updateSL(mContext,cart.getMaSP(),cart.getTenKh(), so_luong);
                    float giasptheoSL = so_luong * sanPham.getGiaSP();
                    sanPhamDAO.updateGia(mContext,cart.getMaSP(),cart.getTenKh(),giasptheoSL);
                    cart.setGiaSP(Math.round(giasptheoSL));
                    cart.setSoluong(so_luong);
                    soluong.setText(String.valueOf(so_luong));
                    DecimalFormat decimalFormat = new DecimalFormat("###.##");
                    String formattedValue = decimalFormat.format(giasptheoSL);
                    giaSP.setText(formattedValue + " vnđ");
                }

            });

        }

    }
}
