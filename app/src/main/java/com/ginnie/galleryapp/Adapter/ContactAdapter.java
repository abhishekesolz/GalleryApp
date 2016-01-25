package com.ginnie.galleryapp.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ginnie.galleryapp.Fragments.APP_DATA;
import com.ginnie.galleryapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by su on 31/12/15.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private JSONArray jsonArray=new JSONArray();
    private Context context;

    public ContactAdapter(JSONArray jsonArray, Context context) {
        this.jsonArray = jsonArray;
        this.context=context;
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        JSONObject ci;

        try {
            ci = jsonArray.getJSONObject(i);
            Log.d("jsonary",jsonArray.getJSONObject(i).toString());

//            contactViewHolder.vName.setText(jsonArray.getJSONObject(i).getString("details"));
//            contactViewHolder.location.setText(jsonArray.getJSONObject(i).getString("location"));
//            contactViewHolder.date.setText(jsonArray.getJSONObject(i).getString("added_on"));
//
//            JSONArray ping_details=ci.getJSONArray("ping_details");




//            if (ping_details.length() != 0) {
//
//                ping_details.get(0);
////
////        contactViewHolder.vName.setText(ping_details.getJSONObject(0).getString("details"));
////        contactViewHolder.date.setText(ping_details.getJSONObject(0).getString("added_on"));
////        contactViewHolder.location.setText(ping_details.getJSONObject(0).getString("location"));
////                APP_DATA.trip_image=ping_details.getJSONObject(0).getString("images");
//
//                if(ping_details.getJSONObject(0).getString("images")!=null & ping_details.getJSONObject(0).getString("images").length()>0)
//                {
//                    Picasso.with(context).load(ping_details.getJSONObject(0).getString("images")).fit().placeholder(R.drawable.abc_dialog_material_background_dark).error(R.drawable.abc_dialog_material_background_light).into(contactViewHolder.menu);
//                }else
//                {
//                    Picasso.with(context).load(R.drawable.logo).fit().placeholder(R.drawable.abc_dialog_material_background_dark).error(R.drawable.abc_dialog_material_background_light).into(contactViewHolder.menu);
//                }
//
//
////                images
//
//
//            }



        } catch (JSONException e) {
            e.printStackTrace();
        }



//        contactViewHolder.vName.setText(ci.getDetails());
//        contactViewHolder.date.setText(ci.getAdded_on());
//        contactViewHolder.location.setText(ci.getLocation());

//        contactViewHolder.menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////
//                APP_DATA.page_set="pik_detail";
////
////                Intent i=new Intent(context,TrackMyTravel.class);
////                context.startActivity(i);
//                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
//                ImageDetailFragment fragment2 = new ImageDetailFragment();
//                ft.replace(R.id.my_trip_list_cont, fragment2);
//                ft.commit();
//            }
//        });


    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);




        return new ContactViewHolder(itemView);
    }




    public static class ContactViewHolder extends RecyclerView.ViewHolder {
//       TextView date,location;
//        TextView vName;


        ImageView menu;


        public ContactViewHolder(View v) {
            super(v);
//            vName =  (TextView) v.findViewById(R.id.txtName);
//            menu=(ImageView)v.findViewById(R.id.menu);
//            date=  (TextView) v.findViewById(R.id.date);
//                    location=  (TextView) v.findViewById(R.id.location);
        }
    }



}