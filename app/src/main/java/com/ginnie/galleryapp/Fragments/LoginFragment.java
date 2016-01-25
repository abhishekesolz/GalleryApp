package com.ginnie.galleryapp.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ginnie.galleryapp.Activity.MainActivity;
import com.ginnie.galleryapp.R;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by su on 7/8/15.
 */
public class LoginFragment extends Fragment {

EditText username,paswrd;
    Button login;
    String url;
    public LoginFragment()
    {

    }

    ImageView back;
    private static final String TAG = "SignupActivity";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        username=(EditText)view.findViewById(R.id.username);
        paswrd=(EditText)view.findViewById(R.id.paswrd);
        login=(Button)view.findViewById(R.id.login);
        back=(ImageView)view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SplashFragment fragment2 = new SplashFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.main_container, fragment2);
                transaction.commit();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signup();

            }
        });


        return view;
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        login.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        String name = username.getText().toString();

        String password = paswrd.getText().toString();
//url="\n" +
//        "http://www.esolz.co.in/lab2/android/prac/user_login.php?user_name=sutirtha&password=123456";

        url=APP_DATA.BASE_URL+"user_login.php?user_name="+name+"&password="+password;


        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success

                        onSignupSuccess();

                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public boolean validate() {
        boolean valid = true;
        String name = username.getText().toString();

        String password = paswrd.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            username.setError("at least a characters");
            valid = false;
        } else {
            username.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            paswrd.setError("Minimum of 6 alphanumeric characters");
            valid = false;
        } else {
            paswrd.setError(null);
        }



        return valid;
    }

    public void onSignupSuccess() {
        login.setEnabled(true);

        APP_DATA.page_set="main";
        makeJsonObjectRequest();

    }
    public void onSignupFailed() {
        Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();

        login.setEnabled(true);
    }
    private void makeJsonObjectRequest() {



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    String code = response.getString("code");
                    Log.d("User Login!", response.toString());
                    if(code.equals("0"))
                    {

                        JSONObject c=response.getJSONObject("response");

                        APP_DATA.userid=c.getString("user_id");
                        APP_DATA.user_name=c.getString("user_name");
                        APP_DATA.first_name=c.getString("first_name");
                        APP_DATA.last_name=c.getString("last_name");
                        APP_DATA.email=c.getString("email");
                        APP_DATA.dob=c.getString("dob");
                        APP_DATA.phone_no=c.getString("phone");
                        APP_DATA.image=c.getString("image");


                        Toast.makeText(getActivity(),
                                response.getString("message"),
                                Toast.LENGTH_LONG).show();
                        APP_DATA.page_set="main";
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getActivity(), "Login Succesfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(),
                                response.getString("message"),
                                Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getActivity(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        APP_DATA.getInstance().addToRequestQueue(jsonObjReq);


    }

}
