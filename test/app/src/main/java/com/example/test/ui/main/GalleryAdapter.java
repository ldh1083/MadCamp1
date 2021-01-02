package com.example.test.ui.main;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.MainActivity;
import com.example.test.R;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private Context context;
    private int[] images;
    protected  PhotoListener photoListener;
    private OnItemClickListener mListener = null;

    public  GalleryAdapter(Context context, int[] images, PhotoListener photoListener){
        this.context = context;
        this.images = images;
        this.photoListener = photoListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false)
        );
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        holder.image.setImageResource(images[position]);
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("view holder on click");
            }
        });*/
    }
    @Override
    public int getItemCount(){
        return images.length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView image;

         public ViewHolder(@NonNull View itemView){
             super(itemView);
             itemView.setOnClickListener(new View.OnClickListener(){
                 @Override
                 public void onClick(View v){
                     int pos = getAdapterPosition();
                     if(pos != RecyclerView.NO_POSITION){
                         if(mListener != null){
                             mListener.onItemClick(v, pos);
                         }
                     }
                 }
             });
             image = itemView.findViewById(R.id.image);
         }
    }

    public interface PhotoListener{
        void onPhotoClick(String path);
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
}