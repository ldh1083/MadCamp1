package com.example.test.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.test.MainActivity;
import com.example.test.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GalleryFragment extends Fragment  {
    private static final String ARG_SECTION_NUMBER = "section_number";
    ViewPager viewPager;
    ScrollView sv;
    GalleryAdaptor gelleryAdapter;
    public static GalleryFragment newInstance(int index) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery_fragment, container, false);
        sv = (ScrollView) view.findViewById(R.id.main_frame);
        ArrayList<ImageView> images = new ArrayList<>();
        ImageView iv;
        for(int i=0; i<10;i++){
            String iid_s = "img"+(i+1);
            int iid;
            try {
                Field idField = R.id.class.getDeclaredField(iid_s);
                iid =  idField.getInt(idField);
            } catch (Exception e) {
                throw new RuntimeException("No resource ID found for: "
                        + iid_s + " / " + R.id.class, e);
            }
            iv = (ImageView) view.findViewById(iid);
            images.add(iv);
        }

        ArrayList<ImageView> big_images = new ArrayList<>();
        ImageView iv_big;
        for(int i=0; i<10;i++){
            String iid_s = "image"+(i+1);
            int iid;
            try {
                Field idField = R.id.class.getDeclaredField(iid_s);
                iid =  idField.getInt(idField);
            } catch (Exception e) {
                throw new RuntimeException("No resource ID found for: "
                        + iid_s + " / " + R.id.class, e);
            }
            iv_big = (ImageView) view.findViewById(iid);
            big_images.add(iv_big);
        }

        gelleryAdapter = new GalleryAdaptor(getChildFragmentManager());
        viewPager = (ViewPager)view.findViewById(R.id.view_pager1);
        viewPager.setAdapter(gelleryAdapter);
        viewPager.setVisibility(View.INVISIBLE);
        for(int i=0; i<10;i++){
            int finalI = i;
            images.get(i).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(finalI);
                    viewPager.setVisibility(View.VISIBLE);
                    //big_images.get(finalI).setVisibility(View.VISIBLE);
                    //sv.setVisibility(View.INVISIBLE);
                }
            });
        }
        viewPager.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //big_images.get(finalI).setVisibility(View.INVISIBLE);
                //viewPager.setVisibility(View.INVISIBLE);
                //sv.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }
    public void back(){
        viewPager.setVisibility(View.INVISIBLE);
        sv.setVisibility(View.VISIBLE);
    }
}