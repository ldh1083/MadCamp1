package com.example.test.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.test.R;

public class PhonenumberAdapter extends ArrayAdapter<Phonenumber> {
    private Context context;
    private final ArrayList<Phonenumber> phonenumbers;

    public PhonenumberAdapter(Context context, ArrayList<Phonenumber> phonenumbers) {
        super(context,-1,phonenumbers);
        this.context = context;
        this.phonenumbers = phonenumbers;
    }

    /*@Override
    public int getCount() {
        return array_mountain.size();
    }

    @Override
    public Object getItem(int position) {
        return array_mountain.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHoldr 패턴
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_phonenumber, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        // View에 Data 세팅
        mViewHolder.txt_name.setText(array_mountain.get(position));
        return convertView;
    }

    public class ViewHolder {
        private TextView txt_name;
        public ViewHolder(View convertView) {
            txt_name = (TextView) convertView.findViewById(R.id.txt_name);
        }
    }*/
}