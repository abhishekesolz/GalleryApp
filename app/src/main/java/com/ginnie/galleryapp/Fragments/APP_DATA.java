package com.ginnie.galleryapp.Fragments;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.ginnie.galleryapp.MainActivity;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;

/**
 * Created by su on 13/1/16.
 */

public class APP_DATA extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "uoWf86Sj95ZwEEBMwDisqasC8";
    private static final String TWITTER_SECRET = "X2zNGgkul2WBKTXSVdGbjSFDgxczNNrklKRUDPfPrcsm9GSdvD";
    public static AuthCallback authCallback;

    public static int loadmore=0;
    public static int last_row=0;

    public static String group_name="";
    public static String userid="";
    public static String first_name="";
    public static String last_name="";
    public static String image="";
    public static String email="";
    public static String dob="";
    public static String password="";
    public static String phone_no="";
    public static String user_name="";
    public static String group_id="";




    public static String page_set="";


    public static String color_choose="0";
    public static String BASE_URL="http://www.esolz.co.in/lab2/android/prac/";
    public static String BASE_URL_TRAVEL="http://203.196.159.37/lab4/findme/appconnect/vacation.php?";
    public static String BASE_URL1="http://203.196.159.37/lab4/findme/appconnect/app_connect.php?";
    public static String BASE_URL_TRAVEL1="http://203.196.159.37/lab4/findme/appconnect/vacation.php?";
    public static final String TAG = APP_DATA.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static APP_DATA mInstance;




    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits());
        authCallback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // Do something with the session

            }



            @Override
            public void failure(DigitsException exception) {
                // Do something on failure
            }
        };
    }



    public AuthCallback getAuthCallback(){
        return authCallback;
    }


    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }





    public static synchronized APP_DATA getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    public int getAppVersion() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
}
