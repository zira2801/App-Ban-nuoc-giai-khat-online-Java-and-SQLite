package com.example.demoapp;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private Context mContext;
    private List<Payment> payments;
    SanPhamDAO sanPhamDAO;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    KhachHangDao khachHangDao;
    private ByteArrayOutputStream byteArrayOutputStream;
    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";

    private ItemLSClickListener mClickListener;
    public interface ItemLSClickListener{
        void onItemClick(Payment payment);
    }
    public PaymentAdapter(Context context,List<Payment> paymentList,ItemLSClickListener itemLSClickListener){
        mContext = context;
        this.payments = paymentList;
        mClickListener = itemLSClickListener;
    }


    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_thanhtoan,parent,false);
        sanPhamDAO = new SanPhamDAO(mContext);
        khachHangDao = new KhachHangDao(mContext);
        sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return new PaymentViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Payment p = payments.get(position);
        if(payments == null){
            return;
        }
        float giaSp = p.getTongGia();
        Bitmap bitmap = p.getImageSP();
        holder.anhSP.setImageBitmap(bitmap);
        holder.tenSP.setText(p.getTenSP());
        holder.giaSP.setText(String.format("%.0f",giaSp) + " vnđ");
        holder.soluong.setText(String.valueOf(p.getSoluong()));
        holder.itemView.setOnClickListener(view -> {
            mClickListener.onItemClick(p); // nó sẽ hiện lên vị trí item trong recyleview
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
                        sanPhamDAO.deletePayment(mContext,p.getId_thanhtoan());
                        payments.remove(p);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "Xóa đơn hàng " + p.getNgayDatHang() + " thành công", Toast.LENGTH_SHORT).show();
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
        if(payments != null){
            return payments.size();
        }
        return 0;
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder{
        TextView tenSP,giaSP,soluong;
        ImageView anhSP;
        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);

            tenSP = itemView.findViewById(R.id.tv_tenspDatHang);
            giaSP = itemView.findViewById(R.id.tv_giaspDatHang);
            giaSP = itemView.findViewById(R.id.tv_soluongDatHang);
            soluong = itemView.findViewById(R.id.tv_soluongDatHang);
            anhSP = itemView.findViewById(R.id.imageSPLSDatHang);
        }
    }
}
