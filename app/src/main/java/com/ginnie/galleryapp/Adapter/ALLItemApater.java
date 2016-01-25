package com.ginnie.galleryapp.Adapter;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ginnie.galleryapp.Datatype.FollowAllitemRow;
import com.ginnie.galleryapp.Fragments.GroupsFragment;
import com.ginnie.galleryapp.R;
import com.ginnie.galleryapp.helper.ConnectionDetector;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

/**
 * Created by su on 25/11/15.
 */
public class ALLItemApater extends ArrayAdapter<FollowAllitemRow>{
GroupsFragment cd=new GroupsFragment();
    String track_id;
    private static final String TAG ="Tab1" ;
    LinkedList<FollowAllitemRow> data;
    FollowAllitemRow followAllitemRow;
    ConnectionDetector cd1;
    Context context;
    int layoutResID;
    LayoutInflater inflater;
    String list_id,grp_id;
    private ProgressDialog pDialog;
    NewsHolder holder = null;
    public boolean loading = false;
    public ALLItemApater(Context context, int resource, LinkedList<FollowAllitemRow> objects) {
        super(context, resource, objects);
        this.data=objects;
        this.context=context;
        this.layoutResID=resource;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

//    public ALLItemApater(Context context, int layoutResourceId,LinkedList<FollowAllitemRow> data) {
//
//        super(context, layoutResourceId, data);
//        //	pDialog = new ProgressDialog(context);
//        this.data=data;
//        this.context=context;
//        this.layoutResID=layoutResourceId;
//        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//            }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View row = convertView;


//        final Tab1 cd=new Tab1();
        if(row == null)
        {
            row = inflater.inflate(R.layout.custom_row_followall,null);

            holder = new NewsHolder();
            holder.linearlistrow= (LinearLayout)row.findViewById(R.id.linearlistrow);
            holder.itemName = (TextView)row.findViewById(R.id.example_itemname_followall);
            holder.icon=(ImageView)row.findViewById(R.id.example_image_followall);
            holder.example_location_followall=(TextView)row.findViewById(R.id.example_location_followall);
            row.setTag(holder);
        }
        else
        {
            holder = (NewsHolder)row.getTag();
        }

        FollowAllitemRow itemdata = data.get(position);

//        holder.linearlistrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                followAllitemRow=data.get(position);
//                context.startActivity(new Intent(getContext(), FamilyLandingActivity.class));
//            }
//        });


        Log.d("itemdata", itemdata.getImage().toString());
        holder.itemName.setText(itemdata.getName());



        list_id=itemdata.getTeam_list_id();
        grp_id=itemdata.getGroup_id();
        track_id=data.get(position).getTeam_list_id();
        int total_d=data.size();
if (!data.get(position).getMember_id().equals("0")) {
    if (data.get(position).getLocation().length()>0) {
        holder.example_location_followall.setVisibility(View.VISIBLE);
        holder.example_location_followall.setText(data.get(position).getLocation());
    }
}
        else
{
    holder.example_location_followall.setVisibility(View.GONE);
}

        if(itemdata.getImage()!=null & itemdata.getImage().length()>0)
        {
            Picasso.with(context).load(itemdata.getImage()).resize(400, 400).centerInside().placeholder(R.drawable.abc_dialog_material_background_dark).error(R.drawable.abc_dialog_material_background_light).into(holder.icon);
        }else {
            Picasso.with(context).load(R.drawable.logo).resize(400, 400).centerInside().placeholder(R.drawable.abc_dialog_material_background_dark).error(R.drawable.abc_dialog_material_background_light).into(holder.icon);
        }



        if(position<total_d)
        {
            cd.loadMore(position + 10);


            cd.loading=false;
        }

        return row;

    }




    static class NewsHolder{

        TextView itemName;
        TextView example_location_followall;
        ImageView icon;
        LinearLayout linearlistrow;


    }




}
