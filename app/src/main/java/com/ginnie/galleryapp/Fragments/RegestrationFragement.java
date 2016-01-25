package com.ginnie.galleryapp.Fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.ginnie.galleryapp.Activity.MainActivity;
import com.ginnie.galleryapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by su on 7/8/15.
 */
public class RegestrationFragement extends FragmentActivity {
    String monthformat="feb";
EditText fname,lname,paswrd,email,username;
    String strfname,strlname,strdob,strpaswrd,stremail,url;
    Button signup,next;
    ImageView back;
    String formattedDate;
    LinearLayout next_layout,main_layout;
    private int myear;
    private int mmonth;
    private int mday;
    static final int DATE_DIALOG_ID = 999;
    public RegestrationFragement()
    {

    }
    TextView dob;
    String jsondate;
    private static final String TAG = "RegestrationFragement";


    private static final String TWITTER_KEY = "AORuJpTsqf5Fm4P61VQjsZuo4";
    private static final String TWITTER_SECRET = "Xj0wX4ZpYFOpCbkOHvZb20AcxZ4qZFrM5l3iDrpI1jnCPNtecz";
    private AuthCallback authCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration);
        next_layout=(LinearLayout)findViewById(R.id.next_layout);
        main_layout=(LinearLayout)findViewById(R.id.main_layout);
        fname=(EditText)findViewById(R.id.fname);
        lname=(EditText)findViewById(R.id.lname);
        email=(EditText)findViewById(R.id.email);
        dob=(TextView)findViewById(R.id.dob);
        paswrd=(EditText)findViewById(R.id.paswrd);
        signup=(Button)findViewById(R.id.signup);
        next=(Button)findViewById(R.id.next);
        back=(ImageView)findViewById(R.id.back);
        username=(EditText)findViewById(R.id.username);




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    APP_DATA.user_name=username.getText().toString();

                if ( APP_DATA.user_name.length()>0){

                    url="http://www.esolz.co.in/lab2/android/prac/update_user_dtls.php?user_id="+APP_DATA.userid+"&user_name="+APP_DATA.user_name+"&phone=9874563210";

                    CreateUser1(url);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                else {
                    Toast.makeText(RegestrationFragement.this, "fill the field", Toast.LENGTH_SHORT).show();
                }

            }});
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        setCurrentDateOnView();
        addListenerOnButton();

    }
    public void addListenerOnButton() {

        dob=(TextView)findViewById(R.id.dob);
        SimpleDateFormat format = new SimpleDateFormat("d");
        String date = format.format(new Date());

        if(date.endsWith("1") && !date.endsWith("11"))
            format = new SimpleDateFormat(" MMM d'st', yyyy");
        else if(date.endsWith("2") && !date.endsWith("12"))
            format = new SimpleDateFormat(" MMM d'nd', yyyy");
        else if(date.endsWith("3") && !date.endsWith("13"))
            format = new SimpleDateFormat(" MMM d'rd', yyyy");
        else
            format = new SimpleDateFormat(" MMM d'th', yyyy");

        String yourDate = format.format(new Date());
        dob.setText(yourDate);



        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signup();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });




    }


    public void onSignupSuccess() {
        signup.setEnabled(true);

        url="http://www.esolz.co.in/lab2/android/prac/registraion.php?first_name="+APP_DATA.first_name+"&last_name="+APP_DATA.last_name+"&email="+APP_DATA.email+"&password=1"+APP_DATA.password+"&dob="+formattedDate;
        CreateUser(url);



    }

    public void onSignupFailed() {
        Toast.makeText(getApplicationContext(), "Regestration failed", Toast.LENGTH_LONG).show();

        signup.setEnabled(true);
    }


    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

       signup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegestrationFragement.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        String name = fname.getText().toString();
        String lastname = lname.getText().toString();
        String email1 = email.getText().toString();
        String phno = dob.getText().toString();
        String password = paswrd.getText().toString();

        fname.setText(APP_DATA.first_name);
        lname.setText(APP_DATA.last_name);
        email.setText(APP_DATA.email);
        dob.setText(APP_DATA.dob);

        APP_DATA.image="null";
        APP_DATA.first_name=name;
        APP_DATA.password=password;
        APP_DATA.email=email1;
        APP_DATA.phone_no=phno;
        APP_DATA.last_name=lastname;





        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
