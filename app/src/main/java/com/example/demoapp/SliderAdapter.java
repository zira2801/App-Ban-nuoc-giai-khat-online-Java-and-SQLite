package com.example.demoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends FragmentStateAdapter {

    private List<SliderItem> mListSlide;

    public SliderAdapter(@NonNull Fragment fragment,List<SliderItem> list) {
        super(fragment);
        this.mListSlide = list;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        SliderItem sliderItem = mListSlide.get(position);

        //Xét bundle vào Fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_slide",sliderItem);
        SliderFragment sliderFragment = new SliderFragment();
        sliderFragment.setArguments(bundle);
        return sliderFragment;
    }

    @Override
    public int getItemCount() {
        if(mListSlide!=null){
            return mListSlide.size();
        }
        return 0;
    }
}

