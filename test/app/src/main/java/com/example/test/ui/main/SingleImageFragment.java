package com.example.test.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.R;

public class SingleImageFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    int[] images;

    /*public SingleImageFragment(){
        this.images = new int[] {R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q};
    }*/
    public static SingleImageFragment newInstance() {
        return new SingleImageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        /*View view = inflater.inflate(R.layout.single_image, container, false);
        ImageView imageView = view.findViewById(R.id.image_single);
        imageView.setImageResource(images[0]);*/
        this.images = new int[] {R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q};
        return inflater.inflate(R.layout.single_image, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        Bundle args = getArguments();
        ImageView imageView = view.findViewById(R.id.image_single);
        imageView.setImageResource(images[0]);
    }
}