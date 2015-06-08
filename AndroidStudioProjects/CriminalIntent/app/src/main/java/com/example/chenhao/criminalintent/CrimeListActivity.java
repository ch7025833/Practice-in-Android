package com.example.chenhao.criminalintent;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by chenhao on 4/3/2015.
 */
public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks,CrimeFragment.Callbacks{
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detailFragmentContainer) == null){
            //phone version,start the crimePagerActivity
            Intent intent  = new Intent(this,CrimePagerActivity.class);
            intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
            startActivity(intent);
        }else {
            //tablet version,use callback,let activity to host the CrimeFragment.
            FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //remove old fragment if has one,replace with the new one.
            Fragment oldFragment = fragmentManager.findFragmentById(R.id.detailFragmentContainer);
            Fragment newFragment = CrimeFragment.newInstance(crime.getId());

            if (oldFragment != null){
                fragmentTransaction.remove(oldFragment);
            }
            fragmentTransaction.add(R.id.detailFragmentContainer,newFragment).commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CrimeListFragment listFragment = (CrimeListFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
        listFragment.updateUI();
    }
}
