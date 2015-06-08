package com.example.chenhao.criminalintent;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by chenhao on 4/3/2015.
 */
public class CrimeLab {

    private static final String TAG ="CrimeLab";
    private static final String FILENAME = "crimes.json";

    private ArrayList<Crime> mCrimes;
    private CriminalIntentJSONSerializer mSerializer;
    //s prefix is for static variable.
    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    private CrimeLab(Context appContext){
        mAppContext = appContext;
        mCrimes = new ArrayList<Crime>(100);
        mSerializer = new CriminalIntentJSONSerializer(mAppContext,FILENAME);
        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (IOException e) {
            mCrimes = new ArrayList<Crime>();
            Log.e(TAG,"Error loading files:",e);
        } catch (JSONException e) {
            //do nothing
        }
    }

    public static CrimeLab get(Context c){

        if (sCrimeLab == null){
            //CrimeLab instance should be application-wide singleton.
            //c could be any context ,so make sure CrimeLab exists as long as the application exists,use getApplicationContext().
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime c : mCrimes){
            if (id.equals(c.getId())){
                return c;
            }
        }
        return  null;
    }

    public void addCrime(Crime c){
        mCrimes.add(c);
    }

    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "crime saved to file");
            return true;
        } catch (JSONException e) {
            Log.e(TAG, "Error saving crimes(JSON problem): ", e);
            return false;
        } catch (IOException e) {
            Log.e(TAG, "Error saving crimes(IO problem): ", e);
            return false;
        }
    }

    public void deleteCrime(Crime crime){
        mCrimes.remove(crime);
    }
}
