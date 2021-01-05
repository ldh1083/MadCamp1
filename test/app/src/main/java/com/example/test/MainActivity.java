package com.example.test;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.test.ui.main.CustomViewPager;
import com.example.test.ui.main.FreeFragment;
import com.example.test.ui.main.PhoneNumberFragment;
import com.example.test.ui.main.Phonenumber;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Phonenumber> phonenumbers = new ArrayList<>();
    public static ArrayList<Phonenumber> sub_phonenumbers = new ArrayList<>();
    private static final int REQUEST_RUNTIME_PERMISSION = 123;
    String[] permissons = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
    private EditText nameEditText = null;
    private EditText numberEditText = null;
    CustomViewPager viewPager;
    private boolean isFocused;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        phonenumbers = new ArrayList<>();
        sub_phonenumbers = new ArrayList<>();


        init_user();

        String path = getFilesDir().getAbsolutePath() + "/Phonenumbers.json";
        File file = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            System.out.println("error");
            e.printStackTrace();
        }

        String path1 = getFilesDir().getAbsolutePath() + "/SubPhonenumbers.json";
        File file1 = new File(path1);
        try {
            FileOutputStream fos = new FileOutputStream(file1, true);
        } catch (FileNotFoundException e) {
            System.out.println("error");
            e.printStackTrace();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String json = null;
        try {
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
            for (int i = 0; i < contactArray.length(); i++) {
                jsonObject = contactArray.getJSONObject(i);
                phonenumbers.add(new Phonenumber(jsonObject.getString("name"), jsonObject.getString("number"), jsonObject.getInt("order")));
            }
            Collections.sort(phonenumbers);
            for(int i=0; i<phonenumbers.size();i++) {
                phonenumbers.get(i).setOrder(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        json = null;
        try {
            InputStream is = getApplicationContext().openFileInput("SubPhonenumbers.json");
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
            for (int i = 0; i < contactArray.length(); i++) {
                jsonObject = contactArray.getJSONObject(i);
                sub_phonenumbers.add(new Phonenumber(jsonObject.getString("name"), jsonObject.getString("number"), 0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        while (!CheckPermission(getApplicationContext(), permissons[0]) || !CheckPermission(getApplicationContext(), permissons[1]))
        {
            RequestPermission(this, permissons, REQUEST_RUNTIME_PERMISSION);
        }

        if (sub_phonenumbers.size() == 0) {
            init_nutrition();
            JSONObject jsonObject5 = new JSONObject();
            JSONArray newArray = new JSONArray();
            try {
                int i=0;
                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("name", "밥");
                    jsonObject1.put("kcal", "310kcal");
                    jsonObject1.put("num", "0");
                    jsonObject1.put("order", Integer.toString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newArray.put(jsonObject1);
                i++;
                jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("name", "돼지갈비(100g)");
                    jsonObject1.put("kcal", "240kcal");
                    jsonObject1.put("num", "0");
                    jsonObject1.put("order", Integer.toString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newArray.put(jsonObject1);
                i++;
                jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("name", "고등어 구이(100g)");
                    jsonObject1.put("kcal", "275kcal");
                    jsonObject1.put("num", "0");
                    jsonObject1.put("order", Integer.toString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newArray.put(jsonObject1);
                i++;
                jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("name", "계란");
                    jsonObject1.put("kcal", "155kcal");
                    jsonObject1.put("num", "0");
                    jsonObject1.put("order", Integer.toString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newArray.put(jsonObject1);
                i++;
                jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("name", "김치(40g)");
                    jsonObject1.put("kcal", "10kcal");
                    jsonObject1.put("num", "0");
                    jsonObject1.put("order", Integer.toString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newArray.put(jsonObject1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                jsonObject5.put("Foods", newArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String filename = "Foods.json";
            try {
                try (FileOutputStream fos = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
                    fos.write(jsonObject5.toString().getBytes());
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setPagingEnabled(true);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("전화번호부"));
        tabs.addTab(tabs.newTab().setText("갤러리"));
        tabs.addTab(tabs.newTab().setText("식단"));
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
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context mContext = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.save_phonenumber, (ViewGroup) findViewById(R.id.layout_root));
                AlertDialog.Builder aDialog = new AlertDialog.Builder(MainActivity.this);
                aDialog.setTitle("New");
                aDialog.setView(layout);
                nameEditText = layout.findViewById(R.id.name);
                numberEditText = layout.findViewById(R.id.number);
                aDialog.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = nameEditText.getText().toString();
                        String newNunmber = numberEditText.getText().toString();

                        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, newName)
                                .putExtra(ContactsContract.Intents.Insert.EMAIL, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                                .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                                .putExtra(ContactsContract.Intents.Insert.PHONE, newNunmber)
                                .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                        startActivity(intent);

                        if (newName.length() != 0 && newNunmber.length()!=0) {
                            phonenumbers.add(new Phonenumber(newName, newNunmber, phonenumbers.get(phonenumbers.size()-1).getOrder()+1));
                            Collections.sort(phonenumbers);
                            for(int i=0; i<phonenumbers.size();i++) {
                                phonenumbers.get(i).setOrder(i);
                            }
                            ((PhoneNumberFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + viewPager.getId() + ":" + sectionsPagerAdapter.getItemId(0))).refresh();

                            JSONObject jsonObject5 = new JSONObject();
                            JSONArray newArray = new JSONArray();
                            try {
                                for (int i = 0; i < phonenumbers.size(); i++) {
                                    JSONObject jsonObject1 = new JSONObject();
                                    try {
                                        jsonObject1.put("name", phonenumbers.get(i).getName());
                                        jsonObject1.put("number", phonenumbers.get(i).getNumber());
                                        jsonObject1.put("order", phonenumbers.get(i).getOrder());
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
/*
    public void move_fragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view_pager, FreeFragment.newInstance(2));
        fragmentTransaction.commit();
    }*/
    public void can_scroll(boolean key) {
        this.viewPager.setPagingEnabled(key);
    }

    public void init_nutrition() {
        JSONObject jsonObject5 = new JSONObject();
        JSONArray newArray = new JSONArray();
        try {
            int i=0;
            JSONObject jsonObject1 = new JSONObject();
            try {
                jsonObject1.put("carb", "43");
                jsonObject1.put("protein", "25");
                jsonObject1.put("fat", "10");
                jsonObject1.put("order", i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            newArray.put(jsonObject1);

            i++;
            jsonObject1 = new JSONObject();
            try {
                jsonObject1.put("carb", "8");
                jsonObject1.put("protein", "14");
                jsonObject1.put("fat", "19");
                jsonObject1.put("order", i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            newArray.put(jsonObject1);
            i++;
            jsonObject1 = new JSONObject();
            try {
                jsonObject1.put("carb", "1");
                jsonObject1.put("protein", "23");
                jsonObject1.put("fat", "20");
                jsonObject1.put("order", i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            newArray.put(jsonObject1);
            i++;
            jsonObject1 = new JSONObject();
            try {
                jsonObject1.put("carb", "1");
                jsonObject1.put("protein", "13");
                jsonObject1.put("fat", "11");
                jsonObject1.put("order", i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            newArray.put(jsonObject1);
            i++;
            jsonObject1 = new JSONObject();
            try {
                jsonObject1.put("carb", "1");
                jsonObject1.put("protein", "1");
                jsonObject1.put("fat", "1");
                jsonObject1.put("order", i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            newArray.put(jsonObject1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            jsonObject5.put("Nutrition", newArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String filename = "Nutritions.json";
        try {
            try (FileOutputStream fos = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
                fos.write(jsonObject5.toString().getBytes());
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init_user() {
        String path = getFilesDir().getAbsolutePath() + "/User.json";
        File file = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }
    public interface OnBackPressedListener{
        void onBack();
    }

    private OnBackPressedListener mBackListener;

    public void setOnBackPressedListener(OnBackPressedListener listener){
        mBackListener = listener;
    }

    @Override
    public void onBackPressed(){
        if((mBackListener != null) && isFocused){
            mBackListener.onBack();
        }
        else{
            super.onBackPressed();
        }
    }
    public void setIsFocused(boolean isFocused){
        this.isFocused = isFocused;
    }
    public boolean CheckPermission(Context context, String Permission) {
        if (ContextCompat.checkSelfPermission(context,
                Permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void RequestPermission(Activity thisActivity, String[] Permission, int Code) {
        if (ContextCompat.checkSelfPermission(thisActivity,
                Permission[0])
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Permission[0])) {
            } else {
                ActivityCompat.requestPermissions(thisActivity, Permission,
                        Code);
            }
        }
    }
}