package com.example.test;

import android.os.Bundle;

import com.example.test.ui.main.Phonenumber;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Phonenumber> phonenumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phonenumbers.add(new Phonenumber("name1", "010-0000-0001"));
        phonenumbers.add(new Phonenumber("name2", "010-0000-0002"));
        phonenumbers.add(new Phonenumber("name3", "010-0000-0003"));
        phonenumbers.add(new Phonenumber("name1", "010-0000-0001"));
        phonenumbers.add(new Phonenumber("name2", "010-0000-0002"));
        phonenumbers.add(new Phonenumber("name3", "010-0000-0003"));
        phonenumbers.add(new Phonenumber("name1", "010-0000-0001"));
        phonenumbers.add(new Phonenumber("name2", "010-0000-0002"));
        phonenumbers.add(new Phonenumber("name3", "010-0000-0003"));
        phonenumbers.add(new Phonenumber("name1", "010-0000-0001"));
        phonenumbers.add(new Phonenumber("name2", "010-0000-0002"));
        phonenumbers.add(new Phonenumber("name3", "010-0000-0003"));

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Contacts"));
        tabs.addTab(tabs.newTab().setText("Gallery"));
        tabs.addTab(tabs.newTab().setText("Free"));
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
    }

}