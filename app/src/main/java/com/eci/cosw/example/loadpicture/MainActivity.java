package com.eci.cosw.example.loadpicture;


import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            showFragment(new NewPostFragment(),true);
        }

    }

    @Override
    public void onBackPressed(){
        Fragment newPostFragment = new NewPostFragment();
        showFragment(newPostFragment,true);
    }

    public void showFragment( Fragment fragment, boolean addToBackStack)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        String tag = fragment.getClass().getSimpleName();
        if ( addToBackStack )
        {
            transaction.addToBackStack( tag );
        }
        transaction.replace( R.id.fragment_container, fragment , tag );
        transaction.commitAllowingStateLoss();
    }
}

