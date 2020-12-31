package com.example.test.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.test.MainActivity;
import com.example.test.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.test.MainActivity.phonenumbers;

public class PhoneNumberFragment extends Fragment  {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private EditText nameEditText = null;
    private EditText numberEditText = null;
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
        adapter = new PhonenumberAdapter(getContext(), phonenumbers);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new  AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.save_phonenumber, (ViewGroup) view.findViewById(R.id.layout_root));
                AlertDialog.Builder aDialog = new AlertDialog.Builder(getContext());
                aDialog.setTitle("상세 정보");
                aDialog.setView(layout);
                nameEditText = layout.findViewById(R.id.name);
                numberEditText = layout.findViewById(R.id.number);
                nameEditText.setText(((MainActivity) getActivity()).phonenumbers.get(position).getName());
                numberEditText.setText(((MainActivity) getActivity()).phonenumbers.get(position).getNumber());

                aDialog.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                aDialog.setNegativeButton("수정", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = nameEditText.getText().toString();
                        String newNunmber = numberEditText.getText().toString();
                        ((MainActivity) getActivity()).phonenumbers.get(position).setName(newName);
                        ((MainActivity) getActivity()).phonenumbers.get(position).setNumber(newNunmber);
                        adapter.notifyDataSetChanged();

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
                            try (FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
                                fos.write(jsonObject5.toString().getBytes());
                                fos.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                aDialog.setNeutralButton("삭제", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity) getActivity()).phonenumbers.remove(position);
                        adapter.notifyDataSetChanged();
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
                            try (FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
                                fos.write(jsonObject5.toString().getBytes());
                                fos.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                AlertDialog ad = aDialog.create();
                ad.show();
            return false;
            }

        });
        return view;
    }

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new PhonenumberAdapter(getContext(), MainActivity.phonenumbers);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }*/

    public void refresh(){
        adapter.notifyDataSetChanged();
        //listView.setAdapter(adapter);
    }
}
