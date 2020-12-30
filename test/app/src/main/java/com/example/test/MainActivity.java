package com.example.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.test.ui.main.Phonenumber;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test.ui.main.SectionsPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    public static ArrayList<Phonenumber> phonenumbers = new ArrayList<>();

    private EditText nameEditText = null;
    private EditText numberEditText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String json = null;
        try{
            InputStream is = getAssets().open("jsons/Phonenumbers.json");
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
                        setStringArrayPref("contact", phonenumbers);
                        JSONObject jsonObject = new JSONObject();
                        try{
                            jsonObject.put("name", newName);
                            jsonObject.put("number", newNunmber);

                        } catch (JSONException e) {
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
    public void setStringArrayPref(String key, ArrayList<Phonenumber> values) {
        SharedPreferences prefs = getSharedPreferences("contact", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.commit();
        System.out.println("hello");
    }

    private ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

}