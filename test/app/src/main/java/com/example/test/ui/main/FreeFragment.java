package com.example.test.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.test.MainActivity;
import com.example.test.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.security.auth.callback.Callback;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.test.MainActivity.phonenumbers;


public class FreeFragment extends Fragment  {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ArrayList<Food> foods = new ArrayList<>();
    public static ArrayList<Day> days = new ArrayList<>();
    FreeAdaptor adapter;
    LineChart lineChart;
    ArrayList<Entry> entries= new ArrayList<>();;
    ArrayList<String> labels = new ArrayList<String>();
    LineDataSet dataset;
    LineData data;
    public static int total_kcal=0;
    public static FreeFragment newInstance(int index) {
        FreeFragment fragment = new FreeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.free_fragment, container, false);
        TextView nameTextView = (TextView) view.findViewById(R.id.total_kcal);
        // get Food.json to foods
        String json = null;
        try {
            InputStream is = getContext().openFileInput("Foods.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray contactArray = jsonObject.getJSONArray("Foods");
            for (int i = 0; i < contactArray.length(); i++) {
                jsonObject = contactArray.getJSONObject(i);
                foods.add(new Food(jsonObject.getString("name"), jsonObject.getString("kcal"), jsonObject.getInt("num")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListView listView = (ListView) view.findViewById(R.id.food_list);
        adapter = new FreeAdaptor(getContext(), foods);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nameTextView.setText("오늘 칼로리: "+adapter.new_kcal);
            }
        });
        nameTextView.setText("오늘 칼로리: "+adapter.new_kcal);

        lineChart = (LineChart) view.findViewById(R.id.chart);

        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        dataset = new LineDataSet(entries, "day");
        data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        lineChart.setData(data);
        lineChart.animateY(5000);
        return view;
    }


}