//                        onSignupSuccess();
//                        jsonData();
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }
    public boolean validate() {
        boolean valid = true;
        String name = fname.getText().toString();
        String lastname = lname.getText().toString();
        String email1 = email.getText().toString();
        String phno = dob.getText().toString();
        String password = paswrd.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            fname.setError("at least a characters");
            valid = false;
        } else {
            fname.setError(null);
        }

        if (lastname.isEmpty() || lastname.length() < 3) {
            lname.setError("at least a characters");
            valid = false;
        } else {
            lname.setError(null);
        }


        if (phno.isEmpty() || phno.length() < 9) {
            dob.setError("wrong paternn of no");
            valid = false;
        } else {
            dob.setError(null);
        }


        if (email1.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            paswrd.setError("Minimum of 6 alphanumeric characters");
            valid = false;
        } else {
            paswrd.setError(null);
        }



        return valid;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                DatePickerDialog _date =   new DatePickerDialog(RegestrationFragement.this, datePickerListener, myear,mmonth,
                        mday){
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        if (year > myear)
                            view.updateDate(myear, mmonth, mday);

                        if (monthOfYear > mmonth && year == myear)
                            view.updateDate(myear, mmonth, mday);

                        if (dayOfMonth > mday && year == myear && monthOfYear == mmonth)
                            view.updateDate(myear, mmonth, mday);

                    }
                };
                return _date;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            myear = selectedYear;
            mmonth = selectedMonth;
            mday = selectedDay;

            if (mmonth==0)

            {
                monthformat="Jan";
            }
            else if (mmonth==1)
            {
                monthformat="Feb";
            }
            else if (mmonth==2)
            {
                monthformat="Mar";
            }
            else if (mmonth==3)
            {
                monthformat="Apr";
            }
            else if (mmonth==4)
            {
                monthformat="May";
            }
            else if (mmonth==5)
            {
                monthformat="Jun";
            }
            else if (mmonth==6)
            {
                monthformat="Jul";
            }
            else if (mmonth==7)
            {
                monthformat="Aug";
            }
            else if (mmonth==8)
            {
                monthformat="Sep";
            } else if (mmonth==9)
            {
                monthformat="Oct";
            } else if (mmonth==10)
            {
                monthformat="Nov";
            } else if (mmonth==11)
            {
                monthformat="Dec";
            }else {
                monthformat="Jan";
            }

            dob.setText(new StringBuilder()
//                // Month is 0 based, just add 1
                    .append(monthformat).append(" ").append(mday).append(" ")
                    .append(myear).append(" "));

//             set selected date into textview
//            logdate.setText(monthformat+selectedDay+selectedYear);
//
            jsondate= String.valueOf((new StringBuilder()
                    .append(myear).append("-").append(mmonth + 1).append("-")
                    .append(mday)));
            Log.d("date",jsondate);





        }
    };

    // display current date
    public void setCurrentDateOnView() {

//        tvDisplayDate = (TextView) findViewById(R.id.tvDate);

        final Calendar c = Calendar.getInstance();
        myear = c.get(Calendar.YEAR);
        mmonth = c.get(Calendar.MONTH);
        mday = c.get(Calendar.DAY_OF_MONTH);
    }



    private void CreateUser1(String url) {
        Log.i("Delete_url", url.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    String  success = response.getString("code");
                    String message=response.getString("message");
                    JSONObject jsonObject=response.getJSONObject("response");
                    if (success.equals("0")){

                        APP_DATA.userid=jsonObject.getString("user_id");
//                        APP_DATA.user_name=jsonObject.getString("user_name");
//
//                        startActivity(new Intent(getApplicationContext(), VeriActivity.class));

//                        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
//                        digitsButton.setCallback(((APP_DATA) getApplication()).getAuthCallback());
//
//                        Digits.authenticate(APP_DATA.authCallback, "+918583818151");
////
////                        next_layout.setVisibility(View.VISIBLE);
////                        main_layout.setVisibility(View.GONE);
//

                    }else
                    {

                        Toast.makeText(getApplicationContext(),
                                "Registratyion failed", Toast.LENGTH_SHORT).show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();


                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volly_error", "Error: " + error.getMessage());

                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

        APP_DATA.getInstance().addToRequestQueue(jsonObjReq);

    }




    private void CreateUser(String url) {
        Log.i("Delete_url", url.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                  String  success = response.getString("code");
                    String message=response.getString("message");
                    JSONObject jsonObject=response.getJSONObject("response");
                    if (success.equals("0")){

                        APP_DATA.userid=jsonObject.getString("user_id");
                        APP_DATA.first_name=jsonObject.getString("first_name");
                        APP_DATA.first_name=jsonObject.getString("last_name");
                        APP_DATA.email=jsonObject.getString("email");
                        APP_DATA.dob=jsonObject.getString("dob");

//                        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
//                        digitsButton.setCallback(((APP_DATA) getApplication()).getAuthCallback());

//                        Digits.authenticate(APP_DATA.authCallback, "+918583818151");

                        next_layout.setVisibility(View.VISIBLE);
                        main_layout.setVisibility(View.GONE);


                    }else
                    {

                        Toast.makeText(getApplicationContext(),
                               "Registratyion failed", Toast.LENGTH_SHORT).show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();


                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volly_error", "Error: " + error.getMessage());

                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

        APP_DATA.getInstance().addToRequestQueue(jsonObjReq);

    }


}
