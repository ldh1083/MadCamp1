package com.example.test.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.test.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.test.MainActivity.phonenumbers;

public class FreeFragment extends Fragment implements FreeAdaptor.AdapterCallback {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ArrayList<Food> foods = new ArrayList<>();
    public static ArrayList<Day> days = new ArrayList<>();
    FreeAdaptor adapter;
    LineChart lineChart;
    ArrayList<Entry> entries= new ArrayList<>();;
    ArrayList<String> labels = new ArrayList<String>();
    ArrayList<Nutrition> nutritions = new ArrayList<>();
    ArrayList<Owner> user = new ArrayList<>();
    int recommend;
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
        user = new ArrayList<>();
        user = get_user();
        nutritions = new ArrayList<>();
        nutritions = get_nutrition();
        foods = new ArrayList<>();
        days = new ArrayList<>();
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        date =Integer.parseInt(mFormat.format(mDate));
        System.out.println(date);

        View view = inflater.inflate(R.layout.free_fragment, container, false);
        nameTextView = (TextView) view.findViewById(R.id.total_kcal);
        TextView user_text = (TextView) view.findViewById(R.id.user_info);
        TextView recommand_text = (TextView) view.findViewById(R.id.recommend_kcal);
        if (user.size() > 0) {
            if (user.get(0).getGender()){
                user_text.setText("이름: " + user.get(0).getName() + ", 성별: 여자" + ", 키: " + user.get(0).getHeight() + ", 몸무게: " + user.get(0).getWeight());
                recommand_text.setText("권장 칼로리: " + user.get(0).getWeight()*38);
                recommend = user.get(0).getWeight()*38;
                if (total_kcal>recommend) {
                    nameTextView.setTextColor(Color.RED);
                }
                else {
                    nameTextView.setTextColor(Color.GREEN);
                }
            }
            else {
                user_text.setText("이름: " + user.get(0).getName() + ", 성별: 남자" + ", 키: " + user.get(0).getHeight() + ", 몸무게: " + user.get(0).getWeight());
                recommand_text.setText("권장 칼로리: " + user.get(0).getWeight()*43);
                recommend = user.get(0).getWeight()*43;
                if (total_kcal>recommend) {
                    nameTextView.setTextColor(Color.RED);
                }
                else {
                    nameTextView.setTextColor(Color.GREEN);
                }
            }
        }
        user_text.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.save_user_info, (ViewGroup) view.findViewById(R.id.layout_roots));
                AlertDialog.Builder aDialog = new AlertDialog.Builder(getContext());
                aDialog.setTitle("사용자 정보 설정");
                aDialog.setView(layout);
                EditText nameEditText = layout.findViewById(R.id.name);
                EditText heightEditText = layout.findViewById(R.id.height);
                EditText weightEditText = layout.findViewById(R.id.weight);

                aDialog.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = nameEditText.getText().toString();
                        String newHeignt = heightEditText.getText().toString();
                        String newWeignt = weightEditText.getText().toString();
                        RadioGroup genderRadioGroup = (RadioGroup) layout.findViewById(R.id.gender);
                        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
                        if (selectedId == R.id.female || selectedId == R.id.male) {
                            user = new ArrayList<>();
                            user.add(new Owner(newName, (selectedId == R.id.female), Integer.parseInt(newHeignt), Integer.parseInt(newWeignt)));
                            JSONObject jsonObject5 = new JSONObject();
                            try {
                                jsonObject5.put("name", user.get(0).getName());
                                jsonObject5.put("gender", user.get(0).getGender());
                                jsonObject5.put("height", user.get(0).getHeight());
                                jsonObject5.put("weight", user.get(0).getWeight());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String filename = "User.json";
                            try {
                                try (FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
                                    fos.write(jsonObject5.toString().getBytes());
                                    fos.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            TextView user_text = (TextView) v.findViewById(R.id.user_info);
                            if (user.get(0).getGender()){
                                user_text.setText("이름: " + user.get(0).getName() + ", 성별: 여자" + ", 키: " + user.get(0).getHeight() + ", 몸무게: " + user.get(0).getWeight());
                                recommand_text.setText("권장 칼로리: " + user.get(0).getWeight()*38);
                                recommend = user.get(0).getWeight()*38;
                                if (total_kcal>recommend) {
                                    nameTextView.setTextColor(Color.RED);
                                }
                                else {
                                    nameTextView.setTextColor(Color.GREEN);
                                }
                            }
                            else {
                                user_text.setText("이름: " + user.get(0).getName() + ", 성별: 남자" + ", 키: " + user.get(0).getHeight() + ", 몸무게: " + user.get(0).getWeight());
                                recommand_text.setText("권장 칼로리: " + user.get(0).getWeight()*43);
                                recommend = user.get(0).getWeight()*43;
                                if (total_kcal>recommend) {
                                    nameTextView.setTextColor(Color.RED);
                                }
                                else {
                                    nameTextView.setTextColor(Color.GREEN);
                                }
                            }
                        }
                        /*phonenumbers.add(new Phonenumber(newName, newNunmber));
                        ((PhoneNumberFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + viewPager.getId() + ":" + sectionsPagerAdapter.getItemId(0))).refresh();

                        JSONObject jsonObject5 = new JSONObject();
                        JSONArray newArray = new JSONArray();
                        try {
                            for (int i = 0; i < phonenumbers.size(); i++) {
                                JSONObject jsonObject1 = new JSONObject();
                                try {
                                    jsonObject1.put("name", phonenumbers.get(i).getName());
                                    jsonObject1.put("number", phonenumbers.get(i).getNumber());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                newArray.put(jsonObject1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            jsonObject5.put("Contacts", newArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String filename = "Phonenumbers.json";
                        try {
                            try (FileOutputStream fos = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
                                fos.write(jsonObject5.toString().getBytes());
                                fos.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                });
                aDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog ad = aDialog.create();
                ad.show();
                return false;
            }
        });

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
        nameTextView.setTextColor(Color.GREEN);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray contactArray = jsonObject.getJSONArray("Foods");
            for (int i = 0; i < contactArray.length(); i++) {
                jsonObject = contactArray.getJSONObject(i);
                foods.add(new Food(jsonObject.getString("name"), jsonObject.getString("kcal"), jsonObject.getInt("num")));
                total_kcal += jsonObject.getInt("num")*Integer.parseInt(foods.get(i).getkcal().substring(0, foods.get(i).getkcal().length()-4));
            }
            if (total_kcal>recommend) {
                nameTextView.setTextColor(Color.RED);
            }
            else {
                nameTextView.setTextColor(Color.GREEN);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView = (ListView) view.findViewById(R.id.food_list);
        this.adapter = new FreeAdaptor(getContext(), foods, days, this);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.info_food, (ViewGroup) view.findViewById(R.id.layout_roots));
                AlertDialog.Builder aDialog = new AlertDialog.Builder(getContext());
                aDialog.setTitle("상세 정보");
                aDialog.setView(layout);

                TextView name = layout.findViewById(R.id.foodname);
                name.setText(foods.get(position).getName());
                TextView kcal = layout.findViewById(R.id.foodkcal);
                kcal.setText(foods.get(position).getkcal());
                TextView carb = layout.findViewById(R.id.carb);
                carb.setText("탄수화물: "+nutritions.get(position).getCarb()+"g");
                TextView protein = layout.findViewById(R.id.protein);
                protein.setText("단백질: "+nutritions.get(position).getProtein()+"g");
                TextView fat = layout.findViewById(R.id.fat);
                fat.setText("지방: "+nutritions.get(position).getFat()+"g");
                aDialog.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog ad = aDialog.create();
                ad.show();
                return false;

            }
        });

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
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        data = new LineData(labels, dataset);
        data.setValueTextSize(40f);
        lineChart.setData(data);
        lineChart.setDescription("");
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
        total_kcal += new_kcal;
        if (total_kcal>recommend) {
            nameTextView.setTextColor(Color.RED);

            if (total_kcal>recommend) {
                nameTextView.setTextColor(Color.RED);

                Toast toast = Toast.makeText(getActivity(), (total_kcal - recommend) + "kcal over..", Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 1000);
            }
        }
        else {
            nameTextView.setTextColor(Color.GREEN);
        }
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
        nameTextView.setTextColor(Color.GREEN);
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
                StringBuffer origin = new StringBuffer(sdate);
                origin.insert(2, '/');
                sdate = origin.toString();
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
            StringBuffer origin = new StringBuffer(sdate);
            origin.insert(2, '/');
            sdate = origin.toString();
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
            StringBuffer origin = new StringBuffer(sdate);
            origin.insert(2, '/');
            sdate = origin.toString();
            labels.add(sdate);
        }
        dataset = new LineDataSet(entries, "day");
        data = new LineData(labels, dataset);
        data.setValueTextSize(10f);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return value+"kcal";
            }
        });
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        lineChart.setData(data);

        LimitLine ll2 = new LimitLine(recommend, "");
        ll2.setLineWidth(2f);
        ll2.enableDashedLine(5f, 5f, 0f);//dashed line
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);//Set the position of the label display
        ll2.setTextSize(10f);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(R.color.white);
        yLAxis.setTextSize(7);
        //yLAxis.setDrawLabels(false);
        yLAxis.addLimitLine(ll2);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setTextColor(R.color.white);
        yRAxis.setTextSize(7);

        lineChart.invalidate();
    }
    public ArrayList<Nutrition> get_nutrition() {
        ArrayList<Nutrition> nutritions = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getContext().openFileInput("Nutritions.json");
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
            JSONArray contactArray = jsonObject.getJSONArray("Nutrition");
            for (int i = 0; i < contactArray.length(); i++) {
                jsonObject = contactArray.getJSONObject(i);
                nutritions.add(new Nutrition(jsonObject.getInt("carb"), jsonObject.getInt("protein"), jsonObject.getInt("fat")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nutritions;
    }
    public ArrayList<Owner> get_user() {
        ArrayList<Owner> user = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getContext().openFileInput("User.json");
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
            user.add(new Owner(jsonObject.getString("name"), jsonObject.getBoolean("gender"), jsonObject.getInt("height"), jsonObject.getInt("weight")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}