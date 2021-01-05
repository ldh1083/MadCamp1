package com.example.test.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.test.R;

public class BigImageFragment extends Fragment {
    public static int img[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8, R.drawable.img9, R.drawable.img10};
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static BigImageFragment newInstance(int index) {
        BigImageFragment fragment = new BigImageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.big_image_fragment, container, false);
        ImageView iv = view.findViewById(R.id.image2);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        iv.setImageResource(img[index]);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                GalleryFragment gf = (GalleryFragment)BigImageFragment.this.getParentFragment();
                gf.back();
            }
        });
        return view;
    }
}
