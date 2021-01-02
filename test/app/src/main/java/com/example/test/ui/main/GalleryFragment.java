package com.example.test.ui.main;

import android.content.Context;
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
import com.example.test.ui.main.CustomViewPager;
import com.example.test.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GalleryFragment extends Fragment implements MainActivity.OnBackPressedListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private CustomViewPager viewPager;
    private ScrollView sv;
    private GalleryAdaptor gelleryAdapter;
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
        viewPager = (CustomViewPager)view.findViewById(R.id.view_pager1);

        for(int i=0; i<10;i++){
            int finalI = i;
            images.get(i).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    gelleryAdapter = new GalleryAdaptor(getChildFragmentManager(), 10);
                    ((MainActivity) getActivity()).can_scroll(false);
                    viewPager.setAdapter(gelleryAdapter);
                    viewPager.setVisibility(View.INVISIBLE);
                    viewPager.setPagingEnabled(true);
                    viewPager.setCurrentItem(finalI);
                    viewPager.setVisibility(View.VISIBLE);
                    //big_images.get(finalI).setVisibility(View.VISIBLE);
                    //sv.setVisibility(View.INVISIBLE);
                    ((MainActivity)getContext()).setIsFocused(true);
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
        this.viewPager.setPagingEnabled(false);
        gelleryAdapter = new GalleryAdaptor(getChildFragmentManager(), 1);
        viewPager.setAdapter(gelleryAdapter);
        this.viewPager.setVisibility(View.INVISIBLE);
        this.sv.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).can_scroll(true);
    }
    @Override
    public void onBack(){
        MainActivity activity = (MainActivity)getActivity();
        activity.setIsFocused(false);
        this.viewPager.setPagingEnabled(false);
        gelleryAdapter = new GalleryAdaptor(getChildFragmentManager(), 1);
        viewPager.setAdapter(gelleryAdapter);
        this.viewPager.setVisibility(View.INVISIBLE);
        this.sv.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).can_scroll(true);
    }
    @Override
    public void onAttach (Context context){
        super.onAttach(context);
        ((MainActivity)context).setOnBackPressedListener(this);
    }
}