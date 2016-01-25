package com.ginnie.galleryapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ginnie.galleryapp.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    TextView first_name,user_name;
    public HomeFragment() {
    }

    RelativeLayout post;
    LinearLayout hidden_layout;
    ImageView arrow,video_day,image_day;
    View view;
    boolean flag=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    /* Initialize recyclerview */

        view=inflater.inflate(R.layout.fragment_home, container, false);
        first_name=(TextView)view.findViewById(R.id.first_name);
                user_name=(TextView)view.findViewById(R.id.user_name);

        first_name.setText(APP_DATA.first_name);
        user_name.setText(APP_DATA.user_name);

        return view;
    }
}
