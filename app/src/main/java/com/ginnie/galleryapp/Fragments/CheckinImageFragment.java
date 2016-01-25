package com.ginnie.galleryapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ginnie.galleryapp.Adapter.ContactAdapter;
import com.ginnie.galleryapp.Datatype.ContactInfo;
import com.ginnie.galleryapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by Ratan on 7/29/2015.
 */
public class CheckinImageFragment extends Fragment {
    View view;
    RecyclerView recList;
    boolean click=false;
    String sucess,url;
    JSONArray json_arr_lv=new JSONArray();

    ContactAdapter ca;
    LinkedList<ContactInfo> contactList = new LinkedList<ContactInfo>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.premium_layout,null);

//        recList = (RecyclerView) view.findViewById(R.id.cardList);


        recList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);



        url="http://esolz.co.in/lab4/findme/appconnect/vacation.php?mode=only_ping_listing&vacation_id=114";
        Getdetails1(url);
        return view;
    }

    private void Getdetails1(String url) {



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("volly_error", response.toString());

                try {
                    sucess=response.getString("code");

                    json_arr_lv = response.getJSONArray("response");

                    ca = new ContactAdapter(json_arr_lv,getActivity());
                    ca.notifyDataSetChanged();
                    recList.setAdapter(ca);
//                    if (json_arr_lv.length() != 0) {
//
//                        for (int i = 0; i < json_arr_lv.length(); i++) {
//                            JSONObject Json_Obj_temp;
//                            JSONArray jsonArray;
//                            Json_Obj_temp = json_arr_lv.getJSONObject(i);
//
//
//
//
//                            ContactInfo obj = new ContactInfo(Json_Obj_temp.getString("id"),
//                                    Json_Obj_temp.getString("details"),
//                                    Json_Obj_temp.getString("location"),
//                                    Json_Obj_temp.getString("added_on"));
//
////                            APP_DATA.vacation_success=Json_Obj_temp.getString("vacation_end");
//
//
//
//                            contactList.add(obj);
//
//
//
//                        }
//
//
//                    }
//
//                    else
//                    {
//
//                    }

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
