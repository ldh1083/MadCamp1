package com.example.test.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.test.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.test.MainActivity.phonenumbers;

public class FreeFragment extends Fragment implements FreeAdaptor.AdapterCallback {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ArrayList<Food> foods = new ArrayList<>();
    public static ArrayList<Day> days = new ArrayList<>();
    FreeAdaptor adapter;
    LineChart lineChart;
    ArrayList<Entry> entries= new ArrayList<>();;
    ArrayList<String> labels = new ArrayList<String>();
    LineDataSet dataset;
    LineData data;
    ListView listView;
    TextView nameTextView;
    public static int total_kcal=0;

    int date;
    SimpleDateFormat mFormat = new SimpleDateFormat("MMdd");
    long mNow;
    Date mDate;
    public static FreeFragment newInstance(int index) {
        FreeFragment fragment = new FreeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        foods = new ArrayList<>();
        days = new ArrayList<>();
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        date =Integer.parseInt(mFormat.format(mDate));
        System.out.println(date);


        View view = inflater.inflate(R.layout.free_fragment, container, false);
        nameTextView = (TextView) view.findViewById(R.id.total_kcal);

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
        total_kcal = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray contactArray = jsonObject.getJSONArray("Foods");
            for (int i = 0; i < contactArray.length(); i++) {
                jsonObject = contactArray.getJSONObject(i);
                foods.add(new Food(jsonObject.getString("name"), jsonObject.getString("kcal"), jsonObject.getInt("num")));
                total_kcal += jsonObject.getInt("num")*Integer.parseInt(foods.get(i).getkcal().substring(0, foods.get(i).getkcal().length()-4));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView = (ListView) view.findViewById(R.id.food_list);
        this.adapter = new FreeAdaptor(getContext(), foods, days, this);
        listView.setAdapter(adapter);



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
        //lineChart.animateY(5000);


        // 파일이 없다면 생성
        String path = getActivity().getFilesDir().getAbsolutePath() + "/Days.json";
        File file = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            System.out.println("error");
            e.printStackTrace();
        }

        try {
            InputStream is = getContext().openFileInput("Days.json");
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
            JSONArray contactArray = jsonObject.getJSONArray("Dates");
            for (int i = 0; i < contactArray.length(); i++) {
                jsonObject = contactArray.getJSONObject(i);
                days.add(new Day(jsonObject.getInt("date"), jsonObject.getInt("kcal")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (days.size()>0) {
            int last_day = days.get(days.size()-1).getDate();
            if (last_day != date){
                System.out.println("101010");
                update();
            }
        }
        else {
            System.out.println("123123");
            update();
        }
        drawgraph();
        nameTextView.setText("오늘 칼로리: "+total_kcal);
        return view;
    }

    @Override
    public void eat_food(int new_kcal) {
        System.out.println(total_kcal);
        total_kcal += new_kcal;
        System.out.println(total_kcal);
        nameTextView.setText("오늘 칼로리: "+total_kcal);
        days.get(days.size()-1).setkcal(total_kcal);
    }

    @Override
    public ArrayList<Food> update() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        date =Integer.parseInt(mFormat.format(mDate));
        if (days.size()>0) {
            days.get(days.size()-1).setkcal(total_kcal);
        }
        if (days.size()==7) {
            days.remove(0);
            days.add(new Day(date, 0));
        }
        else {
            days.add(new Day(date, 0));
        }
        JSONObject jsonObject5 = new JSONObject();
        JSONArray newArray = new JSONArray();
        try {
            for (int i = 0; i < days.size(); i++) {
                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("date", days.get(i).getDate());
                    jsonObject1.put("kcal", days.get(i).getkcal());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newArray.put(jsonObject1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            jsonObject5.put("Dates", newArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String filename = "Days.json";
        try {
            try (FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
                fos.write(jsonObject5.toString().getBytes());
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        total_kcal = 0;

        jsonObject5 = new JSONObject();
        newArray = new JSONArray();
        try {
            for (int i = 0; i <5; i++) {
                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("name", "밥");
                    jsonObject1.put("kcal", "310kcal");
                    jsonObject1.put("num", "0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newArray.put(jsonObject1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            jsonObject5.put("Foods", newArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        filename = "Foods.json";
        try {
            try (FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
                fos.write(jsonObject5.toString().getBytes());
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0; i<foods.size();i++)
        {
            foods.get(i).setNum(0);
        }
        adapter.notifyDataSetChanged();

        drawgraph();
        return foods;
    }

    @Override
    public ArrayList<Day> return_day() {
        return days;
    }

    @Override
    public void drawgraph() {
        System.out.println("draw");
        ArrayList<Entry> entries= new ArrayList<>();;
        ArrayList<String> labels = new ArrayList<String>();
        if (days.size()>1) {
            for (int i=0; i<days.size()-1; i++) {
                entries.add(new Entry(days.get(i).getkcal(), i));

                String sdate;
                if (days.get(i).getDate()<1000) {
                    sdate = "0"+days.get(i).getDate();
                }
                else {
                    sdate = Integer.toString(days.get(i).getDate());
                }
                labels.add(sdate);
            }
            entries.add(new Entry(total_kcal, days.size()-1));

            String sdate;
            if (days.get(days.size()-1).getDate()<1000) {
                sdate = "0"+days.get(days.size()-1).getDate();
            }
            else {
                sdate = Integer.toString(days.get(days.size()-1).getDate());
            }
            labels.add(sdate);
        }
        else {
            entries.add(new Entry(total_kcal, 0));

            String sdate;
            if (days.get(0).getDate()<1000) {
                sdate = "0"+days.get(0).getDate();
            }
            else {
                sdate = Integer.toString(days.get(days.size()-1).getDate());
            }
            labels.add(sdate);
        }
        dataset = new LineDataSet(entries, "day");
        data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        lineChart.setData(data);
        lineChart.invalidate();
    }

}