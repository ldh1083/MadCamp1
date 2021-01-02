package com.example.test.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.test.R;

public class ImageFocusAdapter extends PagerAdapter {
    private Context mContext = null;
    private int[] images2;
    public ImageFocusAdapter(){
        images2 = new int[] {R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q};
    }
    public ImageFocusAdapter(Context context){
        mContext = context;
        images2 = new int[] {R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q, R.drawable.c, R.drawable.e, R.drawable.j, R.drawable.q};
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        // LayoutInflater를 통해 "/res/layout/page.xml"을 뷰로 생성.
        System.out.println("aaaaaaaaaaaaaaa");
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_image, container, false);
        ImageView imageView = view.findViewById(R.id.image_single);
        System.out.println(images2[position]);
        imageView.setImageResource(images2[position]);
        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object){
        return (view == (View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
    /*@Override
    public Fragment getItem(int position){
        Fragment fragment = new SingleImageFragment();
        Bundle args = new Bundle();
        args.putInt(SingleImageFragment.ARG_OBJECT, position+1);
        fragment.setArguments(args);
        return fragment;
    }*/
    @Override
    public int getCount(){
        return images2.length;
    }
}