package com.example.test.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.test.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FreeAdaptor extends ArrayAdapter<Food> implements Filterable {
    private Context context;
    private ArrayList<Food> foods;
    public ArrayList<Food> filteredfoods=foods;
    private ArrayList<Day> days;
    AdapterCallback callback;
    public static int new_kcal;
    FreeFragment fragment;
    Filter listFilter ;
    int date;
    SimpleDateFormat mFormat = new SimpleDateFormat("MMdd");
    long mNow;
    Date mDate;
    public interface AdapterCallback{
        void eat_food(int new_kcal);
        ArrayList<Food> update();
        ArrayList<Day> return_day();
        void drawgraph();
    }

    public FreeAdaptor(Context context, ArrayList<Food> foods, ArrayList<Day>days, AdapterCallback callback) {
        super(context,-1,foods);
        this.context = context;
        this.callback = callback;
        this.foods = foods;
        this.filteredfoods = foods;
        this.days = days;
    }

    @Override
    public int getCount() {
        return filteredfoods.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_food, parent, false);

        TextView nameTextView = (TextView) rowView.findViewById(R.id.food_name);
        nameTextView.setText(filteredfoods.get(position).getName());
        TextView kcalTextView = (TextView) rowView.findViewById(R.id.food_kcal);
        kcalTextView.setText(filteredfoods.get(position).getkcal());
        TextView numTextView = (TextView) rowView.findViewById(R.id.food_num);
        numTextView.setText("수량: " + filteredfoods.get(position).getNum());

        ImageButton plus_botton = rowView.findViewById(R.id.plus_button);
        plus_botton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mNow = System.currentTimeMillis();
                mDate = new Date(mNow);
                date =Integer.parseInt(mFormat.format(mDate));
                days = callback.return_day();
                if (days.size() > 0) {
                    if (date != days.get(days.size()-1).getDate()) {
                        if (callback != null) {
                            callback.update();
                        }
                    }
                }

                int origin_position = -1;
                for (int i=0; i<foods.size(); i++) {
                    if (filteredfoods.get(position).getOrder() == foods.get(i).getOrder()) {
                        origin_position = i;
                        break;
                    }
                }

                foods.get(origin_position).setNum(foods.get(origin_position).getNum()+1);
                numTextView.setText("수량: " + foods.get(origin_position).getNum());
                new_kcal =  Integer.parseInt(foods.get(origin_position).getkcal().substring(0, foods.get(origin_position).getkcal().length()-4));

                if(callback != null) {
                    callback.eat_food(new_kcal);
                }

                JSONObject jsonObject5 = new JSONObject();
                JSONArray newArray = new JSONArray();
                try {
                    for (int i = 0; i < foods.size(); i++) {
                        JSONObject jsonObject1 = new JSONObject();
                        try {
                            jsonObject1.put("name", foods.get(i).getName());
                            jsonObject1.put("kcal", foods.get(i).getkcal());
                            jsonObject1.put("num", foods.get(i).getNum());
                            jsonObject1.put("order", foods.get(i).getOrder());
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

                String filename = "Foods.json";
                try {
                    try (FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
                        fos.write(jsonObject5.toString().getBytes());
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                callback.drawgraph();
            }
        });
        ImageButton minus_botton = rowView.findViewById(R.id.minus_button);
        minus_botton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (foods.get(position).getNum()>0) {
                    mNow = System.currentTimeMillis();
                    mDate = new Date(mNow);
                    date = Integer.parseInt(mFormat.format(mDate));
                    days = callback.return_day();
                    if (days.size() > 0) {
                        if (date != days.get(days.size() - 1).getDate()) {
                            if (callback != null) {
                                foods = callback.update();
                            }
                        }
                    }

                    int origin_position = -1;
                    for (int i=0; i<foods.size(); i++) {
                        if (filteredfoods.get(position).getOrder() == foods.get(i).getOrder()) {
                            origin_position = i;
                            break;
                        }
                    }

                    foods.get(origin_position).setNum(foods.get(origin_position).getNum() - 1);
                    numTextView.setText("수량: " + foods.get(origin_position).getNum());
                    new_kcal = Integer.parseInt(foods.get(origin_position).getkcal().substring(0, foods.get(origin_position).getkcal().length() - 4));

                    if (callback != null) {
                        callback.eat_food(-new_kcal);
                    }

                    JSONObject jsonObject5 = new JSONObject();
                    JSONArray newArray = new JSONArray();
                    try {
                        for (int i = 0; i < foods.size(); i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            try {
                                jsonObject1.put("name", foods.get(i).getName());
                                jsonObject1.put("kcal", foods.get(i).getkcal());
                                jsonObject1.put("num", foods.get(i).getNum());
                                jsonObject1.put("order", foods.get(i).getOrder());
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

                    String filename = "Foods.json";
                    try {
                        try (FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
                            fos.write(jsonObject5.toString().getBytes());
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.drawgraph();
                }
            }
        });

        // View에 Data 세팅
        return rowView;
    }

    /*public class ViewHolder {
        private TextView txt_name;
        public ViewHolder(View convertView) {
            txt_name = (TextView) convertView.findViewById(R.id.txt_name);
        }
    }*/
    @Override
    public Food getItem(int position) {
        return filteredfoods.get(position) ;
    }
    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Filter getFilter() {
        if (listFilter == null) {
            listFilter = new ListFilter() ;
        }
        return listFilter ;
    }

    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults() ;

            if (constraint == null || constraint.length() == 0) {
                results.values = foods ;
                results.count = foods.size() ;
            } else {
                ArrayList<Food> itemList = new ArrayList<Food>() ;

                for (Food item : foods) {
                    if (item.getName().toUpperCase().contains(constraint.toString().toUpperCase()))
                    {
                        itemList.add(item) ;
                    }
                }
                results.values = itemList ;
                results.count = itemList.size() ;
            }
            System.out.println("hello");
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            // update listview by filtered data list.
            filteredfoods = (ArrayList<Food>) results.values ;

            // notify
            if (results.count > 0) {
                notifyDataSetChanged() ;
            } else {
                notifyDataSetInvalidated() ;
            }
        }
    }
}