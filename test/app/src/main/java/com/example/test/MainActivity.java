package com.example.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.test.ui.main.ImageFocusFragment;
import com.example.test.ui.main.Phonenumber;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test.ui.main.SectionsPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Phonenumber> phonenumbers = new ArrayList<>();
    private EditText nameEditText = null;
    private EditText numberEditText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        phonenumbers = new ArrayList<>();

        // 초기화
        JSONObject jsonObject0 = new JSONObject();
        String filename = "Phonenumbers.json";
        String fileContents = "Hello world!";
        try {
            try (FileOutputStream fos = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
                fos.write(jsonObject0.toString().getBytes());
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String json = null;
        try{
            InputStream is = getApplicationContext().openFileInput("Phonenumbers.json");
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
            JSONArray contactArray = jsonObject.getJSONArray("Contacts");
            for(int i=0; i<contactArray.length(); i++) {
                jsonObject = contactArray.getJSONObject(i);
                phonenumbers.add(new Phonenumber(jsonObject.getString("name"), jsonObject.getString("number")));
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("전화번호부"));
        tabs.addTab(tabs.newTab().setText("갤러리"));
        tabs.addTab(tabs.newTab().setText("자유"));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.show();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    fab.setImageResource(android.R.drawable.ic_input_add);
                    fab.show();
                } else {
                    fab.hide();
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context mContext = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.save_phonenumber,(ViewGroup) findViewById(R.id.layout_root));
                AlertDialog.Builder aDialog = new AlertDialog.Builder(MainActivity.this);
                aDialog.setTitle("New");
                aDialog.setView(layout);
                nameEditText = layout.findViewById(R.id.name);
                numberEditText = layout.findViewById(R.id.number);
                aDialog.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = nameEditText.getText().toString();
                        String newNunmber = numberEditText.getText().toString();
                        phonenumbers.add(new Phonenumber(newName, newNunmber));

                        JSONObject jsonObject5 = new JSONObject();
                        JSONArray newArray = new JSONArray();
                        try{
                            for(int i=0; i<phonenumbers.size(); i++) {
                                JSONObject jsonObject1 = new JSONObject();
                                try{
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
                        }
                    }
                });
                aDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog ad = aDialog.create();
                ad.show();
            }
        });
    }
    public void replaceFragment(Fragment fragment){
        System.out.println(fragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view_pager, fragment).commit();
        System.out.println("reached");
    }
}