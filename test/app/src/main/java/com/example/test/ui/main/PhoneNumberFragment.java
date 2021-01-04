package com.example.test.ui.main;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import static com.example.test.MainActivity.sub_phonenumbers;

public class PhoneNumberFragment extends Fragment  {
    private static final int REQUEST_RUNTIME_PERMISSION = 123;
    private static final String ARG_SECTION_NUMBER = "section_number";
    String[] permissons = {Manifest.permission.READ_CONTACTS};
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
    public void onStart() {
        super.onStart();
        if (sub_phonenumbers.size() == 0)
        {
            if (CheckPermission(getContext(), permissons[0])) {
                // you have permission go ahead
                System.out.println("1");
                read_contact();
            } else {
                // you do not have permission go request runtime permissions
                RequestPermission(getActivity(), permissons, REQUEST_RUNTIME_PERMISSION);
                System.out.println("2");
            }
            System.out.println("3");
        }
        adapter = new PhonenumberAdapter(getContext(), phonenumbers);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phonenumber_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.listview1);
        adapter = new PhonenumberAdapter(getContext(), phonenumbers);
        listView.setAdapter(adapter);

        if (sub_phonenumbers.size() == 0)
        {
            if (CheckPermission(getContext(), permissons[0])) {
                // you have permission go ahead
                System.out.println("1");
                read_contact();
            } else {
                // you do not have permission go request runtime permissions
                RequestPermission(getActivity(), permissons, REQUEST_RUNTIME_PERMISSION);
            }
        }


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

                        ContentResolver cr = getActivity().getContentResolver();
                        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                                null, null, null, null);
                        if ((cur != null ? cur.getCount() : 0) > 0) {
                            while (cur != null && cur.moveToNext()) {
                                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                if (name.equalsIgnoreCase(phonenumbers.get(position).getName()))
                                {
                                    String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                                    Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                                    cr.delete(uri, null, null);
                                }
                            }
                        }

                        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, newName)
                                .putExtra(ContactsContract.Intents.Insert.EMAIL, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                                .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                                .putExtra(ContactsContract.Intents.Insert.PHONE, newNunmber)
                                .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                        startActivity(intent);

                        if (newName.length() != 0 && newNunmber.length()!=0) {
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
                    }
                });
                aDialog.setNeutralButton("삭제", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ContentResolver cr = getActivity().getContentResolver();
                        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                                null, null, null, null);
                        if ((cur != null ? cur.getCount() : 0) > 0) {
                            while (cur != null && cur.moveToNext()) {
                                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                if (name.equalsIgnoreCase(phonenumbers.get(position).getName()))
                                {
                                    String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                                    Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                                    cr.delete(uri, null, null);
                                }
                            }
                        }
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


    private void read_contact() {
        sub_phonenumbers.add(new Phonenumber("dummy", "dummy"));
        JSONObject jsonObject5 = new JSONObject();
        JSONArray newArray = new JSONArray();
        try {
            JSONObject jsonObject1 = new JSONObject();
            try {
                jsonObject1.put("name", sub_phonenumbers.get(0).getName());
                jsonObject1.put("number", sub_phonenumbers.get(0).getNumber());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            newArray.put(jsonObject1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            jsonObject5.put("Contacts", newArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String filename = "SubPhonenumbers.json";
        try {
            try (FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
                fos.write(jsonObject5.toString().getBytes());
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                        phonenumbers.add(new Phonenumber(name, phoneNo));
                        adapter.notifyDataSetChanged();
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
        JSONObject jsonObject50 = new JSONObject();
        JSONArray newArray0 = new JSONArray();
        try {
            for (int i = 0; i < phonenumbers.size(); i++) {
                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("name", phonenumbers.get(i).getName());
                    jsonObject1.put("number", phonenumbers.get(i).getNumber());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newArray0.put(jsonObject1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            jsonObject50.put("Contacts", newArray0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String filename0 = "Phonenumbers.json";
        try {
            try (FileOutputStream fos = getContext().openFileOutput(filename0, Context.MODE_PRIVATE)) {
                fos.write(jsonObject50.toString().getBytes());
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case REQUEST_RUNTIME_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // you have permission go ahead
                    read_contact();
                } else {
                    // you do not have permission show toast.
                }
                return;
            }
        }
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
