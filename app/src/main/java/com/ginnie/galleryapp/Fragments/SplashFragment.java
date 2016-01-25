package com.ginnie.galleryapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ginnie.galleryapp.R;


/**
 * Created by su on 7/8/15.
 */
public class SplashFragment extends Fragment {

LinearLayout login,signup;
    public SplashFragment()
    {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_splash, container, false);

        login=(LinearLayout)view.findViewById(R.id.login);
        signup=(LinearLayout)view.findViewById(R.id.signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginFragment fragment2 = new LoginFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.main_container, fragment2);
                transaction.commit();

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(),RegestrationFragement.class);
                startActivity(i);


            }
        });

        return view;
    }
}
