package com.ginnie.galleryapp.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import com.ginnie.galleryapp.Adapter.ALLItemApater;
import com.ginnie.galleryapp.Adapter.CountryAdapter;
import com.ginnie.galleryapp.Datatype.DataObject;
import com.ginnie.galleryapp.Datatype.FollowAllitemRow;
import com.ginnie.galleryapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;


public class GroupsFragment extends Fragment {

    private ListView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    int pos, count=0;
    public    LinkedList<FollowAllitemRow> itemData =new LinkedList<>();// for Follow_all
String follow_url,group_id;
    int sucess;
    JSONArray json_arr_lv;
    public boolean loading = false;
    public GroupsFragment() {
    }
    ALLItemApater allItemApater=new ALLItemApater(getActivity(),0,itemData);
    RelativeLayout post;
    LinearLayout hidden_layout;
    ImageView arrow,video_day,image_day;
    View view;
    private static final String TAG = "Tab1";

    boolean flag=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    /* Initialize recyclerview */

        view=inflater.inflate(R.layout.fragment_group, container, false);
        mRecyclerView = (ListView) view.findViewById(R.id.my_recycler_view);


        mLayoutManager = new LinearLayoutManager(getActivity());

//        mAdapter = new CountryAdapter(getDataSet());
        mRecyclerView.setAdapter(allItemApater);


        return view;
    }

    public void loadMore(int position) {


        pos = position;

        itemData=new LinkedList<>();
        if (position == 0) {
            group_id="1";
            itemData=new LinkedList<>();
            follow_url = APP_DATA.BASE_URL1 + "mode=follow_list&user_id=" + APP_DATA.userid + "&last_row=0&group="+group_id+"&track_type=0";
            System.out.print("!! Response : " + follow_url.toString());

            Follow_all(follow_url);

            Log.i("follow:", "" + follow_url);

        } else {
            group_id="1";
            follow_url = APP_DATA.BASE_URL1 + "mode=follow_list&user_id=" + APP_DATA.userid + "&last_row=" + pos + "&group=" + group_id + "&track_type=0";
            System.out.print("!! Response : " + follow_url.toString());
            Follow_all(follow_url);

            Log.i("follow :", "" + follow_url);
        }


        final Context cont = getActivity();



    }

    public void Follow_all(String url) {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                follow_ind(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getActivity(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
        APP_DATA.getInstance().addToRequestQueue(jsonObjReq);


    }
    public void  follow_ind( JSONObject  response)
    {
        try {
            String msg = response.getString("message");
            sucess = response.getInt("code");
            JSONObject c = response.getJSONObject("response");
            Log.i("follow_url", "" + c.toString());
            //count=0;
            count = c.getInt("R_count");
            //Toast.makeText(getActivity()(), "" + count, Toast.LENGTH_SHORT).show();



            json_arr_lv = c.getJSONArray("memberlist");

            System.out.print("!! Response : " + json_arr_lv.toString());

            if (json_arr_lv.length() != 0) {
                itemData=new LinkedList<>();
                for (int i = 0; i < json_arr_lv.length(); i++) {
                    JSONObject Json_Obj_temp;

                    Json_Obj_temp = json_arr_lv.getJSONObject(i);

                    FollowAllitemRow obj = new FollowAllitemRow(Json_Obj_temp.getString("team_list_id"), Json_Obj_temp.getString("member_id"), Json_Obj_temp.getString("lead_id"), Json_Obj_temp.getString("image"), Json_Obj_temp.getString("name"), Json_Obj_temp.getString("phone"), Json_Obj_temp.getString("location"), Json_Obj_temp.getString("group_id"),
                            Json_Obj_temp.getString("dialog_boxid"), Json_Obj_temp.getString("quickboxid"), Json_Obj_temp.getString("group_creator_id"), Json_Obj_temp.getString("group_creator_name"),
                            Json_Obj_temp.getString("group_creator_phone"), Json_Obj_temp.getString("group_creator_image"), Json_Obj_temp.getString("group_creator_quickboxid"));

                    itemData.add(obj);

                }


            } else {
//            txt_nodata.setVisibility(View.VISIBLE);
                       /* NoDataFound fragment2 = new NoDataFound();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frgtrackteam, fragment2);
                        fragmentTransaction.commit();*/
            }
            if (sucess == 0) {
                if (pos == 0) {

                    mRecyclerView.setAdapter(new ALLItemApater(getActivity(), 0, itemData));



                } else {

                    new ALLItemApater(getActivity(), 0, itemData).notifyDataSetChanged();


                }


                loading = false;

            }else
            {
//            pd.dismiss();
//            listview.setVisibility(View.GONE);
//            flw.setText("" + count);
//            flwing.setText(""+s2);
//            pendng.setText("" + s3);
//            txt_nodata.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();

            Toast.makeText(getActivity(),
                    "Error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

}
