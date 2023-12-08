package com.example.demoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.makeramen.roundedimageview.RoundedImageView;

public class SliderFragment extends Fragment {
    private View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_photo,container,false);

        Bundle bundle = getArguments();

        SliderItem sliderItem = (SliderItem) bundle.get("object_slide");
        RoundedImageView imageSlide = (RoundedImageView) mView.findViewById(R.id.img_photo);
        imageSlide.setImageResource(sliderItem.getResourceId());
        return mView;
    }
}
