package com.ginnie.galleryapp.Activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.ginnie.galleryapp.Fragments.APP_DATA;
import com.ginnie.galleryapp.Fragments.CreateGroup;
import com.ginnie.galleryapp.Fragments.CreateGroupFragment;
import com.ginnie.galleryapp.Fragments.GroupListFragment;
import com.ginnie.galleryapp.Fragments.GroupsFragment;
import com.ginnie.galleryapp.Fragments.HomeFragment;
import com.ginnie.galleryapp.Fragments.ImageUploadFragment;
import com.ginnie.galleryapp.R;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private static final String TWITTER_KEY = "AORuJpTsqf5Fm4P61VQjsZuo4";
    private static final String TWITTER_SECRET = "Xj0wX4ZpYFOpCbkOHvZb20AcxZ4qZFrM5l3iDrpI1jnCPNtecz";
    private AuthCallback authCallback;
    boolean flage = false;
    Button image_click;
    ImageView image_show;
    TextView username;

    SharedPreferences loginPreferences;
    private static final String TAG_SUCCESS = "code";
    private static final String TAG_MESSAGE = "message";

    RelativeLayout back;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Bitmap imagebitmap = null;
    private String imagepath = null;
    Bitmap imagepic;
    String url;
    String success;
    ScrollView scrolllayout;
    DigitsAuthButton digitsButton;
    String bitmap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

         digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setCallback(((APP_DATA) getApplication()).getAuthCallback());

        Digits.authenticate(APP_DATA.authCallback, "+918583818151");


        if (APP_DATA.page_set.equals("main")){SelectItem(0);}

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            SelectItem(0);
            digitsButton.setVisibility(View.GONE);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            SelectItem(1);
        } else if (id == R.id.nav_slideshow) {
            digitsButton.setVisibility(View.GONE);
            SelectItem(2);
        } else if (id == R.id.nav_manage) {
            SelectItem(3);
            digitsButton.setVisibility(View.GONE);
        } else if (id == R.id.nav_share) {
            digitsButton.setVisibility(View.GONE);
        } else if (id == R.id.nav_send) {
            digitsButton.setVisibility(View.GONE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void SelectItem(int possition) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (possition) {
            case 0:
                fragment = new HomeFragment();

                break;
            case 1:
                fragment = new CreateGroupFragment();
                break;

            case 2:
                fragment = new GroupListFragment();
                break;
            case 3:
                fragment = new ImageUploadFragment();
                break;
            case 4:
                fragment = new ImageUploadFragment();
                break;

            default:
                break;
        }

        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.main_containt, fragment).addToBackStack(null)
                .commit();


    }

}
