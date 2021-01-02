package com.example.test.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.test.MainActivity;
import com.example.test.R;

public class ImageFocusFragment extends Fragment {

    ImageFocusAdapter imageFocusAdapter;
    ViewPager imageFocusViewPager;

    /*public static ImageFocusFragment newInstance() {
        return new ImageFocusFragment();
    }*/
    public static ImageFocusFragment newInstance() {
        ImageFocusFragment fragment = new ImageFocusFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        System.out.println("asdf");
        View view = inflater.inflate(R.layout.image_focus, container, false);
        imageFocusAdapter = new ImageFocusAdapter(getContext());
        imageFocusViewPager = view.findViewById(R.id.view_pager2);
        imageFocusViewPager.setAdapter(imageFocusAdapter);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

    }
}