package com.ginnie.galleryapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ginnie.galleryapp.Adapter.TripAdapter;
import com.ginnie.galleryapp.Datatype.PrevWorkTripDataType;
import com.ginnie.galleryapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;


public class GroupListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    JSONArray json_arr_lv;
    int pos;
    public GroupListFragment() {
    }
    LinkedList<PrevWorkTripDataType> itemData = new LinkedList<PrevWorkTripDataType>();
    RelativeLayout post;
    LinearLayout hidden_layout;
    ImageView arrow,video_day,image_day;
    View view;
    String sucess;
    boolean flag=false;
    ListView list_view;
    public boolean loading = false;
    String url;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    /* Initialize recyclerview */

        view=inflater.inflate(R.layout.fragment_grplist, container, false);

        list_view = (ListView) view.findViewById(R.id.list_view);

        Getdetails1(url);
        loadMore(0);
        return view;
    }
    public void loadMore(int position) {

        int pos = position;
        String vacation_type="1";
        if (position == 0) {

            url = APP_DATA.BASE_URL_TRAVEL+"mode=vacation_list&vacation_type="+vacation_type+"&userid="+ APP_DATA.userid + "&last_row=0";
            System.out.print("!! Response : " + url.toString());
            Log.i("urlvac",url.toString());
        } else {
            url = APP_DATA.BASE_URL_TRAVEL+"mode=vacation_list&vacation_type="+vacation_type+"&userid="+ APP_DATA.userid + "&last_row=" + APP_DATA.last_row;
            System.out.print("!! Response : " + url.toString());
            Log.i("urlvac", url.toString());
        }

        Getdetails1(url);

    }

    private void Getdetails1(String url) {



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("volly_error", response.toString());

                try {
                    sucess=response.getString("code");
                    JSONObject c = response.getJSONObject("response");
                    JSONObject d=c.getJSONObject("option");

                    APP_DATA.loadmore=d.getInt("load_more");

                    APP_DATA.last_row=d.getInt("last_row");

                    json_arr_lv = c.getJSONArray("vacation_list");

                    if (json_arr_lv.length() != 0) {

                        for (int i = 0; i < json_arr_lv.length(); i++) {
                            JSONObject Json_Obj_temp;

                            Json_Obj_temp = json_arr_lv.getJSONObject(i);



                            PrevWorkTripDataType obj = new PrevWorkTripDataType(Json_Obj_temp.getString("vacation_id"),
                                    Json_Obj_temp.getString("vacation_name"),
                                    Json_Obj_temp.getString("vacation_type"),
                                    Json_Obj_temp.getString("vacation_start_date"),
                                    Json_Obj_temp.getString("vacation_end_date"),
                                    Json_Obj_temp.getString("vacation_end"));


                            //obj.setTotal_data(json_lv);
                            //obj.setIs_added(true);
                            itemData.add(obj);



                        }

                    }

                    else
                    {

                    }
                    if (sucess.equals("0")) {
                        if (pos == 0) {



                            list_view.setAdapter(new TripAdapter(getActivity(), 0, itemData));



                        } else {

                            new TripAdapter(getActivity(), 0, itemData).notifyDataSetChanged();


                        }


                        loading = false;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("vbolly_error", "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        APP_DATA.getInstance().addToRequestQueue(jsonObjReq);


    }

}
