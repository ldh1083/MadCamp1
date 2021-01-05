package com.example.test.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.test.MainActivity;
import com.example.test.ui.main.CustomViewPager;
import com.example.test.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class GalleryFragment extends Fragment implements MainActivity.OnBackPressedListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private CustomViewPager viewPager;
    private ScrollView sv;
    private GalleryAdaptor gelleryAdapter;
    private ImageButton focus_button;
    public static int img[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8, R.drawable.img9, R.drawable.img10};
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
        focus_button = (ImageButton)view.findViewById(R.id.share_icon);
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
        focus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    shareImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
    public void shareImage() throws IOException {
       /* Uri imageUri = null;
        try {
            imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                    BitmapFactory.decodeResource(getResources(), R.drawable.img1), null, null));
        } catch (Exception e) { System.out.println(e.toString()); }*/
        System.out.println("image1 write0");
        Bitmap bm = BitmapFactory.decodeResource(getResources(), img[viewPager.getCurrentItem()]);

        File outputFile = new File(getContext().getCacheDir(), "share.jpg");
        FileOutputStream outPutStream = new FileOutputStream(outputFile);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, outPutStream);
        outPutStream.flush();
        outPutStream.close();
        outputFile.setReadable(true, false);

//        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//        File file = new File(extStorageDirectory, "example.PNG");
//        FileOutputStream outStream = new FileOutputStream(file);
//        bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
//        outStream.flush();
//        outStream.close();
//        System.out.println("image1 write");
        /*
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try
        {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
        finally
        {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }*/
//        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//        Uri imageUri = Uri.parse(getActivity().getExternalCacheDir()+"/example.PNG");
//        sharingIntent.setType("image/*");
//        sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//        startActivity(Intent.createChooser(sharingIntent, "Share image using"));
        Uri imageUri = FileProvider.getUriForFile(getContext(), "com.example.test.fileprovider", outputFile);
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        getContext().startActivity(shareIntent);
    }
    public void back(){
//        this.viewPager.setPagingEnabled(false);
//        gelleryAdapter = new GalleryAdaptor(getChildFragmentManager(), 1);
//        viewPager.setAdapter(gelleryAdapter);
//        this.viewPager.setVisibility(View.INVISIBLE);
//        this.sv.setVisibility(View.VISIBLE);
//        ((MainActivity) getActivity()).can_scroll(true);
        if(focus_button.getVisibility() == View.INVISIBLE) {
            focus_button.setVisibility(View.VISIBLE);
        }
        else{
            focus_button.setVisibility(View.INVISIBLE);
        }
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
        focus_button.setVisibility(View.INVISIBLE);
        ((MainActivity) getActivity()).can_scroll(true);
    }
    @Override
    public void onAttach (Context context){
        super.onAttach(context);
        ((MainActivity)context).setOnBackPressedListener(this);
    }
}