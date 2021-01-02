package com.example.test.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.test.MainActivity;
import com.example.test.R;

import java.util.List;

public class GalleryFragment extends Fragment  {
    private static final String ARG_SECTION_NUMBER = "section_number";
    //ImageFocusFragment imageFocusFragment;
    ImageFocusAdapter imageFocusAdapter;
    ViewPager vp2;
    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;
    int[] images;
    TextView gallery_number;

    private  static final int MY_READ_PERMISSION_CODE = 101;

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
        imageFocusAdapter = new ImageFocusAdapter(getContext());
        gallery_number = view.findViewById(R.id.gallery_number);
        recyclerView = view.findViewById(R.id.recyclerview_gallery_images);
        System.out.println("bbbbbbb");
        vp2 = view.findViewById(R.id.view_pager2);
        vp2.setAdapter(imageFocusAdapter);
        //imageFocusFragment = new ImageFocusFragment();
        images = new int[] {R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q};
        loadImages();
        return view;
    }

    private void loadImages(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
//        images = 미리 저장해둔 이미지
        galleryAdapter = new GalleryAdapter(getContext(), images, new GalleryAdapter.PhotoListener() {
            @Override
            public void onPhotoClick(String path) {
                Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
            }
        });
        galleryAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                /*((MainActivity)getActivity()).replaceFragment(imageFocusFragment);*/
                recyclerView.setVisibility(View.GONE);
                System.out.println("click visible");
                vp2.setVisibility(View.VISIBLE);
            }
        });
        recyclerView.setAdapter(galleryAdapter);
        gallery_number.setText("Photos");

    }
}