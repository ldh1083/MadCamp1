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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_phonenumber, parent, false);

        TextView nameTextView = (TextView) rowView.findViewById(R.id.txt_name);
        nameTextView.setText(phonenumbers.get(position).getName());
        // View에 Data 세팅
        return rowView;
    }

    /*public class ViewHolder {
        private TextView txt_name;
        public ViewHolder(View convertView) {
            txt_name = (TextView) convertView.findViewById(R.id.txt_name);
        }
    }*/
}