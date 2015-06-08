package com.example.chenhao.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by chenhao on 4/3/2015.
 */
public abstract class SingleFragmentActivity extends ActionBarActivity{
    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null){
            fragment = createFragment();
            fm.beginTransaction().
                    add(R.id.fragmentContainer,fragment)
                    .commit();
        }
    }

    protected int getLayoutResId(){
        return R.layout.activity_fragment;
    }
}
