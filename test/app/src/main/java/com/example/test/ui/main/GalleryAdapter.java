package com.example.test.ui.main;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.test.R;

public class GalleryAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    int img[];
    LayoutInflater inf;

    public GalleryAdapter(Context context, int layout, int[] img) {
        this.context = context;
        this.layout = layout;
        this.img = img;
        inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return img.length;
    }
    @Override
    public Object getItem(int position){
        return img[position];
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rowView = inflater.inflate(R.layout.layout_phonenumber, parent, false);

//        TextView nameTextView = (TextView) rowView.findViewById(R.id.txt_name);
//        nameTextView.setText(phonenumbers.get(position).getName());
//        final TextView numberTextView = (TextView) rowView.findViewById(R.id.number);
//        numberTextView.setText(phonenumbers.get(position).getNumber());
//        // View에 Data 세팅
//        return rowView;

        if(convertView == null)
            convertView = inf.inflate(layout, null);
        ImageView iv = (ImageView)convertView.findViewById(R.id.imageView1);
        iv.setImageResource(img[position]);

        return convertView;
    }

    /*public class ViewHolder {
        private TextView txt_name;
        public ViewHolder(View convertView) {
            txt_name = (TextView) convertView.findViewById(R.id.txt_name);
        }
    }*/
}