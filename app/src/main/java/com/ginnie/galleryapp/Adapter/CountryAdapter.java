package com.ginnie.galleryapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ginnie.galleryapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by su on 31/12/15.
 */
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private JSONArray jsonArray;
    private Context context;

    public CountryAdapter(JSONArray jsonArray, Context context) {
        this.jsonArray = jsonArray;
        this.context=context;
    }
    public void add(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    @Override
    public void onBindViewHolder(CountryViewHolder contactViewHolder, int i) {
        JSONObject ci;

        try {
            ci = jsonArray.getJSONObject(i);

            Log.d("jsonary", jsonArray.getJSONObject(i).toString());

            contactViewHolder.country_name.setText(ci.getString("country_name"));


        } catch (JSONException e) {
            e.printStackTrace();
        }


//contactViewHolder.list_click.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        String jkl=ci.getString()
//    }
//});


    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.country_list, viewGroup, false);

        CountryViewHolder holder =new CountryViewHolder(itemView);


        return holder;
    }




    public static class CountryViewHolder extends RecyclerView.ViewHolder {
        TextView country_name;
        LinearLayout list_click;


        public CountryViewHolder(View v) {
            super(v);
            country_name =  (TextView)v.findViewById(R.id.country_name);
            list_click=  (LinearLayout)v.findViewById(R.id.list_click);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                }
            });

        }
    }



}