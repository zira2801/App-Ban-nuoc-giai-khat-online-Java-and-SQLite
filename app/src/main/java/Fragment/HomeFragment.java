package Fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.demoapp.PaginationScrollListener;
import com.example.demoapp.R;
import com.example.demoapp.SanPhamDAO;
import com.example.demoapp.SliderAdapter;
import com.example.demoapp.SliderItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import DanhSachSP.SanPham;
import DanhSachSP.SanPhamAdapter;
import book.Book;
import book.BookAdapter;
import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager2;

    private CircleIndicator3 circleIndicator3;
    private List<SliderItem> mListPhoto;

    //auto slider
    private Handler handler = new Handler();

    // Xử lý RecyleView SanPham
    private RecyclerView rcvSanpham;
    private SanPhamAdapter sanPhamAdapter;

    private SearchView searchView;
        private  List<SanPham> sanPhamList;
    SanPhamDAO sanPhamDAO;

    private boolean isLoading;
    private boolean isLastPage;
    private int totalPage = 5;
    private int currentPage = 1;
    public HomeFragment()   {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            Window window = this.getActivity().getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //end toolbar

        viewPager2 =  view.findViewById(R.id.viewPager  );
       circleIndicator3 = view.findViewById(R.id.circle_indicator);

       mListPhoto = getListPhoto();
        //khởi tạo ảnh từ drawble
        SliderAdapter sliderAdapter = new SliderAdapter(HomeFragment.this,mListPhoto);
        viewPager2.setAdapter(sliderAdapter);
        circleIndicator3.setViewPager(viewPager2);

       viewPager2.setClipToPadding(false);
        //sẽ bỏ qua các khoảng trống giữa cửa sổ hiển thị và nội dung bên trong của ViewPager2. Theo mặc định, ViewPager2
        // sẽ chèn nội dung vào trong vùng hiển thị, do đó sẽ có một số khoảng trống để tránh việc che mất phần nào của nội dung.

       viewPager2.setClipChildren(false);
       //sẽ cho phép các View con của ViewPager2 bao phủ ra ngoài khu vực hiển thị của nó. Điều này có nghĩa là các phần của
        // View con sẽ không bị cắt bỏ nếu chúng vượt quá khung nhìn của ViewPager2.

        viewPager2.setOffscreenPageLimit(5);  // 5 anh
        //đặt giới hạn số lượng trang được lưu trong bộ đệm (off-screen pages) của ViewPager2 thành 5. Nhờ đó, khi người dùng cuộn trang,
        // các trang đã được tải trước đó sẽ được giữ lại trong bộ đệm, giúp tăng tốc độ hiển thị.

        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        //sử dụng phương pháp getChildAt để truy cập đến View con đầu tiên của ViewPager2, rồi tắt tính năng quá cuộn (overscroll) bằng cách thiết lập
        // OVER_SCROLL_NEVER cho RecyclerView. Điều này sẽ ngăn chặn hiện tượng kéo quá cuộn màn hình khi người dùng cuộn ViewPager2.

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {

                float r = 1-Math.abs(position);
                page.setScaleY(0.85f + r*0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);

        //auto sider
       viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                handler.removeCallbacks(sliderRunnable);
                handler.postDelayed(sliderRunnable,3000);
            }
      });

        //Xử lý RecycleView sản
        rcvSanpham = view.findViewById(R.id.rcv_sanpham);
        sanPhamDAO = new SanPhamDAO(getActivity());
        sanPhamList = (List<SanPham>) sanPhamDAO.getAll();
        sanPhamAdapter = new SanPhamAdapter(sanPhamList,getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rcvSanpham.setLayoutManager(gridLayoutManager);
        rcvSanpham.setAdapter(sanPhamAdapter);
        return view;

    }

    private List<SliderItem> getListPhoto(){

        List<SliderItem> list = new ArrayList<>();
        list.add(new SliderItem(R.drawable.anh_1));
        list.add(new SliderItem(R.drawable.anh_2));
        list.add(new SliderItem(R.drawable.anh_3));
        list.add(new SliderItem(R.drawable.anh_4));
        list.add(new SliderItem(R.drawable.anh_5));
        return list;
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int currentPosition = viewPager2.getCurrentItem();
            if(currentPosition == mListPhoto.size()-1){
                viewPager2.setCurrentItem(0);
            }
            else{
                viewPager2.setCurrentItem(currentPosition+1);
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(sliderRunnable,3000);
    }

}