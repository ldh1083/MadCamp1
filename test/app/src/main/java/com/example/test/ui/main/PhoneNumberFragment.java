package com.example.test.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.widget.ListView;
import java.util.ArrayList;

import com.example.test.MainActivity;
import com.example.test.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PhoneNumberFragment extends Fragment  {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ListView listView;
    PhonenumberAdapter adapter;
    public static PhoneNumberFragment newInstance(int index) {
        PhoneNumberFragment fragment = new PhoneNumberFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phonenumber_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.listview1);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new PhonenumberAdapter(getContext(), MainActivity.phonenumbers);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    public void refresh(){
        adapter.notifyDataSetChanged();
        //listView.setAdapter(adapter);
    }
}
