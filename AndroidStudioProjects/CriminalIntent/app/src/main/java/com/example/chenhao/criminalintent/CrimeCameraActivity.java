package com.example.chenhao.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by chenhao on 4/12/2015.
 */
public class CrimeCameraActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //must be called before the activity's view is created
        //Hide the window title.This does not work,use hide() of the actionBar.
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Hide the status bar and other OS-level chrome
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
    }
}
