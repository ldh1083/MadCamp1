package com.example.test.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.widget.ListView;
import java.util.ArrayList;

import com.example.test.R;

public class PhoneNumberFragment extends Fragment  {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<String> array_mountain;
    private ListView listView;
    String[] name= {"a", "b", "c"};

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
        return view;
    }
}
