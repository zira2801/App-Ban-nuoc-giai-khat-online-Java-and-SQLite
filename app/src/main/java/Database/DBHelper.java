package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.demoapp.KhachHang;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "AppDatDoUong.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }


    //Tạo bảng khách hàng
    private static final String TAO_BANG_KHACHHANG = "create table KhachHang ("
            + "TenKh text primary key not null,"
            + "PassKH text not null,"
            + "SoDT text not null,"
            + "GioiTinh text not null,"
            + "EmailKH text not null,"
            + "DateKH text not null,"
            + "Avatar blob )";

    private static final String TAO_BANG_SANPHAM = "create table SanPham ("
            + "MaSP text primary key,"
            + "TenSP text not null,"
            + "LoaiSP text not null,"
            + "ThongTinSP text not null,"
            + "GiaSP float not null,"
            + "FavoriteStatus text not null,"
            + "CartStatus text not null,"
            + "imageSP blob )";

    private static final String TAO_BANG_SP_YEU_THICH = "create table SanPhamYeuThich ("
            + "id_fav integer primary key autoincrement,"
            + "MaSP text not null,"
            + "TenSP text not null,"
            + "GiaSP float not null,"
            + "imageSP blob ,"
            + "ThongTinSP text not null,"
            + "FavoriteStatus text not null,"
            + "TenKh text not null,"
            + "foreign key (MaSP) references SanPham(MaSP),"
            + "foreign key (TenKh) references KhachHang(TenKh))";

    private static final String TAO_BANG_SP_CART = "create table GIOHANG ("
            + "id_cart integer primary key autoincrement,"
            + "MaSP text not null,"
            + "TenSP text not null,"
            + "GiaSP float not null,"
            + "imageSP blob ,"
            + "Soluong integer not null,"
            + "ThongTinSP text not null,"
            + "CartStatus text not null,"
            + "TenKh text not null,"
            + "foreign key (MaSP) references SanPham(MaSP),"
            + "foreign key (TenKh) references KhachHang(TenKh))";

    private static final String TAO_BANG_SP_BUY = "create table THANHTOAN ("
            + "id_thanhtoan integer primary key autoincrement,"
            + "MaSP text not null,"
            + "TenSP text not null,"
            + "DiaChi text not null,"
            + "SoDT text not null,"
            + "imageSP blob ,"
            + "Soluong integer not null,"
            + "TongGia float not null,"
            + "ThongTinSP text not null,"
            + "TenNguoiNhan text not null,"
            + "NgayDatHang text not null,"
            + "TenKh text not null,"
            + "foreign key (MaSP) references SanPham(MaSP),"
            + "foreign key (TenKh) references KhachHang(TenKh))";

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        //tạo database
       MyDB.execSQL(TAO_BANG_KHACHHANG);
       MyDB.execSQL(TAO_BANG_SANPHAM);
       MyDB.execSQL(TAO_BANG_SP_YEU_THICH);
       MyDB.execSQL(TAO_BANG_SP_CART);
       MyDB.execSQL(TAO_BANG_SP_BUY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDBLogin, int i, int i1) {
        MyDBLogin.execSQL("drop Table if exists KhachHang");
        MyDBLogin.execSQL("drop Table if exists SanPham");
        MyDBLogin.execSQL("drop Table if exists SanPhamYeuThich");
        MyDBLogin.execSQL("drop Table if exists GIOHANG");
        MyDBLogin.execSQL("drop Table if exists THANHTOAN");
    }

}
