package Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.demoapp.CartFragment;
import com.example.demoapp.FavoriteFragment;
import com.example.demoapp.ListSPFragment;
import com.example.demoapp.ProfileFragment;

public class MyViewpager2Adapter extends FragmentStateAdapter {
    public MyViewpager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new ListSPFragment();
            case 2:
                return new CartFragment();
            case 3:
                return new FavoriteFragment();
            case 4:
                return new ProfileFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
