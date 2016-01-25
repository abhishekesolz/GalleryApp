package com.ginnie.galleryapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ginnie.galleryapp.Datatype.PrevWorkTripDataType;
import com.ginnie.galleryapp.Fragments.GroupListFragment;
import com.ginnie.galleryapp.R;
import com.ginnie.galleryapp.helper.ConnectionDetector;

import java.util.LinkedList;

/**
 * Created by su on 30/12/15.
 */
public class TripAdapter extends ArrayAdapter<PrevWorkTripDataType> {

    LinkedList<PrevWorkTripDataType> data;
    Context context;
    ConnectionDetector cd1;
    int layoutResID;
    LayoutInflater inflater;
    String list_id,grp_id,vacation_id;

    GroupListFragment cd=new GroupListFragment();

//    public TripAdapter(TrackMyTravel trackMyTravel, int i, LinkedList<PrevWorkTripDataType> itemData) {
//        super(trackMyTravel, i, itemData);
//        this.data=itemData;
//       this.context=trackMyTravel;
//       inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//    }

    public TripAdapter(Context ctx, int layoutResourceId, LinkedList<PrevWorkTripDataType> data) {
        super(ctx, layoutResourceId, data);
        this.data=data;
        this.context=ctx;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NewsHolder holder = null;
        View row = convertView;
        holder = null;
        cd1 = new ConnectionDetector(context);

        if(row == null)
        {

            row = inflater.inflate(R.layout.my_prev_work_trip_row_item, null);

            holder = new NewsHolder();

            holder.trip_name = (TextView)row.findViewById(R.id.trip_name);
            holder.list_row = (LinearLayout)row.findViewById(R.id.list_row);


            row.setTag(holder);
        }
        else
        {
            holder = (NewsHolder)row.getTag();
        }

        PrevWorkTripDataType itemdata = data.get(position);
        holder.trip_name.setText(itemdata.getVacation_name());

//        if (APP_DATA.vacation_success.equals("1")){
//
//            holder.list_row.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        }
        list_id=itemdata.getVacation_id();

        int total_d=data.size();
//        if (APP_DATA.loadmore==1)
//
//        {
//
//            if (position < total_d) {
//                cd.loadMore(APP_DATA.last_row);
//
//            }
//        }

        return row;

    }



    static class NewsHolder{

        TextView trip_name;
        LinearLayout list_row;
    }


}
