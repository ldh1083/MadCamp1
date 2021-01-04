package com.example.test.ui.main;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.test.R;

import static com.example.test.MainActivity.phonenumbers;

public class PhonenumberAdapter extends ArrayAdapter<Phonenumber> {
    private Context context;
    private final ArrayList<Phonenumber> phonenumbers;

    public PhonenumberAdapter(Context context, ArrayList<Phonenumber> phonenumbers) {
        super(context,-1,phonenumbers);
        this.context = context;
        this.phonenumbers = phonenumbers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_phonenumber, parent, false);

        TextView nameTextView = (TextView) rowView.findViewById(R.id.txt_name);
        nameTextView.setText(phonenumbers.get(position).getName());
        final TextView numberTextView = (TextView) rowView.findViewById(R.id.number);
        numberTextView.setText(phonenumbers.get(position).getNumber());

        ImageView iv_contact = (ImageView)rowView.findViewById(R.id.image_contact);

        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            Uri u= null;
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (name.equalsIgnoreCase(phonenumbers.get(position).getName()))
                {
                    try {
                        Cursor cur1 = cr.query(
                                ContactsContract.Data.CONTENT_URI,
                                null,
                                ContactsContract.Data.CONTACT_ID + "=" + id + " AND "
                                        + ContactsContract.Data.MIMETYPE + "='"
                                        + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,
                                null);
                        if (cur1 != null) {
                            if (!cur1.moveToFirst()) {
                                break; // no photo
                            }
                        } else {
                            break; // error in cursor process
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                    Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long
                            .parseLong(id));
                    u = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
                    break;
                }
            }

            if (u != null) {
                iv_contact.setImageURI(u);
            }
        }

        // View에 Data 세팅
        return rowView;
    }

    /*public class ViewHolder {
        private TextView txt_name;
        public ViewHolder(View convertView) {
            txt_name = (TextView) convertView.findViewById(R.id.txt_name);
        }
    }*/
}