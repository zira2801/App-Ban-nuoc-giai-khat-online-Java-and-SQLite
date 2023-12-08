package DanhSachSP;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.DetailSPActivity;
import com.example.demoapp.KhachHang;
import com.example.demoapp.KhachHangDao;
import com.example.demoapp.LoginActivity;
import com.example.demoapp.R;
import com.example.demoapp.SanPhamDAO;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import Database.DBHelper;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder> implements Filterable {
    private List<SanPham> sanPhamList;
    private Context mContext;
    private List<SanPham> mListSPOld;
    private SanPhamDAO sanPhamDAO;
    private KhachHangDao khachHangDao;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DBHelper dbHelper;

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOADING = 2;
    private boolean isLoading; //Kiểm tra xem có đang loading ko
    private ByteArrayOutputStream byteArrayOutputStream;
    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    @Override
    public int getItemViewType(int position) {
        if(sanPhamList != null && position == sanPhamList.size() - 1 && isLoading){
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    public SanPhamAdapter(List<SanPham> sanPhamList, Context mContext) {
        this.sanPhamList = sanPhamList;
        this.mContext = mContext;
        mListSPOld = sanPhamList;
    }

    public SanPhamAdapter(List<SanPham> filteredList) {
        sanPhamList = filteredList;
    }


    public void setData(List<SanPham> list){
        this.sanPhamList = list;
        notifyDataSetChanged();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    sanPhamList = mListSPOld;
                }
                else{
                    List<SanPham> list = new ArrayList<>();
                    for(SanPham sanPham : mListSPOld){
                        if(sanPham.getTenSP().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(sanPham);
                        }
                    }

                    sanPhamList = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = sanPhamList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                sanPhamList = (List<SanPham>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sanPhamDAO = new SanPhamDAO(mContext);
        khachHangDao = new KhachHangDao(mContext);
        dbHelper = new DBHelper(mContext);
        sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham, parent, false);
            return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
            SanPham sanPham = sanPhamList.get(position);
            SanPhamViewHolder sanPhamViewHolder = (SanPhamViewHolder) holder;
            if(sanPham == null){
                return;
            }
            float giaSp = sanPham.getGiaSP();
            Bitmap bitmap = sanPham.getImageSP();
            sanPhamViewHolder.imgSp.setImageBitmap(bitmap);
            sanPhamViewHolder.tvName.setText(sanPham.getTenSP());
            sanPhamViewHolder.tvGia.setText(String.format("%.0f",giaSp) + " vnđ");
            sanPhamViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailSPActivity.class);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bitmap = sanPham.getImageSP();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("tensp",sanPham.getTenSP());
                    intent.putExtra("ttsp",sanPham.getThongTinSP());
                    intent.putExtra("giasp",sanPham.getGiaSP());
                    intent.putExtra("imagesp",byteArray);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            });

            //Kiểm tra tình trang yêu thích
            String name = sharedPreferences.getString(KEY_USERNAME,null);
            if(name == null){
                return;
            }
            else {
                Cursor cursor = null;
                try {
                    SQLiteDatabase database = dbHelper.getReadableDatabase();
                    cursor = database.rawQuery("SELECT * FROM SanPhamYeuThich WHERE MaSP = ? AND TenKh = ?", new String[]{sanPham.getMaSP(), name});
                    while (cursor.moveToNext()) {
                        @SuppressLint("Range")
                        String favoriteStatus = cursor.getString(cursor.getColumnIndex("FavoriteStatus"));
                        //nếu tình trạng yêu thích là 1, hiển thị đã yêu thích
                        sanPham.setFavStatus(favoriteStatus);
                        if ("1".equals(favoriteStatus) && favoriteStatus != null) {
                            sanPhamViewHolder.btn_fav.setBackgroundResource(R.drawable.baseline_favorite_24_3);
                        }
                        //nếu tình trạng yêu thích là '0', hiển thị trái tim ko yêu thích
                        else if ("0".equals(favoriteStatus) && favoriteStatus != null) {
                            sanPhamViewHolder.btn_fav.setBackgroundResource(R.drawable.baseline_favorite_24_4);
                        }
                    }
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }

    @Override
    public int getItemCount() {
        if(sanPhamList!=null){
            return sanPhamList.size();
        }
        return 0;
    }
    public class SanPhamViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSp;
        private TextView tvName;
        private TextView tvGia;
        private Button btn_fav;
        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSp = itemView.findViewById(R.id.img_sp);
            tvName = itemView.findViewById(R.id.tv_namesp);
            tvGia = itemView.findViewById(R.id.tv_giasp);
            btn_fav = itemView.findViewById(R.id.btn_favorite);
            btn_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = sharedPreferences.getString(KEY_USERNAME,null);
                    KhachHang khachHang = khachHangDao.getPersonByUsername(name);
                    int positon = getAdapterPosition();
                    SanPham sanPham = sanPhamList.get(positon);
                    if(name == null)
                    {
                        Intent i = new Intent(mContext, LoginActivity.class);
                        mContext.startActivity(i);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        return;
                    }
                    else{
                        if("0".equals(sanPham.getFavStatus())){
                            sanPham.setFavStatus("1");
                            Bitmap bitmap = sanPham.getImageSP();
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                            byte[] imageSP = byteArrayOutputStream.toByteArray();
                            sanPhamDAO.insertDataToFavorite(mContext,sanPham.getMaSP(),sanPham.getTenSP(),sanPham.getGiaSP(),imageSP,sanPham.getFavStatus(),sanPham.getThongTinSP(),khachHang.getTenKH());
                            Toast.makeText(mContext, "Sản phẩm đã thêm vào danh dách yêu thích", Toast.LENGTH_SHORT).show();
                            btn_fav.setBackgroundResource(R.drawable.baseline_favorite_24_3);
                        }
                        else
                        {
                            sanPham.setFavStatus("0");
                            sanPhamDAO.removeFavorite(mContext,sanPham.getMaSP(),khachHang.getTenKH());
                            btn_fav.setBackgroundResource(R.drawable.baseline_favorite_24_4);
                        }
                    }
                }
            });
        }
    }
}
